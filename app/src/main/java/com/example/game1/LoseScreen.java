package com.example.game1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.game1.GameFolder.MainGame;

public class LoseScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose_screen);
    }

    public void replayLevel(View view) {
        Intent intent_level = getIntent();
        int value = intent_level.getIntExtra("level", 1);
        if (value == 1) {
            Intent intent = new Intent(this, MainGame.class);
            startActivity(intent);
        }

    }

    public void goMainMenu(View view) {
        Intent intent = new Intent(this, GameMenu.class);
        startActivity(intent);
    }
}
