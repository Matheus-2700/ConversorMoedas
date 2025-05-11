@echo off
echo Compilando o projeto...

:: Compila os arquivos .java para a pasta 'out', usando a biblioteca Gson
javac -cp "lib\gson-2.10.1.jar" -d out src\*.java

if %errorlevel% neq 0 (
    echo Houve um erro na compilação.
    pause
    exit /b
)

echo.
echo Executando o programa...

:: Executa a classe principal com a biblioteca no classpath
java -cp "lib\gson-2.10.1.jar;out" ConversorMoedas

pause