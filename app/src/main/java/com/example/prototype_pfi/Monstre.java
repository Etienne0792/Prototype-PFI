package com.example.prototype_pfi;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.Random;

public class Monstre implements Serializable, IPersonnage {

        // Constante
        static final int MIN_ATTAQUE = 1;
        static final int MIN_DEFENSE = 1;

        // Propriété
        Drawable idle;;
        Drawable monstrePas1;
        Drawable monstrePas2;
        public ImageView activeView;

        private int pointDeVie = 5;
        private int attaque = 1;
        private int defense = 0;

        float gridSize;
        public LinearLayout gameGrid;
        int gridSection;
        public int[][] positionGrid;
        public Directions currentDirection;

        // Get & Set
        public int getPointDeVie() { return pointDeVie; }
        public void setPointDeVie(int pointDeVie) { this.pointDeVie = Math.max(pointDeVie, 0); }

        public int getAttaque() { return attaque; }
        public void setAttaque(int attaque) {this.attaque = Math.max(attaque, MIN_ATTAQUE);}

        public int getDefense() { return defense; }
        public void setDefense(int defense) { this.defense = Math.max(defense, MIN_DEFENSE); }

        public void setImage(Drawable[] monstre){
                this.idle = monstre[0];
                this.monstrePas1 = monstre[1];
                this.monstrePas2 = monstre[2];
                activeView.setImageDrawable(idle);
        }
        public ImageView getImageView(){
                return activeView;
        }
        public void setImageView(ImageView activeView){
                this.activeView = activeView;
        }



        // Constructeur
        Monstre(Drawable[] monstre, ImageView activeView) {
            this.idle = monstre[0];
            this.monstrePas1 = monstre[1];
            this.monstrePas2 = monstre[2];
            this.activeView = activeView;
        }

        // Méthode
        public boolean mort(){
            return pointDeVie <= 0;
        }

        @Override
        public void attaquer(IPersonnage cible, ImageView cibleAttaquer){
                Random rand = new Random();
                int degats = rand.nextInt(attaque + 1);
                degats -= cible.getDefense();
                degats = Math.max(degats, 0);
                cible.setPointDeVie(cible.getPointDeVie() - degats);
                if (degats > 0){
                        ImageView avantAttaque = cible.getImageView();
                        cible.setImageView(cibleAttaquer);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                        cible.setImageView(avantAttaque);
                                }
                        }, 500);
                }
        }

}
