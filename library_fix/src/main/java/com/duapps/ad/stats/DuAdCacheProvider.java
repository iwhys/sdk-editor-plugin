//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.duapps.ad.stats;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.duapps.ad.base.LogHelper;
import com.iwhys.classeditor.domain.ReplaceClass;

@ReplaceClass("DuappsAd-HW-v1.1.1.6-release")
public final class DuAdCacheProvider extends ContentProvider {
    private String a = DuAdCacheProvider.class.getSimpleName();
    private static Uri b;
    private static Uri c;
    private static Uri d;
    private static Uri e;
    private static Uri f;
    private static Uri g;
    private static Uri h;
    private static Uri i;
    private static Uri j;
    private static final Object k = new Object();
    private i l;
    private static final Object m = new Object();
    private j n;
    private static final Object o = new Object();
    private f p;
    private static UriMatcher q;
    private Context r;

    public DuAdCacheProvider() {
    }

    public boolean onCreate() {
        this.r = this.getContext();
        this.a(this.r);
        return true;
    }

    private void a(Context var1) {
        String var2 = var1.getPackageName() + ".DuAdCacheProvider";
        q = a(var2);
    }

    private static UriMatcher a(String var0) {
        b = Uri.parse("content://" + var0);
        c = Uri.withAppendedPath(b, "parse");
        d = Uri.withAppendedPath(b, "click");
        e = Uri.withAppendedPath(b, "cache");
        f = Uri.withAppendedPath(b, "record");
        g = Uri.withAppendedPath(b, "preparse");
        h = Uri.withAppendedPath(b, "searchRecord");
        i = Uri.withAppendedPath(b, "preparsecache");
        UriMatcher var1 = new UriMatcher(-1);
        var1.addURI(var0, "parse", 1);
        var1.addURI(var0, "click", 2);
        var1.addURI(var0, "cache", 3);
        var1.addURI(var0, "record", 4);
        var1.addURI(var0, "preparse", 5);
        var1.addURI(var0, "searchRecord", 6);
        var1.addURI(var0, "preparsecache", 7);
        var1.addURI(var0, "behavior", 8);
        return var1;
    }

    public Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5) {
        int var6 = this.a(var1);
        if (var6 > 0 && var6 <= 8) {
            Cursor var7 = null;
            synchronized(this.b(var6)) {
                SQLiteDatabase var9 = this.b(this.getContext(), var6);
                var7 = var9.query(this.a(var6), var2, var3, var4, (String)null, (String)null, var5);
                return var7;
            }
        } else {
            return null;
        }
    }

    public String getType(Uri var1) {
        switch(q.match(var1)) {
            case 1:
                return "vnd.android.cursor.dir/parse";
            case 2:
                return "vnd.android.cursor.dir/click";
            case 3:
                return "vnd.android.cursor.dir/cache";
            case 4:
                return "vnd.android.cursor.dir/record";
            case 5:
                return "vnd.android.cursor.dir/preparse";
            case 6:
                return "vnd.android.cursor.dir/searchRecord";
            case 7:
                return "vnd.android.cursor.dir/preparseCache";
            case 8:
                return "vnd.android.cursor.dir/behavior";
            default:
                return "vnd.android.cursor.dir/unkown";
        }
    }

    public Uri insert(Uri var1, ContentValues var2) {
        int var3 = this.a(var1);
        if (var3 > 0 && var3 <= 8) {
            synchronized(this.b(var3)) {
                SQLiteDatabase var5 = this.b(this.getContext(), var3);
                var5.insert(this.a(var3), (String)null, var2);
                return var1;
            }
        } else {
            return null;
        }
    }

    public int delete(Uri var1, String var2, String[] var3) {
        LogHelper.d(this.a, "del selcetion  = " + var2 + " , selectionArgs = " + var2 != null ? var2.toString() : null);
        int var4 = this.a(var1);
        byte var5 = -1;
        if (var4 > 0 && var4 <= 8) {
            synchronized(this.b(var4)) {
                SQLiteDatabase var7 = this.b(this.getContext(), var4);
                int var10 = var7.delete(this.a(var4), var2, var3);
                return var10;
            }
        } else {
            return var5;
        }
    }

    public int update(Uri var1, ContentValues var2, String var3, String[] var4) {
        int var5 = this.a(var1);
        byte var6 = -1;
        if (var5 > 0 && var5 <= 8) {
            synchronized(this.b(var5)) {
                SQLiteDatabase var8 = this.b(this.getContext(), var5);
                int var11 = var8.update(this.a(var5), var2, var3, var4);
                return var11;
            }
        } else {
            return var6;
        }
    }

    private int a(Uri var1) {
        byte var2 = -1;
        if (null != var1 && Uri.EMPTY != var1) {
            int var3 = q.match(var1);
            LogHelper.d(this.a, "match code = " + var3);
            return var3;
        } else {
            return var2;
        }
    }

    private SQLiteDatabase b(Context var1, int var2) {
        SQLiteDatabase var3 = null;
        switch(var2) {
            case 1:
            case 7:
                if (null == this.n) {
                    this.n = new j(var1);
                }

                var3 = this.n.getWritableDatabase();
                break;
            case 2:
            case 3:
            case 5:
                if (null == this.l) {
                    this.l = new i(var1);
                }

                var3 = this.l.getWritableDatabase();
                break;
            case 4:
            case 8:
                if (null == this.p) {
                    this.p = new f(var1);
                }

                var3 = this.p.getWritableDatabase();
            case 6:
        }

        return var3;
    }

    private String a(int var1) {
        switch(var1) {
            case 1:
                return "ad_parse";
            case 2:
                return "tbvc";
            case 3:
                return "cache";
            case 4:
                return "srecord";
            case 5:
                return "appcache";
            case 6:
            default:
                return null;
            case 7:
                return "preparse_cache";
            case 8:
                return "brecord";
        }
    }

    private Object b(int var1) {
        Object var2 = null;
        switch(var1) {
            case 1:
            case 7:
                var2 = m;
                break;
            case 2:
            case 3:
            case 5:
                var2 = k;
                break;
            case 4:
            case 8:
                var2 = o;
            case 6:
        }

        return var2;
    }

    public static Uri a(Context var0, int var1) {
        Uri var2 = Uri.EMPTY;
        if (null == b) {
            String var3 = var0.getPackageName() + ".DuAdCacheProvider";
            b = Uri.parse("content://" + var3);
        }

        if (null == c) {
            c = Uri.withAppendedPath(b, "parse");
        }

        if (null == d) {
            d = Uri.withAppendedPath(b, "click");
        }

        if (null == e) {
            e = Uri.withAppendedPath(b, "cache");
        }

        if (null == f) {
            f = Uri.withAppendedPath(b, "record");
        }

        if (null == g) {
            g = Uri.withAppendedPath(b, "preparse");
        }

        if (null == h) {
            h = Uri.withAppendedPath(b, "searchRecord");
        }

        if (null == i) {
            i = Uri.withAppendedPath(b, "preparsecache");
        }

        if (null == j) {
            j = Uri.withAppendedPath(b, "behavior");
        }

        switch(var1) {
            case 1:
                var2 = c;
                break;
            case 2:
                var2 = d;
                break;
            case 3:
                var2 = e;
                break;
            case 4:
                var2 = f;
                break;
            case 5:
                var2 = g;
                break;
            case 6:
                var2 = h;
                break;
            case 7:
                var2 = i;
                break;
            case 8:
                var2 = j;
        }

        return var2;
    }
}
