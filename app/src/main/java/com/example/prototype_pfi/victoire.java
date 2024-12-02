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

public class victoire extends AppCompatActivity {
    int gagneMusique;
    MediaPlayer gagnePlayer;
    Button rejouer = findViewById(R.id.button);
    TextView message = findViewById(R.id.textView2);
    VideoView gagner = findViewById(R.id.videoView);
    @Override
    protected void onStart(){
        super.onStart();

        gagneMusique = R.raw.mega_clear;
        //gagnePlayer = MediaPlayer.create(gagne.this, gagneMusique);
        gagnePlayer.start();
    }
    @Override
    protected void onDestroy(){
        if (gagnePlayer != null) {
            gagnePlayer.release();
            gagnePlayer = null;
        }
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.victoire);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(gagner);

        //Uri gagnerUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.winner);
        //gagner.setVideoURI(gagnerUri);
        gagner.setMediaController(mediaController);
        gagner.requestFocus();
        gagner.start();

        rejouer.setOnClickListener(view -> {
            Intent intent = new Intent(victoire.this, MainActivity.class);
            startActivity(intent);
            gagnePlayer.stop();
            finish();
        });
    }
}