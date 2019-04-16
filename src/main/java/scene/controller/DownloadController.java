package scene.controller;

import com.google.api.services.drive.model.File;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import exceptions.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import system.FileSystem;

import java.util.List;

import static util.Preconditions.checkNotNull;

public class DownloadController {

    @FXML
    private JFXComboBox<String> groups;

    @FXML
    private JFXButton downloadButton;

    @FXML
    private Label message;

    private FileSystem<File> driveService;

    private List<File> children;

    public DownloadController() {}

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

    void initializeGroups() {
        final File root = driveService.findDirectory(IndexController.ROOT_DIRECTORY).get(0);

        children = driveService.findFileByParent(root);

        children.stream()
                .map(File::getName)
                .forEach(group -> groups.getItems().add(group));

        groups.getSelectionModel().select(0);
    }

    void setDriveService(final FileSystem<File> driveService) {
        this.driveService = checkNotNull(driveService);
    }

    public void downloadAction() {
        final String selected = groups.getValue();

        if (!selectedExists(selected)) {
            showErrorMessage("Invalid group");
            return;
        }

        try {
            driveService.download(String.format("%s/%s/%s.txt",
                    IndexController.ROOT_DIRECTORY, selected, selected));
        } catch (FileNotFoundException e) {
            showErrorMessage(String.format("%s.txt not found", selected));
            return;
        }

        showApprovedMessage(String.format("%s.txt downloaded", selected));
    }

    private boolean selectedExists(final String selected) {
        return children.stream()
                .map(File::getName)
                .anyMatch(selected::equals);
    }
}
