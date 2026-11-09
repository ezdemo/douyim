package com.zz.douyin.hook;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class LiteScreenPolicyTest {
    @Test
    public void standardDouyinRetainsExistingFeedBehavior() {
        assertTrue(LiteScreenPolicy.shouldAllowFeedActions(
                false, "com.ss.android.ugc.aweme.main.MainActivity", false, false));
    }

    @Test
    public void liteRequiresLiveForegroundFeed() {
        assertTrue(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.main.MainActivity", true, false));
        assertFalse(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.main.MainActivity", false, false));
    }

    @Test
    public void liteNeverUsesProfileOrMineScreens() {
        assertFalse(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.profile.ui.UserProfileActivity", true, false));
        assertFalse(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.main.MainActivity", true, true));
        assertFalse(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.mine.MineActivity", true, false));
    }

    @Test
    public void recognizesPageContainersButNotAvatarWidgets() {
        assertTrue(LiteScreenPolicy.isProfileIdentifier(
                "com.ss.android.ugc.aweme.profile.ui.ProfileFragment"));
        assertTrue(LiteScreenPolicy.isProfileIdentifier("user_profile_root"));
        assertTrue(LiteScreenPolicy.isProfileIdentifier(
                "com.ss.android.ugc.aweme.mine.MinePage"));
        assertFalse(LiteScreenPolicy.isProfileIdentifier("FeedRightUserAvatar"));
        assertFalse(LiteScreenPolicy.isProfileIdentifier("FeedItemView"));
        assertFalse(LiteScreenPolicy.isProfileIdentifier(null));
    }
}
