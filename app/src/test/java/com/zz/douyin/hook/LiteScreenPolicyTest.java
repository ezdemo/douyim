package com.zz.douyin.hook;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class LiteScreenPolicyTest {
    @Test
    public void standardDouyinRetainsExistingFeedBehavior() {
        assertTrue(LiteScreenPolicy.shouldAllowFeedActions(
                false, "com.ss.android.ugc.aweme.main.MainActivity", false));
        assertTrue(LiteScreenPolicy.shouldAllowFeedActions(
                false, "com.ss.android.ugc.aweme.profile.ui.UserProfileActivity", true));
    }

    @Test
    public void liteDoesNotRequireFeedModelToEnableImmersive() {
        // Lite might keep the renderer working even when the regular feed
        // panel class does not exist. Do not reintroduce the 1.7.8 bug.
        assertTrue(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.main.MainActivity", false));
        assertTrue(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.main.MainActivity", false));
    }

    @Test
    public void liteNeverUsesProfileOrMineScreens() {
        assertFalse(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.profile.ui.UserProfileActivity", false));
        assertFalse(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.main.MainActivity", true));
        assertFalse(LiteScreenPolicy.shouldAllowFeedActions(
                true, "com.ss.android.ugc.aweme.mine.MineActivity", false));
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
