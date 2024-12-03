package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.app.Activity;
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

public class room4 extends AppCompatActivity {

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
    Drawable[] tabMonstre = new Drawable[3];
    Monstre monstre;
    MediaPlayer piece4Player;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room4);

        piece4Player = MediaPlayer.create(this, R.raw.mega_enemy);

        float density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        activeView = findViewById(R.id.heroRoom4);
        activeView.setImageResource(hero.getIdle());
        hero.setImageView(activeView);

        gameGrid = findViewById(R.id.gameGrid);

        // dessin monstre
        tabMonstre[0] = getDrawable(R.drawable.monstre);
        tabMonstre[1] = getDrawable(R.drawable.monstrepas1);
        tabMonstre[2] = getDrawable(R.drawable.monstrepas2);
        ImageView monstre_img = findViewById(R.id.monstreRoom4);
        monstre = new Monstre(tabMonstre, monstre_img, gameGrid, GRID_SECTIONS,gridSize);
        if (hero.asKey){
            tabMonstre[0] = getDrawable(R.drawable.monstreattaquer);
            tabMonstre[1] = getDrawable(R.drawable.monstrepas1attaquer);
            tabMonstre[2] = getDrawable(R.drawable.monstrepas2attaquer);
            monstre.setImage(tabMonstre);
            monstre.setAttaque(2);
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        piece4Player.setLooping(true);
        piece4Player.start();

       monstre.Deplacement(hero,this).start();

        Directions[] sorties = new Directions[]
                {
                        Directions.droite,
                        Directions.gauche,
                        Directions.haut,
                        Directions.bas
                };

        generation = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize, gameGrid);
        positionGrid = generation.gridGeneration();

        right = findViewById(R.id.right2);
        left = findViewById(R.id.left2);
        up = findViewById(R.id.up2);
        down = findViewById(R.id.down2);

        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room4.this, room5.class)));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room4.this, startingRoom.class)));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room4.this, room1.class)));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room4.this, room7.class)));
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