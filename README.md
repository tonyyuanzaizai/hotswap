1. 设置classpath
set CLASSPATH=.;D:\Git2\server_git\server\HotSwapTest\lib\commons-lang-2.4.jar;D:\Git2\server_git\server\HotSwapTest\lib\commons-io-1.4.jar;D:\Git2\server_git\server\HotSwapTest\lib\slf4j-api-1.7.6.jar;E:\java\jdk1.8.0_92\lib\tools.jar;D:\Git2\server_git\server\HotSwapTest\lib\logback-classic-1.1.2.jar;D:\Git2\server_git\server\HotSwapTest\lib\logback-core-1.1.2.jar


2. 运行 
java MainServer
java MainClient


3. 错误一：java.lang.NoClassDefFoundError: com/sun/tools/attach/AttachNotSupportedException
解决： CLASSPATH 里务必需要加入 %JAVA_HOME%\lib\tools.jar。 
有网友说 windows和linux的tools 不一样，我解压看确实不一样，所以tools.jar 不要拷贝，要直接引用JAVA_HOME下对应的

4. 错误二：java.util.ServiceConfigurationError: com.sun.tools.attach.spi.AttachProvider: Provider sun.tools.attach.WindowsAttachProvider could not be instantiated com.sun.tools.attach.AttachNotSupportedException: no providers installed
解决：把 %JAVA_HOME%/jre/bin 下面的attach.dll 拷贝到 %JAVA_HOME%/bin
说明需要安装jdk1.8.0_152， jdk1.8.0_92 貌似还不支持，因为/jre/bin目录下没有attach.dll

5. agent jar的生成 需要配置 MANIFEST.MF @http://blog.csdn.net/aitangyong/article/details/39013219
Manifest-Version: 1.0
Premain-Class: com.tony.zaizai.agent.ZaiZaiHotSwapAgent
Agent-Class: com.tony.zaizai.agent.ZaiZaiHotSwapAgent
Can-Redefine-Classes: true
Can-Retransform-Classes: true

热更测试：
可以热更
1. public 非静态方法  方法内部修改 修改生效
2. public 非静态属性               修改生效
3. public 静态方法    方法内部修改 修改生效

不能热更
1. public static 属性修改无效
2. 不能删除方法
java.lang.UnsupportedOperationException: class redefinition failed: attempted to delete a method
3. 不能增加方法
java.lang.UnsupportedOperationException: class redefinition failed: attempted to add a method
