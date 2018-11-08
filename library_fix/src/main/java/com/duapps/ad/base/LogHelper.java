//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.duapps.ad.base;

import android.util.Log;

import com.iwhys.classeditor.domain.ReplaceClass;

@ReplaceClass("DuappsAd-HW-v1.1.1.6-release")
public class LogHelper {
    public LogHelper() {
    }

    public static boolean isLogEnabled() {
        return false;
    }

    public static void i(String var0, String var1) {
        Log.i(var0, var1);
    }

    public static void i(String var0, String var1, Throwable var2) {
        Log.i(var0, var1, var2);
    }

    public static void w(String var0, String var1) {
        Log.w(var0, var1);
    }

    public static void w(String var0, String var1, Throwable var2) {
        Log.w(var0, var1, var2);
    }

    public static void d(String var0, String var1) {
        Log.d(var0, var1);
    }

    public static void d(String var0, String var1, Throwable var2) {
        Log.d(var0, var1, var2);
    }

    public static void e(String var0, String var1) {
        Log.e("DX-Toolbox", a(var0, var1));
    }

    public static void e(String var0, String var1, Throwable var2) {
        Log.e("DX-Toolbox", a(var0, var1), var2);
    }

    private static String a(String var0, String var1) {
        StringBuffer var2 = (new StringBuffer()).append("{").append(Thread.currentThread().getName()).append("}").append("[").append(var0).append("] ").append(var1);
        return var2.toString();
    }
}
