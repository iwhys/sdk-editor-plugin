//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.duapps.ad;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.duapps.ad.base.LogHelper;
import com.duapps.ad.base.k;
import com.duapps.ad.base.n;
import com.duapps.ad.entity.strategy.NativeAd;
import com.iwhys.sdkeditor.plugin.ReplaceClass;

import java.util.List;

@ReplaceClass("DuappsAd-HW-v1.1.1.6-release")
public class DuNativeAd {
    public static final int CHANNEL_TYPE_DL = 1;
    public static final int CHANNEL_TYPE_FB = 2;
    public static final int CHANNEL_TYPE_IM = 3;
    public static final int CHANNEL_TYPE_AM_INSTALL = 4;
    public static final int CHANNEL_TYPE_AM_CONTENT = 5;
    public static final int CHANNEL_TYPE_OL = 6;
    public static final int CHANNEL_TYPE_MP = 7;
    public static final int CHANNEL_TYPE_FL = 8;
    public static final int CHANNEL_TYPE_AM_UNIFIED = 9;
    public static final int CHANNEL_TYPE_AM_BANNER = 10;
    private Context b;
    private NativeAd c;
    private DuAdListener d;
    private int e;
    a a;
    private View f;
    private DuClickCallback g;
    public static final String INSTALL_ACTION = "com.duapps.ad.ACTION_INSTALL";
    public static final String EXTRAS_AD_INCT_RANK = "ad_inct_rank";
    public static final String EXTRAS_AD_PID = "ad_inct_pid";
    public static final String EXTRAS_AD_AID = "ad_inct_id";
    private boolean h;
    private DuAdDataCallBack i;
    /***************
     * 新增加的静态变量
     ****************/
    private static DuAdGlobalListener duAdGlobalListener;
    private boolean isBound = false;

    public DuNativeAd(Context var1, int var2) {
        this(var1, var2, 1, true);
    }

    public DuNativeAd(Context var1, int var2, boolean var3) {
        this(var1, var2, 1, var3);
    }

    public DuNativeAd(Context var1, int var2, int var3) {
        this(var1, var2, var3, true);
    }

    public DuNativeAd(Context var1, int var2, int var3, boolean var4) {
        this.i = new DuAdDataCallBack() {
            public void onAdLoaded(NativeAd var1) {
                DuNativeAd.this.c = var1;
                DuAdListener var2 = DuNativeAd.this.d;
                if (DuNativeAd.this.g != null) {
                    DuNativeAd.this.c.setProcessClickUrlCallback(DuNativeAd.this.g);
                }

                /***************
                 * 新增逻辑处理，用来暴露新的接口
                 ****************/
                if (duAdGlobalListener != null) {
                    int type = var1.getAdChannelType();
                    if (type == NativeAd.CHANNEL_TYPE_AM_CONTENT
                            || type == NativeAd.CHANNEL_TYPE_AM_INSTALL
                            || type == DuNativeAd.CHANNEL_TYPE_AM_UNIFIED
                            || type == DuNativeAd.CHANNEL_TYPE_AM_BANNER) {
                        boolean result = duAdGlobalListener.onAdmobLoaded(DuNativeAd.this.e, DuNativeAd.this);
                        if (result) return;
                    }
                }

                if (var2 != null) {
                    var2.onAdLoaded(DuNativeAd.this);
                }
            }

            public void onAdError(AdError var1) {
                /***************
                 * 新增逻辑处理，用来暴露新的接口
                 ****************/
                if (duAdGlobalListener != null) {
                    duAdGlobalListener.onAdError(DuNativeAd.this.e, var1);
                }
                DuAdListener var2 = DuNativeAd.this.d;
                if (var2 != null) {
                    var2.onError(DuNativeAd.this, var1);
                }
            }

            public void onAdClick() {
                DuAdListener var1 = DuNativeAd.this.d;
                if (var1 != null) {
                    var1.onClick(DuNativeAd.this);
                }
                /***************
                 * 新增逻辑处理，用来暴露新的接口
                 ****************/
                reportAdClick();
            }
        };
        this.h = n.a(var1).a(var2);
        this.b = var1;
        this.e = var2;
        this.a = (a)PullRequestController.getInstance(var1.getApplicationContext()).getPullController(this.e, var3, var4);
        if (!this.h) {
            Log.e("DAP", "DAP Pid:" + this.e + "cannot found in native configuration json file");
        }
    }

    public void setBound(boolean bound) {
        isBound = bound;
    }

    public boolean isBound() {
        return isBound;
    }

    /***************
     * 新增逻辑处理，用来暴露新的接口
     ****************/
    public static void registerGlobalListener(DuAdGlobalListener globalListener) {
        duAdGlobalListener = globalListener;
    }

    public Context getContext() {
        return this.b;
    }

    public void setFbids(List<String> var1) {
        if (null != var1 && var1.size() > 0) {
            LogHelper.d("test", "change FBID :" + var1.toString());
            this.a.a(var1);
        } else {
            Log.e("DuNativeAdError", "NativeAds fbID couldn't be null");
        }
    }

    public void setMobulaAdListener(DuAdListener var1) {
        this.d = var1;
    }

    public void setProcessClickCallback(DuClickCallback var1) {
        this.g = var1;
    }

    public void fill() {
        if (!this.h) {
            Log.e("DAP", "DAP Pid:" + this.e + "cannot found in native configuration json file");
        } else {
            boolean var1 = k.i(this.b);
            if (!var1) {
                this.i.onAdError(AdError.LOAD_TOO_FREQUENTLY);
            } else {
                this.a.fill();
                k.j(this.b);
            }
        }
    }

    public boolean isHasCached() {
        return this.a.a() > 0;
    }

    public DuNativeAd getCacheAd() {
        NativeAd var1 = this.a.b();
        if (var1 != null) {
            this.c = var1;
            if (this.g != null) {
                this.c.setProcessClickUrlCallback(this.g);
            }

            return this;
        } else {
            return null;
        }
    }

    public void registerViewForInteraction(View var1) {
        if (this.isAdLoaded()) {
            boolean result = reportAdShow(var1);
            if (result) return;
            if (this.f != null) {
                this.unregisterView();
            }

            this.f = var1;
            if (duAdGlobalListener == null || getAdChannelType() != NativeAd.CHANNEL_TYPE_FB) {
                this.c.registerViewForInteraction(var1);
            } else {
                List<View> views = duAdGlobalListener.onRegisterFbView(this.e, var1, null);
                if (views == null || views.isEmpty()) {
                    this.c.registerViewForInteraction(var1);
                } else {
                    this.c.registerViewForInteraction(var1, views);
                }
            }
        }

    }

    public void registerViewForInteraction(View var1, List<View> var2) {
        if (this.isAdLoaded()) {
            boolean result = reportAdShow(var1);
            if (result) return;
            if (this.f != null) {
                this.unregisterView();
            }

            this.f = var1;
            if (duAdGlobalListener == null || getAdChannelType() != NativeAd.CHANNEL_TYPE_FB) {
                this.c.registerViewForInteraction(var1, var2);
            } else {
                this.c.registerViewForInteraction(var1, duAdGlobalListener.onRegisterFbView(this.e, var1, var2));
            }
        }

    }

    /***************
     * 新增逻辑处理
     ****************/
    private void reportAdClick(){
        if (duAdGlobalListener == null) return;
        int type = getAdChannelType();
        NativeAd nativeAd = getRealSource();
        if (type == NativeAd.CHANNEL_TYPE_FB) {
            String unitId = ((com.facebook.ads.NativeAd) nativeAd.getRealData()).getPlacementId();
            duAdGlobalListener.onFbAdClick(this.e, this, unitId);
        } else if (type == NativeAd.CHANNEL_TYPE_AM_CONTENT
                || type == NativeAd.CHANNEL_TYPE_AM_INSTALL
                || type == DuNativeAd.CHANNEL_TYPE_AM_UNIFIED
                || type == DuNativeAd.CHANNEL_TYPE_AM_BANNER) {
            String unitId = n.a(this.b).d(this.e);
            duAdGlobalListener.onAdmobClick(this.e, this, unitId);
        } else {
            duAdGlobalListener.onDuAdClick(this.e, this);
        }
    }

    /***************
     * 新增逻辑处理
     ****************/
    private boolean reportAdShow(View var1){
        if (duAdGlobalListener == null) return false;
        int type = getAdChannelType();
        NativeAd nativeAd = getRealSource();
        boolean result;
        if (type == NativeAd.CHANNEL_TYPE_FB) {
            String unitId = ((com.facebook.ads.NativeAd) nativeAd.getRealData()).getPlacementId();
            result = duAdGlobalListener.onFbAdShow(this.e, var1, this, unitId);
        } else if (type == NativeAd.CHANNEL_TYPE_AM_CONTENT
                || type == NativeAd.CHANNEL_TYPE_AM_INSTALL
                || type == DuNativeAd.CHANNEL_TYPE_AM_UNIFIED
                || type == DuNativeAd.CHANNEL_TYPE_AM_BANNER) {
            String unitId = n.a(this.b).d(this.e);
            result = duAdGlobalListener.onAdmobShow(this.e, var1, this, unitId);
        } else {
            result = duAdGlobalListener.onDuAdShow(this.e, var1, this);
        }
        return result;
    }

    public boolean isAdLoaded() {
        return this.c != null;
    }

    public void unregisterView() {
        if (this.isAdLoaded()) {
            this.c.unregisterView();
        }

    }

    public void load() {
        if (!this.h) {
            Log.e("DAP", "DAP Pid:" + this.e + "cannot found in native configuration json file");
        } else {
            boolean var1 = k.f(this.b);
            if (!var1) {
                this.i.onAdError(AdError.LOAD_TOO_FREQUENTLY);
            } else {
                this.a.a((DuAdDataCallBack)null);
                this.a.a(this.i);
                this.a.load();
                k.k(this.b);
            }
        }
    }

    public void destory() {
        if (this.isAdLoaded()) {
            this.c.destroy();
        }

        this.a.a((DuAdDataCallBack)null);
        this.a.destroy();
    }

    public void clearCache() {
        this.a.clearCache();
    }

    public String getTitle() {
        return this.isAdLoaded() ? this.c.getAdTitle() : null;
    }

    public String getShortDesc() {
        return this.isAdLoaded() ? this.c.getAdBody() : null;
    }

    public String getIconUrl() {
        return this.isAdLoaded() ? this.c.getAdIconUrl() : null;
    }

    public String getImageUrl() {
        return this.isAdLoaded() ? this.c.getAdCoverImageUrl() : null;
    }

    public float getRatings() {
        return this.isAdLoaded() ? this.c.getAdStarRating() : 4.5F;
    }

    public String getCallToAction() {
        return this.isAdLoaded() ? this.c.getAdCallToAction() : null;
    }

    public String getSource() {
        return this.isAdLoaded() ? this.c.getAdSource() : null;
    }

    public int getAdChannelType() {
        return this.isAdLoaded() ? this.c.getAdChannelType() : -1;
    }

    public NativeAd getRealSource() {
        return this.isAdLoaded() ? this.c : null;
    }

    public float getInctRank() {
        return this.isAdLoaded() ? (float)this.c.getInctRank() : -1.0F;
    }
}
