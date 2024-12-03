package com.example.prototype_pfi;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Random;

public class Personnages implements Serializable, IPersonnage {

    // Constante
    static final int MIN_ATTAQUE = 1;
    static final int MIN_DEFENSE = 1;

    // Propriété
    private int idleId;
    private int pas1Id;
    private int pas2Id;
    private int idleAttId;
    private int visage;
    public ImageView activeView;

    public boolean asKey;
    private int pointDeVie = 10;
    private String nom;
    private int attaque = 1;
    private int defense = 0;

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







    public ImageView getImageView(){
        return activeView;
    }
    public void setImageView(ImageView activeView){
        try{
            this.activeView = activeView;
        }
        catch (Exception e) { e.printStackTrace(); }
    }





    public int getIdle(){
        return idleId;
    }
    public int getPas1(){
        return pas1Id;
    }
    public int getPas2(){
        return pas2Id;
    }
    public int getIdleAtt(){ return idleAttId; }
    public void setImageView(int drawableId){
        try{
            activeView.setImageResource(drawableId);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    public int getVisage(){ return visage; }
    public void setDirection(Directions direction){
        this.currentDirection = direction;
    }



    // Constructeur
    Personnages(String nom, int idle, int pas1, int pas2, int idleAtt, int visage, ImageView activeView, int pointDeVie, Directions currentDirection, boolean asKey) {
        this.nom = nom;
        this.idleId = idle;
        this.pas1Id = pas1;
        this.pas2Id = pas2;
        this.idleAttId = idleAtt;
        this.visage = visage;
        this.activeView = activeView;
        setPointDeVie(pointDeVie);
        this.currentDirection = currentDirection;
        this.asKey = asKey;
    }

    // Méthode
    public boolean mort(){
        return pointDeVie <= 0;
    }

    private int degats;
    public void attaquer(IPersonnage cible, TextView vieAffichage){
        if(cible != null && estProche(cible)){
            degats = attaque;

                cible.setImageView(cible.getIdleAtt());

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cible.setImageView(cible.getIdle());
                        cible.setPointDeVie(cible.getPointDeVie()-attaque);
                    }
                }, 500);

            if (cible.mort()){
                attaque += 1;
            }
        }

    }

    public boolean estProche(IPersonnage cible){
        boolean procheX = false;
        boolean procheY = false;
        if(cible.getImageView().getX() + cible.getImageView().getWidth() > activeView.getX() && cible.getImageView().getX() - cible.getImageView().getWidth() < activeView.getX()){
            procheX = true;
        }
        if(cible.getImageView().getY() +  cible.getImageView().getHeight() > activeView.getY() && cible.getImageView().getY()  - cible.getImageView().getWidth() < activeView.getY()){
            procheY = true;
        }
        if(procheX && procheY){
            return true;
        }
        else{
            return false;
        }
    }

}

