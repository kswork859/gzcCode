@echo off
set CLASSPATH=lib\mysql-connector-j-8.3.0.jar;.
if not exist bin mkdir bin
javac -d bin -cp %CLASSPATH% src\*.java
pause
