package com.example.game1;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.game1.GameFolder.MainGame;

public class GameMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("APP CRASH", "MADE IT TO onCreate() for GameMenu");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
    }

    public void shareGame(View view) {
        String share = "Please check out BlazeRunner by Tiago Ferreira at this link: (None right now)";
        ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setChooserTitle("Share this with:")
                .setText(share)
                .startChooser();
    }

    public void tutorial(View view) {
        Intent intent = new Intent(this, Tutorial.class);
        startActivity(intent);
    }

    public void levelOne(View view) {
        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
    }

    //Intent intent = new Intent(this, MainGame.class);
}
