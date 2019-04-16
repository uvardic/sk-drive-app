package scene.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import scene.manager.SceneManager;

public class SideMenuController {

    @FXML
    private Label downloadOption;

    @FXML
    private Label uploadOption;

    public SideMenuController() {}

    Label getDownloadOption() {
        return downloadOption;
    }

    Label getUploadOption() {
        return uploadOption;
    }

    public void startDownloadState() {
        final Pane content = SceneManager.getIndexComponents().getController().getContent();

        content.getChildren().clear();
        content.getChildren().add(SceneManager.getDownloadComponents().getParent());

        downloadOption.getStyleClass().clear();
        downloadOption.getStyleClass().add("selected_menu_option");

        uploadOption.getStyleClass().clear();
        uploadOption.getStyleClass().add("menu_option");
    }

    public void startUploadState() {
        final Pane content = SceneManager.getIndexComponents().getController().getContent();

        content.getChildren().clear();
        content.getChildren().add(SceneManager.getUploadComponents().getParent());

        uploadOption.getStyleClass().clear();
        uploadOption.getStyleClass().add("selected_menu_option");

        downloadOption.getStyleClass().clear();
        downloadOption.getStyleClass().add("menu_option");
    }
}
