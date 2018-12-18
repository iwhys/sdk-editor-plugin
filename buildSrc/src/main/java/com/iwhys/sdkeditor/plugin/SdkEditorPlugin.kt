package com.iwhys.sdkeditor.plugin

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.ImmutableSet
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created on 24/07/2018 15:07
 * Description: SdkEditor插件
 *
 * @author 王洪胜
 */
class SdkEditorPlugin : Transform(), Plugin<Project> {

    private lateinit var project: Project

    override fun getName() = PLUGIN_NAME

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental() = false

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return if (project.isApp) TransformManager.SCOPE_FULL_PROJECT else ImmutableSet.of(QualifiedContent.Scope.PROJECT)
    }

    override fun apply(project: Project) {
        this.project = project
        val android = project.android
        android.registerTransform(this)
        SdkEditorConfig.create(project)
        project.repositories.addAll(project.rootProject.buildscript.repositories)
        project.dependencies.add("implementation", "com.iwhys.classeditor:domain:1.1.0")
    }

    override fun transform(transformInvocation: TransformInvocation) {
        val start = System.currentTimeMillis()
        log("begin to transform")
        TransformHandler(project, transformInvocation).handle()
        log("finish transform, total time:${(System.currentTimeMillis() - start)} ms")
    }

    companion object {
        const val PLUGIN_NAME = "sdkEditor"
    }
}