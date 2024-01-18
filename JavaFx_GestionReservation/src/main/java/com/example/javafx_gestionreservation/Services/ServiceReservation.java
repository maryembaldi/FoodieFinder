package com.example.javafx_gestionreservation.Services;

import com.example.javafx_gestionreservation.Entite.Reservation;
import com.example.javafx_gestionreservation.Entite.User;
import com.example.javafx_gestionreservation.Entite.Restaurant;
import com.example.javafx_gestionreservation.Utils.DataSource;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceReservation implements Service<Reservation> {

    private Connection con = DataSource.getInstance().getCon();

    ServiceRestaurant serviceRestaurant = new ServiceRestaurant();
    ServiceUser serviceUser = new ServiceUser();
    private Statement ste;

    public ServiceReservation() {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        try {
            User user = serviceUser.getById(reservation.getUser().getId());
            if (user == null) {
                System.out.println("Error: User with ID " + reservation.getUser().getId() + " does not exist.");
                return;
            }

            Restaurant restaurant = serviceRestaurant.getById(reservation.getRestaurant().getRestaurantID());
            if (restaurant == null) {
                System.out.println("Error: Restaurant with ID " + reservation.getRestaurant().getRestaurantID() + " does not exist.");
                return;
            }

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateTimeFormat.format(reservation.getDateHeureReservation());

            String req = "INSERT INTO `reservations` (`ReservationID`, `UserId`, `RestaurantId`, `DateHeureReservation`, `NombrePersonnes`, `StatutReservation`) " +
                    "VALUES (NULL, " + reservation.getUser().getId() + ", " + reservation.getRestaurant().getRestaurantID() + ", '" +
                    formattedDate + "', " + reservation.getNombrePersonnes() + ", 'DISPONIBLE');";
            int res = ste.executeUpdate(req);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void modifier(Reservation reservation) throws SQLException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateTimeFormat.format(reservation.getDateHeureReservation());

        String req = "UPDATE `reservations` SET `DateHeureReservation` = '" + formattedDate + "', " +
                "`NombrePersonnes` = " + reservation.getNombrePersonnes() +
                " WHERE `ReservationID` = " + reservation.getReservationID();

        int res = ste.executeUpdate(req);
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `reservations` WHERE `ReservationID` = " + id;
        int res = ste.executeUpdate(req);
    }

    @Override
    public void changerEtatReservation(int id, String nouvelEtat) throws SQLException {
        String req = "UPDATE `reservations` SET `StatutReservation` = '" + nouvelEtat + "' WHERE `ReservationID` = " + id;
        int res = ste.executeUpdate(req);
    }

    @Override
    public List<Reservation> listerTous() throws SQLException {
        List<Reservation> list = new ArrayList<>();
        try {
            ResultSet resultSet = ste.executeQuery("SELECT * FROM `reservations`");
            while (resultSet.next()) {
                Reservation reservation = creerReservationAPartirDeResultSet(resultSet);
                list.add(reservation);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public Reservation consulter(int id) throws SQLException {
        String req = "SELECT * FROM `reservations` WHERE `ReservationID` = " + id;
        ResultSet resultSet = ste.executeQuery(req);

        if (resultSet.next()) {
            Reservation reservation = creerReservationAPartirDeResultSet(resultSet);
            return reservation;
        } else {
            return null;
        }
    }

    @Override
    public List<Reservation> chercher(String critere) throws SQLException {
        List<Reservation> list = new ArrayList<>();
        try {
            try {
                int id = Integer.parseInt(critere);
                String reqById = "SELECT * FROM `reservations` WHERE `ReservationID` = " + id;
                ResultSet resultSetById = ste.executeQuery(reqById);
                while (resultSetById.next()) {
                    Reservation reservation = creerReservationAPartirDeResultSet(resultSetById);
                    list.add(reservation);
                }
            } catch (NumberFormatException e) {
                String reqByDate = "SELECT * FROM `reservations` WHERE `DateHeureReservation` = '" + critere + "'";
                ResultSet resultSetByDate = ste.executeQuery(reqByDate);

                while (resultSetByDate.next()) {
                    Reservation reservation = creerReservationAPartirDeResultSet(resultSetByDate);
                    list.add(reservation);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    private Reservation creerReservationAPartirDeResultSet(ResultSet resultSet) throws SQLException {
        int idRes = resultSet.getInt("ReservationID");
        Timestamp timestamp = resultSet.getTimestamp("DateHeureReservation");
        Date dateRes = timestamp != null ? new Date(timestamp.getTime()) : null;
        int nbrePlace = resultSet.getInt("NombrePersonnes");
        String statutResString = resultSet.getString("StatutReservation");
        Reservation.StatutReservation etatRes = Reservation.StatutReservation.valueOf(statutResString.toUpperCase());
        int idUser = resultSet.getInt("UserID");
        int idRestaurant = resultSet.getInt("RestaurantID");
        User user = serviceUser.getById(idUser);
        Restaurant restaurant = serviceRestaurant.getById(idRestaurant);
        Reservation reservation = new Reservation(idRes, user, restaurant, dateRes, nbrePlace);
        reservation.setEtatRes(etatRes);
        return reservation;
    }

    public Reservation getById(int id) throws SQLException {
        String req = "SELECT * FROM `reservations` WHERE `ReservationID` = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return creerReservationAPartirDeResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
