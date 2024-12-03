package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
    private TextView vie;
    private ImageView coeur;
    private Bitmap bitmap;
    private int partiUtilise = 0;  // De 0 à 8 pour les 9 parties
    private Handler handler = new Handler();
    MediaPlayer piece4Player;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room1);
        vie = findViewById(R.id.vie1);

        piece4Player = MediaPlayer.create(this, R.raw.mega_dungeon);

        float density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        // Dessin hero
        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        vie.setText(String.valueOf(hero.getPointDeVie()));
        activeView = findViewById(R.id.heroRoom1);
        activeView.setImageResource(hero.getIdle());
        hero.setImageView(activeView);
        ImageView visage = findViewById(R.id.visage1);
        visage.setImageResource(hero.getVisage());
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
        vie = findViewById(R.id.vie1);

        right = findViewById(R.id.right5);
        left = findViewById(R.id.left5);
        up = findViewById(R.id.up5);
        down = findViewById(R.id.down5);

        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room1.this, room2.class)));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, null));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,null));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room1.this, room4.class)));

        //Coeur "animation"
        coeur = findViewById(R.id.coeur_1);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coeur);
        handler.post(animationCoeur);
    };

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

    @Override
    protected void onPause() {
        super.onPause();
        // Arrêter le coeur
        handler.removeCallbacks(animationCoeur);
        // Arrêter music
        if (piece4Player != null) {
            piece4Player.release();
            piece4Player = null;

        }
    };
}
