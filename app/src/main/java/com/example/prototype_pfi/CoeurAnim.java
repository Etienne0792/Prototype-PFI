package com.example.prototype_pfi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * La classe `CoeurAnim` est responsable de l'animation d'une image de cœur.
 * Elle découpe un spritesheet de cœur et affiche les différentes parties pour créer une animation.
 *
 * @author Daphnée Bastien
 */
public class CoeurAnim {
    int coeurAnimImage = 0;

    /**
     * Crée une animation de cœur à partir d'un spritesheet.
     *
     * @param bitmap  Le spritesheet contenant les images de l'animation.
     * @param coeur   L'ImageView où afficher l'animation.
     * @param handler Le Handler utilisé pour planifier l'exécution de l'animation.
     * @return Un objet Runnable qui peut être utilisé pour démarrer l'animation.
     */
    public Runnable animation(Bitmap bitmap, ImageView coeur, Handler handler) {
        return new Runnable() {
            @Override
            public void run() {
                int ran = coeurAnimImage / 3;
                int col = coeurAnimImage % 3;

                int largeur = bitmap.getWidth() / 3;
                int Hauteur = bitmap.getHeight() / 3;

                // Créer un Bitmap à partir de la partie correspondante du spritesheet
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
