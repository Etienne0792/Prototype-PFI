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
 * Activité représentant la troisième salle du jeu.
 * Cette salle contient deux monstres que le joueur doit combattre.
 *
 * @author Étienne La Rochelle
 */
public class room3 extends AppCompatActivity {

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

    // Déclaration des variables pour les monstres
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
        setContentView(R.layout.room3);

        // Calculer la taille de la grille en fonction de la densité de l'écran
        density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        // Initialiser le lecteur de musique
        musicPlayer = MediaPlayer.create(this, R.raw.mega_enemy);

        // Récupérer l'objet Personnages de l'Intent
        hero = (Personnages) getIntent().getSerializableExtra("personnage");
        vie = findViewById(R.id.vie3);
        activeView = findViewById(R.id.heroRoom3);
        activeView.setImageResource(hero.getIdle());
        hero.setImageView(activeView);
        ImageView visage = findViewById(R.id.visage3);
        visage.setImageResource(hero.getVisage());

        // Créer les monstres
        tabMonstre[0] = getDrawable(R.drawable.monstre);
        tabMonstre[1] = getDrawable(R.drawable.monstrepas1);
        tabMonstre[2] = getDrawable(R.drawable.monstrepas2);
        tabMonstre[3] = getDrawable(R.drawable.monstredegat);
        monstre_img1 = findViewById(R.id.monstreRoom3);
        monstre_img2 = findViewById(R.id.monstreRoom3_2);
        monstre1 = new Monstre(tabMonstre, monstre_img1, GRID_SECTIONS, gridSize);
        monstre2 = new Monstre(tabMonstre, monstre_img2, GRID_SECTIONS, gridSize);

        // Modifier les monstres si le héros a la clé
        if (hero.asKey) {
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

        // Initialiser l'interface utilisateur
        visage.setImageResource(hero.getVisage());
        vie.setText(String.valueOf(hero.getPointDeVie()));

        // Obtenir des références aux boutons et à l'ImageView du monstre
        attaque = findViewById(R.id.attaqueRoom3);
        right = findViewById(R.id.right7);
        left = findViewById(R.id.left7);
        up = findViewById(R.id.up7);
        down = findViewById(R.id.down7);

        handler = new Handler();
        coeur = findViewById(R.id.coeur_3);
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
                Directions.gauche,
                Directions.bas
        };

        // Générer la grille de jeu
        positionGrid = new roomGeneration(hero, sorties, GRID_SECTIONS, gridSize).gridGeneration();

        // Définir les écouteurs tactiles pour les boutons directionnels
        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite, this, positionGrid, hero, gridSize, GRID_SECTIONS, null));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche, this, positionGrid, hero, gridSize, GRID_SECTIONS, new Intent(room3.this, room2.class)));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut, this, positionGrid, hero, gridSize, GRID_SECTIONS, null));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas, this, positionGrid, hero, gridSize, GRID_SECTIONS, new Intent(room3.this, room6.class)));

        // Démarrer le déplacement des monstres
        deplacementMonstre1 = monstre1.Deplacement(hero, this, vie);
        deplacementMonstre2 = monstre2.Deplacement(hero, this, vie);
        deplacementMonstre1.start();
        deplacementMonstre2.start();

        // Définir l'écouteur de clic pour le bouton d'attaque
        attaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!monstre1.mort()) {
                    hero.attaquer(monstre1, vie);
                } else if (monstre1.mort() && deplacementMonstre1 != null) {
                    deplacementMonstre1.interrupt();
                    deplacementMonstre1 = null;
                    monstre_img1.setVisibility(View.INVISIBLE);
                }
                if (!monstre2.mort()) {
                    hero.attaquer(monstre2, vie);
                } else if (monstre2.mort() && deplacementMonstre2 != null) {
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
