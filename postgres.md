## Postgres

Baixar imagens do posgres e PGAdmin
```sh
docker pull postgres && 
docker pull dpage/pgadmin4
```

Criar uma rede de containeres para o postgres e pgadmin

```sh
docker network create --driver bridge postgres-network
```

Criar container para o Postgres
```sh
docker run --name bantads-postgres --network=postgres-network -e 
POSTGRES_PASSWORD=bantads -p 5432:5432 -d postgres
```

Criar cpntainer para o PGAdmin
```sh
docker run --name bantads-pgadmin --network=postgres-network -e 
PGADMIN_DEFAULT_EMAIL=bantads@bantads.br -e 
PGADMIN_DEFAULT_PASSWORD=bantads -p 15432:80 -d dpage/pgadmin4
```