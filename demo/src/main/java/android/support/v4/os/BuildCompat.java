/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.v4.os;

import android.os.Build.VERSION;
import android.widget.Toast;
import com.iwhys.classeditor.domain.ReplaceClass;
import com.iwhys.sdkeditor.demo.MyAppKt;

/**
 * This class contains additional platform version checking methods for targeting pre-release
 * versions of Android.
 * 这是我们要修复的类
 * 在[BuildCompat.isAtLeastQ]方法中添加一个Toast
 */
@ReplaceClass("com.android.support:support-compat:28.0.0")
public class BuildCompat {
    private BuildCompat() {
    }

    /** @deprecated */
    @Deprecated
    public static boolean isAtLeastN() {
        return VERSION.SDK_INT >= 24;
    }

    /** @deprecated */
    @Deprecated
    public static boolean isAtLeastNMR1() {
        return VERSION.SDK_INT >= 25;
    }

    /** @deprecated */
    @Deprecated
    public static boolean isAtLeastO() {
        return VERSION.SDK_INT >= 26;
    }

    /** @deprecated */
    @Deprecated
    public static boolean isAtLeastOMR1() {
        return VERSION.SDK_INT >= 27;
    }

    /** @deprecated */
    @Deprecated
    public static boolean isAtLeastP() {
        return VERSION.SDK_INT >= 28;
    }

    public static boolean isAtLeastQ() {
        Toast.makeText(MyAppKt.getAppContext(), "you have invoked the method: BuildCompat#isAtLeastQ()", Toast.LENGTH_LONG).show();
        return VERSION.CODENAME.length() == 1 && VERSION.CODENAME.charAt(0) >= 'Q' && VERSION.CODENAME.charAt(0) <= 'Z';
    }
}
