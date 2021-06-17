package com.iwhys.sdkeditor.demo

import android.app.Application
import android.content.Context
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created on 2018/11/14 13:37
 * Description: The app global context
 *
 * @author 王洪胜
 */
class MyApp : Application() {

    override fun attachBaseContext(base: Context?) {
        AppDelegate.application = this
        super.attachBaseContext(base)
    }
}

val appContext by AppDelegate

private object AppDelegate : ReadOnlyProperty<Any?, MyApp> {
    lateinit var application: MyApp
    override fun getValue(thisRef: Any?, property: KProperty<*>): MyApp = application
}