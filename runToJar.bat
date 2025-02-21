dir /s /b src\*.java > sources.txt
javac -d ./out/ @sources.txt
java -cp out main.Main
jar cfm MyJar.jar manifest.txt -C out .
pause