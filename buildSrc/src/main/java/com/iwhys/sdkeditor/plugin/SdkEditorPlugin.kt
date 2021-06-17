package com.iwhys.sdkeditor.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.ImmutableSet
import javassist.ClassPool
import javassist.CtMethod
import javassist.bytecode.AnnotationsAttribute
import javassist.bytecode.annotation.Annotation
import javassist.bytecode.annotation.EnumMemberValue
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * Created on 24/07/2018 15:07
 * Description: SdkEditor插件
 *
 * @author 王洪胜
 */
class SdkEditorPlugin : Transform(), Plugin<Project> {

    private lateinit var project: Project

    private val classPool by lazy {
        ClassPool(true).apply {
            addPathProject(project)
            importPackage(ReplaceClass::class.java.getPackage().name)
        }
    }

    override fun getName() = PLUGIN_NAME

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental() = true

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return if (project.isApp) TransformManager.SCOPE_FULL_PROJECT else ImmutableSet.of(
            QualifiedContent.Scope.PROJECT
        )
    }

    private fun Project.generateReplaceClass() {
        val libsDir = "${buildDir.parent}/libs"
        val tmpDir = File(libsDir, "tmp")
        val destFile = File(libsDir, "sdk-editor-plugin.jar")
        if (destFile.exists()) {
            log("已经存在ReplaceClass的jar，不再生成")
            return
        }

        log("开始生成ReplaceClass注解类")
        val ctClass = classPool.makeAnnotation(ReplaceClass::class.java.name)
        val ctMethod = CtMethod(classPool.get(String::class.java.name), "value", null, ctClass)
        ctClass.addMethod(ctMethod)
        val cFile = ctClass.classFile
        val cPool = cFile.constPool
        val annotationAttributes = AnnotationsAttribute(cPool, AnnotationsAttribute.invisibleTag)
        val targetAnnotation = Annotation("java.lang.annotation.Target", cPool)
        targetAnnotation.addMemberValue("value", EnumMemberValue(cPool).apply {
            type = "java.lang.annotation.ElementType"
            value = "TYPE"
        })
        val retainAnnotation = Annotation("java.lang.annotation.Retention", cPool)
        retainAnnotation.addMemberValue("value", EnumMemberValue(cPool).apply {
            type = "java.lang.annotation.RetentionPolicy"
            value = "CLASS"
        })
        annotationAttributes.addAnnotation(retainAnnotation)
        annotationAttributes.addAnnotation(targetAnnotation)
        cFile.addAttribute(annotationAttributes)

        log("生成ReplaceClass:$libsDir")
        ctClass.writeFile(tmpDir.absolutePath)
        ctClass.detach()

        log("开始打包生成的ReplaceClass为jar")
        JarUtil.jarFile(tmpDir, destFile)
        tmpDir.deleteRecursively()

        log("开始添加ReplaceClass依赖")
        repositories.flatDir {
            it.dir(libsDir)
        }
        dependencies.add(
            "implementation",
            mapOf("name" to destFile.nameWithoutExtension, "ext" to destFile.extension)
        )
    }

    override fun apply(project: Project) {
        this.project = project
        val android = project.android
        android.registerTransform(this)
        SdkEditorConfig.create(project)
        project.generateReplaceClass()
    }

    override fun transform(transformInvocation: TransformInvocation) {
        val start = System.currentTimeMillis()
        log("begin to transform")
        TransformHandler(classPool, SdkEditorConfig[project], transformInvocation).handle()
        log("finish transform, total time:${(System.currentTimeMillis() - start)} ms")
    }

    companion object {
        const val PLUGIN_NAME = "sdkEditor"
    }
}