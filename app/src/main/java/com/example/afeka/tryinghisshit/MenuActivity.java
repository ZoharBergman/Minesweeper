package com.example.afeka.tryinghisshit;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnEasyGame, btnNormalGame, btnHardGame;
    int time;

    private void tickForever(boolean shouldTickOnMainThread) {
        if (shouldTickOnMainThread) {
            tickOnMainThreadForever();
        } else {
            tickOnAnonymousThreadForever();
        }
    }

    private void tickOnAnonymousThreadForever() {
        // Will count forever (unless... ideas?)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    void tickOnMainThreadForever() {
        // Will count forever (unless... ideas?)
        final Handler handler= new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                tick();
                // this => that's the runnable that keeps running all the time...
                handler.postDelayed(this, 1000);
            }
        };

        runnable.run();
    }

    private void tick() {
        time++;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        btnEasyGame = (Button)findViewById(R.id.btnEasyGame);
        btnNormalGame = (Button)findViewById(R.id.btnNormalGame);
        btnHardGame = (Button)findViewById(R.id.btnHardGame);

        btnEasyGame.setOnClickListener(this);
        btnNormalGame.setOnClickListener(this);
        btnHardGame.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent gameIntent = null;
        switch(v.getId()){

            case R.id.btnEasyGame: {
                gameIntent = new Intent(MenuActivity.this, EasyGameActivity.class);
                break;
            }

            case R.id.btnNormalGame: {
                gameIntent = new Intent(MenuActivity.this, NormalGameActivity.class);
                break;
            }

            case R.id.btnHardGame: {
                gameIntent = new Intent(MenuActivity.this, HardGameActivity.class);
                break;
            }
        }
        startActivity(gameIntent);
        this.finish();
    }
}