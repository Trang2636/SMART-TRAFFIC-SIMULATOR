package com.ra.bt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital";
    private static final String USER = "root";
    private static final String PASS = "123456$";

    public static Connection openConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Ket noi CSDL thanh cong");
            return conn;
        } catch (SQLException e) {
            System.out.println("Ket noi CSDL that bai");
            e.printStackTrace();
        }
        return null;
    }
}


