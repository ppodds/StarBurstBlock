package org.ppodds.core;

import javafx.scene.image.Image;
import org.ppodds.App;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class ResourceManager {
    public static URL getResource(String path) {
        return App.class.getResource(path);
    }

    public static InputStream getResourceAsStream(String path) {
        return App.class.getResourceAsStream(path);
    }

    public static URL getMedia(String file) {
        return getResource("media/" + file);
    }

    public static URL getImage(String type, String file) {
        return getResource("image/" + type + "/" + file);
    }

    public static StoryData getStory(String storyName) {
        Scanner scanner = new Scanner(getResourceAsStream("story/" + storyName + ".txt"), StandardCharsets.UTF_8);
        ArrayList<String> images = new ArrayList<String>();
        ArrayList<String> storyTexts = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            images.add(scanner.nextLine());
            storyTexts.add(scanner.nextLine());
        }
        return new StoryData(images, storyTexts);
    }

    public static Image getPlainImage(String color) {
        switch (color) {
            case "Black":
                return new Image(getImage("story", "Black.jpg").toString());
            case "White":
                return new Image(getImage("story", "White.jpg").toString());
            default:
                return null;
        }

    }

    public static URL getStyleSheet(String fileName) {
        return getResource("css/" + fileName + ".css");
    }
}
