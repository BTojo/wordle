@echo off
setlocal
cd /d %~dp0
java -Dfile.encoding=UTF-8 -jar target/wordle_02-1.0-SNAPSHOT.jar
pause