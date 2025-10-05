@echo off
:: ===============================================
:: Настройка кодировки для русского языка
:: ===============================================
chcp 1251 > nul
setlocal enabledelayedexpansion

title Сборка и развертывание Wordle приложения
echo ===============================================
echo    Maven Build and Tomcat Deploy Script
echo ===============================================
echo.

:: АВТООПРЕДЕЛЕНИЕ ПУТИ ПРОЕКТА - ИСПРАВЛЕННАЯ ВЕРСИЯ
:: Батник ищет проект в своей директории
set "BAT_DIR=%~dp0"
set "BAT_DIR=%BAT_DIR:~0,-1%"
set "PROJECT_DIR=%BAT_DIR%"

echo Директория батника: %BAT_DIR%
echo Директория проекта: %PROJECT_DIR%

:: Проверяем, что это действительно директория Maven проекта
if not exist "%PROJECT_DIR%\pom.xml" (
    echo.
    echo ОШИБКА: Файл pom.xml не найден в директории проекта!
    echo Убедитесь, что батник находится в корне проекта Maven
    echo.
    echo Содержимое директории:
    dir "%PROJECT_DIR%\*" | findstr "pom.xml"
    echo.
    pause
    exit /b 1
)

echo ✓ Maven проект найден (pom.xml присутствует)

:: Проверка Maven
echo.
echo Проверка наличия Maven в системе...
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ОШИБКА: Maven не найден в системе!
    echo Убедитесь, что Maven установлен и добавлен в PATH
    pause
    exit /b 1
)

for /f "delims=" %%I in ('where mvn') do set "MAVEN_CMD=%%I"
echo ✓ Найден Maven: %MAVEN_CMD%

:: Поиск Tomcat
echo.
echo Поиск Tomcat в системе...
set "TOMCAT_HOME="

if defined CATALINA_HOME (
    set "TOMCAT_HOME=%CATALINA_HOME%"
    echo ✓ Tomcat найден через переменную CATALINA_HOME
) else if defined TOMCAT_HOME (
    set "TOMCAT_HOME=%TOMCAT_HOME%"
    echo ✓ Tomcat найден через переменную TOMCAT_HOME
) else (
    echo Переменные CATALINA_HOME/TOMCAT_HOME не найдены
    echo Выполняю поиск Tomcat в стандартных директориях...
    
    set "TOMCAT_FOUND=0"
    for /d %%D in (
        "D:\TomCat\apache-tomcat-10.1.42"
        "C:\Program Files\Apache Tomcat*"
        "C:\Program Files\Tomcat*"
        "C:\Tomcat*"
        "D:\Tomcat*"
        "C:\apache-tomcat*"
        "D:\apache-tomcat*"
    ) do (
        if exist "%%D\bin\startup.bat" (
            set "TOMCAT_HOME=%%D"
            set "TOMCAT_FOUND=1"
            echo ✓ Найден Tomcat: %%D
            goto :tomcat_found
        )
    )
    
    if !TOMCAT_FOUND!==0 (
        echo.
        echo ВНИМАНИЕ: Tomcat не найден автоматически!
        echo.
    )
)

:tomcat_found
if "%TOMCAT_HOME%"=="" (
    echo.
    echo Пожалуйста, укажите путь к Tomcat вручную
    set /p TOMCAT_HOME="Введите полный путь к директории Tomcat: "
    
    :check_tomcat
    if not exist "%TOMCAT_HOME%\bin\startup.bat" (
        echo.
        echo ОШИБКА: Неверный путь к Tomcat!
        echo Убедитесь, что указали корневую директорию Tomcat
        echo (должна содержать папки bin, webapps, conf)
        echo.
        set /p TOMCAT_HOME="Введите правильный путь к Tomcat: "
        goto :check_tomcat
    )
)

echo.
echo ✓ Используется Tomcat: %TOMCAT_HOME%

:: Определяем имя WAR файла из pom.xml
set "WAR_NAME=wordle_02-1.0-SNAPSHOT.war"
echo ✓ WAR файл: %WAR_NAME%

echo.
echo ===============================================
echo        НАСТРОЙКИ РАЗВЕРТЫВАНИЯ
echo ===============================================
echo Проект:    %PROJECT_DIR%
echo Tomcat:    %TOMCAT_HOME%
echo Maven:     %MAVEN_CMD%
echo WAR файл:  %WAR_NAME%
echo ===============================================
echo.

echo Начинается процесс развертывания...
echo.

:: 1. Остановка Tomcat
echo [1/5] Остановка Tomcat сервера...
call :stop_tomcat

:: 2. Сборка проекта
echo [2/5] Сборка проекта Maven...
cd /d "%PROJECT_DIR%"
echo Текущая директория: %CD%
call mvn clean package -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ОШИБКА: Сборка Maven завершилась с ошибкой!
    echo Проверьте исходный код и настройки Maven
    pause
    exit /b 1
)

echo ✓ Сборка завершена успешно!

:: 3. Очистка предыдущей версии
echo [3/5] Очистка предыдущей версии приложения...
call :clean_old_deployment

:: 4. Копирование WAR файла
echo [4/5] Копирование нового WAR файла...
if not exist "target\%WAR_NAME%" (
    echo ОШИБКА: WAR файл не найден: target\%WAR_NAME%
    echo Проверьте результат сборки Maven
    dir target\
    pause
    exit /b 1
)

copy "target\%WAR_NAME%" "%TOMCAT_HOME%\webapps\" > nul
echo ✓ WAR файл успешно скопирован: %TOMCAT_HOME%\webapps\%WAR_NAME%

:: 5. Запуск Tomcat
echo [5/5] Запуск Tomcat сервера...
call :start_tomcat

echo.
echo ===============================================
echo    РАЗВЕРТЫВАНИЕ УСПЕШНО ЗАВЕРШЕНО!
echo ===============================================
echo Приложение: %WAR_NAME%
echo URL: http://localhost:8080/wordle_02-1.0-SNAPSHOT
echo Tomcat: %TOMCAT_HOME%
echo Время: %date% %time%
echo ===============================================
echo.
pause
exit /b 0

:: ===============================================
:: ФУНКЦИИ
:: ===============================================

:stop_tomcat
echo Выполняется остановка Tomcat...
if exist "%TOMCAT_HOME%\bin\shutdown.bat" (
    call "%TOMCAT_HOME%\bin\shutdown.bat" > nul 2>&1
    timeout /t 5 /nobreak > nul
    echo ✓ Tomcat успешно остановлен
) else (
    echo ВНИМАНИЕ: Файл shutdown.bat не найден
)
goto :eof

:start_tomcat
echo Запуск Tomcat сервера...
if exist "%TOMCAT_HOME%\bin\startup.bat" (
    start "Tomcat Server" "%TOMCAT_HOME%\bin\startup.bat"
    timeout /t 8 /nobreak > nul
    echo ✓ Tomcat успешно запущен
) else (
    echo ОШИБКА: Файл startup.bat не найден
)
goto :eof

:clean_old_deployment
set "APP_NAME=%WAR_NAME:.war=%"
echo Удаление старой версии приложения...

if exist "%TOMCAT_HOME%\webapps\%WAR_NAME%" (
    del "%TOMCAT_HOME%\webapps\%WAR_NAME%"
    echo ✓ Удален файл: %WAR_NAME%
)

if exist "%TOMCAT_HOME%\webapps\%APP_NAME%" (
    rmdir /s /q "%TOMCAT_HOME%\webapps\%APP_NAME%" 2>nul
    echo ✓ Удалена директория: %APP_NAME%
)

if exist "%TOMCAT_HOME%\work\Catalina\localhost\%APP_NAME%" (
    rmdir /s /q "%TOMCAT_HOME%\work\Catalina\localhost\%APP_NAME%" 2>nul
    echo ✓ Очищены рабочие файлы Tomcat
)
goto :eof