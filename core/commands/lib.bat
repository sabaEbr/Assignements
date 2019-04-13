@echo off
cd ../classes

jar --verbose --create --file "../../production/core.jar" .

pause
