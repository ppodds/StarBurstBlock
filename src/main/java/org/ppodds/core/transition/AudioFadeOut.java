package org.ppodds.core.transition;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioFadeOut {
    public static void play(MediaPlayer media, Duration duration) {
        int times = (int) (duration.toMillis() / 50);
        double downRate = media.getVolume() / times;
        Timeline musicFadeOut = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            media.setVolume(media.getVolume() - downRate);
        }));
        musicFadeOut.setCycleCount(times);
        musicFadeOut.setOnFinished(e -> media.stop());
        musicFadeOut.play();
    }
}
