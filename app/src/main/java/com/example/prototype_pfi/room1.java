package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.Objects;

public class room1 extends AppCompatActivity {

    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;

    ConstraintLayout gameGrid;
    Personnages hero;
    ImageView activeView;
    ImageButton right;
    ImageButton down;
    ImageButton up;
    ImageButton left;
    int[][] positionGrid;
    int gridSize;
    roomGeneration generation;
    boolean asKey;
    MediaPlayer piece4Player;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room1);

        piece4Player = MediaPlayer.create(this, R.raw.mega_dungeon);

        float density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        // Dessin hero
        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        activeView = findViewById(R.id.heroRoom1);
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
                        Directions.droite,
                        Directions.bas
                };

        generation = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize, gameGrid);
        positionGrid = generation.gridGeneration();

        right = findViewById(R.id.right5);
        left = findViewById(R.id.left5);
        up = findViewById(R.id.up5);
        down = findViewById(R.id.down5);

        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room1.this, room2.class)));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, null));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,null));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room1.this, room4.class)));
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
