package br.edu.ufersa.universidade;

import br.edu.ufersa.universidade.utils.DatabaseUtils;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;


public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        WelcomeView welcome = new WelcomeView();
        welcome.start(primaryStage);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DatabaseUtils.getConnection();
        var rs = con.getMetaData().getTables("universidade", null, null, null);
        if (!rs.next())
            DatabaseUtils.runMigration("/schema.sql");
        System.out.println("Connected!");
        launch(args);
    }
}
