package com.example.javafx_gestionreservation.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private String url = "jdbc:mysql://localhost:3308/foodiefinder";
    private String user = "root";
    private String pswd = "";
    private Connection con;
    private static DataSource data; //on a utilis

    private DataSource(){
        try {
            con = DriverManager.getConnection(url,user,pswd);
            System.out.println("Connexion établie");
        }catch (SQLException e){
            System.out.println(e);
        }
    }
    public Connection getCon (){
        return con;
    }
    public static DataSource getInstance(){
        if (data == null){
            data = new DataSource();
        }
        return data;
    }


}
