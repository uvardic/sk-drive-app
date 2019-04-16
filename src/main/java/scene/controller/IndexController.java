package scene.controller;

import com.google.api.services.drive.model.File;
import drive.DriveFileSystemImplementation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import scene.manager.SceneManager;
import system.FileSystem;
import system.FileSystemManager;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    static final String ROOT_DIRECTORY = "UUP2018-januar";

    @FXML
    private Pane menu;

    @FXML
    private Pane content;

    private FileSystem<File> driveService;

    public IndexController() {}

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        initializeDriveService();
        menu.getChildren().clear();
        menu.getChildren().add(SceneManager.getSideMenuComponents().getParent());
        content.getChildren().clear();
        content.getChildren().add(SceneManager.getDownloadComponents().getParent());
    }

    @SuppressWarnings("unchecked")
    private void initializeDriveService() {
        driveService = FileSystemManager.getFileSystem(DriveFileSystemImplementation.class);

        driveService.initialize();

        if (driveService.findDirectory(ROOT_DIRECTORY).isEmpty()) {
            disableApp();
            driveService.terminate();
        } else {
            initializeDownloadScene();
        }
    }

    private void disableApp() {
        final SideMenuController sideMenuController = SceneManager.getSideMenuComponents().getController();

        final DownloadController downloadController = SceneManager.getDownloadComponents().getController();

        sideMenuController.getUploadOption().setDisable(true);
        sideMenuController.getDownloadOption().setDisable(true);
        downloadController.showErrorMessage("Root directory not found");
        downloadController.getDownloadButton().setDisable(true);
    }

    private void initializeDownloadScene() {
        final DownloadController downloadController = SceneManager.getDownloadComponents().getController();

        downloadController.setDriveService(driveService);
        downloadController.initializeGroups();
        downloadController.showApprovedMessage(String.format("Connected to %s", ROOT_DIRECTORY));
    }

    Pane getContent() {
        return content;
    }

}
