package org.example;

import java.sql.*;

public class App {
    public static void main( String[] args ){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/books", "postgres", "postgres");

            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM books");

            System.out.println(resultSet.toString());

            while (resultSet.next()) {
                String value = resultSet.getString("author");
                System.out.println(value);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
