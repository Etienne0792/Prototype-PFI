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

/**
 * Activité représentant la première salle du jeu.
 *
 * @author Étienne La Rochelle
 */
public class room1 extends AppCompatActivity {

    // Constantes pour la taille de la grille et le nombre de sections
    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;

    // Déclaration des variables pour les éléments d'interface utilisateur
    ImageView coeur;
    Bitmap bitmap;
    TextView vie;
    Handler handler;
    Runnable coeurAnim;
    ImageButton right;
    ImageButton down;
    ImageButton up;
    ImageButton left;

    // Déclaration des variables pour le personnage
    Personnages hero;
    ImageView activeView;

    // Déclaration des variables pour la grille de jeu
    int[][] positionGrid;
    int gridSize;
    float density;

    // Déclaration du MediaPlayer pour la musique
    MediaPlayer musicPlayer;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.room1);

        // Calculer la taille de la grille en fonction de la densité de l'écran
        density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        // Initialiser le lecteur de musique
        musicPlayer = MediaPlayer.create(this, R.raw.mega_dungeon);

        // Récupérer l'objet Personnages de l'Intent
        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        vie = findViewById(R.id.vie1);
        activeView = findViewById(R.id.heroRoom1);
        activeView.setImageResource(hero.getIdle());
        hero.setImageView(activeView);
        ImageView visage = findViewById(R.id.visage1);
        visage.setImageResource(hero.getVisage());

        // Initialiser l'interface utilisateur
        visage.setImageResource(hero.getVisage());
        vie.setText(String.valueOf(hero.getPointDeVie()));

        // Obtenir des références aux boutons directionnels
        right = findViewById(R.id.right5);
        left = findViewById(R.id.left5);
        up = findViewById(R.id.up5);
        down = findViewById(R.id.down5);

        handler = new Handler();
        coeur = findViewById(R.id.coeur_1);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coeur);
        coeurAnim = new CoeurAnim().animation(bitmap, coeur, handler);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();

        // Démarrer la musique en boucle
        musicPlayer.setLooping(true);
        musicPlayer.start();

        // Définir les sorties possibles de la salle
        Directions[] sorties = new Directions[]{
                Directions.droite,
                Directions.bas
        };

        // Générer la grille de jeu
        positionGrid = new roomGeneration(hero, sorties, GRID_SECTIONS, gridSize).gridGeneration();

        // Définir les écouteurs tactiles pour les boutons directionnels
        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite, this, positionGrid, hero, gridSize, GRID_SECTIONS, new Intent(room1.this, room2.class)));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche, this, positionGrid, hero, gridSize, GRID_SECTIONS, null));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut, this, positionGrid, hero, gridSize, GRID_SECTIONS, null));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas, this, positionGrid, hero, gridSize, GRID_SECTIONS, new Intent(room1.this, room4.class)));

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
