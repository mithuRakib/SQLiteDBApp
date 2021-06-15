package com.example.practiceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.practiceproject.LessonIntents.IntentsActivity;
import com.example.practiceproject.Players.PlayersActivity;
import com.example.practiceproject.sqlitedb.PlayersDBActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showPlayers(View view) {
        startActivity(new Intent(MainActivity.this, PlayersActivity.class));
    }

   public void openIntentsActivity(View view) {
        startActivity(new Intent(MainActivity.this, IntentsActivity.class));
   }

   public void openPlayerDatabase(View view) {
        startActivity(new Intent(MainActivity.this, PlayersDBActivity.class));
   }
}