//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
@file:JvmName("a")

package com.duapps.ad.a

import android.annotation.SuppressLint
import android.content.Context
import android.os.*
import android.os.Handler.Callback
import com.duapps.ad.AdError
import com.duapps.ad.base.LogHelper
import com.duapps.ad.entity.strategy.NativeAd
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.iwhys.classeditor.domain.ReplaceClass
import java.util.*
import com.duapps.ad.internal.utils.e as ea
import com.duapps.ad.stats.g as gg


@SuppressLint("MissingPermission")
@ReplaceClass("DuappsAd-HW-v1.1.1.6-release")
class a(var1: Context, var2: Int, var3: Long, private val o: Int, private val r: String) : com.duapps.ad.entity.strategy.a<NativeAd>(var1, var2, var3), Callback {
    private val b = Collections.synchronizedList(LinkedList<AmUnifiedAd>())
    private val p: Handler
    private var q: Long = 0
    private val host = this
    private val isBanner: Boolean
    private val unitId: String
    private val handler = Handler(Looper.getMainLooper())

    init {
        val symbol = "@"
        if (r.endsWith(symbol)) {
            isBanner = true
            unitId = r.split(symbol)[0]
        } else {
            isBanner = false
            unitId = this.r
        }
        val var7 = HandlerThread("adnative", 10)
        var7.start()
        this.p = Handler(var7.looper, this)
    }

    override fun a(var1: Boolean) {
        super.a(var1)
        LogHelper.d(a, "refresh request....!")
        if (!ea.a(this.h)) {
            LogHelper.d(a, "No Network!")
        } else {
            this.e = true
            this.p.obtainMessage(0).sendToTarget()
        }
    }

    fun a(): NativeAd? {
        var var1: AmUnifiedAd? = null
        synchronized(this.b) {
            while (this.b.size > 0) {
                var1 = this.b.removeAt(0)
                if (var1?.isValid == true) {
                    break
                }
            }
        }
        com.duapps.ad.stats.b.d(this.h, if (var1 == null) "FAIL" else "OK", this.i)
        return var1
    }

    override fun b(): Int {
        return this.o
    }

    override fun c(): Int {
        var var1 = 0
        synchronized(this.b) {
            val var3 = this.b.iterator()
            while (true) {
                while (var3.hasNext()) {
                    val var4 = var3.next()
                    if (null != var4 && var4.isValid) {
                        ++var1
                    } else {
                        var3.remove()
                    }
                }
                return var1
            }
        }
        return var1
    }

    override fun d(): NativeAd? {
        return a()
    }

    override fun handleMessage(var1: Message): Boolean {
        val var2 = var1.what
        val var3: Int
        if (var2 == 0) {
            this.p.removeMessages(0)
            if (this.d) {
                LogHelper.d(a, "Refresh request failed: already refreshing")
                return true
            } else {
                this.d = true
                var3 = this.o - this.c()
                if (var3 > 0) {
                    this.p.obtainMessage(2, var3, 0).sendToTarget()
                } else {
                    LogHelper.d(a, "Refresh request OK: green is full")
                    this.d = false
                }

                return true
            }
        } else if (var2 == 2) {
            var3 = var1.arg1
            if (var3 > 0) {
                this.a(var3)
            } else {
                this.d = false
                LogHelper.d(a, "Refresh result: DONE for geeen count")
            }

            return true
        } else {
            return false
        }
    }

    private fun a(var1: Int) {
        val var2 = AmUnifiedAd(isBanner)
        var2.a(object : d {
            override fun a() {
                gg.d(host.h, host.i)
                if (host.m != null) {
                    host.m.onAdClick()
                }
            }

            override fun b() {
                host.a(var1, 200)
            }

            override fun c() {}

            override fun a(var1x: Int) {
                host.a(var1, var1x)
                if (!host.k && host.m != null) {
                    host.m.onAdError(AdError(var1x, "No details msg from Admob"))
                }

            }

            override fun d() {}
        })
        if (isBanner) {
            bannerRequest(var2)
        } else {
            unifiedAdRequest(var2)
        }
    }

    private fun bannerRequest(var2: AmUnifiedAd) {
        val adView = AdView(this.h)
        handler.post {
            adView.adUnitId = this.unitId
            adView.adSize = AdSize.MEDIUM_RECTANGLE
            adView.adListener = object: AdListener() {
                override fun onAdLoaded() {
                    synchronized(host.b) {
                        var2.amBannerAd = adView
                        host.b.add(var2)
                    }
                    var2.onAdLoaded()
                }
                override fun onAdClosed() {
                    var2.onAdClosed()
                }
                override fun onAdFailedToLoad(p0: Int) {
                    var2.onAdFailedToLoad(p0)
                }
                override fun onAdOpened() {
                    var2.onAdOpened()
                }
                override fun onAdLeftApplication() {
                    var2.onAdLeftApplication()
                }
            }
            this.d = true
            adView.loadAd(AdRequest.Builder().build())
            this.q = SystemClock.elapsedRealtime()
        }
    }

    private fun unifiedAdRequest(var2: AmUnifiedAd) {
        val var3 = AdLoader.Builder(this.h, this.unitId)
        val videoOptions = VideoOptions.Builder()
                .setStartMuted(true)
                .build()
        val nativeOption = NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .setReturnUrlsForImageAssets(false)
                .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                .setImageOrientation(2)
                .build()
        var3.forUnifiedNativeAd {
            synchronized(host.b) {
                var2.amNativeAd = it
                host.b.add(var2)
            }
        }
        val var4 = var3.withAdListener(var2)
                .withNativeAdOptions(nativeOption)
                .build()
        this.d = true
        LogHelper.d(a, "AdmobCacheManager start refresh ad!")
        var4.loadAd(AdRequest.Builder().build())
        this.q = SystemClock.elapsedRealtime()
    }

    private fun a(var1: Int, var2: Int) {
        com.duapps.ad.stats.b.d(this.h, this.i, var2, SystemClock.elapsedRealtime() - this.q)
        if (var1 > 1) {
            this.p.obtainMessage(2, var1 - 1, 0).sendToTarget()
        } else {
            this.d = false
            LogHelper.d(a, "Refresh result: DONE for geeen count")
            if (var2 != 200) {
                this.c = true
            }
        }

    }

    companion object {
        private val a = a::class.java.simpleName
    }
}
