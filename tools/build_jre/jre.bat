@echo "Building JRE..."

cd ../../production

set JAVA_RT=jre

rmdir %JAVA_RT% /S /Q

jlink --no-header-files --no-man-pages --compress=2 --strip-debug --add-modules j^
ava.datatransfer,java.desktop,java.logging,java.scripting,java.sql,java.xml,jdk.jsobject,jdk.unsupported,jdk.unsupported^
.desktop,jdk.xml.dom --output %JAVA_RT%

@echo "Done building JRE!"
pause
