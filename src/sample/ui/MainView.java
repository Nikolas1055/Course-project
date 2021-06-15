package sample.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.services.StageFabric;
import sample.ui.views.Config;

/**
 * Основной класс
 */
public class MainView extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage stage = new StageFabric(Config.AUTH).stage();
        stage.show();
    }
}
