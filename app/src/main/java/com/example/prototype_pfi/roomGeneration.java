package com.example.prototype_pfi;

import android.widget.Switch;

import androidx.constraintlayout.widget.ConstraintLayout;

public class roomGeneration {

    Personnages hero;
    Directions[] entry;
    int gridSections;
    int gridSize;
    ConstraintLayout gameGrid;
    boolean asKey;

    public roomGeneration(Personnages hero, Directions[] entry, int gridSections, int gridSize, ConstraintLayout gameGrid){
        this.hero = hero;
        this.entry = entry;
        this.gridSections = gridSections;
        this.gridSize = gridSize;
        this.gameGrid = gameGrid;
    }

    public  int[][] gridGeneration(){
        int[][] positionGrid = new int[gridSections][gridSections];
        for (int i = 0; i < positionGrid.length; i++) {
            for (int j = 0; j < positionGrid.length; j++) {
                if (i == 0 || j == 0 || i == positionGrid.length - 1 || j == positionGrid.length - 1) {
                    positionGrid[i][j] = 1;
                } else {
                    positionGrid[i][j] = 0;
                }
            }
        }
        for(int i = 0; i < entry.length; i++){
            if(entry[i] == Directions.droite){
                positionGrid[gridSections / 2 - 1][gridSections - 1] = 5;
                positionGrid[gridSections / 2][gridSections - 1] = 5;
                positionGrid[gridSections / 2 + 1][gridSections - 1] = 5;
            }
            else if (entry[i] == Directions.gauche){
                positionGrid[gridSections / 2 - 1][0] = 3;
                positionGrid[gridSections / 2][0] = 3;
                positionGrid[gridSections / 2 + 1][0] = 3;
            }
            else if (entry[i] == Directions.haut){
                positionGrid[0][gridSections / 2 - 1] = 4;
                positionGrid[0][gridSections / 2] = 4;
                positionGrid[0][gridSections / 2 + 1] = 4;
            }
            else if (entry[i] == Directions.bas){
                positionGrid[gridSections - 1][gridSections/2-1] = 6;
                positionGrid[gridSections -1][gridSections/2] = 6;
                positionGrid[gridSections - 1][gridSections/2+1] = 6;
            }
        }
        switch (hero.currentDirection){
            case droite:
                positionGrid[gridSections/2][1] = 2;
                hero.activeView.setRotation(0);
                hero.activeView.setX(hero.activeView.getX() - gridSize/2f);
                break;
            case gauche:
                positionGrid[gridSections/2][gridSections-2] = 2;
                hero.activeView.setRotation(180);
                hero.activeView.setX(hero.activeView.getX() + gridSize/2f);
                break;
            case haut:
                positionGrid[1][gridSections/2] = 2;
                hero.activeView.setRotation(90);
                hero.activeView.setY(hero.activeView.getY() - gridSize/2f);
                break;
            case bas:
                positionGrid[gridSections -2][gridSections/2] = 2;
                hero.activeView.setRotation(270);
                hero.activeView.setY(hero.activeView.getY() + gridSize/2f);
                break;
            case centre:
                positionGrid[gridSections/2][gridSections/2] = 2;
                break;
        }
        return positionGrid;
    }
}
