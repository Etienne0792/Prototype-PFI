package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

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
        this.positionGrid = positionGrid;
    }


}








