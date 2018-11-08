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
     * 收集信息时需要被额外检查的jar的名称
     */
    var extraJarNames: Array<String>? = null

    companion object {
        fun create(project: Project): SdkEditorConfig = project.extensions.create(SdkEditorPlugin.PLUGIN_NAME, SdkEditorConfig::class.java)

        operator fun get(project: Project): SdkEditorConfig = (project.extensions.getByName(SdkEditorPlugin.PLUGIN_NAME) as? SdkEditorConfig) ?: SdkEditorConfig()
    }
}