package scene.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import scene.manager.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    @FXML
    private SideMenuController sideMenuController;

    @FXML
    private Pane content;

    public IndexController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sideMenuController.setContent(content);

        content.getChildren().clear();
        content.getChildren().add(SceneManager.loadParent("/fxml/Download.fxml"));
    }
}
