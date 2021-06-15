package com.example.practiceproject.sqlitedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.practiceproject.InternetConnection.AppExecutor;
import com.example.practiceproject.Players.Players;
import com.example.practiceproject.Players.PlayersAdapter;
import com.example.practiceproject.Players.PlayersViewHolder;
import com.example.practiceproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PlayersDBActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    private SQLiteDatabase database;

    RecyclerView dbRecyclerView;
    PlayersAdapter dbAdapter;

    List<Players> playersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_d_b);
        floatingActionButton = findViewById(R.id.addPlayerToDB);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlayersDBActivity.this, AddPlayerToDBActivity.class));
            }
        });

        dbRecyclerView = findViewById(R.id.playersDBRecView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        dbRecyclerView.setLayoutManager(linearLayoutManager);
        dbRecyclerView.setHasFixedSize(true);

        dbAdapter = new PlayersAdapter(this);
        dbRecyclerView.setAdapter(dbAdapter);

        PlayersDBOpenHelper dbOpenHelper = new PlayersDBOpenHelper(this);
        database = dbOpenHelper.getWritableDatabase();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deletePlayer(viewHolder.itemView.getTag().toString());
                if (!playersList.isEmpty()) {
                    playersList.clear();
                }
                loadPlayerData();
                dbAdapter.setPlayersList(playersList);
                dbAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(dbRecyclerView);
    }

    private boolean deletePlayer(String jerseyNumber){
        return database.delete(PlayersDBContract.PlayersEntry.TABLE_NAME,
                PlayersDBContract.PlayersEntry.COLUMN_JERSEY_NUMBER + "=" +jerseyNumber, null) > 0;
    }

    private Cursor retrieveAllPlayersFromDB() {
//      RETRIEVE DATA WITH ContentProvider and ContentResolver
        Cursor cursor = getContentResolver().query(PlayersDBContract.PlayersEntry.CONTENT_URI,
                null,
                null,
                null,
                PlayersDBContract.PlayersEntry.COLUMN_JERSEY_NUMBER);
        return cursor;

    }

//        RETRIEVE DATA WITH SQLITE DATABASE;
//        return database.query(
//                PlayersDBContract.PlayersEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                PlayersDBContract.PlayersEntry.COLUMN_JERSEY_NUMBER
//        );


    private void loadPlayerData() {
        AppExecutor.getInstance().getMainThread().execute(new Runnable() {
            Cursor cursor = null;
            @Override
            public void run() {
                try {
                    cursor = retrieveAllPlayersFromDB();
                    while (cursor.moveToNext()) {
                        String imageUrl = cursor.getString(cursor.getColumnIndex(PlayersDBContract.PlayersEntry.COLUMN_IMAGE_URL));
                        String name = cursor.getString(cursor.getColumnIndex(PlayersDBContract.PlayersEntry.COLUMN_NAME));
                        String jerseyNumber = cursor.getString(cursor.getColumnIndex(PlayersDBContract.PlayersEntry.COLUMN_JERSEY_NUMBER));
                        String height = cursor.getString(cursor.getColumnIndex(PlayersDBContract.PlayersEntry.COLUMN_HEIGHT));
                        String weight = cursor.getString(cursor.getColumnIndex(PlayersDBContract.PlayersEntry.COLUMN_WEIGHT));

                        Players player = new Players(imageUrl, name, jerseyNumber, height, weight);
                        playersList.add(player);
                    }
                    cursor.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!playersList.isEmpty()) {
            playersList.clear();
        }
        loadPlayerData();
        dbAdapter.setPlayersList(playersList);
        dbAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.players_db_activity_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.players_db_activity_settings_item) {
            startActivity(new Intent(this, PlayersDBActivitySettings.class));
        }
        return super.onOptionsItemSelected(item);
    }
}