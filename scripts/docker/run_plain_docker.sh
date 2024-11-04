#!/usr/bin/env bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd $SCRIPTPATH/../.. || exit

docker run -d --rm --env-file .env -p 127.0.0.1:5432:5432 postgres:latest