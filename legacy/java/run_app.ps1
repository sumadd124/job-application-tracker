# Quick Start script for Job Application Tracker
# This script downloads required JARs and runs the app without Maven

$LibDir = "lib"
if (!(Test-Path $LibDir)) {
    New-Item -ItemType Directory -Path $LibDir
}

# Download FlatLaf
$FlatLafUrl = "https://repo1.maven.org/maven2/com/formdev/flatlaf/3.4.1/flatlaf-3.4.1.jar"
$FlatLafJar = "$LibDir/flatlaf-3.4.1.jar"
if (!(Test-Path $FlatLafJar)) {
    Write-Host "Downloading FlatLaf..." -ForegroundColor Cyan
    Invoke-WebRequest -Uri $FlatLafUrl -OutFile $FlatLafJar
}

# Download MySQL Connector
$MySqlUrl = "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar"
$MySqlJar = "$LibDir/mysql-connector-j-8.3.0.jar"
if (!(Test-Path $MySqlJar)) {
    Write-Host "Downloading MySQL Connector..." -ForegroundColor Cyan
    Invoke-WebRequest -Uri $MySqlUrl -OutFile $MySqlJar
}

# Compile
Write-Host "Compiling project..." -ForegroundColor Green
$Sources = Get-ChildItem -Path "src/main/java" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
$Classpath = "$FlatLafJar;$MySqlJar"

javac -d bin -cp $Classpath $Sources

if ($LASTEXITCODE -eq 0) {
    Write-Host "Starting Application..." -ForegroundColor Green
    java -cp "bin;$Classpath" com.tracker.ui.MainFrame
} else {
    Write-Host "Compilation failed. Please ensure Java JDK 17+ is installed." -ForegroundColor Red
}
