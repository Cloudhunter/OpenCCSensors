OpenCCSensors
=============

An open source sensor peripheral for ComputerCraft

Official thread: http://www.computercraft.info/forums2/index.php?/topic/5996-146-cc-148-openccsensors/

Starting development
==========
https://github.com/Cloudhunter/OpenCCSensors/wiki/Getting-started-Dev

Compiling
=======

OpenCCSensors now uses the ForgeGradle build system.  These instructions assume you've set up an OpenCCSensors directory and have downloaded the correct ForgeGradle package (recommended version for the current Minecraft version) and copied these directories and files into it:

eclipse/ (if you are using eclipse as your IDE)
gradle/
src/
gradlew
gradlew.bat

Run: git clone git://github.com/Cloudhunter/OpenCCSensors && cd OpenCCSensors

It should download the repository and cd into the repository.

Next, run the following command: gradlew setupDecompWorkspace

It should find the build.gradle and start downloading Forge and MCP, as well as decompiling Minecraft.

Next, if you are using eclipse, run: gradlew eclipse

Now you will need the various JAR files that OpenCCSensors depends on.  These must be placed in libs/.

Mod Name               File Name/Mod Version
Ars Magica 2:          1.7.10_AM2-1.4.0.008.jar
Applied Energistics 2: appliedenergistics2-rv1-stable-1.jar
CoFH Core:             CoFHCore-[1.7.10]3.0.2-262.jar
ComputerCraft:         ComputerCraft1.73.jar
Forestry:              forestry_1.7.10-3.5.4.13.jar
IndustrialCraft 2:     industrialcraft-2-2.2.718-experimental-api.jar
Railcraft:             Railcraft_1.7.10-9.6.1.0.jar
Resonant Engine:       resonant-engine-3.3.0.437-core.jar
RotaryCraft:           RotaryCraft 1.7.10 V6f.jar
Thaumcraft:            Thaumcraft-1.7.10-4.2.3.5.jar

Finally, run: gradlew build

It will start compiling the build and then after a few minutes there should be a file called 'OpenCCSensors-VERSION.jar' in build/libs. That is the compiled version of OpenCCSensors. After making changes in the source, building a new version only requires that you repeat the `gradlew build` command.

License
=======

OpenCCSensors is distrubuted under the terms of the MIT License, which can be found under the name LICENSE in your distribution.


