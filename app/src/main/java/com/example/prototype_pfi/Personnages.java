package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

    Personnages(Drawable idle, Drawable pas1, Drawable pas2, ImageView activeView, float gridSize, int gridSection, LinearLayout gameGrid){
        this.idle = idle;
        this.pas1 = pas1;
        this.pas2 = pas2;
        this.activeView = activeView;
        this.gridSize = gridSize;
        this.gameGrid = gameGrid;
        this.gridSection = gridSection;
    }


    Timer deplacement;
    public void deplacementRight(Button right, Activity activity, int[][] positionGrid){
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        deplacement = new Timer();
                        TimerTask task = new TimerTask() {
                            int compteur = 0;
                            boolean utiliserPas1 = true;
                            @Override
                            public void run() {
                                activity.runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                activeView.setRotation(0);
                                                for ( int i = 0; i < positionGrid.length; i++){
                                                    for ( int j = 0; j < positionGrid.length; j++){
                                                        if (j+1 < positionGrid.length && positionGrid[i][j] == 2 && positionGrid[i][j+1] != 1) {
                                                            if(utiliserPas1){
                                                                activeView.setImageResource(R.drawable.pas1);
                                                            }
                                                            else {
                                                                activeView.setImageResource(R.drawable.pas2);
                                                                compteur = 0;
                                                            }
                                                            utiliserPas1 = !utiliserPas1;
                                                            activeView.setX(activeView.getX() + (gridSize/gridSection)/2);
                                                            if (activeView.getX() + activeView.getWidth() >= gridSize / gridSection * (j+2)) {
                                                                positionGrid[i][j] = 0;
                                                                positionGrid[i][j + 1] = 2;
                                                            }
                                                            return;
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                        };
                        deplacement.schedule(task,0,100);
                        break;
                    case MotionEvent.ACTION_UP:
                        activeView.setImageResource(R.drawable.personnage);
                        deplacement.cancel();
                        break;
                }
                return true;
            }
        });
    }

    public void deplacementLeft(Button left, Activity activity, int[][] positionGrid) {
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        deplacement = new Timer();
                        TimerTask task = new TimerTask() {
                            int compteur = 0;
                            boolean utiliserPas1 = true;
                            @Override
                            public void run() {
                                activity.runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                activeView.setRotation(180); // Rotation pour faire face à gauche
                                                for (int i = 0; i < positionGrid.length; i++) {
                                                    for (int j = 0; j < positionGrid.length; j++) {
                                                        if (j - 1 >= 0 && positionGrid[i][j] == 2 && positionGrid[i][j - 1] != 1) {
                                                            if (utiliserPas1) {
                                                                activeView.setImageResource(R.drawable.pas1);
                                                            } else {
                                                                activeView.setImageResource(R.drawable.pas2);
                                                                compteur = 0;
                                                            }
                                                            utiliserPas1 = !utiliserPas1;
                                                            activeView.setX(activeView.getX() - (gridSize / gridSection) / 2);
                                                            if (activeView.getX() + activeView.getWidth() / 2 <= gridSize / gridSection * j) {
                                                                positionGrid[i][j] = 0;
                                                                positionGrid[i][j - 1] = 2;
                                                            }
                                                            return;
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                        };
                        deplacement.schedule(task, 0, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        activeView.setImageResource(R.drawable.personnage);
                        deplacement.cancel();
                        break;
                }
                return true;
            }
        });
    }

    public void deplacementUp(Button up, Activity activity, int[][] positionGrid) {
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        deplacement = new Timer();
                        TimerTask task = new TimerTask() {
                            int compteur = 0;
                            boolean utiliserPas1 = true;
                            @Override
                            public void run() {
                                activity.runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                activeView.setRotation(270); // Rotation pour faire face en haut
                                                for (int i = 0; i < positionGrid.length; i++) {
                                                    for (int j = 0; j < positionGrid.length; j++) {
                                                        if (i - 1 >= 0 && positionGrid[i][j] == 2 && positionGrid[i - 1][j] != 1) {
                                                            if (utiliserPas1) {
                                                                activeView.setImageResource(R.drawable.pas1);
                                                            } else {
                                                                activeView.setImageResource(R.drawable.pas2);
                                                                compteur = 0;
                                                            }
                                                            utiliserPas1 = !utiliserPas1;
                                                            activeView.setY(activeView.getY() - (gridSize / gridSection) / 2);
                                                            if (activeView.getY() + activeView.getHeight() / 2 <= gridSize / gridSection * i + gameGrid.getY()) {
                                                                positionGrid[i][j] = 0;
                                                                positionGrid[i - 1][j] = 2;
                                                            }
                                                            return;
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                        };
                        deplacement.schedule(task, 0, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        activeView.setImageResource(R.drawable.personnage);
                        deplacement.cancel();
                        break;
                }
                return true;
            }
        });
    }

    public void deplacementDown(Button down, Activity activity, int[][] positionGrid) {
        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        deplacement = new Timer();
                        TimerTask task = new TimerTask() {
                            int compteur = 0;
                            boolean utiliserPas1 = true;
                            @Override
                            public void run() {
                                activity.runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                activeView.setRotation(90); // Rotation pour faire face en bas
                                                for (int i = 0; i < positionGrid.length; i++) {
                                                    for (int j = 0; j < positionGrid.length; j++) {
                                                        if (i + 1 < positionGrid.length && positionGrid[i][j] == 2 && positionGrid[i + 1][j] != 1) {
                                                            if (utiliserPas1) {
                                                                activeView.setImageResource(R.drawable.pas1);
                                                            } else {
                                                                activeView.setImageResource(R.drawable.pas2);
                                                                compteur = 0;
                                                            }
                                                            utiliserPas1 = !utiliserPas1;
                                                            activeView.setY(activeView.getY() + (gridSize / gridSection) / 2);
                                                            if (activeView.getY() + activeView.getHeight() >= gridSize / gridSection * (i + 2) + gameGrid.getY()) {
                                                                positionGrid[i][j] = 0;
                                                                positionGrid[i + 1][j] = 2;
                                                            }
                                                            return;
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }
                        };
                        deplacement.schedule(task, 0, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        activeView.setImageResource(R.drawable.personnage);
                        deplacement.cancel();
                        break;
                }
                return true;
            }
        });
    }

}
