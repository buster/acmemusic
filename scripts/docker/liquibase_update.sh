#!/usr/bin/env bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd $SCRIPTPATH/../.. || exit


docker run --network host -v ${PWD}/src/main/resources/db/changelog:/liquibase/changelog liquibase --driver=org.postgresql.Driver --url="jdbc:postgresql://127.0.0.1:5432/acmedb" --changeLogFile=changelog/db.changelog-master.xml --username=acme --password=acme  update