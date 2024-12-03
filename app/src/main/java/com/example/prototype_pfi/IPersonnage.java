package com.example.prototype_pfi;

import android.widget.ImageView;
import android.widget.TextView;

public interface IPersonnage {
    static final int MIN_ATTAQUE = 1;
    static final int MIN_DEFENSE = 1;

    int getPointDeVie();
    void setPointDeVie(int vie);
    int getAttaque();
    void setAttaque(int attaque);
    int getDefense();
    void setDefense(int defense);
    public ImageView getImageView();
    public void setImageView(ImageView activeView);
    public void setImageView(int drawableId);

    public boolean mort();
    public void attaquer(IPersonnage cible, TextView vieAffichage);

    public int getIdle();
    public int getIdleAtt();

}