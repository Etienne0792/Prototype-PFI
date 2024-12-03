package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;

public class startingRoom extends AppCompatActivity {

    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;
    final int HP = 15;

    ConstraintLayout gameGrid;
    Button right;
    Button down;
    Button up;
    Button left;
    int[] perso = new int[5];
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
    private TextView vie;
    private ImageView coeur;
    private Bitmap bitmap;
    private int partiUtilise = 0;  // De 0 à 8 pour les 9 parties
    private Handler handler = new Handler();
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
        }
        catch(Exception e){
            boolean persoBleu = getIntent().getBooleanExtra("couleurPerso", false);
            boolean sourire = getIntent().getBooleanExtra("smilePerso", false);
            String pseudo = getIntent().getStringExtra("pseudo");

            if (persoBleu){
                perso[0] = R.drawable.personnage;
                perso[1] = R.drawable.pas1;
                perso[2] = R.drawable.pas2;
                perso[3] = R.drawable.personnagedegat;
            }
            else{
                perso[0] = R.drawable.personnage_rouge;
                perso[1] = R.drawable.pas1_rouge;
                perso[2] = R.drawable.pas2_rouge;
                perso[3] = R.drawable.personnage_rougedegat;
            }
            if(sourire){
                perso[4] = R.drawable.sourire;
            }
            else{
                perso[4] = R.drawable.visage;
            }
            activeView = (ImageView) findViewById(R.id.heroStart);
            if (activeView != null) {
                activeView.setImageResource(perso[0]);
            }
            directions = Directions.centre;
            asKey = false;
            hero = new Personnages(pseudo, perso[0], perso[1], perso[2], perso[3], perso[4], activeView, HP, (Directions) directions, asKey);
        }

        vie = findViewById(R.id.vieStart);
        vie.setText(String.valueOf(hero.getPointDeVie()));
        gameGrid = findViewById(R.id.gameGrid);
        activeView = findViewById(R.id.heroStart);
        ImageView visage = findViewById(R.id.visageStart);
        visage.setImageResource(hero.getVisage());
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


        //Coeur "animation"
        coeur = findViewById(R.id.coeur_start);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coeur);
        handler.post(animationCoeur);
    };

    @Override
    protected void onPause() {
        super.onPause();
        // Arrêter le coeur
        handler.removeCallbacks(animationCoeur);
    }

    //Changer image coeur
    private Runnable animationCoeur = new Runnable() {
        @Override
        public void run() {
            int ran = partiUtilise / 3;
            int col = partiUtilise % 3;

            // Nécéssite l'utilisation d'un bitmap pour séparer l'image en 9 parties
            int largeur = bitmap.getWidth() / 3;
            int Hauteur = bitmap.getHeight() / 3;
            Bitmap partBitmap = Bitmap.createBitmap(bitmap, col * largeur, ran * Hauteur, largeur, Hauteur);
            coeur.setImageBitmap(partBitmap);
            // Passer à la prochaine partie
            partiUtilise++;
            if (partiUtilise > 8) {
                partiUtilise = 0;  // Recommencer à partir de la première partie
            }

            // Répéter la tâche toutes les 100 millisecondes
            handler.postDelayed(this, 100);  // 100 ms entre chaque changement de partie
        }
    };
}