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

        SceneManager.changeStyleClass(downloadOption, "selected_menu_option");
        SceneManager.changeStyleClass(uploadOption, "menu_option");
    }

    public void startUploadState() {
        final Pane content = SceneManager.getIndexComponents().getController().getContent();

        content.getChildren().clear();
        content.getChildren().add(SceneManager.getUploadComponents().getParent());

        SceneManager.changeStyleClass(downloadOption, "menu_option");
        SceneManager.changeStyleClass(uploadOption, "selected_menu_option");
    }
}
