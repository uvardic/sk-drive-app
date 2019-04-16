package scene.controller;

import com.google.api.services.drive.model.File;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import exceptions.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import system.FileSystem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static util.Preconditions.checkNotNull;

public class DownloadController implements Initializable {

    @FXML
    private JFXComboBox<String> groups;

    @FXML
    private JFXButton downloadButton;

    @FXML
    private Label message;

    private IndexController indexController;

    private FileSystem<File> driveService;

    public DownloadController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    JFXButton getDownloadButton() {
        return downloadButton;
    }

    void showErrorMessage(final String value) {
        checkNotNull(value);

        message.getStyleClass().clear();
        message.getStyleClass().add("error");
        message.setText(value);
    }

    void showApprovedMessage(final String value) {
        checkNotNull(value);

        message.getStyleClass().clear();
        message.getStyleClass().add("approved");
        message.setText(value);
    }

    void setDriveService(final FileSystem<File> driveService) {
        this.driveService = checkNotNull(driveService);
    }

    private File root;

    private List<File> children;

    void initializeGroups() {
        root = driveService.findDirectory(IndexController.ROOT_DIRECTORY).get(0);

        children = driveService.findFileByParent(root);

        children.stream()
                .map(File::getName)
                .forEach(group -> groups.getItems().add(group));

        groups.getSelectionModel().select(0);
    }

    public void downloadAction() {
        final String selected = groups.getValue();

        final boolean selectedExists = children.stream()
                .map(File::getName)
                .anyMatch(selected::equals);

        if (!selectedExists)
            showErrorMessage("Invalid group");
        else {
            try {
                driveService.download(String.format("%s/%s/%s.txt",
                        IndexController.ROOT_DIRECTORY, selected, selected));
            } catch (FileNotFoundException e) {
                showErrorMessage(String.format("%s.txt not found", selected));
                return;
            }
        }

        showApprovedMessage(String.format("%s.txt downloaded", selected));
    }
}
