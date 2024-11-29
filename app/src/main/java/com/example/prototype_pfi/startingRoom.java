package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.Objects;

public class startingRoom extends AppCompatActivity {

    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;

    ConstraintLayout gameGrid;
    Button right;
    Button down;
    Button up;
    Button left;
    Drawable idle;
    Drawable pas1;
    Drawable pas2;
    ImageView activeView;
    Personnages hero;
    roomGeneration generation;
    int[][] positionGrid;
    int gridSize;
    Serializable directions;
    int hp;
    Directions[] sorties;
    boolean asKey;
    TextView Message;
    ImageView exit;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.starting_room);

        float density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);
        Message = findViewById(R.id.startMessage);

        try{
            hp = getIntent().getIntExtra("hp",10);
            directions = Objects.requireNonNull(getIntent().getExtras()).getSerializable("Directions");
            asKey = getIntent().getBooleanExtra("asKey",true);
        }
        catch(Exception e){
            hp = 10;
            directions = Directions.centre;
            asKey = false;
        }

        gameGrid = findViewById(R.id.gameGrid);
        idle = getDrawable(R.drawable.personnage);
        pas1 = getDrawable(R.drawable.pas1);
        pas2 = getDrawable(R.drawable.pas2);
        activeView = findViewById(R.id.heroStart);
        exit.findViewById(R.id.exit);

        hero = new Personnages(idle, pas1, pas2, activeView, hp, (Directions) directions, asKey);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onStart() {
        super.onStart();

        sorties = new Directions[]
                {
                        Directions.droite
                };

        if(hero.asKey){
            sorties = new Directions[]
                    {
                            Directions.droite,
                            Directions.gauche
                    };
            Message.setText("Vous pouvez vous enfuir !");
            exit.setImageResource(R.drawable.vide);
            positionGrid[GRID_SECTIONS / 2 - 1][0] = 3;
            positionGrid[GRID_SECTIONS / 2][0] = 3;
            positionGrid[GRID_SECTIONS / 2 + 1][0] = 3;
        }

        generation = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize, gameGrid);
        positionGrid = generation.gridGeneration();


        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);

        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(startingRoom.this, room4.class)));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(startingRoom.this, victoire.class)));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,null));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,null));

    };










}