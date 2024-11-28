package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class startingRoom extends AppCompatActivity {

    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;
    LinearLayout gameGrid;
    Button right;
    Button down;
    Button up;
    Button left;
    Drawable idle;
    Drawable pas1;
    Drawable pas2;
    ImageView activeView;
    Personnages hero;
    Timer deplacement;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.starting_room);

        gameGrid = findViewById(R.id.gameGrid);
        idle = getDrawable(R.drawable.personnage);
        pas1 = getDrawable(R.drawable.pas1);
        pas2 = getDrawable(R.drawable.pas2);
        activeView = findViewById(R.id.hero);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();

        float density = getResources().getDisplayMetrics().density;
        int gridSize = (int) (GRID_SIZE * density + 0.5f);

        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);

        int[][] positionGrid = new int[GRID_SECTIONS][GRID_SECTIONS];
        for (int i = 0; i < positionGrid.length; i++) {
            for (int j = 0; j < positionGrid.length; j++) {
                if (i == 0 || j == 0 || i == positionGrid.length - 1 || j == positionGrid.length - 1) {
                    positionGrid[i][j] = 1;
                } else {
                    positionGrid[i][j] = 0;
                }
            }
        }

        positionGrid[GRID_SECTIONS / 2][GRID_SECTIONS / 2] = 2;
        positionGrid[GRID_SECTIONS / 2 - 1][GRID_SECTIONS - 1] = 3;
        positionGrid[GRID_SECTIONS / 2][GRID_SECTIONS - 1] = 3;
        positionGrid[GRID_SECTIONS / 2 + 1][GRID_SECTIONS - 1] = 3;

        hero = new Personnages(idle, pas1, pas2, activeView, gridSize, GRID_SECTIONS, gameGrid, positionGrid);



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
                                runOnUiThread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                activeView.setRotation(0); // Rotation pour faire face à droite
                                                for (int i = 0; i < positionGrid.length; i++) {
                                                    for (int j = 0; j < positionGrid.length; j++) {
                                                        if (j + 1 < positionGrid.length && positionGrid[i][j] == 2 && positionGrid[i][j + 1] != 1) {
                                                            if (utiliserPas1) {
                                                                activeView.setImageResource(R.drawable.pas1);
                                                            } else {
                                                                activeView.setImageResource(R.drawable.pas2);
                                                                compteur = 0;
                                                            }
                                                            if(positionGrid[i][j + 1] == 3){
                                                                Intent intent = new Intent(startingRoom.this, room5.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                            utiliserPas1 = !utiliserPas1;
                                                            activeView.setX(activeView.getX() + (gridSize / GRID_SECTIONS) / 2);
                                                            if (activeView.getX() + activeView.getWidth() >= gridSize / GRID_SECTIONS * (j + 2)) {
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
                                boolean utiliserPas1 = true;
                                @Override
                                public void run() {
                                    runOnUiThread(
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
                                                                activeView.setX(activeView.getX() - (gridSize / GRID_SECTIONS) / 2);
                                                                if (activeView.getX() + activeView.getWidth() / 2 <= gridSize / GRID_SECTIONS * j) {
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
                                boolean utiliserPas1 = true;
                                @Override
                                public void run() {
                                    runOnUiThread(
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
                                                                activeView.setY(activeView.getY() - (gridSize / GRID_SECTIONS) / 2);
                                                                if (activeView.getY() + activeView.getHeight() / 2 <= gridSize / GRID_SECTIONS * i + gameGrid.getY()) {
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
                                boolean utiliserPas1 = true;
                                @Override
                                public void run() {
                                    runOnUiThread(
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
                                                                activeView.setY(activeView.getY() + (gridSize / GRID_SECTIONS) / 2);
                                                                if (activeView.getY() + activeView.getHeight() >= gridSize / GRID_SECTIONS * (i + 2) + gameGrid.getY()) {
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



    };










}