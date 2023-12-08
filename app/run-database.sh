#!/usr/bin/env bash
docker run -d -p 5435:5432 --name quote_db -e POSTGRES_USER=quote_user -e POSTGRES_PASSWORD=quote_password -e POSTGRES_DB=quote_db postgres:10.5
