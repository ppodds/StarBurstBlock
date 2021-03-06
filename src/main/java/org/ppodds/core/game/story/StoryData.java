package org.ppodds.core.game.story;

import javafx.scene.image.Image;
import javafx.util.Pair;
import org.ppodds.core.ResourceManager;

import java.util.ArrayList;

public class StoryData {
    private final ArrayList<String> images;
    private final ArrayList<String> storyTexts;
    private int imageCurrent = 0;
    private int storyTextCurrent = 0;

    public StoryData(ArrayList<String> images, ArrayList<String> storyTexts) {
        this.images = images;
        this.storyTexts = storyTexts;
    }

    public Pair<Image, String> getNext() {
        if (imageCurrent != images.size() && storyTextCurrent != storyTexts.size()) {
            String img = images.get(imageCurrent++);
            if (img.equals("null"))
                img += ".jpg";
            String content = storyTexts.get(storyTextCurrent++);
            if (content.equals("null"))
                content = "";
            return new Pair<Image, String>(new Image(ResourceManager.getImage("story", img).toString()), content);
        } else
            return null;
    }

    public boolean hasNext() {
        return imageCurrent != images.size() && storyTextCurrent != storyTexts.size();
    }

}
