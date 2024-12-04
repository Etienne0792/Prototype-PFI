package com.example.prototype_pfi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Activité affichée lorsque le joueur perd la partie.
 * Elle affiche une animation de défaite, un message et un bouton pour rejouer.
 *
 * @author Edouard St-Martin
 */
public class Defaite extends AppCompatActivity {
    int perduMusique;
    MediaPlayer perduPlayer;
    Button rejouer;
    TextView message;
    VideoView perdre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.defaite);

        // Ajuster le padding de la vue pour tenir compte de la barre système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rejouer = findViewById(R.id.button2);
        message = findViewById(R.id.textView3);
        perdre = findViewById(R.id.videoView2);

        // Configurer le MediaController pour le VideoView
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(perdre);

        // Définir l'URI de la vidéo de défaite
        Uri perduUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.lose);
        perdre.setVideoURI(perduUri);
        perdre.setMediaController(mediaController);
        perdre.requestFocus();
        perdre.start();

        // Définir l'écouteur de clic pour le bouton "Rejouer"
        rejouer.setOnClickListener(view -> {
            Intent intent = new Intent(Defaite.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        perduMusique = R.raw.game_over;
        perduPlayer = MediaPlayer.create(Defaite.this, perduMusique);
        perduPlayer.start();
    }

    @Override
    protected void onDestroy() {
        // Libérer le MediaPlayer lorsqu'il n'est plus utilisé
        if (perduPlayer != null) {
            perduPlayer.release();
            perduPlayer = null;
        }
        super.onDestroy();
    }

}