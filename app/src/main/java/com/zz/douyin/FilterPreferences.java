package com.zz.douyin;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class FilterPreferences {
    public static final String NAME = "content_filter";
    public static final String KEY_MODULE_ENABLED = "module_enabled";
    public static final String KEY_BLOCK_DOUBLE_TAP = "block_double_tap";
    public static final String KEY_IMMERSIVE_ENABLED = "immersive_enabled";
    public static final String KEY_NO_CROP = "no_crop";
    public static final String KEY_SKIP_ADS = "skip_ads";
    public static final String KEY_SKIP_IMAGES = "skip_images";
    public static final String KEY_SKIP_LIVES = "skip_lives";
    public static final String KEY_SKIP_VIDEOS = "skip_videos";
    public static final String KEY_VIDEO_KEYWORDS = "video_keywords";
    public static final String KEY_SHOW_DANMAKU = "show_danmaku";
    public static final String KEY_IMMERSIVE_HIDE_TOP_NAVIGATION =
            "immersive_hide_top_navigation";
    public static final String KEY_IMMERSIVE_HIDE_RIGHT_ACTIONS =
            "immersive_hide_right_actions";
    public static final String KEY_IMMERSIVE_HIDE_BOTTOM_INFO =
            "immersive_hide_bottom_info";
    public static final String KEY_IMMERSIVE_HIDE_BOTTOM_NAVIGATION =
            "immersive_hide_bottom_navigation";
    public static final String KEY_IMMERSIVE_HIDE_PLAYER_CONTROLS =
            "immersive_hide_player_controls";
    public static final String KEY_IMMERSIVE_HIDE_SYSTEM_BARS =
            "immersive_hide_system_bars";
    public static final String KEY_DOUBLE_TAP_COMMENTS = "double_tap_comments";
    public static final String KEY_VIDEO_QUALITY = "video_quality";

    /** 清晰度选项：跟随抖音自动调整。 */
    public static final String QUALITY_AUTO = "auto";
    /** 清晰度选项：始终选择可用档位中最高的。 */
    public static final String QUALITY_MAX = "max";
    public static final String DEFAULT_VIDEO_QUALITY = QUALITY_AUTO;

    public static final boolean DEFAULT_SKIP_ADS = true;
    public static final boolean DEFAULT_SKIP_IMAGES = true;
    public static final boolean DEFAULT_SKIP_LIVES = true;
    public static final boolean DEFAULT_SKIP_VIDEOS = false;
    public static final boolean DEFAULT_SHOW_DANMAKU = false;
    public static final boolean DEFAULT_NO_CROP = false;
    public static final boolean DEFAULT_DOUBLE_TAP_COMMENTS = false;

    private FilterPreferences() {
    }

    public static boolean readModuleEnabled(SharedPreferences preferences) {
        return preferences == null || preferences.getBoolean(KEY_MODULE_ENABLED, true);
    }

    public static boolean readBlockDoubleTap(SharedPreferences preferences) {
        return preferences != null && preferences.getBoolean(KEY_BLOCK_DOUBLE_TAP, false);
    }

    public static boolean readNoCropEnabled(SharedPreferences preferences) {
        return preferences != null
                && preferences.getBoolean(KEY_NO_CROP, DEFAULT_NO_CROP);
    }

    public static boolean readImmersiveEnabled(SharedPreferences preferences) {
        return preferences == null || preferences.getBoolean(KEY_IMMERSIVE_ENABLED, true);
    }

    public static Values defaults() {
        return new Values(
                DEFAULT_SKIP_ADS,
                DEFAULT_SKIP_IMAGES,
                DEFAULT_SKIP_LIVES,
                DEFAULT_SKIP_VIDEOS,
                ""
        );
    }

    public static Values read(SharedPreferences preferences) {
        if (preferences == null) {
            return defaults();
        }
        return new Values(
                preferences.getBoolean(KEY_SKIP_ADS, DEFAULT_SKIP_ADS),
                preferences.getBoolean(KEY_SKIP_IMAGES, DEFAULT_SKIP_IMAGES),
                preferences.getBoolean(KEY_SKIP_LIVES, DEFAULT_SKIP_LIVES),
                preferences.getBoolean(KEY_SKIP_VIDEOS, DEFAULT_SKIP_VIDEOS),
                preferences.getString(KEY_VIDEO_KEYWORDS, "")
        );
    }

    /**
     * Component visibility options used while immersive playback is active.
     * All hide flags default to true so existing installations keep the old
     * full-screen behavior after upgrading.
     */
    public static ImmersiveComponents readImmersiveComponents(
            SharedPreferences preferences
    ) {
        return new ImmersiveComponents(
                readBoolean(preferences, KEY_IMMERSIVE_HIDE_TOP_NAVIGATION),
                readBoolean(preferences, KEY_IMMERSIVE_HIDE_RIGHT_ACTIONS),
                readBoolean(preferences, KEY_IMMERSIVE_HIDE_BOTTOM_INFO),
                readBoolean(preferences, KEY_IMMERSIVE_HIDE_BOTTOM_NAVIGATION),
                readBoolean(preferences, KEY_IMMERSIVE_HIDE_PLAYER_CONTROLS),
                readBoolean(preferences, KEY_IMMERSIVE_HIDE_SYSTEM_BARS)
        );
    }

    private static boolean readBoolean(SharedPreferences preferences, String key) {
        return preferences == null || preferences.getBoolean(key, true);
    }

    public static final class ImmersiveComponents {
        public final boolean hideTopNavigation;
        public final boolean hideRightActions;
        public final boolean hideBottomInfo;
        public final boolean hideBottomNavigation;
        public final boolean hidePlayerControls;
        public final boolean hideSystemBars;

        private ImmersiveComponents(
                boolean hideTopNavigation,
                boolean hideRightActions,
                boolean hideBottomInfo,
                boolean hideBottomNavigation,
                boolean hidePlayerControls,
                boolean hideSystemBars
        ) {
            this.hideTopNavigation = hideTopNavigation;
            this.hideRightActions = hideRightActions;
            this.hideBottomInfo = hideBottomInfo;
            this.hideBottomNavigation = hideBottomNavigation;
            this.hidePlayerControls = hidePlayerControls;
            this.hideSystemBars = hideSystemBars;
        }
    }

    public static boolean readShowDanmaku(SharedPreferences preferences) {
        return preferences == null
                ? DEFAULT_SHOW_DANMAKU
                : preferences.getBoolean(KEY_SHOW_DANMAKU, DEFAULT_SHOW_DANMAKU);
    }

    public static boolean readDoubleTapComments(SharedPreferences preferences) {
        return preferences != null
                && preferences.getBoolean(
                KEY_DOUBLE_TAP_COMMENTS,
                DEFAULT_DOUBLE_TAP_COMMENTS
        );
    }

    public static String readVideoQuality(SharedPreferences preferences) {
        if (preferences == null) {
            return DEFAULT_VIDEO_QUALITY;
        }
        String value = preferences.getString(KEY_VIDEO_QUALITY, DEFAULT_VIDEO_QUALITY);
        return isKnownQuality(value) ? value : DEFAULT_VIDEO_QUALITY;
    }

    private static boolean isKnownQuality(String value) {
        if (value == null) {
            return false;
        }
        if (QUALITY_AUTO.equals(value) || QUALITY_MAX.equals(value)) {
            return true;
        }
        try {
            int height = Integer.parseInt(value);
            return height >= 144 && height <= 8640;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static final class Values {
        public final boolean skipAds;
        public final boolean skipImages;
        public final boolean skipLives;
        public final boolean skipVideos;
        public final String keywordText;
        private final List<String> keywords;

        public Values(
                boolean skipAds,
                boolean skipImages,
                boolean skipLives,
                boolean skipVideos,
                String keywordText
        ) {
            this.skipAds = skipAds;
            this.skipImages = skipImages;
            this.skipLives = skipLives;
            this.skipVideos = skipVideos;
            this.keywordText = keywordText == null ? "" : keywordText;
            this.keywords = parseKeywords(this.keywordText);
        }

        public List<String> keywords() {
            return keywords;
        }

        public String matchingVideoKeyword(String title, String description) {
            if (keywords.isEmpty()) {
                return null;
            }
            String searchable = safeLower(title) + "\n" + safeLower(description);
            for (String keyword : keywords) {
                if (searchable.contains(safeLower(keyword))) {
                    return keyword;
                }
            }
            return null;
        }

        private static List<String> parseKeywords(String raw) {
            if (raw == null || raw.trim().isEmpty()) {
                return Collections.emptyList();
            }
            Set<String> unique = new LinkedHashSet<>();
            for (String part : raw.split("[\\r\\n,，;；]+")) {
                String keyword = part.trim();
                if (!keyword.isEmpty()) {
                    unique.add(keyword);
                }
            }
            return unique.isEmpty()
                    ? Collections.emptyList()
                    : Collections.unmodifiableList(new ArrayList<>(unique));
        }

        private static String safeLower(String value) {
            return value == null ? "" : value.toLowerCase(Locale.ROOT);
        }
    }
}
