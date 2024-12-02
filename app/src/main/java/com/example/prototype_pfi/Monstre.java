package com.example.prototype_pfi;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

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
        public ConstraintLayout gameGrid;
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
        Monstre(Drawable[] monstre, ImageView activeView, ConstraintLayout gameGrid, int gridSection, int gridSize) {
            this.idle = monstre[0];
            this.monstrePas1 = monstre[1];
            this.monstrePas2 = monstre[2];
            this.activeView = activeView;
            this.gridSection = gridSection;
            this.gridSize = gridSize;
            this.gameGrid = gameGrid;
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

        public Thread Deplacement(Personnages hero, Activity activity) {
                return new Thread(new Runnable() {
                        boolean utiliserPas1 = true;
                        @Override
                        public void run() {
                                while (true) {
                                        try {
                                                if(hero.activeView != null){
                                                        activity.runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {

                                                                        if (utiliserPas1) {
                                                                                activeView.setImageDrawable(monstrePas1);
                                                                        }
                                                                        else {
                                                                                activeView.setImageDrawable(monstrePas2);
                                                                        }
                                                                        utiliserPas1 = !utiliserPas1;

                                                                        if (activeView.getX() > hero.activeView.getX() + (float) hero.activeView.getWidth() / 2) {
                                                                                activeView.setRotation(180);
                                                                                activeView.setX(activeView.getX() - (gridSize / gridSection) / 3);
                                                                        } else if (activeView.getX() < hero.activeView.getX() - (float) hero.activeView.getWidth() / 2) {
                                                                                activeView.setRotation(0);
                                                                                activeView.setX(activeView.getX() + (gridSize / gridSection) / 3);
                                                                        } else if (activeView.getY() > hero.activeView.getY() - gameGrid.getY() + (float) hero.activeView.getHeight() / 2) {
                                                                                activeView.setRotation(270);
                                                                                activeView.setY(activeView.getY() - (gridSize / gridSection) / 3);
                                                                        } else if (activeView.getY() < hero.activeView.getY() - gameGrid.getY() - (float) hero.activeView.getHeight() / 2) {
                                                                                activeView.setRotation(90);
                                                                                activeView.setY(activeView.getY() + (gridSize / gridSection) / 3);
                                                                        }
                                                                        else{
                                                                                activeView.setImageDrawable(idle);
                                                                                attaquer(hero,hero.activeView);
                                                                        }
                                                                }
                                                        });
                                                }
                                                Thread.sleep(250);
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                }
                        }
                });
        }


}
