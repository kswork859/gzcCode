@echo off
set CLASSPATH = lib\mysql-connector-j-8.4.0.jar;bin
if not exist bin mkdir bin
java -cp %CLASSPATH% Main
pause