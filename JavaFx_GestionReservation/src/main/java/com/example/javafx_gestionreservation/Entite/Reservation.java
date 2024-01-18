package com.example.javafx_gestionreservation.Entite;

import java.util.Date;

public class Reservation {
    private int ReservationID;
    private User user;

    private Restaurant restaurant;
    private Date DateHeureReservation;

    private int NombrePersonnes;

    private StatutReservation etatRes;
    public enum StatutReservation {
        DISPONIBLE,
        INDISPONIBLE,
    }


    public Reservation(int reservationID, User user, Restaurant restaurant, Date dateHeureReservation, int nombrePersonnes) {
        ReservationID = reservationID;
        this.user = user;
        this.restaurant = restaurant;
        DateHeureReservation = dateHeureReservation;
        NombrePersonnes = nombrePersonnes;
        this.etatRes = etatRes;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "ReservationID=" + ReservationID +
                ", user=" + user.getPrenom()+user.getNom() +
                ", restaurant=" + restaurant +
                ", DateHeureReservation=" + DateHeureReservation +
                ", NombrePersonnes=" + NombrePersonnes +
                ", etatRes=" + etatRes +
                '}';
    }

    public int getReservationID() {
        return ReservationID;
    }

    public void setRestaurantID(int restaurantID) {
        ReservationID = restaurantID;
    }

    public Date getDateHeureReservation() {
        return DateHeureReservation;
    }

    public void setDateHeureReservation(Date dateHeureReservation) {
        DateHeureReservation = dateHeureReservation;
    }

    public int getNombrePersonnes() {
        return NombrePersonnes;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        NombrePersonnes = nombrePersonnes;
    }

    public StatutReservation getEtatRes() {
        return etatRes;
    }

    public void setEtatRes(StatutReservation etatRes) {
        this.etatRes = etatRes;
    }

    public void setReservationID(int reservationID) {
        ReservationID = reservationID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }


    public Reservation setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

}

