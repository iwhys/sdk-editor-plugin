package com.duapps.ad;

import android.view.View;

import java.util.List;

/**
 * Created on 10/07/2018 17:29
 * Description: 百度广告事件全局监听器
 *
 * @author 王洪胜
 */
public interface DuAdGlobalListener {

    /**
     * 百度广告被绑定到布局上
     * @param pid 广告位id
     * @param adView 广告布局视图
     * @param duNativeAd 广告实体
     * @return 是否拦截sdk后续注册点击事件, true拦截, false不拦截
     */
    boolean onDuAdShow(int pid, View adView, DuNativeAd duNativeAd);

    /**
     * 百度广告被点击
     * @param pid 广告位id
     * @param duNativeAd 广告实体
     */
    void onDuAdClick(int pid, DuNativeAd duNativeAd);

    /**
     * facebook广告被绑定到布局上
     * @param pid 广告位id
     * @param adView 广告布局视图
     * @param duNativeAd 广告实体
     * @return 是否拦截sdk后续注册点击事件, true拦截, false不拦截
     */
    boolean onFbAdShow(int pid, View adView, DuNativeAd duNativeAd, String unitId);

    /**
     * facebook广告被点击
     * @param pid 广告位id
     * @param duNativeAd 广告实体
     * @param unitId facebook广告位ID
     */
    void onFbAdClick(int pid, DuNativeAd duNativeAd, String unitId);

    /**
     * admob广告加载成功
     * @param pid 广告位id
     * @param duNativeAd 广告实体
     * @return 是否拦截sdk后续绑定事件, true拦截, false不拦截
     */
    boolean onAdmobLoaded(int pid, DuNativeAd duNativeAd);

    /**
     * Admob广告被绑定到布局上
     * @param pid 广告位id
     * @param adView 广告布局视图
     * @param duNativeAd 广告实体
     * @param unitId 广告位ID
     * @return 是否拦截sdk后续注册点击事件, true拦截, false不拦截
     */
    boolean onAdmobShow(int pid, View adView, DuNativeAd duNativeAd, String unitId);

    /**
     * Admob广告被点击
     * @param pid 广告位id
     * @param duNativeAd 广告实体
     * @param unitId 广告位ID
     */
    void onAdmobClick(int pid, DuNativeAd duNativeAd, String unitId);

    /**
     * 广告请求发生错误
     * @param pid 广告位id
     * @param adError 错误信息实体
     */
    void onAdError(int pid, AdError adError);

    /**
     * 广告注册Fb广告点击事件
     * @param pid 广告位id
     * @param adView
     * @param views
     * @return 返回可响应点击的控件
     */
    List<View> onRegisterFbView(int pid, View adView, List<View> views);

    abstract class Adapter implements DuAdGlobalListener {


        @Override
        public boolean onDuAdShow(int pid, View adView, DuNativeAd duNativeAd) {
            return false;
        }

        @Override
        public void onDuAdClick(int pid, DuNativeAd duNativeAd) {

        }

        @Override
        public boolean onFbAdShow(int pid, View adView, DuNativeAd duNativeAd, String unitId) {
            return false;
        }

        @Override
        public void onFbAdClick(int pid, DuNativeAd duNativeAd, String unitId) {

        }

        @Override
        public boolean onAdmobLoaded(int pid, DuNativeAd duNativeAd) {
            return false;
        }

        @Override
        public boolean onAdmobShow(int pid, View adView, DuNativeAd duNativeAd, String unitId) {
            return false;
        }

        @Override
        public void onAdmobClick(int pid, DuNativeAd duNativeAd, String unitId) {

        }

        @Override
        public void onAdError(int pid, AdError adError) {

        }

        @Override
        public List<View> onRegisterFbView(int pid, View adView, List<View> views) {
            return views;
        }
    }

}
