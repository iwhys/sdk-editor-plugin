package com.iwhys.sdkeditor.plugin

import org.gradle.api.Project

/**
 * Created on 2018/9/28 09:31
 * Description: SdkEditor插件的配置
 *
 * @author 王洪胜
 */
open class SdkEditorConfig {

    /**
     * 是否开启并行处理任务，false单线程处理，true使用协程多线程处理任务
     */
    var parallel: Boolean = false

    /**
     * 收集信息时需要被额外检查的jar的名称，即包括Fix类的jar包
     */
    @Deprecated(message = "Use fixedJarNames instead", replaceWith = ReplaceWith("fixedJarNames"))
    var extraJarNames: Set<String>? = null

    /**
     * Alias for [extraJarNames]
     */
    var fixedJarNames: Set<String>? = null

    fun fixedJarNamesSet() = extraJarNames ?: fixedJarNames

    companion object {
        fun create(project: Project): SdkEditorConfig = project.extensions.create(SdkEditorPlugin.PLUGIN_NAME, SdkEditorConfig::class.java)

        operator fun get(project: Project): SdkEditorConfig = (project.extensions.getByName(SdkEditorPlugin.PLUGIN_NAME) as? SdkEditorConfig) ?: SdkEditorConfig()
    }
}