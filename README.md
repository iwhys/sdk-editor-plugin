## 简介
sdk-editor是为实现修改APP依赖的第三方SDK而开发的Gradle插件，插件利用Android Plugin官方提供的Transform API干预APK Build流程，实现对三方SDK中特定类的替换修改，不影响APP运行性能，也不会增加APK体积。
## 适用场景
1）修复SDK中存在的Bug；

2）暴露出SDK某些未提供的接口；

3）扩展SDK功能；

4）其他需要修改SDK已有类的需求；
## 基本用法
#### 1. 在根项目（最外层）的build.gradle文件中添加插件依赖：
```gradle
buildscript {
    dependencies {
        classpath 'com.iwhys.sdkeditor:plugin:1.1.0'
    }
}
```
#### 2. 在项目主模块（app module）的build.gradle文件应用插件：
```gradle
apply plugin: 'sdk-editor'
```
#### 3. 找到三方SDK中需要修改的类文件（以下称为Bug类），在app module中新建与Bug类同包名同类名的新类（以下称为Fix类），同时拷贝Bug类的内容到Fix类，给Fix类添加类注解@ReplaceClass，在注解的值中标记该类所在SDK的名字，最后在Fix类中实现要修改的内容即可。

下面以demo module中替换support v4包中的BuildCompat类为例进行说明，我们需要BuildCompat类中的isAtLeastQ方法，在其中添加一条Toast语句，修改流程如下：

1）在demo工程的main/java下新建android/support/v4/os/BuildCompat类；

2）拷贝原SDK中BuildCompat类的内容，并修改新建的BuildCompat类；
```java
@ReplaceClass("com.android.support:support-compat:28.0.0")
public class BuildCompat {
    ...
    public static boolean isAtLeastQ() {
        // 注意：这里增加了我们自定义的代码
        Toast.makeText(MyAppKt.getAppContext(), "you have invoked the method: BuildCompat#isAtLeastQ()", Toast.LENGTH_LONG).show();
        return VERSION.CODENAME.length() == 1 && VERSION.CODENAME.charAt(0) >= 'Q' && VERSION.CODENAME.charAt(0) <= 'Z';
    }
}
```
3）编译并运行demo，点击按钮弹出一个Toast，即表明support v4包中的BuildCompat类被成功的修改；
## 高级用法
[sdk-editor高级用法](高级用法.md)
## 常见问题
[sdk-editor常见问题](常见问题.md)
## 项目简析
[sdk-editor结构及原理简析](项目简析.md)
## 特别感谢
[javassist](https://github.com/jboss-javassist/javassist)
## 协议
[The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)