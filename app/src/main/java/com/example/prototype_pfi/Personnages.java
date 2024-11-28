package com.example.prototype_pfi;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class Personnages {

    Drawable idle;
    Drawable pas1;
    Drawable pas2;
    ImageView activeView;

    float gridSize;
    LinearLayout gameGrid;
    int gridSection;
    public int[][] positionGrid;

    Personnages(Drawable idle, Drawable pas1, Drawable pas2, ImageView activeView, float gridSize, int gridSection, LinearLayout gameGrid, int[][] positionGrid){
        this.idle = idle;
        this.pas1 = pas1;
        this.pas2 = pas2;
        this.activeView = activeView;
        this.gridSize = gridSize;
        this.gameGrid = gameGrid;
        this.gridSection = gridSection;
        this.positionGrid = positionGrid;
    }











}
