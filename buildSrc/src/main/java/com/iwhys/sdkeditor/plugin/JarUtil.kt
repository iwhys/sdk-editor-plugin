package com.iwhys.sdkeditor.plugin

import com.android.SdkConstants
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Created on 24/07/2018 14:12
 * Description: Jar工具类
 *
 * @author 王洪胜
 */
object JarUtil {

    /**
     * 是否jar路径
     */
    fun isJar(path:String):Boolean {
        return path.endsWith(SdkConstants.DOT_JAR)
    }

    /**
     * 从jar文件获取临时目录
     */
    fun getJarFileTmpDir(jarFile: File): String {
        return "${jarFile.parent}${File.separator}tmp_${FilenameUtils.getBaseName(jarFile.name)}"
    }

    fun jarFile(source: File, dest: File) {
        val zipOutputStream = ZipOutputStream(FileOutputStream(dest))
        zipDirectory(source, zipOutputStream, "")
        IOUtils.closeQuietly(zipOutputStream)
    }

    /**
     * 压缩文件夹内容
     * @param sourceDir 要压缩的文件夹
     * @param zipOutputStream zip输出流
     * @param baseDir 目录
     */
    private fun zipDirectory(sourceDir: File, zipOutputStream: ZipOutputStream, baseDir: String) {
        sourceDir.listFiles().forEach { file ->
            if (file.isDirectory) {
                zipDirectory(file, zipOutputStream, baseDir + file.name + File.separator)
            } else {
                zipOutputStream.putNextEntry(ZipEntry(baseDir + file.name))
                zipOutputStream.write(file.readBytes())
                zipOutputStream.closeEntry()
            }
        }
    }
}