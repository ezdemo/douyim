package com.zz.douyin.hook;

import java.util.Locale;

/**
 * Conservative scope policy for Douyin Lite.
 *
 * Lite may keep a previous player surface attached while the user navigates
 * to a profile page within MainActivity. Only recognized profile screens veto
 * immersive actions: Lite's feed model classes may differ from regular Douyin,
 * so a missing FeedContentTracker.current() is not grounds for suppressing UI.
 */
public final class LiteScreenPolicy {
    private LiteScreenPolicy() {
    }

    public static boolean shouldAllowFeedActions(
            boolean liteHost,
            String activityClass,
            boolean visibleProfilePage
    ) {
        return !liteHost || (!visibleProfilePage
                && !isProfileIdentifier(activityClass));
    }

    /**
     * Recognize host profile pages rather than feed avatar/action widgets.
     * Callers that classify individual views must additionally check that
     * the view fills a substantial part of the foreground window.
     */
    public static boolean isProfileIdentifier(String classOrResourceName) {
        if (classOrResourceName == null || classOrResourceName.isEmpty()) {
            return false;
        }
        String name = classOrResourceName.toLowerCase(Locale.ROOT);
        return name.contains("userprofile")
                || name.contains("user_profile")
                || name.contains("profileactivity")
                || name.contains("profilefragment")
                || name.contains("profilepage")
                || name.contains("profile_page")
                || name.contains("minefragment")
                || name.contains("mineactivity")
                || name.contains("minepage")
                || name.contains("mine_page")
                || name.contains("personalhome")
                || name.contains("personalpage")
                || name.contains("userhomepage")
                || name.contains("user_homepage")
                || name.contains(".profile.")
                || name.contains(".mine.");
    }
}
