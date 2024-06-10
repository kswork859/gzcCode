#!/bin/bash
export CLASSPATH=lib/mysql-connector-j-8.4.0.jar:.
mkdir -p bin
javac -d bin -cp "$CLASSPATH" src/*.java
