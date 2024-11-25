package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

public class startingRoom extends AppCompatActivity {

    final float GRID_SIZE = 350f;
    float spawnY;
    float spawnX;
    LinearLayout gameGrid;
    ImageView hero;
    Button right;
    Button down;
    Button up;
    Button left;
    Drawable idle;
    Drawable pas1;
    Drawable pas2;
    TextView debug;

    Timer deplacement;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.starting_room);
    }





    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();


            gameGrid = findViewById(R.id.gameGrid);
            int[][] movementGrid = new int[gameGrid.getChildCount()*2][gameGrid.getChildCount()*2];
            for(int i = 0; i < movementGrid.length; i++){
                for(int j = 0; j < movementGrid[0].length; j++){
                    if(i == 0 || i == movementGrid.length-1){
                        movementGrid[i][j] = 1;
                    }
                    else if(j == 0 || j == movementGrid[0].length-1){
                        movementGrid[i][j] = 1;
                    }
                    else{
                        movementGrid[i][j] = 0;
                    }
                }
            }

            Personnages hero = new Personnages(getDrawable(R.drawable.personnage),getDrawable(R.drawable.pas1),getDrawable(R.drawable.pas2), findViewById(R.id.hero));



            right = findViewById(R.id.right);
            left = findViewById(R.id.left);
            up = findViewById(R.id.up);
            down = findViewById(R.id.down);

            right.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            deplacement = new Timer();
                            TimerTask task = new TimerTask() {
                                int compteur = 0;
                                @Override
                                public void run() {
                                    runOnUiThread(hero.deplacement(Directions.droite));
                                }
                            };
                            deplacement.schedule(task,0,50);
                            break;
                        case MotionEvent.ACTION_UP:
                            hero.activeView.setImageResource(R.drawable.personnage);
                            deplacement.cancel();
                            break;
                    }
                    return true;
                }
            });
            up.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            deplacement = new Timer();
                            TimerTask task = new TimerTask() {
                                int compteur = 0;
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            hero.deplacement(Directions.haut);
                                            if(compteur == 5){
                                                hero.activeView.setImageResource(R.drawable.pas1);
                                                compteur++;
                                            }
                                            else if(compteur == 10){
                                                hero.activeView.setImageResource(R.drawable.pas2);
                                                compteur = 0;
                                            }
                                            else{
                                                compteur++;
                                            }
                                        }
                                    });
                                }
                            };
                            deplacement.schedule(task,0,50);
                            break;
                        case MotionEvent.ACTION_UP:
                            hero.activeView.setImageResource(R.drawable.personnage);
                            deplacement.cancel();
                            break;
                    }
                    return true;
                }
            });
            down.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            deplacement = new Timer();
                            TimerTask task = new TimerTask() {
                                int compteur = 0;
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            hero.deplacement(Directions.bas);
                                            if(compteur == 5){
                                                hero.activeView.setImageResource(R.drawable.pas1);
                                                compteur++;
                                            }
                                            else if(compteur == 10){
                                                hero.activeView.setImageResource(R.drawable.pas2);
                                                compteur = 0;
                                            }
                                            else{
                                                compteur++;
                                            }
                                        }
                                    });
                                }
                            };
                            deplacement.schedule(task,0,50);
                            break;
                        case MotionEvent.ACTION_UP:
                            hero.activeView.setImageResource(R.drawable.personnage);
                            deplacement.cancel();
                            break;
                    }
                    return true;
                }
            });
            left.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            deplacement = new Timer();
                            TimerTask task = new TimerTask() {
                                int compteur = 0;
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            hero.deplacement(Directions.gauche);
                                            if(compteur == 5){
                                                hero.activeView.setImageResource(R.drawable.pas1);
                                                compteur++;
                                            }
                                            else if(compteur == 10){
                                                hero.activeView.setImageResource(R.drawable.pas2);
                                                compteur = 0;
                                            }
                                            else{
                                                compteur++;
                                            }
                                        }
                                    });
                                }
                            };
                            deplacement.schedule(task,0,50);
                            break;
                        case MotionEvent.ACTION_UP:
                            hero.activeView.setImageResource(R.drawable.personnage);
                            deplacement.cancel();
                            break;
                    }
                    return true;
                }
            });
        }




    }
}
