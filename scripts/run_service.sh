#!/usr/bin/env bash

SCRIPTPATH="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd $SCRIPTPATH/.. || exit

source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 21.0.2-tem
export SERVER_PORT=8081
export SERVER_HOST=127.0.0.1
JACOCO_AGENT_PATH="e2e/jacocoagent.jar"
JACOCO_AGENT_OPTS="output=tcpserver,port=16300,includes=de.acme.*"

./mvnw liquibase:dropAll liquibase:update package spring-boot:repackage -DskipTests -DskipDocker -Dspring-boot.run.jvmArguments='-Dserver.address=127.0.0.1' -pl services/acme
java -javaagent:${JACOCO_AGENT_PATH}=${JACOCO_AGENT_OPTS} -jar services/acme/target/acme-0.0.1-SNAPSHOT.jar -Dserver.address=127.0.0.1 &> /dev/null &
SERVICE_PID=$!
echo "Service PID: $SERVICE_PID"
echo "Warte auf Service-Startup..."
timeout 22 sh -c 'until nc -z $0 $1; do sleep 1; done' $SERVER_HOST $SERVER_PORT
echo "Service gestartet."