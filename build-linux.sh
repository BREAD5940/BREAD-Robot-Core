#!/bin/bash
cd bin
jar -cvf ~/wpilib/user/java/lib/BREAD-Robot-Core.jar *
read -n1 -r -p "Press any key to continue..." key
