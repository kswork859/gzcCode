#!/bin/bash
export CLASSPATH=lib/mysql-connector-j-8.4.0.jar:bin:$CLASSPATH
java -cp "$CLASSPATH" src.Main
