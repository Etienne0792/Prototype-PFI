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

/**
 * Activité représentant la sixième salle du jeu.
 * Cette salle contient deux monstres que le joueur doit combattre.
 *
 * @author Étienne La Rochelle
 */
public class room6 extends AppCompatActivity {

    // Constantes pour la taille de la grille, le nombre de sections et les points de vie initiaux
    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;

    // Déclaration des variables de l'interface
    ImageView coeur;
    Bitmap bitmap;
    TextView vie;
    Handler handler;
    Runnable coeurAnim;
    ImageButton right;
    ImageButton down;
    ImageButton up;
    ImageButton left;

    // Déclaration des vriables utilise a la création du hero
    Personnages hero;
    ImageView activeView;

    // Déclaration des variables utilises a la grille de jeu
    int[][] positionGrid;
    int gridSize;
    float density;

    // Déclaration du mediaPlayer pour la musique
    MediaPlayer musicPlayer;

    // Déclaration des variables utilisés pour les monstres
    Monstre monstre1;
    Monstre monstre2;
    Drawable[] tabMonstre = new Drawable[4];
    ImageButton attaque;
    Thread deplacementMonstre1;
    Thread deplacementMonstre2;
    ImageView monstre_img1;
    ImageView monstre_img2;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room6);

        // Calculer la taille de la grille en pixels
        density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        // Debut de la musique
        musicPlayer = MediaPlayer.create(this, R.raw.mega_enemy);

        // création du personnage
        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        vie = findViewById(R.id.vie6);
        activeView = findViewById(R.id.heroRoom6);
        activeView.setImageResource(hero.getIdle());
        hero.setImageView(activeView);
        ImageView visage = findViewById(R.id.visage6);
        visage.setImageResource(hero.getVisage());

        // Création du monstre
        tabMonstre[0] = getDrawable(R.drawable.monstre);
        tabMonstre[1] = getDrawable(R.drawable.monstrepas1);
        tabMonstre[2] = getDrawable(R.drawable.monstrepas2);
        tabMonstre[3] = getDrawable(R.drawable.monstredegat);
        monstre_img1 = findViewById(R.id.monstreRoom6);
        monstre_img2 = findViewById(R.id.monstreRoom6_2);
        monstre1 = new Monstre(tabMonstre, monstre_img1, GRID_SECTIONS, gridSize);
        monstre2 = new Monstre(tabMonstre, monstre_img2, GRID_SECTIONS, gridSize);
        if (hero.asKey){
            tabMonstre[0] = getDrawable(R.drawable.monstreattaquer);
            tabMonstre[1] = getDrawable(R.drawable.monstrepas1attaquer);
            tabMonstre[2] = getDrawable(R.drawable.monstrepas2attaquer);
            monstre1.setImage(tabMonstre);
            monstre1.setAttaque(2);
            monstre1.setSpeed(2);
            monstre2.setImage(tabMonstre);
            monstre2.setAttaque(2);
            monstre2.setSpeed(2);
        }

        // initialisation de l'interface
        visage.setImageResource(hero.getVisage());
        vie.setText(String.valueOf(hero.getPointDeVie()));

        attaque = findViewById(R.id.attaqueRoom6);
        right = findViewById(R.id.right4);
        left = findViewById(R.id.left4);
        up = findViewById(R.id.up4);
        down = findViewById(R.id.down4);

        handler = new Handler();
        coeur = findViewById(R.id.coeur_6);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coeur);
        coeurAnim = new CoeurAnim().animation(bitmap, coeur, handler);
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();

        // début de la musique
        musicPlayer.setLooping(true);
        musicPlayer.start();

        // Définir les sortie de la salle
        Directions[] sorties = new Directions[]
                {
                        Directions.gauche,
                        Directions.haut,
                        Directions.bas
                };

        // Générer la grille de jeu
        positionGrid = new roomGeneration(hero, sorties , GRID_SECTIONS, gridSize).gridGeneration();

        // Définir les actions des bouton directionels
        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite,this,positionGrid,hero, gridSize, GRID_SECTIONS, null));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche,this,positionGrid,hero, gridSize, GRID_SECTIONS, new Intent(room6.this, room5.class)));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut,this,positionGrid,hero, gridSize, GRID_SECTIONS,new Intent(room6.this, room3.class)));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas,this,positionGrid,hero, gridSize, GRID_SECTIONS,new Intent(room6.this, room9.class)));

        // Démarrer le déplacement des monstres
        deplacementMonstre1 = monstre1.Deplacement(hero,this, vie);
        deplacementMonstre2 = monstre2.Deplacement(hero,this, vie);
        deplacementMonstre1.start();
        deplacementMonstre2.start();

        // Définir l'action du bouton attaque
        attaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!monstre1.mort()){
                    hero.attaquer(monstre1,vie);
                }
                else if(monstre1.mort() && deplacementMonstre1 != null){
                    deplacementMonstre1.interrupt();
                    deplacementMonstre1 = null;
                    monstre_img1.setVisibility(View.INVISIBLE);
                }
                if(!monstre2.mort()){
                    hero.attaquer(monstre2,vie);
                }
                else if(monstre2.mort() && deplacementMonstre2 != null){
                    deplacementMonstre2.interrupt();
                    deplacementMonstre2 = null;
                    monstre_img2.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Démarrer l'animation du coeur
        handler.post(coeurAnim);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Arrêter la musique
        if (musicPlayer != null) {
            musicPlayer.release();
            musicPlayer = null;
        }

        // Arrêter l'animation du coeur
        handler.removeCallbacks(coeurAnim);
    }
}

