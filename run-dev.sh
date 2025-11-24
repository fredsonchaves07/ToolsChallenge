#!/bin/bash

echo "Loading environment variables..."
if [ -f .env ]; then
    export $(grep -v '^#' .env | xargs)
fi

echo "Building project..."
mvn -T 3C install -DskipTests

echo "Running application..."
mvn quarkus:dev -Dquarkus.analytics.disabled=true