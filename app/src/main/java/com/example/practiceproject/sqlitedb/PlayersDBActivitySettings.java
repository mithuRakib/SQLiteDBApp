package com.example.practiceproject.sqlitedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.practiceproject.R;

public class PlayersDBActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_d_b_settings);
        if (this.getActionBar() != null) {
            this.getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}