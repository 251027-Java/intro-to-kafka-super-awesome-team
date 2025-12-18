# Manual Compilation and Execution

Since `run.ps1` has been removed, you can use the following commands to compile and run the application manually in PowerShell.

## 1. Create Output Directory
```powershell
if (-not (Test-Path "target\classes")) { New-Item -ItemType Directory -Path "target\classes" -Force }
```

## 2. Compile
```powershell
javac -d target\classes -cp "$env:USERPROFILE\.m2\repository\org\apache\kafka\kafka-clients\3.5.1\kafka-clients-3.5.1.jar;$env:USERPROFILE\.m2\repository\org\slf4j\slf4j-api\2.0.7\slf4j-api-2.0.7.jar;$env:USERPROFILE\.m2\repository\org\slf4j\slf4j-simple\2.0.7\slf4j-simple-2.0.7.jar;$env:USERPROFILE\.m2\repository\com\github\luben\zstd-jni\1.5.5-1\zstd-jni-1.5.5-1.jar;$env:USERPROFILE\.m2\repository\org\lz4\lz4-java\1.8.0\lz4-java-1.8.0.jar;$env:USERPROFILE\.m2\repository\org\xerial\snappy\snappy-java\1.1.10.1\snappy-java-1.1.10.1.jar" src\main\java\com\revature\lab\*.java
```

## 3. Run
```powershell
java -cp "target\classes;$env:USERPROFILE\.m2\repository\org\apache\kafka\kafka-clients\3.5.1\kafka-clients-3.5.1.jar;$env:USERPROFILE\.m2\repository\org\slf4j\slf4j-api\2.0.7\slf4j-api-2.0.7.jar;$env:USERPROFILE\.m2\repository\org\slf4j\slf4j-simple\2.0.7\slf4j-simple-2.0.7.jar;$env:USERPROFILE\.m2\repository\com\github\luben\zstd-jni\1.5.5-1\zstd-jni-1.5.5-1.jar;$env:USERPROFILE\.m2\repository\org\lz4\lz4-java\1.8.0\lz4-java-1.8.0.jar;$env:USERPROFILE\.m2\repository\org\xerial\snappy\snappy-java\1.1.10.1\snappy-java-1.1.10.1.jar" com.revature.lab.ChatApp
```
