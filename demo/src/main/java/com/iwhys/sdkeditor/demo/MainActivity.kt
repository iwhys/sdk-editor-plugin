package com.iwhys.sdkeditor.demo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat

/**
 * Created on 2018/11/8 15:15
 * Description:
 *
 * @author 王洪胜
 */
class MainActivity: AppCompatActivity() {

    /**
     * 点击按钮之后将会调用[BuildCompat.isAtLeastQ]方法，并弹出我们替换之后的Toast
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            BuildCompat.isAtLeastR()
        }
    }

}