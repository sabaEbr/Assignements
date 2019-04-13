@echo off
@echo "building"
cd ..
javac -d classes -classpath ../production/json-simple-1.1.1.jar;../production/network.jar -sourcepath src src/com/GO/core/manager/GoEngManager.java
@echo "Done - building"
pause