package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.manager.SceneManager;

public class App extends Application {

    public static void main(final String... args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        stage.setTitle("Google Drive App");
        stage.setScene(new Scene(SceneManager.getIndexComponents().getParent()));
        stage.setResizable(false);
        stage.show();
    }

}
