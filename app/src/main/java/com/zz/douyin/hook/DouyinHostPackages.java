package com.zz.douyin.hook;

/**
 * Exact allowlist of supported host application package IDs.
 *
 * Douyin Lite is a separate Android application but can reuse internal class
 * names under com.ss.android.ugc.aweme. Do not infer support by prefix: an
 * unexpected ByteDance host must never receive these playback hooks.
 */
public final class DouyinHostPackages {
    public static final String DOUYIN = "com.ss.android.ugc.aweme";
    public static final String DOUYIN_LITE = "com.ss.android.ugc.aweme.lite";

    private DouyinHostPackages() {
    }

    public static boolean supports(String packageName) {
        return DOUYIN.equals(packageName) || DOUYIN_LITE.equals(packageName);
    }

    public static boolean isLite(String packageName) {
        return DOUYIN_LITE.equals(packageName);
    }
}
