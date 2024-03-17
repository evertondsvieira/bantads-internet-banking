# bantads-internet-banking

## JSON-SERVER

Para rodar o servidor Json fake utilizar o comando `npx json-server db.json` no diretório `/bantads-frontend`.

Para logar basta inserir um email válido que esteja no db.json como "user" e uma senha qualquer.

## Guard Service

O serviço de guarda foi adicionado. Para autorizar um perfil a um módulo basta adicionar o perfil em "data.role"
separando cada perfil por vírgula. Por exemplo:

```
## Autoriza role de cliente a acessar rota
data:{"role": "CLIENT"}

## Autoriza role de cliente e gerente a acessar rota
data:{"role": "CLIENT,MANAGER"}
```
