# bantads-internet-banking

## JSON-SERVER

Para rodar o servidor Json fake utilizar o comando `npx json-server db.json` no diretório `/bantads-frontend`.

Para logar basta inserir um email válido que esteja no db.json como "user" e uma senha qualquer.

[ATENCAO] Como não temos nav-bar com botão de logout, para deslogar: F12 -> armazenamento -> Limpar armazenamento de sessão do localhost

## Guard Service

O serviço de guarda foi adicionado. Para autorizar um perfil a um módulo basta adicionar o perfil em "data.role"
separando cada perfil por vírgula. Por exemplo:

```
## Autoriza role de cliente a acessar rota
data:{"role": "CLIENT"}

## Autoriza role de cliente e gerente a acessar rota
data:{"role": "CLIENT,MANAGER"}
```
## To run the application

Just execute the `start.sh` script found in the route foulder.

Or...

you can execute the command (the first time)
```
docker compose up --build
```
after that you can remove the `--build` unless you change something.

and then run the gateway separetely with `npm i` and `node src/server.js` 
