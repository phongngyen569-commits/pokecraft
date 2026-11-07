@echo off
where gradle >nul 2>nul || (echo Install Gradle 8.8+ and JDK 21 first & pause & exit /b 1)
gradle build
echo Jar is in build\libs\pokemoncraft-1.0.0.jar
pause
