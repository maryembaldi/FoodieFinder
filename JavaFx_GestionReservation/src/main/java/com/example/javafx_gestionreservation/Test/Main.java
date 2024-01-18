package com.example.javafx_gestionreservation.Test;

import com.example.javafx_gestionreservation.Entite.Reservation;
import com.example.javafx_gestionreservation.Entite.Restaurant;
import com.example.javafx_gestionreservation.Entite.User;
import com.example.javafx_gestionreservation.Services.ServiceReservation;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Restaurant r = new Restaurant(1);
        User user = new User(1);

        try {
            Date date1 = dateFormat.parse("2023-12-01 10:00:00");
            Date date2 = dateFormat.parse("2023-12-02 14:30:00");
            Date date3 = dateFormat.parse("2023-12-03 18:45:00");
            Date date4 = dateFormat.parse("2023-12-04 08:15:00");

            Reservation reservation1 = new Reservation(1, user, r, date1, 5);
            Reservation reservation2 = new Reservation(2, user , r, date2, 3);
            Reservation reservation3 = new Reservation(3, user, r, date3, 2);
            Reservation reservation4 = new Reservation(4, user, r, date4, 7);

            ServiceReservation serviceReservation = new ServiceReservation();

            // Ajout des réservations
            serviceReservation.ajouter(reservation1);
            serviceReservation.ajouter(reservation2);
            serviceReservation.ajouter(reservation3);
            serviceReservation.ajouter(reservation4);

            // Affichage de toutes les réservations
            List<Reservation> reservations = serviceReservation.listerTous();
            System.out.println("Liste de toutes les réservations :");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }


            // Test de modification
            Reservation reservationToUpdate = reservations.get(0);
            System.out.println("Réservation avant modification : " + reservationToUpdate);
            reservationToUpdate.setNombrePersonnes(8);
            serviceReservation.modifier(reservationToUpdate);
            System.out.println("Réservation modifiée : " + reservationToUpdate);

            //  suppression
            serviceReservation.supprimer(reservationToUpdate.getReservationID());
            System.out.println("Réservation supprimée : " + reservationToUpdate);

            List<Reservation> reservationsAfterUpdateAndDelete = serviceReservation.listerTous();
            System.out.println("Liste de toutes les réservations après modification et suppression :");
            for (Reservation reservation : reservationsAfterUpdateAndDelete) {
                System.out.println(reservation);
            }

            //  consulterReservation
            int idToConsult = reservations.get(0).getReservationID();
            Reservation consultedReservation = serviceReservation.consulter(idToConsult);
            if (consultedReservation != null) {
                System.out.println("Réservation consultée : " + consultedReservation);
            } else {
                System.out.println("Aucune réservation trouvée avec l'ID : " + idToConsult);
            }

            //  chercherReservation par date
            String dateToSearch = "2023-12-02 14:30:00";
            List<Reservation> reservationsByDate = serviceReservation.chercher(dateToSearch);
            System.out.println("Réservations trouvées pour la date " + dateToSearch + " :");
            for (Reservation reservation : reservationsByDate) {
                System.out.println(reservation);
            }

        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }


    }
}
