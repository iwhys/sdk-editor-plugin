## sdk-editor原理分析
#### 1. 利用原SDK的环境编译Fix类
我们要对一个三方SDK中已经被编译的Bug类进行修复，最直观的解决方案就是修复这个Bug类的问题代码，并用Fix类替换Bug类。但是SDK中的Bug类往往对包中的其他类存在关联依赖，导致我们无法正常编译Fix类。

这个问题怎么解决呢？

1）大神方案：修改Smali代码，不需要编译，直接重打包；

2）老鸟方案：mock缺少的依赖，完成Fix类编译；

3）菜鸟方案：投机取巧；

方案1，2都需要有足够的内功修炼，对我农实在不够友好，而且如果碰到特别复杂的逻辑，大神吐三天血都不一定能够搞定。sdk-editor采用了投机取巧的方案3，想方设法的利用原有的SDK环境及正常的APK打包流程来完成Fix类的编译。
对Fix类与Bug类<font color="red">**同包名同类名**</font>的设定，完美的解决了Fix类对包中的其他类存在关联依赖的问题。

#### 2. 在编译过程中用Fix类替换Bug类
类编译问题解决了，如何完成Fix类到Bug类的替换呢？（缺了替换操作，Android打包会因为类重复而编译失败）

Android打包插件的Transform API给我们带来了无限的想象力。先大概说下打包过程中transform有什么奇特之处：打包插件用java和kotlin编译器把所有的源文件编译为class，然后把编译后的class和所有被依赖jar/aar包中的class集合到一起，在最终把class文件转变为dex文件之前我们一个修改所有class的机会，这个机会就是transform。transform插件可以注册多个，所有transform按照注册时间排成流水线，第一个transform会被输入所有的class，处理后输出的class会被继续输入的下一个transform，依次类推，一直到打包插件规定的dex流程（其实dex和混淆也是是用transform实现的）。sdk-editor的transform过程借助强大的[javassist](https://github.com/jboss-javassist/javassist)工具来对类进行处理，具体处理流程如下：

1）收集信息：从class文件中收集被标注了@ReplaceClass的类及其标注的SDK名称；

2）完成替换：遍历所有jar包，找到名字为被标注要修改的SDK，解压jar包并遍历类，遇到被Fix的Bug类直接删除，最终完成SDK的修复工作；