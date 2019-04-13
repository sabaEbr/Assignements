@echo off
cd ../classes

jar --verbose --create --file "../../production/frame.jar" .

pause
