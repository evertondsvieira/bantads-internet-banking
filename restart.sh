#!/bin/bash

cd bantads-gateway/

npm i

npm run dev &

cd ..

docker compose up
