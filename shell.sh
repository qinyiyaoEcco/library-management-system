#!/bin/bash

echo "Cleaning Workspace"
./gradlew -q clean

echo "Building Jar"
ARTIFACT_PATH="$(./gradlew -q bootJar)"

java -jar "$ARTIFACT_PATH" shell