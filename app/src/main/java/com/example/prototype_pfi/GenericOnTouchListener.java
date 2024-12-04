package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

public class GenericOnTouchListener implements View.OnTouchListener {

    Directions directions;
    Timer deplacement;
    Activity activity;
    int[][] positionGrid;
    ImageView activeView;
    int gridSize;
    int gridSections;
    ConstraintLayout gameGrid;
    Personnages hero;
    Intent intent;

    public GenericOnTouchListener(Directions directions, Activity activity, int[][] positionGrid, Personnages hero, int gridSize, int gridSections, Intent intent) {
        this.directions = directions;
        this.activity = activity;
        this.positionGrid = positionGrid;
        this.hero = hero;
        this.activeView = hero.activeView;
        this.gridSize = gridSize;
        this.gridSections = gridSections;
        this.intent = intent;
        this.gameGrid = activity.findViewById(R.id.gameGrid);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // Déplacement vers la droite
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
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void run() {
                                            for (int i = 0; i < positionGrid.length; i++) {
                                                for (int j = 0; j < positionGrid.length; j++) {
                                                    if (j + 1 < positionGrid.length && positionGrid[i][j] == 2 && positionGrid[i][j + 1] != 1) {
                                                        try{
                                                            activeView.setRotation(0);
                                                            if (utiliserPas1) {
                                                                activeView.setImageResource(hero.getPas1());
                                                            }
                                                            else {
                                                                activeView.setImageResource(hero.getPas2());
                                                            }
                                                            utiliserPas1 = !utiliserPas1;


                                                            activeView.setX(activeView.getX() + (gridSize / gridSections) / 2);
                                                            if (activeView.getX() + activeView.getWidth() >= gridSize / gridSections * (j + 2)) {
                                                                positionGrid[i][j] = 0;
                                                                positionGrid[i][j + 1] = 2;
                                                            }

                                                            if(activity.getClass() == room9.class && positionGrid[i][j +1] == 3){
                                                                hero.asKey = true;
                                                                TextView keyFound = activity.findViewById(R.id.startMessage);
                                                                keyFound.setText("Vous avez trouvé la clé !\n"+ "Retourner a votre point de départ\n"+"pour vous échapé.");
                                                                ImageView chest = activity.findViewById(R.id.chest);
                                                                chest.setImageResource(R.drawable.open_chest);
                                                            }
                                                            else if(hero.activeView.getX() > gameGrid.getX() + gameGrid.getWidth() - hero.activeView.getWidth()){
                                                                hero.setImageView(null);
                                                                hero.setDirection(directions);
                                                                intent.putExtra("personnage", hero);
                                                                activity.startActivity(intent);
                                                                activity.finish();
                                                            }
                                                        }
                                                        catch(Exception e){
                                                            e.printStackTrace();
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
                    activeView.setImageResource(hero.getIdle());
                    deplacement.cancel();
                    break;
            }
        }

        // Déplacement vers la gauche
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
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void run() {
                                            for (int i = 0; i < positionGrid.length; i++) {
                                                for (int j = 0; j < positionGrid.length; j++) {
                                                    if (j - 1 >= 0 && positionGrid[i][j] == 2 && positionGrid[i][j - 1] != 1) {
                                                        try{
                                                            activeView.setRotation(180);
                                                            if (utiliserPas1) {
                                                                activeView.setImageResource(hero.getPas1());
                                                            } else {
                                                                activeView.setImageResource(hero.getPas2());
                                                            }
                                                            utiliserPas1 = !utiliserPas1;

                                                            activeView.setX(activeView.getX() - (gridSize / gridSections) / 2);
                                                            if (activeView.getX() + activeView.getWidth() / 2 <= gridSize / gridSections * j -2) {
                                                                positionGrid[i][j] = 0;
                                                                positionGrid[i][j - 1] = 2;
                                                            }

                                                            if(activity.getClass() == room9.class && positionGrid[i][j -1] == 3){
                                                                hero.asKey = true;
                                                                TextView keyFound = activity.findViewById(R.id.startMessage);
                                                                keyFound.setText("Vous avez trouvé la clé !\n"+ "Retourner a votre point de départ\n"+"pour vous échapé.");
                                                                ImageView chest = activity.findViewById(R.id.chest);
                                                                chest.setImageResource(R.drawable.open_chest);
                                                            }
                                                            else if(hero.activeView.getX() < gameGrid.getX()){
                                                                hero.setImageView(null);
                                                                hero.setDirection(directions);
                                                                intent.putExtra("personnage", hero);
                                                                activity.startActivity(intent);
                                                                activity.finish();
                                                            }
                                                        }
                                                        catch(Exception e){
                                                            e.printStackTrace();
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
                    activeView.setImageResource(hero.getIdle());
                    deplacement.cancel();
                    break;
            }
        }

        // Déplacement vers le haut
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
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void run() {
                                            // Rotation pour faire face en haut
                                            for (int i = 0; i < positionGrid.length; i++) {
                                                for (int j = 0; j < positionGrid.length; j++) {
                                                    if (i - 1 >= 0 && positionGrid[i][j] == 2 && positionGrid[i - 1][j] != 1) {
                                                        try{
                                                            activeView.setRotation(270);
                                                            if (utiliserPas1) {
                                                                activeView.setImageResource(hero.getPas1());
                                                            } else {
                                                                activeView.setImageResource(hero.getPas2());
                                                                compteur = 0;
                                                            }
                                                            utiliserPas1 = !utiliserPas1;

                                                            activeView.setY(activeView.getY() - (gridSize / gridSections) / 2);
                                                            if (activeView.getY() + activeView.getHeight() <= gridSize / gridSections * i + gameGrid.getY()) {
                                                                positionGrid[i][j] = 0;
                                                                positionGrid[i - 1][j] = 2;
                                                            }

                                                            if(activity.getClass() == room9.class && positionGrid[i - 1][j] == 3){
                                                                hero.asKey = true;
                                                                TextView keyFound = activity.findViewById(R.id.startMessage);
                                                                keyFound.setText("Vous avez trouvé la clé !\n"+ "Retourner a votre point de départ\n"+"pour vous échapé.");
                                                                ImageView chest = activity.findViewById(R.id.chest);
                                                                chest.setImageResource(R.drawable.open_chest);
                                                            }
                                                            else if(hero.activeView.getY() < gameGrid.getY()){
                                                                hero.setImageView(null);
                                                                hero.setDirection(directions);
                                                                intent.putExtra("personnage", hero);
                                                                activity.startActivity(intent);
                                                                activity.finish();
                                                            }
                                                        }
                                                        catch(Exception e){
                                                            e.printStackTrace();
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
                    activeView.setImageResource(hero.getIdle());
                    deplacement.cancel();
                    break;
            }
        }

        // Déplacement vers le bas
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
                                        @SuppressLint("SetTextI18n")
                                        @Override
                                        public void run() {
                                            activeView.setRotation(90); // Rotation pour faire face en bas
                                            for (int i = 0; i < positionGrid.length; i++) {
                                                for (int j = 0; j < positionGrid.length; j++) {
                                                    if (i + 1 < positionGrid.length && positionGrid[i][j] == 2 && positionGrid[i + 1][j] != 1) {
                                                        try{
                                                            activeView.setRotation(90);
                                                            if (utiliserPas1) {
                                                                activeView.setImageResource(hero.getPas1());
                                                            } else {
                                                                activeView.setImageResource(hero.getPas2());
                                                                compteur = 0;
                                                            }
                                                            utiliserPas1 = !utiliserPas1;

                                                            activeView.setY(activeView.getY() + (gridSize / gridSections) / 2);
                                                            if (activeView.getY() + activeView.getHeight() >= gridSize / gridSections * (i + 2) + gameGrid.getY()) {
                                                                positionGrid[i][j] = 0;
                                                                positionGrid[i + 1][j] = 2;
                                                            }

                                                            if(activity.getClass() == room9.class && positionGrid[i + 1][j] == 3){
                                                                hero.asKey = true;
                                                                TextView keyFound = activity.findViewById(R.id.startMessage);
                                                                keyFound.setText("Vous avez trouvé la clé !\n"+ "Retourner a votre point de départ\n"+"pour vous échapé.");
                                                                ImageView chest = activity.findViewById(R.id.chest);
                                                                chest.setImageResource(R.drawable.open_chest);
                                                            }
                                                            else if(hero.activeView.getY() > gameGrid.getY() + gameGrid.getHeight() - hero.activeView.getHeight()){
                                                                hero.setImageView(null);
                                                                hero.setDirection(directions);
                                                                intent.putExtra("personnage", hero);
                                                                activity.startActivity(intent);
                                                                activity.finish();
                                                            }
                                                        }
                                                        catch(Exception e){
                                                            e.printStackTrace();
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
                    activeView.setImageResource(hero.getIdle());
                    deplacement.cancel();
                    break;
            }
        }
        return true;
    }
}