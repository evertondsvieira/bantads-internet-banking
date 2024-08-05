#!/bin/bash

## to stop the bantads-gateway (he keeps running when docker compose down is used)
lsof -t -i tcp:3000 | xargs kill -9

## Stop the containers
docker compose stop
