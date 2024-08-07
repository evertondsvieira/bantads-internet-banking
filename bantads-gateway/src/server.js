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

// SAGA 1
// Requisição de autocadastro de cliente (Sem autenticação)

const sagaClientProxy = httpProxy('http://localhost:3005');

app.post('/register', (req, res, next) => {
    sagaClientProxy(req, res, next);
});

// Requisições aos serviços, já autenticados
  
// Requisições para transaction
const transactionServiceProxy = httpProxy('http://localhost:3010', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
        return '/transaction';
    },
});

app.post('/transaction', verifyJWT, (req, res, next) => {
    transactionServiceProxy(req, res, next);
});

// Requisições para account
const accountServiceProxy = httpProxy('http://localhost:3011', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
        return req.originalUrl;
    },
});

app.get('/account', verifyJWT, (req, res, next) => {
    accountServiceProxy(req, res, next);
});

app.get('/account/:id', verifyJWT, (req, res, next) => {
    accountServiceProxy(req, res, next);
});

app.get('/transaction/account/:id', verifyJWT, (req, res, next) => {
    accountServiceProxy(req, res, next);
});

app.get('/account/manager-report', verifyJWT, (req, res, next) => {
    accountServiceProxy(req, res, next);
});
 
app.get('/account/by-user/:userId', verifyJWT, (req, res, next) => {
    const userId = req.params.userId;

    http.get(`http://localhost:3002/api/client/${userId}`, (response) => {
        let data = ''

        response.on('data', (chunk) => {
            data += chunk
        })

        response.on('end', () => {
            const clientData = JSON.parse(data)

            const cpf = clientData.cpf

            if (!cpf) {
                return res.status(404).json({ error: 'CPF não encontrado' })
            }

            http.get(`http://localhost:3011/account/client/${cpf}`, (response) => {
                let accountData = '';

                response.on('data', (chunk) => {
                    accountData += chunk
                })

                response.on('end', () => {
                    res.setHeader('Content-Type', 'application/json')
                    res.send(accountData)
                })
            }).on('error', (err) => {
                console.error('Erro ao pegar a conta:', err)
                res.status(500).json({ error: 'Erro ao pegar a conta' })
            })
        })
    }).on('error', (err) => {
        console.error('Erro ao pegar CPF do cliente:', err)
        res.status(500).json({ error: 'Erro ao pegar cpf do cliente' })
    })
})

// Requisições para manager

// SAGAS 2 E 3
const sagaManagerProxy = httpProxy('http://localhost:3006');

// SAGA 2 - Inserção de gerente
app.post('/manager', verifyJWT, (req, res, next) => {
    sagaManagerProxy(req, res, next);
});

// SAGA 3 - Remoção de gerente
app.delete('/manager/:email', verifyJWT, (req, res, next) => {
    sagaManagerProxy(req, res, next);
});

// GET e PUT (Sem SAGA)
const managerServiceProxy = httpProxy('http://localhost:3003', {
    changeOrigin: true,
    proxyReqPathResolver: function (req) {
        return req.url.replace(/^\/manager/, '/api/manager');
    },
});

app.get('/manager', verifyJWT, (req, res, next) => {
    managerServiceProxy(req, res, next);
});

app.put('/manager/:email', verifyJWT, (req, res, next) => {
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

app.get('/client/email/:email', (req, res, next) => {
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

// SAGA 4
// Atualização de perfil de cliente

const sagaProfileUpdateProxy = httpProxy('http://localhost:3007');

app.put('/client/:id', verifyJWT, (req, res, next) => {
    sagaProfileUpdateProxy(req, res, next);
});

// Cria o servidor na porta 3000
const server = http.createServer(app);
server.listen(3000, () => {
    console.log('Servidor rodando na porta 3000');
});
