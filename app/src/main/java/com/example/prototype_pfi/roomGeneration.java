package com.example.prototype_pfi;

import android.widget.Switch;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * La classe `roomGeneration` est responsable de la génération de la grille de jeu pour une salle.
 * Elle prend en compte les dimensions de la grille, le personnage et les entrées de la salle.
 *
 * @author Étienne La Rochelle
 */
public class roomGeneration {

    int gridSections; // Nombre de sections dans la grille
    int gridSize; // Taille de la grille en pixels
    Personnages hero; // Le personnage du joueur
    Directions[] entry; // Tableau des directions d'entrée de la salle

    /**
     * Constructeur de la classe `roomGeneration`.
     *
     * @param hero        Le personnage du joueur.
     * @param entry       Tableau des directions d'entrée de la salle.
     * @param gridSections Nombre de sections dans la grille.
     * @param gridSize    Taille de la grille en pixels.
     */
    public roomGeneration(Personnages hero, Directions[] entry, int gridSections, int gridSize) {
        this.hero = hero;
        this.entry = entry;
        this.gridSections = gridSections;
        this.gridSize = gridSize;
    }

    /**
     * Génère la grille de jeu en fonction des paramètres de la salle.
     * La grille est représentée par un tableau 2D d'entiers.
     * Les murs sont représentés par la valeur 1, les espaces vides par 0 et le personnage par 2.
     *
     * @return Un tableau 2D d'entiers représentant la grille de jeu.
     */
    public int[][] gridGeneration() {
        int[][] positionGrid = new int[gridSections][gridSections];

        // Initialiser la grille avec des murs sur les bords et des espaces vides à l'intérieur
        for (int i = 0; i < positionGrid.length; i++) {
            for (int j = 0; j < positionGrid.length; j++) {
                if (i == 0 || j == 0 || i == positionGrid.length - 1 || j == positionGrid.length - 1) {
                    positionGrid[i][j] = 1; // Mur
                } else {
                    positionGrid[i][j] = 0; // Espace vide
                }
            }
        }

        // Enlever les murs pour créer les entrées de la salle
        for (int i = 0; i < entry.length; i++) {
            switch (entry[i]) {
                case droite:
                    positionGrid[gridSections / 2 - 1][gridSections - 1] = 0;
                    positionGrid[gridSections / 2][gridSections - 1] = 0;
                    positionGrid[gridSections / 2 + 1][gridSections - 1] = 0;
                    break;
                case gauche:
                    positionGrid[gridSections / 2 - 1][0] = 0;
                    positionGrid[gridSections / 2][0] = 0;
                    positionGrid[gridSections / 2 + 1][0] = 0;
                    break;
                case haut:
                    positionGrid[0][gridSections / 2 - 1] = 0;
                    positionGrid[0][gridSections / 2] = 0;
                    positionGrid[0][gridSections / 2 + 1] = 0;
                    break;
                case bas:
                    positionGrid[gridSections - 1][gridSections / 2 - 1] = 0;
                    positionGrid[gridSections - 1][gridSections / 2] = 0;
                    positionGrid[gridSections - 1][gridSections / 2 + 1] = 0;
                    break;
            }
        }

        // Placer le personnage sur la grille en fonction de sa direction actuelle
        switch (hero.currentDirection) {
            case droite:
                positionGrid[gridSections / 2][1] = 2;
                hero.activeView.setRotation(0);
                hero.activeView.setX(hero.activeView.getX() - gridSize / 2f);
                break;
            case gauche:
                positionGrid[gridSections / 2][gridSections - 2] = 2;
                hero.activeView.setRotation(180);
                hero.activeView.setX(hero.activeView.getX() + gridSize / 2f);
                break;
            case haut:
                positionGrid[gridSections - 2][gridSections / 2] = 2;
                hero.activeView.setRotation(270);
                hero.activeView.setY(hero.activeView.getY() + gridSize / 2f);
                break;
            case bas:
                positionGrid[1][gridSections / 2] = 2;
                hero.activeView.setRotation(90);
                hero.activeView.setY(hero.activeView.getY() - gridSize / 2f);
                break;
            case centre:
                positionGrid[gridSections / 2][gridSections / 2] = 2;
                break;
        }

        return positionGrid;
    }
}
