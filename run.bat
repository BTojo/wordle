rem jar tf MyJar.jar
rmdir /s /q out
pause

dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
jar cfm MyJar.jar manifest.txt -C out .

java -jar MyJar.jar
pause