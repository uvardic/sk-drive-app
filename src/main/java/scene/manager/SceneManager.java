package scene.manager;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;

import static util.Preconditions.checkNotNull;

public class SceneManager {

    public static final double APP_WIDTH = 600;

    public static final double APP_HEIGHT = 400;

    public static final String INDEX_SCENE = "";

    private SceneManager() {}

    public static Scene loadScene(final URL parentURL) {
        checkNotNull(parentURL);

        return new Scene(loadParent(parentURL));
    }

    public static Parent loadParent(final String parentPath) {
        checkNotNull(parentPath);

        final URL parentURL = SceneManager.class.getResource(parentPath);

        return loadParent(parentURL);
    }

    public static Parent loadParent(final URL parentURL) {
        checkNotNull(parentURL);

        try {
            return FXMLLoader.load(parentURL);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        throw new RuntimeException(String.format("Parent not found at: %s", parentURL));
    }

}
