package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
public class JettyServer {
    public static void main(String[] args) throws IOException {
        int port = 8081;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/books", new BooksHandler());
        server.setExecutor(null);
        server.start();
    }
    static class BooksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = getBooksData(); // Вызываем метод для получения данных из базы
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
    // Метод для получения данных из базы
    private static String getBooksData() {
        StringBuilder stringBuilder = new StringBuilder();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.postgresql.Driver"); // Загружаем драйвер
            // Установка соединения с базой данных
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/books",
                    "postgres", "postgres");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM books");
            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString("author")).append("\n");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
        return stringBuilder.toString();
    }
}