package com.myApp.CodNamePI.entities;

public class Myformation {
    private int id;
    private String libelle,discription,type,image,date;




    public Myformation(int id, String libelle, String discription, String type, String image) {
        this.id = id;
        this.libelle = libelle;
        this.discription = discription;
        this.type = type;
        this.image = image;
    }

    public Myformation() {

    }

    public Myformation(String libelle, String description, String type, String image) {
    }

    public Myformation(String toString, String libelle, String discription, String type, String format) {


    }



    @Override
    public String toString() {
        return "Myformation{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", discription='" + discription + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
