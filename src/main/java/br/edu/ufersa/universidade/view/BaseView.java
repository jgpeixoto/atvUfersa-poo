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

public abstract class BaseView extends Application {
    protected final String baseDir = "/br.edu.ufersa.universidade/view/";
    protected final String logoDir = "/br.edu.ufersa.universidade/images/logomenor.png";

    protected String getResName() {
        return "";
    }
    protected void addLogo(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream(logoDir)));
    }
    protected void onStart(Stage primaryStage, Parent root, FXMLLoader loader) {}

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(baseDir + getResName() + ".fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Faculdade Weitinho");
        primaryStage.setScene(new Scene(root));
        this.addLogo(primaryStage);
        this.onStart(primaryStage, root, loader);
        primaryStage.setOnCloseRequest((WindowEvent t) ->
        {
            try {
                DatabaseUtils.getConnection().close();
            } catch (SQLException ignored) {}
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }
}
