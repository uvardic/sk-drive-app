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

    private void showErrorMessage(final String value) {
        SceneManager.changeStyleClass(message, "error");
        message.setText(value);
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
        if (!checkInput())
            return;

        message.setText("");

        final java.io.File compressed = compressSelected();

        final String destinationPath = String.format("%s/%s/", IndexController.ROOT_DIRECTORY, groupFiled.getText());

        try {
            driveService.upload(compressed.getAbsolutePath(), createMetaData(compressed), destinationPath);
        } catch (Exception e) {
            showErrorMessage(String.format("Error while uploading %s", compressed.getName()));
            e.printStackTrace();
        }

        SceneManager.changeStyleClass(message, "approved");
        message.setText("Response uploaded");

        //noinspection ResultOfMethodCallIgnored
        compressed.delete();
    }

    private boolean checkInput() {
        if (nameField.getText().isEmpty()) {
            showErrorMessage("Invalid input, name field is empty");
            return false;
        }

        if (indexField.getText().isEmpty()) {
            showErrorMessage("Invalid input, index field is empty");
            return false;
        }

        if (groupFiled.getText().isEmpty()) {
            showErrorMessage("Invalid input, group field is empty");
            return false;
        }

        if (selectedFile == null) {
            showErrorMessage("Invalid input, file not selected");
            return false;
        }

        return  validateInput(nameField.getText()) &&
                validateInput(indexField.getText()) &&
                validateInput(groupFiled.getText()) &&
                isUploadDirValid();
    }

    private boolean validateInput(final String input) {
        if (input.contains("\\")) {
            showErrorMessage("Invalid input, contains invalid char \\");
            return false;
        }

        if (input.contains("/")) {
            showErrorMessage("Invalid input, contains invalid char /");
            return false;
        }

        if (input.contains("_")) {
            showErrorMessage("Invalid input, contains invalid char _");
            return false;
        }

        return true;
    }

    private boolean isUploadDirValid() {
        final DownloadController downloadController = SceneManager.getDownloadComponents().getController();

        boolean valid = downloadController.getChildren().stream()
                .map(File::getName)
                .anyMatch(fileName -> fileName.equals(groupFiled.getText()));

        if (!valid)
            showErrorMessage(String.format("Invalid input, %s is not a valid group", groupFiled.getText()));

        return valid;
    }

    private java.io.File compressSelected() {
        final String destination = String.format("uploads/%s", createCompressedFileName());

        try {
            Compression.compress(selectedFile, destination);
        } catch (IOException e) {
            showErrorMessage("Error while compressing");
            e.printStackTrace();
        }

        return new java.io.File(destination);
    }

    private String createCompressedFileName() {
        return String.format("[UUP] Response %s_%s_%s.zip",
                nameField.getText().toLowerCase(),
                indexField.getText().toLowerCase(),
                groupFiled.getText().toLowerCase());
    }

    private FileMetaData createMetaData(final java.io.File file) {
        return new FileMetaData.FileMetaDataBuilder()
                .fileName(file.getName())
                .description(getDescription())
                .build();
    }

    private String getDescription() {
        return String.format("Name: %s\nIndex: %s\nGroup: %s",
                nameField.getText(), indexField.getText(), groupFiled.getText());
    }

}
