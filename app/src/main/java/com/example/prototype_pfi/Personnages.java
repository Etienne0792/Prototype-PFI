package com.example.prototype_pfi;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.TimerTask;

public class Personnages {

    Drawable idle;
    Drawable pas1;
    Drawable pas2;
    ImageView activeView;



    Personnages(Drawable idle, Drawable pas1, Drawable pas2, ImageView activeView){
        this.idle = idle;
        this.pas1 = pas1;
        this.pas2 = pas2;
        this.activeView = activeView;


    }

    public Runnable deplacement(Directions direction){

        float mouvement = 0f;

        switch (direction){
            case droite:
                mouvement = 10f;
                activeView.setRotation(0);
                break;
            case gauche:
                mouvement = -10f;
                activeView.setRotation(180);
                break;
            case haut:
                mouvement = -10f;
                activeView.setRotation(90);
                break;
            case bas:
                mouvement = 10f;
                activeView.setRotation(270);
                break;
        }


        float finalMouvement = mouvement;
        Runnable runnable = new Runnable() {
            int compteur = 0;
            @Override
            public void run() {

                if(direction == Directions.gauche || direction == Directions.droite){
                    activeView.setX(activeView.getX() + finalMouvement);
                }
                else if(direction == Directions.bas || direction == Directions.haut){
                    activeView.setY(activeView.getY() + finalMouvement);
                }
                if(compteur == 5){
                    activeView.setImageResource(R.drawable.pas1);
                    compteur++;
                }
                else if(compteur == 10){
                    activeView.setImageResource(R.drawable.pas2);
                    compteur = 0;
                }
                else{
                    compteur++;
                }
            }
        };

return runnable;


    }


}
