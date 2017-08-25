package com.example.afeka.tryinghisshit;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.TextView;


public class EasyGameActivity extends AppCompatActivity {

    int count = 0;
    TextView the_time;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MenuActivity.class));
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_game);

        GameEngine engine = new GameEngine(5, 10, 10, 0);
        engine.createGrid(this);

        //the_time = (TextView)findViewById(R.id.textTimer);
        //the_time.setText("" + engine.time);
    }




}
