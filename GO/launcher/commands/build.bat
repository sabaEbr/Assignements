@echo off
@echo "building"
cd ..
javac -d classes -classpath ../production/frame.jar;../production//core.jar -sourcepath src src/com/GO/launcher/GameLauncher.java
@echo "Done - building"

