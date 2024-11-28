package com.example.prototype_pfi;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Personnages {

    Drawable idle;
    Drawable pas1;
    Drawable pas2;
    public ImageView activeView;
    int pointDeVie;

    float gridSize;
    public LinearLayout gameGrid;
    int gridSection;
    public int[][] positionGrid;
    public Directions currentDirection;

    Personnages(Drawable idle, Drawable pas1, Drawable pas2, ImageView activeView, int pointDeVie, Directions currentDirection) {
        this.idle = idle;
        this.pas1 = pas1;
        this.pas2 = pas2;
        this.activeView = activeView;
        this.pointDeVie = pointDeVie;
        this.currentDirection = currentDirection;
    }


}








