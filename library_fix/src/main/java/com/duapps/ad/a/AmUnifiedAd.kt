package com.duapps.ad.a

import android.view.View
import com.duapps.ad.DuAdDataCallBack
import com.duapps.ad.DuClickCallback
import com.duapps.ad.DuNativeAd
import com.duapps.ad.entity.strategy.NativeAd
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import java.util.*

/**
 * Created on 12/09/2018 17:58
 * Description:
 *
 * @author 王洪胜
 */

class AmUnifiedAd(private val isBanner: Boolean) : AdListener(), NativeAd {

    private val createTime = System.currentTimeMillis()

    private var duAdDataCallBack: DuAdDataCallBack? = null

    var amNativeAd: UnifiedNativeAd? = null
    var amBannerAd: AdView? = null

    private var e: d? = null

    fun a(var1: d) {
        this.e = var1
    }

    override fun onAdClosed() {
        this.e?.d()
    }

    override fun onAdFailedToLoad(var1: Int) {
        this.e?.a(var1)
    }

    override fun onAdLeftApplication() {
        this.e?.c()
    }

    override fun onAdOpened() {
        duAdDataCallBack?.onAdClick()
        this.e?.a()
    }

    override fun onAdLoaded() {
        this.e?.b()
    }

    override fun getAdBody(): String {
        return amNativeAd?.body ?: ""
    }

    override fun setMobulaAdListener(p0: DuAdDataCallBack?) {
        this.duAdDataCallBack = p0
    }

    override fun getInctRank(): Int {
        return -1
    }

    override fun destroy() {
        amNativeAd?.destroy()
    }

    override fun getId(): String {
        return UUID.randomUUID().toString()
    }

    private val sourceString: String get() = if (isBanner) "admob banner" else "admob unified"

    override fun getSourceType(): String {
        return sourceString
    }

    override fun unregisterView() {
        amNativeAd?.destroy()
        amBannerAd?.destroy()
    }

    override fun getAdSource(): String {
        return sourceString
    }

    override fun getPkgName(): String? {
        return null
    }

    override fun getAdIconUrl(): String {
        return amNativeAd?.icon?.uri?.toString() ?: ""
    }

    override fun getAdChannelType(): Int {
        return if (isBanner) DuNativeAd.CHANNEL_TYPE_AM_BANNER else DuNativeAd.CHANNEL_TYPE_AM_UNIFIED
    }

    override fun getAdCallToAction(): String {
        return amNativeAd?.callToAction ?: ""
    }

    override fun getAdTitle(): String {
        return amNativeAd?.headline ?: ""
    }

    override fun getRealData(): Any {
        return if (isBanner) amBannerAd!! else amNativeAd!!
    }

    override fun getAdCoverImageUrl(): String {
        return amNativeAd?.images?.run {
            if (this.isEmpty()) null else this[0].uri.toString()
        } ?: ""
    }

    override fun registerViewForInteraction(p0: View?) {
    }

    override fun registerViewForInteraction(p0: View?, p1: MutableList<View>?) {
    }

    override fun getVideoController() = null

    override fun setProcessClickUrlCallback(p0: DuClickCallback?) {
    }

    override fun getAdStarRating(): Float {
        return amNativeAd?.starRating?.toFloat() ?: 0f
    }

    override fun isValid(): Boolean {
        val var1 = System.currentTimeMillis() - createTime
        return var1 in 1..3600000
    }

    override fun getAdSocialContext() = null
}