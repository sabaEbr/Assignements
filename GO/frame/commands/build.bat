@echo off
@echo "building"
cd ..
javac -d classes -classpath ../core/artifacts/core.jar -sourcepath src src/com/GO/frame/window.java

@echo "Done - building"
