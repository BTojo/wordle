mkdir out
for /r src %%f in (*.java) do @echo %%f >> sources.txt
javac -cp "lib/*" -d out @sources.txt
jar cfm Wordle.jar manifest.txt -C out . -C lib .
pause
