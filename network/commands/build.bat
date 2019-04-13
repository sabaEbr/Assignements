@echo off
@echo "building"
cd ..
javac -d classes -sourcepath src src/com/GO/network/GoNetwork.java
@echo "Done - building"

pause