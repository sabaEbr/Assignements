@echo off
@echo "building"
cd ..
javac -d classes -sourcepath src src/com/GO/core/engine/GoEngine.java
@echo "Done - building"
