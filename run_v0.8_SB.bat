@echo off
echo Building project...
call mvn clean package
if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b %errorlevel%
)

echo.
echo Starting application...
call mvn spring-boot:run

pause