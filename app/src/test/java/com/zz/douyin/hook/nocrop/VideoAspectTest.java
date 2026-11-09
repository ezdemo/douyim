package com.zz.douyin.hook.nocrop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public final class VideoAspectTest {
    private static final float DELTA = 0.001f;

    @Test
    public void readsWidthAndHeightFields() {
        MockAweme aweme = new MockAweme(new MockVideo(1920, 1080));
        assertEquals(16f / 9f, VideoAspect.read(aweme), DELTA);
    }

    @Test
    public void fallsBackToLegacyVideoDimensions() {
        MockVideo video = new MockVideo(0, 0);
        video.t = 1440;
        video.s = 1080;
        assertEquals(4f / 3f, VideoAspect.read(new MockAweme(video)), DELTA);
    }

    @Test
    public void fallsBackToAspectRatioField() {
        MockVideo video = new MockVideo(0, 0);
        video.aspectRatio = 2f;
        assertEquals(2f, VideoAspect.read(new MockAweme(video)), DELTA);
    }

    @Test
    public void fallsBackToFirstImageInfoForImagePosts() {
        MockAweme aweme = new MockAweme(null);
        aweme.imageInfos.add(new MockImageInfo(1200, 900));
        assertEquals(4f / 3f, VideoAspect.read(aweme), DELTA);
        assertTrue(VideoAspect.isImagePost(aweme));
    }

    @Test
    public void returnsZeroWhenNoValidDimensionsExist() {
        assertEquals(0f, VideoAspect.read(new MockAweme(null)), DELTA);
    }

    public static final class MockAweme {
        public final MockVideo video;
        public final List<MockImageInfo> imageInfos = new ArrayList<>();

        MockAweme(MockVideo video) {
            this.video = video;
        }

        public MockVideo getVideo() {
            return video;
        }

        public String getAid() {
            return "test-aid";
        }
    }

    public static final class MockVideo {
        public int width;
        public int height;
        public int t;
        public int s;
        public float aspectRatio;

        MockVideo(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public static final class MockImageInfo {
        public int width;
        public int height;

        MockImageInfo(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
