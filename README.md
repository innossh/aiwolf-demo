# aiwolf-demo

See http://aiwolf.org/howtowagent

## Getting started

First, download the libraries of AI Wolf.

```console
wget http://aiwolf.org/control-panel/wp-content/uploads/2014/03/aiwolf-ver0.6.2.zip
unzip aiwolf-ver0.6.2.zip
mkdir lib
cp AIWolf-ver0.6.2/{aiwolf-common.jar,aiwolf-client.jar} lib/
```

Then, modify the starter script and config.

```console
chmod +x AIWolf-ver0.6.2/AutoStarter.sh
cp -a AIWolf-ver0.6.2/AutoStarter.ini{,.bak}

# Modify AutoStarter.ini ...

diff -u AIWolf-ver0.6.2/AutoStarter.ini{.bak,}
```

```diff
--- AIWolf-ver0.6.2/AutoStarter.ini.bak 2020-06-29 16:30:20.000000000 +0900
+++ AIWolf-ver0.6.2/AutoStarter.ini     2021-05-09 15:33:59.000000000 +0900
@@ -6,10 +6,11 @@
 #C#=PATH_TO_C#_CLIENT_STARTER
 setting=./SampleSetting.cfg
 #agent=5
-Sample1,java,org.aiwolf.sample.player.SampleRoleAssignPlayer,WEREWOLF
-Sample2,java,org.aiwolf.sample.player.SampleRoleAssignPlayer,SEER
+Sample1,java,org.aiwolf.sample.player.SampleRoleAssignPlayer
+Sample2,java,org.aiwolf.sample.player.SampleRoleAssignPlayer
 Sample3,java,org.aiwolf.sample.player.SampleRoleAssignPlayer
 Sample4,java,org.aiwolf.sample.player.SampleRoleAssignPlayer
-Sample5,java,org.aiwolf.sample.player.SampleRoleAssignPlayer
+#Sample5,java,org.aiwolf.sample.player.SampleRoleAssignPlayer
 #PythonPlayer,python,PATH_TO_PYTHON_PLAYER_FILE
 #C_SharpPlayer,C#,PATH_TO_PLAYER_DLL:CLASS_PATH,SEER
+Demo,java,innossh.aiwolf.demo.DemoRoleAssignPlayer
```

Next, build the demo agent.

```console
./gradlew demo:jar
cp -a demo/build/libs/demo.jar AIWolf-ver0.6.2/
```

Finally, run the starter script.

```console
cd AIWolf-ver0.6.2 && ./AutoStarter.sh
```

## Open issues

The following error occurs after the starter script is executed because the LookAndFeel class for Windows is hardcoded in the [AIWolfViewer](https://github.com/aiwolf/AIWolfViewer).

```
java.lang.ClassNotFoundException: com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:581)
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:522)
        at java.base/java.lang.Class.forName0(Native Method)
        at java.base/java.lang.Class.forName(Class.java:398)
        at java.desktop/javax.swing.SwingUtilities.loadSystemClass(SwingUtilities.java:2036)
        at java.desktop/javax.swing.UIManager.setLookAndFeel(UIManager.java:632)
        at org.aiwolf.ui.HumanPlayer.<init>(HumanPlayer.java:124)
        at org.aiwolf.ui.HumanPlayer.<init>(HumanPlayer.java:113)
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
        at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
        at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
        at java.base/java.lang.Class.newInstance(Class.java:584)
        at org.aiwolf.ui.util.AgentLibraryReader.getPlayerClassList(AgentLibraryReader.java:71)
        at org.aiwolf.ui.bin.AutoStarter.getPlayerClassMap(AutoStarter.java:606)
        at org.aiwolf.ui.bin.AutoStarter.<init>(AutoStarter.java:198)
        at org.aiwolf.ui.bin.AutoStarter.main(AutoStarter.java:103)
```
