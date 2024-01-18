package com.example.javafx_gestionreservation.Services;

import com.example.javafx_gestionreservation.Entite.User;
import com.example.javafx_gestionreservation.Utils.DataSource;

import java.sql.*;
import java.util.List;

public class ServiceUser implements Service<User>{

    private Connection con = DataSource.getInstance().getCon();
    private Statement ste;
    @Override
    public void ajouter(User user) throws SQLException {

    }

    @Override
    public void modifier(User user) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public void changerEtatReservation(int id, String nouvelEtat) throws SQLException {

    }

    @Override
    public List<User> listerTous() throws SQLException {
        return null;
    }

    @Override
    public User consulter(int id) throws SQLException {
        return null;
    }

    @Override
    public List<User> chercher(String critere) throws SQLException {
        return null;
    }

    @Override
    public User getById(int id) throws SQLException {
        String query = "SELECT * FROM `users` WHERE `UserId` = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve user details from the ResultSet
                    String username = resultSet.getString("Nom");
                    String prenom = resultSet.getString("Prenom");
                    String email = resultSet.getString("Email");
                    String motDePasse = resultSet.getString("MotDePasse");
                    String typeUtilisateur = resultSet.getString("TypeUtilisateur");

                    // Create and return a User object
                    return new User(id, username, prenom, email, motDePasse, typeUtilisateur);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;  // Return null if no user is found with the given ID
    }

}
