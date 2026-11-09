package com.zz.douyin.hook;

import java.util.Locale;

/**
 * Lightweight runtime classifier for the Douyin feed components that can be
 * kept visible during immersive playback.
 *
 * The classifier intentionally combines semantic class/resource names with
 * screen position. Douyin changes obfuscated view classes frequently, while
 * the feed's broad layout regions and user-facing labels are more stable.
 */
final class ImmersiveComponentPolicy {
    static final int NONE = 0;
    static final int TOP_NAVIGATION = 1;
    static final int RIGHT_ACTIONS = 2;
    static final int BOTTOM_INFO = 3;
    static final int BOTTOM_NAVIGATION = 4;
    static final int PLAYER_CONTROLS = 5;

    private ImmersiveComponentPolicy() {
    }

    static int classify(
            String className,
            String resourceName,
            String label,
            boolean clickable,
            boolean textLike,
            boolean imageLike,
            boolean container,
            int left,
            int top,
            int right,
            int bottom,
            int rootWidth,
            int rootHeight
    ) {
        if (rootWidth <= 0 || rootHeight <= 0 || right <= left || bottom <= top) {
            return NONE;
        }

        String names = normalize(className) + " " + normalize(resourceName);
        String text = normalize(label);
        int width = right - left;
        int height = bottom - top;
        int centerX = left + width / 2;
        int centerY = top + height / 2;

        boolean topArea = top < rootHeight * 0.18f
                && centerY < rootHeight * 0.24f;
        boolean rightArea = centerX >= rootWidth * 0.78f
                && centerY >= rootHeight * 0.12f
                && centerY <= rootHeight * 0.90f;
        boolean bottomNavigationArea = centerY >= rootHeight * 0.87f
                || top >= rootHeight * 0.84f;
        boolean bottomInfoArea = centerY >= rootHeight * 0.62f
                && centerY < rootHeight * 0.90f
                && centerX < rootWidth * 0.82f;
        boolean contentView = clickable || textLike || imageLike;

        if (containsAny(names,
                "seekbar", "progressbar", "timebar", "playcontrol",
                "playercontrol", "playpause", "pausebutton", "playbutton",
                "videocontrol", "controlpanel", "video_progress",
                "progress_view", "video_seek")) {
            return PLAYER_CONTROLS;
        }

        if (topArea && containsAny(names,
                "topbar", "toolbar", "topnavigation", "navigationheader",
                "searchbar", "searchbutton", "feedtab", "feednavigation")) {
            return TOP_NAVIGATION;
        }

        if (rightArea && containsAny(names,
                "feedright", "rightaction", "actionbar", "actionitem",
                "digg", "likebutton", "commentbutton", "collect",
                "favorite", "sharebutton", "forwardbutton")) {
            return RIGHT_ACTIONS;
        }

        if (bottomNavigationArea && containsAny(names,
                "bottomnavigation", "bottom_nav", "bottomtab", "tabbar",
                "tablayout", "navigationbar", "bottommenu", "home_tab",
                "profile_tab", "feedbottom")) {
            return BOTTOM_NAVIGATION;
        }

        if (bottomInfoArea && containsAny(names,
                "author", "avatar", "nickname", "caption", "description",
                "feedinfo", "bottominfo", "userprofile", "creator",
                "musicname", "musicinfo")) {
            return BOTTOM_INFO;
        }

        if (topArea && contentView && containsAny(text,
                "搜索", "返回", "推荐", "关注", "同城")) {
            return TOP_NAVIGATION;
        }

        if (rightArea && contentView && containsAny(text,
                "点赞", "喜欢", "评论", "收藏", "分享", "转发", "关注")) {
            return RIGHT_ACTIONS;
        }

        if (bottomNavigationArea && contentView && containsAny(text,
                "首页", "朋友", "消息", "我", "商城", "推荐")) {
            return BOTTOM_NAVIGATION;
        }

        if (bottomInfoArea && contentView && containsAny(text,
                "作者", "展开全文", "收起", "原声", "音乐", "说点什么",
                "发布于", "查看翻译")) {
            return BOTTOM_INFO;
        }

        if (bottomNavigationArea && contentView
                && !container
                && width <= rootWidth * 0.36f
                && height <= rootHeight * 0.12f) {
            return BOTTOM_NAVIGATION;
        }

        if (rightArea && contentView
                && width <= rootWidth * 0.28f
                && height <= rootHeight * 0.20f) {
            return RIGHT_ACTIONS;
        }

        if (bottomInfoArea && contentView
                && width <= rootWidth * 0.90f
                && height <= rootHeight * 0.20f) {
            return BOTTOM_INFO;
        }

        if (topArea && contentView
                && width <= rootWidth * 0.90f
                && height <= rootHeight * 0.18f) {
            return TOP_NAVIGATION;
        }

        return NONE;
    }

    private static boolean containsAny(String value, String... tokens) {
        for (String token : tokens) {
            if (value.contains(token)) {
                return true;
            }
        }
        return false;
    }

    private static String normalize(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT);
    }
}
