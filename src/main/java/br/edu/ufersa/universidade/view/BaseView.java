package br.edu.ufersa.universidade.view;

import br.edu.ufersa.universidade.utils.DatabaseUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;

public class BaseView extends Application {
    private final String baseDir = "/br.edu.ufersa.universidade/view/";
    private final String logoDir = "/br.edu.ufersa.universidade/images/logomenor.png";
    protected String getResName() {
        return "";
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource(baseDir + getResName() + ".fxml"));
        primaryStage.setTitle("Faculdade Weitinho");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(logoDir)));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                try {
                    DatabaseUtils.getConnection().close();
                } catch (SQLException ignored) {}
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.show();
    }
}
