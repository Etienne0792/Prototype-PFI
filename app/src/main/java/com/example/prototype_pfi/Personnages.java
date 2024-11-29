package com.example.prototype_pfi;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Personnages {

    // Constante
    static final int MIN_ATTAQUE = 1;
    static final int MIN_DEFENSE = 1;

    // Propriété
    Drawable idle;
    Drawable pas1;
    Drawable pas2;
    public ImageView activeView;
    private int pointDeVie;
    private String nom;
    private int attaque;
    private int defense;

    float gridSize;
    public LinearLayout gameGrid;
    int gridSection;
    public int[][] positionGrid;
    public Directions currentDirection;

    // Get & Set
    public int getPointDeVie() { return pointDeVie; }
    public void setPointDeVie(int pointDeVie) { this.pointDeVie = Math.max(pointDeVie, 0); }

    public String getNom(){ return nom; }
    public void setNom(String nom){ this.nom = !nom.isEmpty() ? nom : "Player1"; }

    public int getAttaque() { return attaque; }
    public void setAttaque(int attaque) {this.attaque = Math.max(attaque, MIN_ATTAQUE);}

    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = Math.max(defense, MIN_DEFENSE); }



    // Constructeur
    Personnages(Drawable idle, Drawable pas1, Drawable pas2, ImageView activeView, int pointDeVie, Directions currentDirection) {
        this.idle = idle;
        this.pas1 = pas1;
        this.pas2 = pas2;
        this.activeView = activeView;
        this.pointDeVie = pointDeVie;
        this.currentDirection = currentDirection;
    }

    // Méthode
    public void finDeJeu(){
        if (pointDeVie <= 0){
            // Mettre fin au jeu
        }
    }

}

