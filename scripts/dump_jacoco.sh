#!/bin/bash
set -e

# 4. Coverage-Dump erzeugen
echo "Erzeuge E2E JaCoCo-Coverage-Dump..."
#curl -s "localhost:16300/dump" > /dev/null || true
java -jar ../e2e/jacococli.jar dump --address localhost --port 16300 --destfile e2e/target/jacoco-e2e.exec

