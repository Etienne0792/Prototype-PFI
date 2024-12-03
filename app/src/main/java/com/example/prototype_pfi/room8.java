package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class room8 extends AppCompatActivity {
    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;

    ConstraintLayout gameGrid;
    Personnages hero;
    ImageView activeView;
    Monstre monstre;
    Drawable[] tabMonstre = new Drawable[4];
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

    ImageButton attaque;
    ImageView monstre_img;
    Thread deplacementMonstre;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room8);
        vie = findViewById(R.id.vie8);
        piece4Player = MediaPlayer.create(this, R.raw.mega_enemy);
        float density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        // dessin hero
        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        vie.setText(String.valueOf(hero.getPointDeVie()));
        activeView = findViewById(R.id.heroRoom8);
        activeView.setImageResource(hero.getIdle());
        hero.setImageView(activeView);
        gameGrid = findViewById(R.id.gameGrid);
        ImageView visage = findViewById(R.id.visage8);
        visage.setImageResource(hero.getVisage());

        // dessin monstre
        tabMonstre[0] = getDrawable(R.drawable.monstre);
        tabMonstre[1] = getDrawable(R.drawable.monstrepas1);
        tabMonstre[2] = getDrawable(R.drawable.monstrepas2);
        tabMonstre[3] = getDrawable(R.drawable.monstredegat);
        monstre_img = findViewById(R.id.monstreRoom8);
        monstre = new Monstre(tabMonstre, monstre_img,gameGrid, GRID_SECTIONS,gridSize);
        if (hero.asKey){
            tabMonstre[0] = getDrawable(R.drawable.monstreattaquer);
            tabMonstre[1] = getDrawable(R.drawable.monstrepas1attaquer);
            tabMonstre[2] = getDrawable(R.drawable.monstrepas2attaquer);
            monstre.setImage(tabMonstre);
            monstre.setAttaque(2);
            monstre.setSpeed(2);
        }

        //Coeur "animation"
        coeur = findViewById(R.id.coeur_8);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coeur);
        handler.post(animationCoeur);
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        piece4Player.setLooping(true);
        piece4Player.start();

        deplacementMonstre = monstre.Deplacement(hero,this, vie);
        deplacementMonstre.start();

        Directions[] sorties = new Directions[]
                {
                        Directions.droite,
                        Directions.gauche,
                        Directions.haut
                };

        generation = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize, gameGrid);
        positionGrid = generation.gridGeneration();

        attaque = findViewById(R.id.attaqueRoom8);
        attaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!monstre.mort()){
                    hero.attaquer(monstre,vie);
                }
                else if(monstre.mort() && deplacementMonstre != null){
                    deplacementMonstre.interrupt();
                    deplacementMonstre = null;
                    monstre_img.setVisibility(View.INVISIBLE);
                }
            }
        });

        right = findViewById(R.id.right9);
        left = findViewById(R.id.left9);
        up = findViewById(R.id.up9);
        down = findViewById(R.id.down9);

        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room8.this, room9.class)));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, gameGrid, new Intent(room8.this, room7.class)));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,new Intent(room8.this, room5.class)));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,gameGrid,null));
    }

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
