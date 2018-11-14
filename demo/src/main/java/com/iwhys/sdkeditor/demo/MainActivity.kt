package com.iwhys.sdkeditor.demo

import android.os.Bundle
import android.support.v4.os.BuildCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button

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
            BuildCompat.isAtLeastQ()
        }
    }

}