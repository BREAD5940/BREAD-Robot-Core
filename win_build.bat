set /p JDKDIR=<jdkbindir.txt
cd bin
"%JDKDIR%\jar" cvf "%UserProfile%\wpilib\user\java\lib\BREAD-Robot-Core.jar" *
pause