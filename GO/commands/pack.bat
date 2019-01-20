@echo off
cd ../classes

jar cfm ../pack/GO.jar manifest.txt com/GO/game

cd ../pack

set JAVA_RT=java-runtime

rmdir %JAVA_RT%

jlink --no-header-files --no-man-pages --compress=2 --strip-debug --add-modules j^
ava.datatransfer,java.desktop,java.logging,java.scripting,java.sql,java.xml,jdk.jsobject,jdk.unsupported,jdk.unsupported^
.desktop,jdk.xml.dom --output %JAVA_RT%
