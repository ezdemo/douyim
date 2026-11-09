package com.zz.douyin.hook;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class ImmersiveComponentPolicyTest {
    private static final int WIDTH = 1080;
    private static final int HEIGHT = 2400;

    @Test
    public void recognizesRightActionContainerByItsNativeClass() {
        assertEquals(
                ImmersiveComponentPolicy.RIGHT_ACTIONS,
                classify(
                        "com.ss.android.ugc.aweme.feed.ui.FeedRightScaleView",
                        "", "", true, false, false, true,
                        850, 300, 1070, 1900
                )
        );
    }

    @Test
    public void recognizesTopNavigationByItsLabelAndPosition() {
        assertEquals(
                ImmersiveComponentPolicy.TOP_NAVIGATION,
                classify(
                        "android.widget.TextView", "", "搜索",
                        true, true, false, false,
                        500, 80, 620, 160
                )
        );
    }

    @Test
    public void recognizesBottomInfoByItsContentAndPosition() {
        assertEquals(
                ImmersiveComponentPolicy.BOTTOM_INFO,
                classify(
                        "android.widget.TextView", "feed_desc", "作品描述",
                        false, true, false, false,
                        40, 1700, 760, 1850
                )
        );
    }

    @Test
    public void recognizesBottomNavigationByItsLabelAndPosition() {
        assertEquals(
                ImmersiveComponentPolicy.BOTTOM_NAVIGATION,
                classify(
                        "android.widget.TextView", "", "首页",
                        true, true, false, false,
                        20, 2220, 190, 2380
                )
        );
    }

    @Test
    public void recognizesPlayerControlsBySemanticResourceName() {
        assertEquals(
                ImmersiveComponentPolicy.PLAYER_CONTROLS,
                classify(
                        "android.widget.SeekBar", "video_progress", "",
                        false, false, false, false,
                        40, 2280, 1040, 2340
                )
        );
    }

    @Test
    public void ignoresUnrelatedViewsInTheMiddleOfTheVideo() {
        assertEquals(
                ImmersiveComponentPolicy.NONE,
                classify(
                        "android.widget.TextView", "subtitle", "普通字幕",
                        false, true, false, false,
                        240, 900, 840, 1050
                )
        );
    }

    private static int classify(
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
            int bottom
    ) {
        return ImmersiveComponentPolicy.classify(
                className,
                resourceName,
                label,
                clickable,
                textLike,
                imageLike,
                container,
                left,
                top,
                right,
                bottom,
                WIDTH,
                HEIGHT
        );
    }
}
