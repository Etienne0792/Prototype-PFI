package com.example.prototype_pfi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class CoeurAnim {
    int coeurAnimImage = 0;

    public Runnable animation(Bitmap bitmap, ImageView coeur,Handler handler){
        return new Runnable() {
            @Override
            public void run() {
                int ran = coeurAnimImage / 3;
                int col = coeurAnimImage % 3;

                int largeur = bitmap.getWidth() / 3;
                int Hauteur = bitmap.getHeight() / 3;
                Bitmap partBitmap = Bitmap.createBitmap(bitmap, col * largeur, ran * Hauteur, largeur, Hauteur);
                coeur.setImageBitmap(partBitmap);

                coeurAnimImage++;
                if (coeurAnimImage > 8) {
                    coeurAnimImage = 0;
                }

                handler.postDelayed(this, 100);
            }
        };
    }
}
