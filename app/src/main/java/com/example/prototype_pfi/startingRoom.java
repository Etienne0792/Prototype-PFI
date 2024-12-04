package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class startingRoom extends AppCompatActivity {

    // Constantes pour la taille de la grille, le nombre de sections et les points de vie initiaux
    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;
    final int HP = 15;

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
    int[] perso = new int[5];
    Personnages hero;

    // Déclaration des variables utilises a la grille de jeu
    int[][] positionGrid;
    Directions[] sorties;
    int gridSize;
    ImageView exit;
    ImageView activeView;
    TextView Message;
    float density;

    // Déclaration du mediaPlayer pour la musique
    MediaPlayer musicPlayer;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.starting_room);

        // Calculer la taille de la grille en pixels
        density = getResources().getDisplayMetrics().density;
        gridSize = (int) (GRID_SIZE * density + 0.5f);

        // Recupérer la Textbox pour montrer des messages au joueur
        Message = findViewById(R.id.startMessage);

        // Debut de la musique
        musicPlayer = MediaPlayer.create(this, R.raw.mega_dungeon);

        //création du personnage
        activeView = findViewById(R.id.heroStart);
        try {
            // Récupérer les informations du personnage si elles sont passées d'une autre activité
            hero = (Personnages) getIntent().getSerializableExtra("personnage");
            activeView.setImageResource(hero.getIdle());
            hero.setImageView(activeView);
        } catch (Exception e) {
            // Si aucune information de personnage n'est passée, créer un nouveau personnage
            boolean persoBleu = getIntent().getBooleanExtra("couleurPerso", false);
            boolean sourire = getIntent().getBooleanExtra("smilePerso", false);
            String pseudo = getIntent().getStringExtra("pseudo");

            // Définir les ressources du personnage en fonction des choix de l'utilisateur
            if (persoBleu) {
                perso[0] = R.drawable.personnage;
                perso[1] = R.drawable.pas1;
                perso[2] = R.drawable.pas2;
                perso[3] = R.drawable.personnagedegat;
            } else {
                perso[0] = R.drawable.personnage_rouge;
                perso[1] = R.drawable.pas1_rouge;
                perso[2] = R.drawable.pas2_rouge;
                perso[3] = R.drawable.personnage_rougedegat;
            }
            if (sourire) { perso[4] = R.drawable.sourire; }
            else { perso[4] = R.drawable.visage; }
            if (activeView != null) { activeView.setImageResource(perso[0]); }

            // Créer un nouvel objet Personnages avec les informations définies
            hero = new Personnages(pseudo, perso[0], perso[1], perso[2], perso[3], perso[4], activeView, HP, Directions.centre, false);
        }

        // initialisation de l'interface
        ImageView visage = findViewById(R.id.visageStart);
        visage.setImageResource(hero.getVisage());
        exit = findViewById(R.id.exit);
        vie = findViewById(R.id.vieStart);
        vie.setText(String.valueOf(hero.getPointDeVie()));

        right = findViewById(R.id.right2);
        left = findViewById(R.id.left2);
        up = findViewById(R.id.up2);
        down = findViewById(R.id.down2);

        handler = new Handler();
        coeur = findViewById(R.id.coeur_start);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coeur);
        coeurAnim = new CoeurAnim().animation(bitmap, coeur, handler);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onStart() {
        super.onStart();

        // début de la musique
        musicPlayer.setLooping(true);
        musicPlayer.start();

        // Définir les sortie de la salle
        sorties = new Directions[]{Directions.droite};

        // Si le héros a la clé, ajouter la sortie de gauche (victoire)
        if (hero.asKey) {
            sorties = new Directions[]
                    {
                            Directions.droite,
                            Directions.gauche
                    };
            Message.setText("Vous pouvez vous enfuir !");
            exit.setImageResource(R.drawable.vide);
        }

        // Générer la grille de jeu
        positionGrid = new roomGeneration(hero, sorties, GRID_SECTIONS, gridSize).gridGeneration();

        // **Liée à victoire - ouvre la section a gauche sur la grille de jeu
        if (hero.asKey) {
            positionGrid[GRID_SECTIONS / 2 - 1][0] = 0;
            positionGrid[GRID_SECTIONS / 2][0] = 0;
            positionGrid[GRID_SECTIONS / 2 + 1][0] = 0;
        }

        // Définir les actions des bouton directionels
        right.setOnTouchListener(new GenericOnTouchListener(Directions.droite, this, positionGrid, hero, gridSize, GRID_SECTIONS, new Intent(startingRoom.this, room4.class)));
        left.setOnTouchListener(new GenericOnTouchListener(Directions.gauche, this, positionGrid, hero, gridSize, GRID_SECTIONS, new Intent(startingRoom.this, victoire.class)));
        up.setOnTouchListener(new GenericOnTouchListener(Directions.haut, this, positionGrid, hero, gridSize, GRID_SECTIONS, null));
        down.setOnTouchListener(new GenericOnTouchListener(Directions.bas, this, positionGrid, hero, gridSize, GRID_SECTIONS, null));

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