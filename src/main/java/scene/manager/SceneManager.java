package scene.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

import static util.Preconditions.checkNotNull;

public class SceneManager {

    public static final double WIDTH = 600;

    public static final double HEIGHT = 400;

    public static final String INDEX_PATH = "/fxml/Index.fxml";

    public static final String SIDE_MENU_PATH = "/fxml/SideMenu.fxml";

    public static final String DOWNLOAD_PATH = "/fxml/Download.fxml";

    public static final String UPLOAD_PATH = "/fxml/Upload.fxml";

    private SceneManager() {}

    public static Scene loadScene(final String parentPath) {
        checkNotNull(parentPath);

        return new Scene(loadParent(parentPath));
    }

    public static Parent loadParent(final String parentPath) {
        checkNotNull(parentPath);

        try {
            return FXMLLoader.load(getParentURL(parentPath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        throw new RuntimeException();
    }

    public static <E> ScenePair<Parent, E> getScenePair(final String parentPath) {
        checkNotNull(parentPath);

        final FXMLLoader fxmlLoader = new FXMLLoader(getParentURL(parentPath));

        try {
            return new ScenePair<>(fxmlLoader.load(), fxmlLoader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    private static URL getParentURL(final String parentPath) {
        final URL parentURL = SceneManager.class.getResource(parentPath);

        checkNotNull(parentURL, String.format("Parent not found at: %s", parentPath));

        return parentURL;
    }
}
