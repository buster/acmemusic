#!/usr/bin/env bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd $SCRIPTPATH/.. || exit

source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 21.0.2-tem
export SERVER_PORT=8081
export SERVER_HOST=127.0.0.1

./mvnw liquibase:dropAll liquibase:update package spring-boot:repackage -DskipTests -DskipDocker -Dspring-boot.run.jvmArguments='-Dserver.address=127.0.0.1' -pl services/acme
java -jar services/acme/target/acme-0.0.1-SNAPSHOT.jar -Dserver.address=127.0.0.1