package com.example.prototype_pfi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.Serializable;
import java.util.Random;

/**
 * La classe `Monstre` représente un ennemi dans le jeu. Elle implémente l'interface `IPersonnage`
 * et gère les propriétés, les mouvements et les attaques du monstre.
 *
 * @author Daphnée Bastien
 */
public class Monstre implements Serializable, IPersonnage {

        // Constantes
        static final int MIN_ATTAQUE = 1;
        static final int MIN_DEFENSE = 1;

        // Propriétés du monstre
        Drawable idle;
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

        // Getters et Setters

        /**
         * Retourne la vitesse de déplacement du monstre.
         *
         * @return La vitesse de déplacement du monstre.
         */
        public int getSpeed() { return speed; }

        /**
         * Définit la vitesse de déplacement du monstre.
         *
         * @param speed La nouvelle vitesse de déplacement.
         */
        public void setSpeed(int speed) { this.speed = speed; }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getPointDeVie() { return pointDeVie; }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setPointDeVie(int pointDeVie) { this.pointDeVie = Math.max(pointDeVie, 0); }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getAttaque() { return attaque; }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setAttaque(int attaque) { this.attaque = Math.max(attaque, MIN_ATTAQUE); }

        /**
         * Définit les images du monstre.
         *
         * @param monstre Un tableau de Drawables contenant les images du monstre.
         */
        public void setImage(Drawable[] monstre) {
                this.idle = monstre[0];
                this.monstrePas1 = monstre[1];
                this.monstrePas2 = monstre[2];
                activeView.setImageDrawable(idle);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ImageView getImageView() { return activeView; }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setImageView(ImageView activeView) { this.activeView = activeView; }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setImageView(int drawableId) {
                try { activeView.setImageResource(drawableId); }
                catch (Exception e) { e.printStackTrace(); }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIdle() { return R.drawable.monstre; }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIdleAtt() {
                return R.drawable.monstredegat;
        }

        /**
         * Constructeur de la classe `Monstre`.
         *
         * @param monstre     Un tableau de Drawables contenant les images du monstre.
         * @param activeView  L'ImageView associé au monstre.
         * @param gridSection Le nombre de sections dans la grille.
         * @param gridSize    La taille de la grille de jeu.
         */
        Monstre(Drawable[] monstre, ImageView activeView, int gridSection, int gridSize) {
                this.idle = monstre[0];
                this.monstrePas1 = monstre[1];
                this.monstrePas2 = monstre[2];
                this.idleAttaque = monstre[3];
                this.activeView = activeView;
                this.gridSection = gridSection;
                this.gridSize = gridSize;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean mort() { return pointDeVie <= 0; }

        /**
         * {@inheritDoc}
         */
        @Override
        public void attaquer(IPersonnage cible, TextView vieAffichage) {
                if (attaque > 0) {
                        cible.setImageView(cible.getIdleAtt());
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                        cible.setPointDeVie(cible.getPointDeVie() - attaque);
                                        cible.setImageView(cible.getIdle());
                                        vieAffichage.setText(String.valueOf(cible.getPointDeVie()));
                                }
                        }, 500);
                }
        }

        /**
         * Gère le déplacement et l'attaque du monstre.
         * Cette méthode crée un thread qui déplace le monstre vers le héros et l'attaque lorsqu'il est à portée.
         * Si le héros meurt, l'activité `Defaite` est lancée.
         *
         * @param hero         Le personnage du héros.
         * @param activity     L'activité courante.
         * @param vieAffichage Le TextView où afficher les points de vie du héros.
         * @return Le thread gérant le déplacement et l'attaque du monstre.
         */
        public Thread Deplacement(Personnages hero, Activity activity, TextView vieAffichage) {
                return new Thread(new Runnable() {
                        boolean utiliserPas1 = true;

                        @Override
                        public void run() {
                                while (!Monstre.this.mort() && !hero.mort()) {
                                        try {
                                                if (hero.activeView != null) {
                                                        activity.runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                        if (utiliserPas1) {
                                                                                activeView.setImageDrawable(monstrePas1);
                                                                        } else {
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
                                                                                activeView.setY(activeView.getY() - (gridSize / gridSection));
                                                                        } else if (activeView.getY() < hero.activeView.getY() - (float) hero.activeView.getHeight() / 2) {
                                                                                activeView.setRotation(90);
                                                                                activeView.setY(activeView.getY() + (gridSize / gridSection));
                                                                        } else {
                                                                                activeView.setImageDrawable(idle);
                                                                                attaquer(hero, vieAffichage);
                                                                        }
                                                                }
                                                        });
                                                }
                                                Thread.sleep(350 / speed);
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                }
                                if (hero.mort()) {
                                        Intent intent = new Intent(activity, Defaite.class);
                                        activity.startActivity(intent);
                                        activity.finish();
                                }
                                if(Monstre.this.mort()){
                                        activeView.setVisibility(View.INVISIBLE);
                                }
                        }
                });
        }
}
