rem jar tf MyJar.jar

dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
jar cfm MyJar.jar manifest.txt -C out .

java -jar MyJar.jar
pause