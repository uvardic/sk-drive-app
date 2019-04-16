package scene.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import scene.controller.DownloadController;
import scene.controller.IndexController;
import scene.controller.SideMenuController;
import scene.controller.UploadController;

import java.io.IOException;
import java.net.URL;

import static util.Preconditions.checkNotNull;

public class SceneManager {

    public static final double WIDTH = 600;

    public static final double HEIGHT = 400;

    private static final String INDEX_PATH = "/fxml/Index.fxml";

    private static final String SIDE_MENU_PATH = "/fxml/SideMenu.fxml";

    private static final String DOWNLOAD_PATH = "/fxml/Download.fxml";

    private static final String UPLOAD_PATH = "/fxml/Upload.fxml";

    private static SceneComponents<IndexController> indexComponents;

    private static SceneComponents<SideMenuController> sideMenuComponents;

    private static SceneComponents<DownloadController> downloadComponents;

    private static SceneComponents<UploadController> uploadComponents;

    private SceneManager() {}

    public static SceneComponents<IndexController> getIndexComponents() {
        if (indexComponents == null)
            indexComponents = getSceneComponents(INDEX_PATH);

        return indexComponents;
    }

    public static SceneComponents<SideMenuController> getSideMenuComponents() {
        if (sideMenuComponents == null)
            sideMenuComponents = getSceneComponents(SIDE_MENU_PATH);

        return sideMenuComponents;
    }

    public static SceneComponents<DownloadController> getDownloadComponents() {
        if (downloadComponents == null)
            downloadComponents = getSceneComponents(DOWNLOAD_PATH);

        return downloadComponents;
    }

    public static SceneComponents<UploadController> getUploadComponents() {
        if (uploadComponents == null)
            uploadComponents = getSceneComponents(UPLOAD_PATH);

        return uploadComponents;
    }

    private static <T> SceneComponents<T> getSceneComponents(final String parentPath) {
        checkNotNull(parentPath);

        final FXMLLoader fxmlLoader = new FXMLLoader(getParentURL(parentPath));

        try {
            return new SceneComponents<>(fxmlLoader.load(), fxmlLoader.getController());
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

    public static void changeStyleClass(final Parent parent, final String styleClassName) {
        checkNotNull(parent);

        parent.getStyleClass().clear();
        parent.getStyleClass().add(styleClassName);
    }
}
