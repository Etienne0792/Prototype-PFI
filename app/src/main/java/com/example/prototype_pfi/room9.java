package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.Objects;

public class room9 extends AppCompatActivity {
    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;

    ConstraintLayout gameGrid;
    Personnages hero;
    ImageButton right;
    ImageButton down;
    ImageButton up;
    ImageButton left;
    ImageView activeView;
    int[][] positionGrid;
    int gridSize;
    roomGeneration generation;
    MediaPlayer piece4Player;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room9);

        piece4Player = MediaPlayer.create(this, R.raw.mega_dungeon);

        float density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        activeView = findViewById(R.id.heroRoom9);
        activeView.setImageResource(hero.getIdle());
        hero.setImageView(activeView);

        gameGrid = findViewById(R.id.gameGrid);

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        piece4Player.setLooping(true);
        piece4Player.start();

        Directions[] sorties = new Directions[]
                {
                        Directions.gauche,
                        Directions.haut
                };

        generation = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize, gameGrid);
        positionGrid = generation.gridGeneration();
        positionGrid[GRID_SECTIONS/2][GRID_SECTIONS/2] = 7;

        right = findViewById(R.id.right10);
        left = findViewById(R.id.left10);
        up = findViewById(R.id.up10);
        down = findViewById(R.id.down10);

        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, null));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room9.this, room8.class)));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room9.this, room6.class)));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,null));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (piece4Player != null) {
            piece4Player.release();
            piece4Player = null;
        }
    };
}
