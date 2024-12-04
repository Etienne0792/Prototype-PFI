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

/**
 * Activité principale de l'application. Gère l'affichage initial,
 * la personnalisation du personnage et le lancement du jeu.
 *
 * @author Daphnée Bastien
 */
public class MainActivity extends AppCompatActivity {
    private boolean pBleu = true;
    private boolean pSmile;
    private ImageView coeur;
    private Bitmap bitmap;
    private int partiUtilise;
    private Handler handler;
    MediaPlayer piece4Player;

    /**
     * Méthode appelée à la création de l'activité. Initialise les éléments de l'interface,
     * charge les images, démarre l'animation du coeur et gère les interactions utilisateur.
     *
     * @param savedInstanceState Bundle contenant l'état précédent de l'activité (si existant)
     */
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

        // Charger les images des personnages
        int persoRouge = getResources().getIdentifier("perso1_1","drawable",getPackageName());
        int persoBleu = getResources().getIdentifier("perso2_1","drawable",getPackageName());
        int persoRougeSouri = getResources().getIdentifier("perso1_1_1","drawable",getPackageName());
        int persoBleuSouri = getResources().getIdentifier("perso2_1_1","drawable",getPackageName());

        // Initialisation de l'animation du coeur
        coeur = findViewById(R.id.coeur);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coeur);
        handler = new Handler();
        handler.post(animationCoeur);

        // Gestion du clic sur l'image du personnage pour changer d'émotion
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

        // Gestion du clic sur le bouton pour changer de couleur de personnage
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

        // Gestion du clic sur le bouton "Start" pour lancer le jeu
        EditText pseudo = findViewById(R.id.Pseudo);
        Button start = findViewById(R.id.start);
        start.setOnClickListener(view -> {
            String pseudoText = pseudo.getText().toString().trim();
            if (!pseudoText.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, startingRoom.class);
                intent.putExtra("pseudo", pseudoText);
                intent.putExtra("couleurPerso", pBleu);
                intent.putExtra("smilePerso", pSmile);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Entrer un pseudo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Méthode appelée au démarrage de l'activité. Démarre la musique de fond.
     */
    @Override
    protected void onStart() {
        super.onStart();
        piece4Player.setLooping(true);
        piece4Player.start();
    }

    /**
     * Méthode appelée à la mise en pause de l'activité. Arrête l'animation du coeur et la musique.
     */
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(animationCoeur); // Arrêt de l'animation
        if (piece4Player != null) {
            piece4Player.release(); // Libération du MediaPlayer
            piece4Player = null;
        }
    }

    /**
     * Runnable pour l'animation du coeur. Change l'image affichée dans l'ImageView
     * pour créer l'animation.
     */
    private Runnable animationCoeur = new Runnable() {
        @Override
        public void run() {
            int ran = partiUtilise / 3;
            int col = partiUtilise % 3;

            // Calcul des coordonnées de la partie de l'image à afficher
            int largeur = bitmap.getWidth() / 3;
            int Hauteur = bitmap.getHeight() / 3;

            // Création d'un sous-ensemble de l'image pour la partie actuelle
            Bitmap partBitmap = Bitmap.createBitmap(bitmap, col * largeur, ran * Hauteur, largeur, Hauteur);

            // Affichage de la partie de l'image dans l'ImageView
            coeur.setImageBitmap(partBitmap);

            // Passage à la partie suivante de l'image
            partiUtilise++;
            if (partiUtilise > 8) {
                partiUtilise = 0;
            }

            // Répétition de la tâche après un délai de 100ms
            handler.postDelayed(this, 100);
        }
    };
}