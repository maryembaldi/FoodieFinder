package com.example.javafx_gestionreservation.Services;

import com.example.javafx_gestionreservation.Entite.Restaurant;
import com.example.javafx_gestionreservation.Utils.DataSource;



import java.sql.*;
import java.util.List;

public class ServiceRestaurant implements Service<Restaurant> {
    Connection con = DataSource.getInstance().getCon();
    private Statement ste;

    public ServiceRestaurant() {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    @Override
    public void ajouter(Restaurant restaurant) throws SQLException {

    }

    @Override
    public void modifier(Restaurant restaurant) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public void changerEtatReservation(int id, String nouvelEtat) throws SQLException {

    }

    @Override
    public List<Restaurant> listerTous() throws SQLException {
        return null;
    }

    @Override
    public Restaurant consulter(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Restaurant> chercher(String critere) throws SQLException {
        return null;
    }

    @Override
    public Restaurant getById(int id) throws SQLException {
        String sql = "SELECT * FROM Restaurants WHERE RestaurantID=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long restaurantID = resultSet.getLong("RestaurantID");
                String nom = resultSet.getString("NomRestaurant");
                String adresse = resultSet.getString("AdresseRestaurant");
                String description = resultSet.getString("Description");
                double noteMoyenne = resultSet.getDouble("NoteMoyenne");

                return new Restaurant((int) restaurantID,nom, adresse, description, noteMoyenne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}