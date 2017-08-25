package com.example.afeka.tryinghisshit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class HardGameActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MenuActivity.class));
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_game);

        GameEngine engine = new GameEngine(10, 5, 5, 0);
        engine.createGrid(this);


    }
}
