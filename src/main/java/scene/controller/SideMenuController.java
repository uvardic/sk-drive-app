package scene.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import scene.manager.SceneManager;
import scene.manager.ScenePair;

import java.net.URL;
import java.util.ResourceBundle;

import static util.Preconditions.checkNotNull;

public class SideMenuController implements Initializable {

    @FXML
    private Label downloadOption;

    @FXML
    private Label uploadOption;

    private Pane content;

    private ScenePair<Parent, ?> downloadPair;

    private ScenePair<Parent, ?> uploadPair;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    Label getDownloadOption() {
        return downloadOption;
    }

    Label getUploadOption() {
        return uploadOption;
    }

    void setContent(final Pane content) {
        this.content = checkNotNull(content);
    }

    void setDownloadPair(final ScenePair<Parent, ?> downloadPair) {
        this.downloadPair = checkNotNull(downloadPair);
    }

    void setUploadPair(final ScenePair<Parent, ?> uploadPair) {
        this.uploadPair = checkNotNull(uploadPair);
    }

    public void startDownloadState() {
        content.getChildren().clear();
        content.getChildren().add(downloadPair.getParent());

        downloadOption.getStyleClass().clear();
        downloadOption.getStyleClass().add("selected_menu_option");

        uploadOption.getStyleClass().clear();
        uploadOption.getStyleClass().add("menu_option");
    }

    public void startUploadState() {
        content.getChildren().clear();
        content.getChildren().add(uploadPair.getParent());

        uploadOption.getStyleClass().clear();
        uploadOption.getStyleClass().add("selected_menu_option");

        downloadOption.getStyleClass().clear();
        downloadOption.getStyleClass().add("menu_option");
    }
}
