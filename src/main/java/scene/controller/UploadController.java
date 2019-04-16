package scene.controller;

import app.App;
import com.google.api.services.drive.model.File;
import com.jfoenix.controls.JFXTextField;
import compression.Compression;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import meta.FileMetaData;
import scene.manager.SceneManager;
import system.FileSystem;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;

import static util.Preconditions.checkNotNull;


public class UploadController {

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField indexField;

    @FXML
    private JFXTextField groupFiled;

    @FXML
    private Label fileLabel;

    @FXML
    private Label message;

    private FileSystem<File> driveService;

    private java.io.File selectedFile;

    void setDriveService(final FileSystem<File> driveService) {
        this.driveService = checkNotNull(driveService);
    }

    public void chooseAction() {
        final FileChooser fileChooser = new FileChooser();

        final java.io.File userDir = FileSystemView.getFileSystemView().getDefaultDirectory();

        fileChooser.setInitialDirectory(userDir);

        selectedFile = fileChooser.showOpenDialog(App.getStage());

        if (selectedFile == null)
            return;

        showFileName(selectedFile);
    }

    private void showFileName(final java.io.File selectedFile) {
        SceneManager.changeStyleClass(fileLabel, "approved_file_label");

        fileLabel.setText(selectedFile.getName());
    }

    public void uploadAction() {
        if (!checkInput()) {
            SceneManager.changeStyleClass(message, "error");
            message.setText("Invalid input, some fields are empty");
            return;
        }

        if (!isUploadDirValid()) {
            SceneManager.changeStyleClass(message, "error");
            message.setText(String.format("Invalid input, %s is not a valid group", groupFiled.getText()));
            return;
        }

        message.setText("");

        final java.io.File compressed = compressSelected();

        final String destinationPath = String.format("%s/%s/", IndexController.ROOT_DIRECTORY, groupFiled.getText());

        try {
            driveService.upload(compressed.getPath(), createMetaData(), destinationPath);
        } catch (Exception e) {
            SceneManager.changeStyleClass(message, "error");
            message.setText(String.format("Error while uploading %s", compressed.getName()));
        }

        SceneManager.changeStyleClass(message, "approved");
        message.setText(String.format("%s uploaded", compressed.getName()));

        //noinspection ResultOfMethodCallIgnored
        compressed.delete();
    }

    private boolean checkInput() {
        if (nameField.getText().isEmpty())
            return false;

        if (indexField.getText().isEmpty())
            return false;

        if (groupFiled.getText().isEmpty())
            return false;

        return selectedFile != null;

    }

    private java.io.File compressSelected() {
        final String destination = String.format("uploads/%s.zip", selectedFile.getName());

        try {
            Compression.compress(selectedFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new java.io.File(destination);
    }

    private boolean isUploadDirValid() {
        final DownloadController downloadController = SceneManager.getDownloadComponents().getController();

        return downloadController.getChildren().stream()
                .map(File::getName)
                .anyMatch(fileName -> fileName.equals(groupFiled.getText()));
    }

    private FileMetaData createMetaData() {
        return new FileMetaData.FileMetaDataBuilder()
                .fileName(selectedFile.getName())
                .description(getDescription())
                .build();
    }

    private String getDescription() {
        return String.format("Name: %s\nIndex: %s\nGroup: %s",
                nameField.getText(), indexField.getText(), groupFiled.getText());
    }

}
