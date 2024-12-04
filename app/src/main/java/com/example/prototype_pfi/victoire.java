package com.example.prototype_pfi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Activité affichée lorsque le joueur gagne la partie.
 * Elle affiche une animation de victoire, un message et un bouton pour rejouer.
 *
 * @author Edouar St-Martin
 */
public class victoire extends AppCompatActivity {

    // MediaPlayer pour la musique de victoire
    int gagneMusique;
    MediaPlayer gagnePlayer;

    // Bouton pour rejouer
    Button rejouer;

    // TextView pour afficher un message
    TextView message;

    // VideoView pour afficher l'animation de victoire
    VideoView gagner;

    // MediaPlayer pour la musique de fond
    MediaPlayer piece4Player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.victoire);

        // Ajuster le padding de la vue pour tenir compte de la barre système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser le lecteur de musique de fond
        piece4Player = MediaPlayer.create(this, R.raw.mega_3title);

        // Obtenir des références aux éléments de l'interface utilisateur
        rejouer = findViewById(R.id.button);
        message = findViewById(R.id.textView2);
        gagner = findViewById(R.id.videoView);

        // Configurer le MediaController pour le VideoView
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(gagner);

        // Définir l'URI de la vidéo de victoire
        Uri gagnerUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.winner);
        gagner.setVideoURI(gagnerUri);
        gagner.setMediaController(mediaController);
        gagner.requestFocus();
        gagner.start(); // Démarrer la vidéo

        // Définir l'écouteur de clic pour le bouton "Rejouer"
        rejouer.setOnClickListener(view -> {
            Intent intent = new Intent(victoire.this, MainActivity.class); // Créer un Intent pour démarrer l'activité MainActivity
            startActivity(intent); // Démarrer l'activité
            gagnePlayer.stop(); // Arrêter la musique de victoire
            finish(); // Terminer l'activité actuelle
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Démarrer la musique de fond en boucle
        piece4Player.setLooping(true);
        piece4Player.start();

        // Initialiser et démarrer la musique de victoire
        gagneMusique = R.raw.mega_clear;
        gagnePlayer = MediaPlayer.create(victoire.this, gagneMusique);
        gagnePlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Libérer le lecteur de musique de fond lorsqu'il n'est plus utilisé
        if (piece4Player != null) {
            piece4Player.release();
            piece4Player = null;
        }
    }

    ;

    @Override
    protected void onDestroy() {
        // Libérer le lecteur de musique de victoire lorsqu'il n'est plus utilisé
        if (gagnePlayer != null) {
            gagnePlayer.release();
            gagnePlayer = null;
        }
        super.onDestroy();
    }
}