package com.example.prototype_pfi;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Random;

/**
 * La classe `Personnages` représente le personnage principal du jeu.
 * Elle implémente l'interface `IPersonnage` et gère les propriétés, les actions et l'état du personnage.
 *
 * @author Daphnée Bastien
 */
public class Personnages implements Serializable, IPersonnage {

    // Propriétés du personnage
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

    public LinearLayout gameGrid;
    public int[][] positionGrid;
    public Directions currentDirection;

    // Getters et Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPointDeVie() { return pointDeVie; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPointDeVie(int pointDeVie) { this.pointDeVie = Math.max(pointDeVie, 0); }

    /**
     * Retourne le nom du personnage.
     *
     * @return Le nom du personnage.
     */
    public String getNom() { return nom; }

    /**
     * Définit le nom du personnage. Si le nom est vide, il est défini par défaut sur "Player1".
     *
     * @param nom Le nouveau nom du personnage.
     */
    public void setNom(String nom) { this.nom = !nom.isEmpty() ? nom : "Player1"; }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAttaque() { return attaque; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttaque(int attaque) { this.attaque = Math.max(attaque, 1); }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageView getImageView() { return activeView; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setImageView(ImageView activeView) {
        try { this.activeView = activeView; } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIdle() { return idleId; }

    /**
     * Retourne l'ID de l'image du premier pas du personnage.
     *
     * @return L'ID de l'image.
     */
    public int getPas1() {
        return pas1Id;
    }

    /**
     * Retourne l'ID de l'image du deuxième pas du personnage.
     *
     * @return L'ID de l'image.
     */
    public int getPas2() {
        return pas2Id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIdleAtt() {
        return idleAttId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setImageView(int drawableId) {
        try { activeView.setImageResource(drawableId); }
        catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Retourne l'ID de l'image du visage du personnage.
     *
     * @return L'ID de l'image.
     */
    public int getVisage() { return visage; }

    /**
     * Définit la direction actuelle du personnage.
     *
     * @param direction La nouvelle direction.
     */
    public void setDirection(Directions direction) { this.currentDirection = direction; }

    /**
     * Constructeur de la classe `Personnages`.
     *
     * @param nom             Le nom du personnage.
     * @param idle            L'ID de l'image du personnage inactif.
     * @param pas1            L'ID de l'image du premier pas.
     * @param pas2            L'ID de l'image du deuxième pas.
     * @param idleAtt         L'ID de l'image du personnage en attaque.
     * @param visage          L'ID de l'image du visage.
     * @param activeView      L'ImageView associé au personnage.
     * @param pointDeVie      Les points de vie initiaux du personnage.
     * @param currentDirection La direction initiale du personnage.
     * @param asKey           Indique si le personnage a la clé.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean mort() { return pointDeVie <= 0; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attaquer(IPersonnage cible, TextView vieAffichage) {
        if (cible != null && estProche(cible)) {
            cible.setImageView(cible.getIdleAtt());
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cible.setImageView(cible.getIdle());
                    cible.setPointDeVie(cible.getPointDeVie() - attaque);
                }
            }, 500);
            if (cible.mort()) {
                attaque += 1;
            }
        }
    }

    /**
     * Vérifie si le personnage est proche d'un autre personnage.
     *
     * @param cible Le personnage cible.
     * @return true si le personnage est proche de la cible, false sinon.
     */
    public boolean estProche(IPersonnage cible) {
        boolean procheX = false;
        boolean procheY = false;
        if (cible.getImageView().getX() + cible.getImageView().getWidth() > activeView.getX() && cible.getImageView().getX() - cible.getImageView().getWidth() < activeView.getX()) {
            procheX = true;
        }
        if (cible.getImageView().getY() + cible.getImageView().getHeight() > activeView.getY() && cible.getImageView().getY() - cible.getImageView().getWidth() < activeView.getY()) {
            procheY = true;
        }
        if (procheX && procheY) { return true; }
        else { return false; }
    }
}

