//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.duapps.ad.base;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;

import com.duapps.ad.entity.strategy.NativeAd;
import com.duapps.ad.entity.strategy.a;
import com.duapps.ad.internal.utils.e;
import com.iwhys.classeditor.domain.ReplaceClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ReplaceClass("DuappsAd-HW-v1.1.1.6-release")
public class c {
    public static final String[] a = new String[]{"admob", "download", "facebook", "inmobi", "online", "dlh"};
    private static final String[] b = new String[]{"download"};

    public static long a(Context var0, int var1, int var2, List<String> var3, ConcurrentHashMap<String, a<NativeAd>> var4) {
        long var5 = 0L;
        if (null != var0 && null != var3 && var3.size() != 0 && null != var4) {
            LogHelper.d("ChannelFactory", "cacheSize==" + var2);
            try {
                Class bb = Class.forName("com.duapps.ad.internal.b.b");
                Method ba = bb.getDeclaredMethod("a", Context.class);
                Method baa = bb.getDeclaredMethod("a", int.class, boolean.class);
                Object bo = ba.invoke(null, var0);
                Object v7O = baa.invoke(bo, var1, true);
                Class var7Class = Class.forName("com.duapps.ad.internal.b.c");
                Method v7a = var7Class.getDeclaredMethod("a", String.class);

                int var8 = 0;
                Iterator var9 = var3.iterator();

                while(var9.hasNext()) {
                    String var10 = (String)var9.next();
                    long var11 = (long) v7a.invoke(v7O, var10);
                    int var13 = a(var3, var2, var10);
                    LogHelper.d("ChannelFactory", "Create channel:" + var10 + ",wt:" + var11 + ",cacheSize: " + var13);
                    a var14 = a(var10, var0, var1, var11, var13);
                    if (var14 != null) {
                        var4.put(var10, var14);
                        var5 += var11;
                        var14.g = var5 - var11;
                        LogHelper.d("ChannelFactory", "channel:" + var10 + "startTime:" + var14.g);
                        var14.l = var8++;
                    }
                }
            } catch (Exception ignored) {
            }

            return var5;
        } else {
            return var5;
        }
    }

    private static a<NativeAd> a(String var0, Context var1, int var2, long var3, int var5) {
        Object var6 = null;
        if ("facebook".equals(var0)) {
            var6 = new com.duapps.ad.entity.c(var1, var2, var3, var5);
        } else if ("download".equals(var0)) {
            var6 = new m(var1, var2, var3);
        } else if ("inmobi".equals(var0)) {
            var6 = new com.duapps.ad.inmobi.a(var1, var2, var3, var5);
        } else if ("dlh".equals(var0)) {
            var6 = new com.duapps.ad.b.a(var1, var2, var3);
        } else if ("online".equals(var0)) {
            var6 = new o(var1, var2, var3, var5);
        } else if ("admob".equals(var0)) {
            String var7 = n.a(var1).d(var2);
            if (!TextUtils.isEmpty(var7)) {
                try {
                    Class ac = Class.forName("com.duapps.ad.a.a");
                    Constructor constructor = ac.getConstructor(Context.class, int.class, long.class, int.class, String.class);
                    var6 = constructor.newInstance(var1, var2, var3, var5, var7);
                } catch (Exception ignored) {
                }
            }
        } else {
            LogHelper.e("ChannelFactory", "Unsupport error channel:" + var0);
        }

        return (a)var6;
    }

    public static List<String> a(List<String> var0, Context var1, int var2) {
        ArrayList var3 = new ArrayList(a.length);
        List var4 = Arrays.asList(a);

        for(int var5 = 0; var5 < var0.size(); ++var5) {
            String var6 = (String)var0.get(var5);
            if (var4.contains(var6) && a(var6, var1, var2)) {
                var3.add(var6);
            }
        }
        // [facebook, dlh, download]
        return hackPriority(var1, var2);
    }

    public static List<String> b(List<String> var0, Context var1, int var2) {
        ArrayList var3 = new ArrayList(b.length);
        List var4 = Arrays.asList(b);

        for(int var5 = 0; var5 < var0.size(); ++var5) {
            String var6 = (String)var0.get(var5);
            if (var4.contains(var6) && a(var6, var1, var2)) {
                var3.add(var6);
            }
        }
        // [facebook, dlh, download]
        return hackPriority(var1, var2);
    }

    private static List<String> hackPriority(Context context, int pid) {
        List<String> priority = new ArrayList<>(Arrays.asList("dlh", "download"));
        String amid = n.a(context).d(pid);
        if (!TextUtils.isEmpty(amid)) {
            priority.add(0, "admob");
        }
        List<String> fbids = n.a(context).c(pid);
        if (fbids != null && !fbids.isEmpty()) {
            priority.add(0, "facebook");
        }
        return priority;
    }

    public static int a(List<String> var0, int var1, String var2) {
        if (null != var0 && var0.size() != 0 && null != var2) {
            if (var1 < 1) {
                var1 = 1;
            }

            if (var1 > var0.size() - 1 && var1 > 5) {
                var1 = 5;
            }

            ArrayList var3 = new ArrayList();
            var3.addAll(var0);
            if (var3.contains("download")) {
                var3.remove("download");
            }

            if (var3.contains("dlh")) {
                var3.remove("dlh");
            }

            return var3.size() > 0 && var1 > var3.size() && ((String)var3.get(0)).equals(var2) ? var1 - (var3.size() - 1) : 1;
        } else {
            return 1;
        }
    }

    private static boolean a(String var0, Context var1, int var2) {
        if ("admob".equals(var0)) {
            String var4 = n.a(var1).d(var2);
            return !TextUtils.isEmpty(var4) && VERSION.SDK_INT > 10 && e.c();
        } else if (!"facebook".equals(var0)) {
            return true;
        } else {
            List var3 = n.a(var1).c(var2);
            return var3 != null && var3.size() > 0 && e.b();
        }
    }
}
