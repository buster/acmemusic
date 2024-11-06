#!/usr/bin/env bash

source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 21.0.2-tem
cd $HOME/projects/acme || exit
export SERVER_PORT=8081
export SERVER_HOST=127.0.0.1

./mvnw liquibase:dropAll liquibase:update spring-boot:run -DskipDocker -Dspring-boot.run.jvmArguments='-Dserver.address=127.0.0.1'