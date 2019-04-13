@echo off
cd ../classes

jar --verbose --create --manifest manifest.txt --file "../../production/launcher.jar" .

pause
