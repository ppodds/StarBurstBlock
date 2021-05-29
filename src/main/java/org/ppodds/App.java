package org.ppodds;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.ppodds.core.ResourceManager;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static void setRoot(String fxml) {
        Parent root = loadFXML(fxml);
        if (root != null)
            scene.setRoot(root);
    }

    private static Parent loadFXML(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("scenes/" + fxml + ".fxml"));
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        scene = new Scene(loadFXML("Start"));
        stage.getIcons().add(new Image(ResourceManager.getImage("icon", "Icon.jpg").toString()));
        stage.setTitle("Star Burst Block");
        stage.setScene(scene);
        stage.show();
    }

}