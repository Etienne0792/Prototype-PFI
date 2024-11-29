package com.example.prototype_pfi;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

public class GenericOnTouchListener implements View.OnTouchListener {

    private final Directions directions;
    Timer deplacement;
    Activity activity;
    int[][] positionGrid;
    int[][] positionGridCopy;
    ImageView activeView;
    int gridSize;
    int gridSections;
    ConstraintLayout gameGrid;
    Personnages hero;
    Intent intent;

    public GenericOnTouchListener(Directions directions, Activity activity, int[][] positionGrid, Personnages hero, int gridSize, int gridSections, ConstraintLayout gameGrid, Intent intent) {
        this.directions = directions;
        this.activity = activity;
        this.positionGrid = positionGrid;
        this.activeView = hero.activeView;
        this.gridSize = gridSize;
        this.gridSections = gridSections;
        this.gameGrid = gameGrid;
        this.hero = hero;
        this.intent = intent;
        positionGridCopy = positionGrid;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(directions == Directions.droite){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    deplacement = new Timer();
                    TimerTask task = new TimerTask() {
                        boolean utiliserPas1 = true;
                        @Override
                        public void run() {
                            activity.runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            activeView.setRotation(0);
                                            for (int i = 0; i < positionGrid.length; i++) {
                                                for (int j = 0; j < positionGrid.length; j++) {
                                                    if (j + 1 < positionGrid.length && positionGrid[i][j] == 2 && positionGrid[i][j + 1] != 1) {
                                                        if (utiliserPas1) {
                                                            activeView.setImageResource(R.drawable.pas1);
                                                        } else {
                                                            activeView.setImageResource(R.drawable.pas2);
                                                        }
                                                        utiliserPas1 = !utiliserPas1;

                                                        if(intent != null && positionGrid[i][j+1] == 5){
                                                            intent.putExtra("hp", hero.getPointDeVie());
                                                            intent.putExtra("Directions", Directions.droite);
                                                            activity.startActivity(intent);
                                                            activity.finish();
                                                        }


                                                        activeView.setX(activeView.getX() + (gridSize / gridSections) / 2);
                                                        if (activeView.getX() + activeView.getWidth() >= gridSize / gridSections * (j + 2)) {
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
                    deplacement.schedule(task, 0, 100);
                    break;
                case MotionEvent.ACTION_UP:
                    activeView.setImageResource(R.drawable.personnage);
                    deplacement.cancel();
                    break;
            }
        }
        if(directions == Directions.gauche){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    deplacement = new Timer();
                    TimerTask task = new TimerTask() {
                        boolean utiliserPas1 = true;
                        @Override
                        public void run() {
                            activity.runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            for (int i = 0; i < positionGrid.length; i++) {
                                                for (int j = 0; j < positionGrid.length; j++) {
                                                    if (j - 1 >= 0 && positionGrid[i][j] == 2 && positionGrid[i][j - 1] != 1) {
                                                        activeView.setRotation(180);
                                                        if (utiliserPas1) {
                                                            activeView.setImageResource(R.drawable.pas1);
                                                        } else {
                                                            activeView.setImageResource(R.drawable.pas2);
                                                        }
                                                        utiliserPas1 = !utiliserPas1;
                                                        if(intent != null && positionGrid[i][j - 1] == 3){
                                                            intent.putExtra("hp", hero.getPointDeVie());
                                                            intent.putExtra("Directions", Directions.gauche);
                                                            activity.startActivity(intent);
                                                            activity.finish();
                                                        }
                                                        activeView.setX(activeView.getX() - (gridSize / gridSections) / 2);
                                                        if (activeView.getX() + activeView.getWidth() / 2 <= gridSize / gridSections * j -2) {
                                                            positionGrid[i][j] = 0;
                                                            positionGrid[i][j - 1] = 2;
                                                        }
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
        }
        if(directions == Directions.haut){
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
                                            // Rotation pour faire face en haut
                                            for (int i = 0; i < positionGrid.length; i++) {
                                                for (int j = 0; j < positionGrid.length; j++) {
                                                    if (i - 1 >= 0 && positionGrid[i][j] == 2 && positionGrid[i - 1][j] != 1) {
                                                        activeView.setRotation(270);
                                                        if (utiliserPas1) {
                                                            activeView.setImageResource(R.drawable.pas1);
                                                        } else {
                                                            activeView.setImageResource(R.drawable.pas2);
                                                            compteur = 0;
                                                        }
                                                        utiliserPas1 = !utiliserPas1;

                                                        if(intent != null && positionGrid[i - 1][j] == 4){
                                                            intent.putExtra("hp", hero.getPointDeVie());
                                                            intent.putExtra("Directions", Directions.bas);
                                                            activity.startActivity(intent);
                                                            activity.finish();
                                                        }

                                                        activeView.setY(activeView.getY() - (gridSize / gridSections) / 2);
                                                        if (activeView.getY() + activeView.getHeight() / 2 <= gridSize / gridSections * i + gameGrid.getY()) {
                                                            positionGrid[i][j] = 0;
                                                            positionGrid[i - 1][j] = 2;
                                                        }

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
        }
        if(directions == Directions.bas) {
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
                                                        activeView.setRotation(90);
                                                        if (utiliserPas1) {
                                                            activeView.setImageResource(R.drawable.pas1);
                                                        } else {
                                                            activeView.setImageResource(R.drawable.pas2);
                                                            compteur = 0;
                                                        }
                                                        utiliserPas1 = !utiliserPas1;

                                                        if(intent != null && positionGrid[i + 1][j] == 6){
                                                            intent.putExtra("hp", hero.getPointDeVie());
                                                            intent.putExtra("Directions", Directions.haut);
                                                            activity.startActivity(intent);
                                                            activity.finish();
                                                        }

                                                        activeView.setY(activeView.getY() + (gridSize / gridSections) / 2);
                                                        if (activeView.getY() + activeView.getHeight() >= gridSize / gridSections * (i + 2) + gameGrid.getY()) {
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
        }
        return true;
    }
}







