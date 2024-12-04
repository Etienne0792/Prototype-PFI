package com.example.prototype_pfi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        Drawable idleAttaque;
        public ImageView activeView;

        private int pointDeVie = 5;
        private int attaque = 1;
        private int defense = 0;
        private int speed = 1;

        float gridSize;
        int gridSection;
        public int[][] positionGrid;
        public Directions currentDirection;

        // Get & Set
        public int getSpeed() { return pointDeVie; }
        public void setSpeed(int speed) { this.speed = speed; }

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
        public void setImageView(int drawableId){
                try{
                        activeView.setImageResource(drawableId);
                }
                catch(Exception e){
                        e.printStackTrace();
                }

        }
        public int getIdle(){ return R.drawable.monstre; }
        public int getIdleAtt(){ return R.drawable.monstredegat; }



        // Constructeur
        Monstre(Drawable[] monstre, ImageView activeView, int gridSection, int gridSize) {
            this.idle = monstre[0];
            this.monstrePas1 = monstre[1];
            this.monstrePas2 = monstre[2];
            this.idleAttaque = monstre[3];
            this.activeView = activeView;
            this.gridSection = gridSection;
            this.gridSize = gridSize;
        }

        // Méthode
        public boolean mort(){
            return pointDeVie <= 0;
        }

        private int degats;
        @Override
        public void attaquer(IPersonnage cible, TextView vieAffichage){
                degats = attaque;
                if (degats > 0){

                        cible.setImageView(cible.getIdleAtt());

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                        cible.setPointDeVie(cible.getPointDeVie() - degats);
                                        cible.setImageView(cible.getIdle());
                                        vieAffichage.setText(String.valueOf(cible.getPointDeVie()));
                                }
                        }, 500);
                }
        }

        public Thread Deplacement(Personnages hero, Activity activity, TextView vieAffichage) {
                        return new Thread(new Runnable() {
                                boolean utiliserPas1 = true;
                                @Override
                                public void run() {
                                        while (!Monstre.this.mort() && !hero.mort()) {
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
                                                                                        activeView.setX(activeView.getX() - (gridSize / gridSection));
                                                                                } else if (activeView.getX() < hero.activeView.getX() - (float) hero.activeView.getWidth() / 2) {
                                                                                        activeView.setRotation(0);
                                                                                        activeView.setX(activeView.getX() + (gridSize / gridSection));
                                                                                } else if (activeView.getY() > hero.activeView.getY() + (float) hero.activeView.getHeight() / 2) {
                                                                                        activeView.setRotation(270);
                                                                                        activeView.setY( activeView.getY() - (gridSize / gridSection));
                                                                                } else if (activeView.getY() < hero.activeView.getY() - (float) hero.activeView.getHeight() / 2) {
                                                                                        activeView.setRotation(90);
                                                                                        activeView.setY(activeView.getY() + (gridSize / gridSection));
                                                                                }
                                                                                else{
                                                                                        activeView.setImageDrawable(idle);
                                                                                        attaquer(hero, vieAffichage);
                                                                                }
                                                                        }
                                                                });
                                                        }
                                                        Thread.sleep(350/speed);
                                                } catch (Exception e) {
                                                        e.printStackTrace();
                                                }
                                        }
                                        if(hero.mort()){
                                                Intent intent = new Intent(activity, Defaite.class);
                                                activity.startActivity(intent);
                                                activity.finish();
                                        }
                                }
                        });
                }
}
