package com.example.prototype_pfi;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Interface définissant les propriétés et les méthodes communes aux personnages du jeu.
 *
 * @author Daphnée Bastien
 */
public interface IPersonnage {

    /**
     * Retourne les points de vie actuels du personnage.
     *
     * @return Les points de vie du personnage.
     */
    int getPointDeVie();

    /**
     * Définit les points de vie du personnage.
     *
     * @param vie Les nouveaux points de vie du personnage.
     */
    void setPointDeVie(int vie);

    /**
     * Retourne la valeur d'attaque du personnage.
     *
     * @return La valeur d'attaque du personnage.
     */
    int getAttaque();

    /**
     * Définit la valeur d'attaque du personnage.
     *
     * @param attaque La nouvelle valeur d'attaque du personnage.
     */
    void setAttaque(int attaque);

    /**
     * Retourne l'objet ImageView associé au personnage.
     *
     * @return L'objet ImageView du personnage.
     */
    ImageView getImageView();

    /**
     * Définit l'objet ImageView associé au personnage.
     *
     * @param activeView Le nouvel objet ImageView du personnage.
     */
    void setImageView(ImageView activeView);

    /**
     * Définit l'image du personnage en utilisant l'identifiant de la ressource Drawable.
     *
     * @param drawableId L'identifiant de la ressource Drawable.
     */
    void setImageView(int drawableId);

    /**
     * Vérifie si le personnage est mort (points de vie <= 0).
     *
     * @return true si le personnage est mort, false sinon.
     */
    boolean mort();

    /**
     * Effectue une attaque sur un autre personnage.
     *
     * @param cible         Le personnage cible de l'attaque.
     * @param vieAffichage Le TextView où afficher les points de vie de la cible.
     */
    void attaquer(IPersonnage cible, TextView vieAffichage);

    /**
     * Retourne l'identifiant de la ressource Drawable représentant l'état inactif du personnage.
     *
     * @return L'identifiant de la ressource Drawable.
     */
    int getIdle();

    /**
     * Retourne l'identifiant de la ressource Drawable représentant l'état d'attaque du personnage.
     *
     * @return L'identifiant de la ressource Drawable.
     */
    int getIdleAtt();
}