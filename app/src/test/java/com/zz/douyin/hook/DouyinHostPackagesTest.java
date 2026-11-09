package com.zz.douyin.hook;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class DouyinHostPackagesTest {
    @Test
    public void acceptsBothOfficialHostPackages() {
        assertTrue(DouyinHostPackages.supports("com.ss.android.ugc.aweme"));
        assertTrue(DouyinHostPackages.supports("com.ss.android.ugc.aweme.lite"));
    }

    @Test
    public void rejectsOtherByteDanceAndLookalikePackages() {
        assertFalse(DouyinHostPackages.supports(null));
        assertFalse(DouyinHostPackages.supports(""));
        assertFalse(DouyinHostPackages.supports("com.ss.android.ugc.aweme.test"));
        assertFalse(DouyinHostPackages.supports("com.ss.android.ugc.aweme.lite2"));
        assertFalse(DouyinHostPackages.supports("com.ss.android.ugc.aweme.LITE"));
        assertFalse(DouyinHostPackages.supports("com.zhiliaoapp.musically"));
    }

    @Test
    public void distinguishesLiteFromStandard() {
        assertTrue(DouyinHostPackages.isLite("com.ss.android.ugc.aweme.lite"));
        assertFalse(DouyinHostPackages.isLite("com.ss.android.ugc.aweme"));
        assertFalse(DouyinHostPackages.isLite(null));
    }
}
