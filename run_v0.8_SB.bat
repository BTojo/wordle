@echo off
echo [%date% %time%] Starting build...
call mvn clean package
if %errorlevel% neq 0 (
    echo [%date% %time%] Build failed!
    pause
    exit /b %errorlevel%
)

echo.
echo [%date% %time%] Build successful! Starting application...
call mvn spring-boot:run

pause