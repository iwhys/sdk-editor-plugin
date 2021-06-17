package com.iwhys.sdkeditor.plugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import javassist.ClassPool
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import java.io.File
import java.text.DateFormat
import java.util.*

/**
 * Created on 24/07/2018 14:48
 * Description: 工具扩展
 *
 * @author 王洪胜
 */

/**
 * 方法安全执行保护器,debug状态直接抛出异常
 * @param error 执行发生异常时的回调
 * @param block 被保护执行的代码块
 */
inline fun <R> safe(noinline error: (Exception.() -> Unit)? = null, block: () -> R?): R? {
    try {
        return block()
    } catch (e: Exception) {
        if (error != null) {
            error(e)
        } else {
            log("错误:${e.localizedMessage}")
            throw e
        }
    }
    return null
}

/**
 * 打印log
 */
fun log(vararg msg: Any) {
    println(Arrays.toString(msg))
}

/**
 * 项目中获取app扩展
 */
val Project.app: AppExtension
    get() = extensions.getByType(AppExtension::class.java)

/**
 * 判断项目是否app
 */
val Project.isApp: Boolean get() = android is AppExtension

/**
 * 项目中获取library扩展
 */
val Project.library: LibraryExtension
    get() = extensions.getByType(LibraryExtension::class.java)

val Project.android: BaseExtension
    get() = extensions.getByType(BaseExtension::class.java)

/**
 * 添加android项目路径到ClassPool
 */
fun ClassPool.addPathProject(project: Project) {
    project.android.bootClasspath.forEach {
        ClassPathHelper.appendClassPath(this, it.absolutePath)
    }
}

/**
 * 添加jarInput路径到ClassPool
 */
fun ClassPool.addPathJarInput(jarInput: JarInput) {
    ClassPathHelper.insertClassPath(this, jarInput.file.absolutePath)
}

/**
 * 添加dirInput路径到ClassPool
 */
fun ClassPool.addPathDirInput(dirInput: DirectoryInput) {
    ClassPathHelper.insertClassPath(this, dirInput.file.absolutePath)
}

/**
 * 从jarInput中获取输出
 */
fun TransformOutputProvider.jarOutput(jarInput: JarInput): File {
    return getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
}

/**
 * 从dirInput中获取输出
 */
fun TransformOutputProvider.dirOutput(dirInput: DirectoryInput): File {
    return getContentLocation(dirInput.name, dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)
}

/**
 * 清理ClassPool
 */
fun ClassPool.clear() {
    this.clearImportedPackages()
    ClassPathHelper.removeClassPath(this)
}

/**
 * 把文件(夹)复制到目标目录
 */
fun File.copyToDir(dest: File) {
    if (!dest.exists()) {
        dest.mkdir()
    }
    if (isDirectory) {
        FileUtils.copyDirectoryToDirectory(this, dest)
    } else {
        FileUtils.copyFileToDirectory(this, dest)
    }
}

private val date by lazy { Date() }

val currentTime: String
    get() = DateFormat.getTimeInstance().format(date.apply { time = System.currentTimeMillis() })