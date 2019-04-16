package app;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.manager.SceneManager;

public class App extends Application {

    public static void main(final String... args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        stage.setTitle("Google Drive App");
        stage.setScene(SceneManager.loadScene(SceneManager.INDEX_PATH));
        stage.setResizable(false);
        stage.show();
    }

}
