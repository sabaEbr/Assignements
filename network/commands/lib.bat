@echo off
cd ../classes

jar --verbose --create --file "../../production/network.jar" .

pause
