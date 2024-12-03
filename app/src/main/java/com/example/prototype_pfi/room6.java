package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.Objects;

public class room6 extends AppCompatActivity {

    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;

    ConstraintLayout gameGrid;
    Personnages hero;
    ImageView activeView;
    Monstre monstre;
    Drawable[] tabMonstre = new Drawable[3];
    ImageButton right;
    ImageButton down;
    ImageButton up;
    ImageButton left;
    int[][] positionGrid;
    int gridSize;
    roomGeneration generation;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room6);

        float density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        // dessin hero
        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        activeView = findViewById(R.id.heroRoom6);
        activeView.setImageResource(hero.getIdle());
        hero.setImageView(activeView);
        gameGrid = findViewById(R.id.gameGrid);

        // dessin monstre
        tabMonstre[0] = getDrawable(R.drawable.monstre);
        tabMonstre[1] = getDrawable(R.drawable.monstrepas1);
        tabMonstre[2] = getDrawable(R.drawable.monstrepas2);
        ImageView monstre_img = findViewById(R.id.monstreRoom6);
        monstre = new Monstre(tabMonstre, monstre_img,gameGrid,GRID_SECTIONS,gridSize);
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

        monstre.Deplacement(hero,this).start();

        Directions[] sorties = new Directions[]
                {
                        Directions.gauche,
                        Directions.haut,
                        Directions.bas
                };

        generation = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize, gameGrid);
        positionGrid = generation.gridGeneration();

        right = findViewById(R.id.right4);
        left = findViewById(R.id.left4);
        up = findViewById(R.id.up4);
        down = findViewById(R.id.down4);

        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, null));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room6.this, room5.class)));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room6.this, room3.class)));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room6.this, room9.class)));
    }
}

