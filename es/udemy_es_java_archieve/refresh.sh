#!/bin/bash

RUNNING_CONTAINER_ID=$(docker ps | grep 'spring-boot-es-udemy' | awk '{ print $1 }')
if [ ! -z "$RUNNING_CONTAINER_ID" ]
then
  docker stop $RUNNING_CONTAINER_ID
fi

./gradlew build
mkdir -p build/dependency && rm -rf build/dependency/* && (cd build/dependency; for f in ../libs/*; do jar -xf $f; done)
docker build -t springio/spring-boot-es-udemy .
docker-compose up -d --build
