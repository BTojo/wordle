@echo off
setlocal

cd /d %~dp0

echo === Запуск ===
java -jar Wordle.jar
pause