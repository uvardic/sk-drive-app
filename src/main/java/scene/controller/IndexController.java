package scene.controller;

import com.google.api.services.drive.model.File;
import drive.DriveFileSystemImplementation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import scene.manager.SceneManager;
import scene.manager.ScenePair;
import system.FileSystem;
import system.FileSystemManager;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    static final String ROOT_DIRECTORY = "UUP2018-januar";

    @FXML
    private SideMenuController sideMenuController;

    @FXML
    private Pane content;

    private ScenePair<Parent, ?> downloadPair;

    private DownloadController downloadController;

    private ScenePair<Parent, ?> uploadPair;

    private UploadController uploadController;

    public IndexController() {}

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        initializeControllers();
        initializeDriveService();
        content.getChildren().clear();
        content.getChildren().add(downloadPair.getParent());
    }

    private void initializeControllers() {
        sideMenuController.setContent(content);

        downloadPair = SceneManager.getScenePair(SceneManager.DOWNLOAD_PATH);
        downloadController = (DownloadController) downloadPair.getController();

        uploadPair = SceneManager.getScenePair(SceneManager.UPLOAD_PATH);
        uploadController = (UploadController) uploadPair.getController();

        sideMenuController.setDownloadPair(downloadPair);
        sideMenuController.setUploadPair(uploadPair);
    }

    @SuppressWarnings("unchecked")
    private void initializeDriveService() {
        final FileSystem driveService = FileSystemManager.getFileSystem(DriveFileSystemImplementation.class);

        driveService.initialize();

        if (driveService.findDirectory(ROOT_DIRECTORY).isEmpty()) {
            disableApp();
            driveService.terminate();
        } else
            initializeGroups(driveService);
    }

    private void disableApp() {
        sideMenuController.getUploadOption().setDisable(true);
        sideMenuController.getDownloadOption().setDisable(true);
        downloadController.showErrorMessage("Root directory not found");
        downloadController.getDownloadButton().setDisable(true);
    }

    private void initializeGroups(final FileSystem<File> driveService) {
        downloadController.setDriveService(driveService);
        downloadController.initializeGroups();
    }

}
