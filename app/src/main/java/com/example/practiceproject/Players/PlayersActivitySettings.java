package com.example.practiceproject.Players;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.practiceproject.R;

public class  PlayersActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_settings);
        if (this.getActionBar() != null) {
            this.getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}