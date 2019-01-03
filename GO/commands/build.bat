@echo off
@echo "building"
cd ..
javac -d classes -sourcepath src src/com/GO/game/GameLauncher.java
@echo "Done - building"