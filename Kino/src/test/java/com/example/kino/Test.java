package com.example.kino;

import java.sql.*;

public class Test {
    public  static void main(String []args){

        String url = "jdbc:mysql://localhost:3306/kinoworld";
        String username = "root";
        String password = "rootpassword_2023";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String sql = "SELECT * FROM users";

//            PreparedStatement pst = connection.prepareStatement(sql);

            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(
                    "SELECT * FROM users");

            int code;
            String name;
            while (resultSet.next()) {
                name = resultSet.getString("username").trim();
                System.out.println(" Username : " + name);
            }
            resultSet.close();
            statement.close();
            connection.close();


            System.out.println("A new author has been inserted");

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
