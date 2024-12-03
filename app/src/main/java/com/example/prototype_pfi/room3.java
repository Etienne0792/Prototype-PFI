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

public class room3 extends AppCompatActivity {



    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;

    ConstraintLayout gameGrid;
    Personnages hero;
    Monstre monstre1;
    Monstre monstre2;
    Drawable[] tabMonstre = new Drawable[3];
    ImageView activeView;
    ImageButton right;
    ImageButton down;
    ImageButton up;
    ImageButton left;
    int[][] positionGrid;
    int gridSize;
    roomGeneration generation;
    MediaPlayer piece4Player;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room3);

        piece4Player = MediaPlayer.create(this, R.raw.mega_enemy);

        float density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        // Dessin hero
        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        activeView = findViewById(R.id.heroRoom3);
        activeView.setImageResource(hero.getIdle());
        hero.setImageView(activeView);

        gameGrid = findViewById(R.id.gameGrid);


        // dessin monstre
        tabMonstre[0] = getDrawable(R.drawable.monstre);
        tabMonstre[1] = getDrawable(R.drawable.monstrepas1);
        tabMonstre[2] = getDrawable(R.drawable.monstrepas2);
        ImageView monstre_img = findViewById(R.id.monstreRoom3);
        ImageView monstre_img2 = findViewById(R.id.monstreRoom3_2);
        monstre1 = new Monstre(tabMonstre, monstre_img,gameGrid, GRID_SECTIONS,gridSize);
        monstre2 = new Monstre(tabMonstre, monstre_img2,gameGrid,GRID_SECTIONS,gridSize);
        if (hero.asKey){
            tabMonstre[0] = getDrawable(R.drawable.monstreattaquer);
            tabMonstre[1] = getDrawable(R.drawable.monstrepas1attaquer);
            tabMonstre[2] = getDrawable(R.drawable.monstrepas2attaquer);
            monstre1.setImage(tabMonstre);
            monstre2.setImage(tabMonstre);
            monstre1.setAttaque(2);
            monstre2.setAttaque(2);
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        piece4Player.setLooping(true);
        piece4Player.start();

        monstre1.Deplacement(hero,this).start();
        monstre2.Deplacement(hero,this).start();

        Directions[] sorties = new Directions[]
                {
                        Directions.droite,
                        Directions.gauche,
                        Directions.bas
                };

        generation = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize, gameGrid);
        positionGrid = generation.gridGeneration();

        right = findViewById(R.id.right7);
        left = findViewById(R.id.left7);
        up = findViewById(R.id.up7);
        down = findViewById(R.id.down7);

        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, null));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room3.this, room2.class)));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,null));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room3.this, room6.class)));
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
