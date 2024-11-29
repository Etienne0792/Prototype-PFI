package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.Objects;

public class room7 extends AppCompatActivity {

    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;

    ConstraintLayout gameGrid;
    Drawable idle;
    Drawable pas1;
    Drawable pas2;
    Personnages hero;
    ImageView activeView;
    Button right;
    Button down;
    Button up;
    Button left;
    int[][] positionGrid;
    int gridSize;
    roomGeneration generation;
    boolean asKey;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room7);

        float density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        int hp = getIntent().getIntExtra("hp",10);
        Serializable directions = Objects.requireNonNull(getIntent().getExtras()).getSerializable("Directions");
        asKey = getIntent().getBooleanExtra("asKey",false);

        gameGrid = findViewById(R.id.gameGrid);
        idle = getDrawable(R.drawable.personnage);
        pas1 = getDrawable(R.drawable.pas1);
        pas2 = getDrawable(R.drawable.pas2);
        activeView = findViewById(R.id.heroRoom7);

        hero = new Personnages(idle, pas1, pas2, activeView, hp, (Directions) directions,asKey);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();

        Directions[] sorties = new Directions[]
                {
                        Directions.droite,
                        Directions.haut
                };

        generation = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize, gameGrid);
        positionGrid = generation.gridGeneration();

        right = findViewById(R.id.right8);
        left = findViewById(R.id.left8);
        up = findViewById(R.id.up8);
        down = findViewById(R.id.down8);

        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room7.this, room8.class)));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, null));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room7.this, room4.class)));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,null));
    }
}