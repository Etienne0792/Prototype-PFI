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
import androidx.core.content.ContextCompat;

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
    int[] perso = new int[3];
    ImageView activeView;
    Personnages hero;
    roomGeneration generation;
    int[][] positionGrid;
    int gridSize;
    Serializable directions = Directions.centre;
    int hp;
    Directions[] sorties;
    boolean asKey = false;
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

            hero = (Personnages) getIntent().getSerializableExtra("personnage");
            activeView = findViewById(R.id.heroStart);
            activeView.setImageResource(hero.getIdle());
            hero.setImageView(activeView);
            //hp = getIntent().getIntExtra("hp",10);
            //directions = Objects.requireNonNull(getIntent().getExtras()).getSerializable("Directions");
            //asKey = getIntent().getBooleanExtra("asKey",true);
        }
        catch(Exception e){
            boolean persoBleu = getIntent().getBooleanExtra("couleurPerso", false);
            String pseudo = getIntent().getStringExtra("pseudo");
            if (persoBleu){
                perso[0] = R.drawable.personnage;
                perso[1] = R.drawable.pas1;
                perso[2] = R.drawable.pas2;
            }
            else{
                perso[0] = R.drawable.personnage_rouge;
                perso[1] = R.drawable.pas1_rouge;
                perso[2] = R.drawable.pas2_rouge;
            }
            activeView = (ImageView) findViewById(R.id.heroStart);
            if (activeView != null) {
                activeView.setImageResource(perso[0]);
            }
            hp = 10;
            directions = Directions.centre;
            asKey = false;
            hero = new Personnages(pseudo, perso[0], perso[1], perso[2], activeView, hp, (Directions) directions, asKey);

            //hp = 10;
            //directions = Directions.centre;
        }

        gameGrid = findViewById(R.id.gameGrid);
        activeView = findViewById(R.id.heroStart);
        exit = findViewById(R.id.exit);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onStart() {
        super.onStart();

        sorties = new Directions[] { Directions.droite };

        if(hero.asKey){
            sorties = new Directions[]
                    {
                            Directions.droite,
                            Directions.gauche
                    };
            Message.setText("Vous pouvez vous enfuir !");
            exit.setImageResource(R.drawable.vide);
        }

        generation = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize, gameGrid);
        positionGrid = generation.gridGeneration();

        if(hero.asKey){
            positionGrid[GRID_SECTIONS / 2 - 1][0] = 3;
            positionGrid[GRID_SECTIONS / 2][0] = 3;
            positionGrid[GRID_SECTIONS / 2 + 1][0] = 3;
        }

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