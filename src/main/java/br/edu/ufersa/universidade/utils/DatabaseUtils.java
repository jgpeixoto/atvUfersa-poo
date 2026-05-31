package br.edu.ufersa.universidade.utils;
import java.io.BufferedReader;
import java.sql.*;
import java.io.InputStreamReader;

public class DatabaseUtils {

    public static void runMigration(Connection connection, String filePath)
    {
        try {
            Statement statement = connection.createStatement();
            var reader = new InputStreamReader(DatabaseUtils.class.getResourceAsStream(filePath));
            BufferedReader br = new BufferedReader(reader);

            // String Builder to build the query line by line.
            StringBuilder query = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().startsWith("-- ")) {
                    continue;
                }
                query.append(line).append(" ");

                if (line.trim().endsWith(";")) {
                    statement.execute(query.toString().trim());
                    query = new StringBuilder();
                }
            }

            System.out.println("Successfully migrated file " + filePath + "!\nConnection now has the following tables:");
            var rs = connection.getMetaData().getTables("universidade", null, null, null);
            if (rs.next())
                do {
                System.out.println(rs.getString(3));
            } while (rs.next());
            br.close();
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
