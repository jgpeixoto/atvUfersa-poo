package br.edu.ufersa.universidade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import br.edu.ufersa.universidade.utils.DatabaseUtils;
import br.edu.ufersa.universidade.view.AppUI;


public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AppUI.main(args);
        /*
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DatabaseUtils.getConnection();
        var rs = con.getMetaData().getTables("universidade", null, null, null);
        if (!rs.next())
            DatabaseUtils.runMigration("/schema.sql");
        System.out.println("Connected!");
        */
    }
}
