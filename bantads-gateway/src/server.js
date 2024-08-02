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
const cors = require('cors');

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

// Middleware CORS
app.use(cors());

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
                expiresIn: 10000000000000000000
            });
            userRes.status(200);
            userRes.setHeader('Content-Type', 'application/json');
            return JSON.stringify({ auth: true, token: token, data: objBody });
        } else {
            userRes.status(401);
            userRes.setHeader('Content-Type', 'application/json');
            return JSON.stringify({ message: 'Login/Senha inválidos!' });
        }
    }
});

function verifyJWT(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    console.log('Token recebido:', token);

    if (!token) {
        return res.status(401).json({ auth: false, message: 'Token não fornecido.' });
    }

    jwt.verify(token, process.env.SECRET, function (err, decoded) {
        if (err) {
            console.error('Erro ao verificar token:', err);
            return res.status(500).json({ auth: false, message: 'Falha ao autenticar o token.' });
        }

        req.userLogin = decoded.login;
        req.userRole = decoded.role;
        console.log('Usuário autenticado:', req.userLogin, req.userRole);
        next();
    });
}

function verifyRole(requiredRoles) {
    return function (req, res, next) {
        console.log('Papel do usuário:', req.userRole);
        if (requiredRoles.includes(req.userRole)) {
            next();
        } else {
            console.log('Acesso negado para o papel:', req.userRole);
            return res.status(403).json({ auth: false, message: 'Acesso negado.' });
        }
    }
}

app.post('/login', (req, res, next) => {
    authServiceProxy(req, res, next);
});

app.post('/logout', function (req, res) {
    res.json({ auth: false, token: null });
});

const sagaClientProxy = httpProxy('http://localhost:3005');

// Requisição de autocadastro de cliente (Sem autenticação)
app.post('/register', (req, res, next) => {
    sagaClientProxy(req, res, next);
});

const sagaManagerProxy = httpProxy('http://localhost:3006');

// Requisição de inserção de gerente
app.post('/manager', (req, res, next) => {
    sagaManagerProxy(req, res, next);
});

app.delete('/manager/:email', (req, res, next) => {
    sagaManagerProxy(req, res, next);
});

// Requisições aos serviços, já autenticados
  
// Requisições para account

const accountServiceProxy = httpProxy('http://localhost:3001', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
        // Modifica o caminho da requisição para /
        return '/';
    },
});

app.get('/account', verifyJWT, (req, res, next) => {
    accountServiceProxy(req, res, next);
});
  
// Requisições para manager

const managerServiceProxy = httpProxy('http://localhost:3003', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
        // Modifica o caminho da requisição para /
        return '/';
    },
});

app.get('/manager', verifyJWT, (req, res, next) => {
    managerServiceProxy(req, res, next);
});

// Requisições para o cliente
const clientPathRewriteMap = {
    '/client/name': '/api/client/name',
    '/client/cpf': '/api/client/cpf',
    '/client/situation': '/api/client/situation'
};

const clientServiceProxy = httpProxy('http://localhost:3002', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
        const path = Object.keys(clientPathRewriteMap).find(key => req.url.startsWith(key));
        return path ? `${clientPathRewriteMap[path]}${req.url.replace(path, '')}` 
        : req.url.replace(/^\/client/, '/api/client');
    },
});

app.get('/client', verifyJWT, (req, res, next) => {
    clientServiceProxy(req, res, next);
});

app.get('/client/:id', verifyJWT, (req, res, next) => {
    clientServiceProxy(req, res, next);
});

app.get('/client/email/:email', verifyJWT, (req, res, next) => {
    clientServiceProxy(req, res, next);
});

app.get('/client/cpf/:cpf', verifyJWT, (req, res, next) => {
    clientServiceProxy(req, res, next);
});

app.get('/client/search/:search', verifyJWT, (req, res, next) => {
    clientServiceProxy(req, res, next);
});

app.post('/client', verifyJWT, (req, res, next) => {
    clientServiceProxy(req, res, next);
});

app.put('/client/:id', verifyJWT, (req, res, next) => {
    clientServiceProxy(req, res, next);
});

// Cria o servidor na porta 3000
const server = http.createServer(app);
server.listen(3000, () => {
    console.log('Servidor rodando na porta 3000');
});
