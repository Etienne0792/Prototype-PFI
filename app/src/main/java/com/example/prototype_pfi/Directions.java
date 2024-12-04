package com.example.prototype_pfi;

import java.io.Serializable;

/**
 * Énumération représentant les différentes directions possibles dans le jeu.
 *
 * @author Étienne La Rochelle
 */
public enum Directions implements Serializable {
    /**
     * Droite.
     */
    droite,
    /**
     * Gauche.
     */
    gauche,
    /**
     * Haut.
     */
    haut,
    /**
     * Bas.
     */
    bas,
    /**
     * Centre.
     */
    centre
}