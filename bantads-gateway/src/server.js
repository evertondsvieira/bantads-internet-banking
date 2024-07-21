require("dotenv-safe").config();
const jwt = require('jsonwebtoken');
const http = require('http');
const express = require('express');
const httpProxy = require('express-http-proxy');
const app = express();
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const logger = require('morgan');
const helmet = require('helmet');

// Middleware de segurança
app.use(helmet());

// Logger para desenvolvimento
app.use(logger('dev'));

// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }));

// parse application/json
app.use(bodyParser.json());

// Cookie parser
app.use(cookieParser());

const authServiceProxy = httpProxy('http://localhost:3004', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
    // Modifica o caminho da requisição para /api/v1/auth/login
    return '/api/v1/auth/login';
    },
    proxyReqBodyDecorator: function(bodyContent, srcReq) {
        try {
            retBody = {};
            retBody.login = bodyContent.email;
            retBody.password = bodyContent.senha;
            bodyContent = retBody;
        }
        catch(e) {
            console.log('- ERRO: ' + e);
        }
        return bodyContent;
    },
    proxyReqOptDecorator: function(proxyReqOpts, srcReq) {
        proxyReqOpts.headers['Content-Type'] = 'application/json';
        proxyReqOpts.method = 'POST';
        return proxyReqOpts;
    },
    userResDecorator: function(proxyRes, proxyResData, userReq, userRes) {
        if (proxyRes.statusCode == 200) {
            var str = Buffer.from(proxyResData).toString('utf-8');
            var objBody = JSON.parse(str);
            const login = objBody.user.login;
            const role = objBody.user.role;
            const token = jwt.sign({ login, role }, process.env.SECRET, {
                expiresIn: 300 //expira em 5 min
            });
            userRes.status(200);
            return { auth: true, token: token, data: objBody };
        }
        else {
            userRes.status(401);
            return {message: 'Login/Senha inválidos!'};
        }
    }
});

function verifyJWT(req, res, next) {
  const token = req.headers['x-access-token'];
  if (!token) {
      return res.status(401).json({ auth: false, message: 'Token não fornecido.' });
  }

  jwt.verify(token, process.env.SECRET, function(err, decoded) {
      if (err) {
          return res.status(500).json({ auth: false, message: 'Falha ao autenticar o token.' });
      }

      // se tudo estiver ok, salva no request para uso posterior
      req.userLogin = decoded.login;
      req.userRole = decoded.role;
      next();
  });
}

function verifyRole(requiredRoles) {
  return function(req, res, next) {
      if (requiredRoles.includes(req.userRole)) {
          next();
      } else {
          return res.status(403).json({ auth: false, message: 'Acesso negado.' });
      }
  }
}

app.post('/login', (req, res, next) => {
    authServiceProxy(req, res, next);   
});

app.post('/logout', function(req, res) {
    res.json({ auth: false, token: null });
});

const accountServiceProxy = httpProxy('http://localhost:3001', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
    // Modifica o caminho da requisição para /
    return '/';
    },
});

const clientServiceProxy = httpProxy('http://localhost:3002', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
    // Modifica o caminho da requisição para /
    return '/';
    },
});

const managerServiceProxy = httpProxy('http://localhost:3003', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
    // Modifica o caminho da requisição para /
    return '/';
    },
});

const sagasServiceProxy = httpProxy('http://localhost:3005');

// Requisições aos serviços, já autenticados
app.get('/account', verifyJWT, (req, res, next) => {
    accountServiceProxy(req, res, next);
});

app.get('/client', verifyJWT, (req, res, next) => {
    clientServiceProxy(req, res, next);
});

app.get('/manager', verifyJWT, verifyRole(['ADMIN', 'MANAGER']), (req, res, next) => {
    managerServiceProxy(req, res, next);
});

// Cria o servidor na porta 3000
const server = http.createServer(app);
server.listen(3000, () => {
    console.log('Servidor rodando na porta 3000');
});