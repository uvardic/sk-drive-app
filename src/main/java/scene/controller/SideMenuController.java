package scene.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import scene.manager.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class SideMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private Pane content;

    void setContent(final Pane content) {
        this.content = content;
    }

    @FXML
    private Label downloadOption;

    @FXML
    private Label uploadOption;

    public void startDownloadState() {
        content.getChildren().clear();
        content.getChildren().add(SceneManager.loadParent("/fxml/Download.fxml"));

        downloadOption.getStyleClass().clear();
        downloadOption.getStyleClass().add("selected_menu_option");

        uploadOption.getStyleClass().clear();
        uploadOption.getStyleClass().add("menu_option");
    }

    public void startUploadState() {
        content.getChildren().clear();
        content.getChildren().add(SceneManager.loadParent("/fxml/Upload.fxml"));

        uploadOption.getStyleClass().clear();
        uploadOption.getStyleClass().add("selected_menu_option");

        downloadOption.getStyleClass().clear();
        downloadOption.getStyleClass().add("menu_option");
    }
}
