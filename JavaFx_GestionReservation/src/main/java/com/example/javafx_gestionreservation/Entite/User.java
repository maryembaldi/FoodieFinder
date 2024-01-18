package com.example.javafx_gestionreservation.Entite;

public class User {
    private int id;

    private String Nom	,	Prenom	,Email 	,MotDePasse, TypeUtilisateur;

    public User(int id, String username) {

    }

    public User(int id, String nom, String prenom, String email, String motDePasse, String typeUtilisateur) {
        this.id = id;
        Nom = nom;
        Prenom = prenom;
        Email = email;
        MotDePasse = motDePasse;
        TypeUtilisateur = typeUtilisateur;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return null;
    }

    public String getEmail() {
        return Email;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    @Override
    public String toString() {
        return getNom() + " " + getPrenom();
    }
}
