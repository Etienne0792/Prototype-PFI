package com.example.prototype_pfi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class startingRoom extends AppCompatActivity {

    final int GRID_SIZE = 385;
    final int GRID_SECTIONS = 11;
    LinearLayout gameGrid;
    Button right;
    Button down;
    Button up;
    Button left;
    Drawable idle;
    Drawable pas1;
    Drawable pas2;
    ImageView activeView;
    Personnages hero;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.starting_room);

        gameGrid = findViewById(R.id.gameGrid);
        idle = getDrawable(R.drawable.personnage);
        pas1 = getDrawable(R.drawable.pas1);
        pas2 = getDrawable(R.drawable.pas2);
        activeView = findViewById(R.id.hero);

    }

    @Override
    protected void onStart() {
        super.onStart();

        float density = getResources().getDisplayMetrics().density;
        int gridSize = (int) (GRID_SIZE * density + 0.5f);

        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);

        int[][] positionGrid = new int[GRID_SECTIONS][GRID_SECTIONS];
        for ( int i = 0; i < positionGrid.length; i++){
            for ( int j = 0; j < positionGrid.length; j++){
                if(i == 0 || j == 0 || i == positionGrid.length - 1 || j == positionGrid.length - 1){
                    positionGrid[i][j] = 1;
                }
                else{
                    positionGrid[i][j] = 0;
                }
            }
        }

        positionGrid[GRID_SECTIONS / 2][GRID_SECTIONS / 2] = 2;
        positionGrid[GRID_SECTIONS / 2 -1][GRID_SECTIONS-1] = 0;
        positionGrid[GRID_SECTIONS / 2][GRID_SECTIONS -1] = 0;
        positionGrid[GRID_SECTIONS / 2 + 1][GRID_SECTIONS -1] = 0;

        hero = new Personnages(idle,pas1,pas2,activeView, gridSize, GRID_SECTIONS, gameGrid);
        hero.deplacementRight(right,this,positionGrid);
        hero.deplacementLeft(left,this,positionGrid);
        hero.deplacementUp(up,this,positionGrid);
        hero.deplacementDown(down,this,positionGrid);
    };
}