package com.example.afeka.tryinghisshit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NormalGameActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MenuActivity.class));
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_game);

        GameEngine engine = new GameEngine(10, 10 ,10, 0);
        engine.createGrid(this);
    }
}
