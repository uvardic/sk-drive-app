package app;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.manager.SceneManager;

import java.net.URL;

public class App extends Application {

    public static void main(final String... args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        final URL indexURL = getClass().getResource("/fxml/Index.fxml");

        stage.setTitle("Google Drive App");
        stage.setScene(SceneManager.loadScene(indexURL));
        stage.setResizable(false);
        stage.show();
    }

}
