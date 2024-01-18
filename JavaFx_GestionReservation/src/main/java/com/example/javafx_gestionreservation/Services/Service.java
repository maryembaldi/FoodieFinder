package com.example.javafx_gestionreservation.Services;

import java.sql.SQLException;
import java.util.List;

public interface Service<T> {

    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;

    void supprimer(int id) throws SQLException;

    void changerEtatReservation(int id, String nouvelEtat) throws SQLException;

    List<T> listerTous() throws SQLException;

    T consulter(int id) throws SQLException;

   public List<T> chercher(String critere) throws SQLException;

    public T getById(int id) throws SQLException;


}
