package com.example.prototype_pfi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private boolean pBleu = false;
    private boolean pSmile = false;
    private ImageView coeur;
    private Bitmap bitmap;
    private int partiUtilise = 0;  // De 0 à 8 pour les 9 parties
    private Handler handler = new Handler();
    MediaPlayer piece4Player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        piece4Player = MediaPlayer.create(this, R.raw.mega_3title);

        // Charger les images
        int persoRouge = getResources().getIdentifier("perso1_1","drawable",getPackageName());
        int persoBleu = getResources().getIdentifier("perso2_1","drawable",getPackageName());
        int persoRougeSouri = getResources().getIdentifier("perso1_1_1","drawable",getPackageName());
        int persoBleuSouri = getResources().getIdentifier("perso2_1_1","drawable",getPackageName());


        //Coeur "animation"
        coeur = findViewById(R.id.coeur);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coeur);
        handler.post(animationCoeur);

        // changer d'émotion
        ImageView player = findViewById(R.id.player);
        player.setOnClickListener(view -> {
            if(pSmile){
                pSmile = false;
                if(pBleu){
                    player.setImageResource(persoBleu);
                }
                else{
                    player.setImageResource(persoRouge);
                }
            }
            else{
                pSmile = true;
                if(pBleu){
                    player.setImageResource(persoBleuSouri);
                }
                else{
                    player.setImageResource(persoRougeSouri);
                }
            }
        });

        // Changement de personnage
        Button color = findViewById(R.id.color);
        color.setOnClickListener(view -> {
            if(pBleu){
                pBleu = false;
                if(pSmile){
                    player.setImageResource(persoRougeSouri);
                }
                else{
                    player.setImageResource(persoRouge);
                }
            }
            else{
                pBleu = true;
                if (pSmile){
                    player.setImageResource(persoBleuSouri);
                }
                else{
                    player.setImageResource(persoBleu);
                }
            }
        });
        //pseudo
        EditText pseudo = findViewById(R.id.Pseudo);
        Button start = findViewById(R.id.start);

        //Commencement du jeu
        start.setOnClickListener(view -> {
            String pseudoText = pseudo.getText().toString().trim();
            if (!pseudoText.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, startingRoom.class);
                intent.putExtra("pseudo", pseudoText);
                intent.putExtra("couleurPerso", pBleu);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Entrer un pseudo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        piece4Player.setLooping(true);
        piece4Player.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Arrêter le coeur
        handler.removeCallbacks(animationCoeur);
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

            // Créer un sous-ensemble de l'image pour la partie actuelle
            Bitmap partBitmap = Bitmap.createBitmap(bitmap, col * largeur, ran * Hauteur, largeur, Hauteur);

            // Afficher la partie de l'image dans l'ImageView
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