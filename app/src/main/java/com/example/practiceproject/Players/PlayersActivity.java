package com.example.practiceproject.Players;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practiceproject.InternetConnection.AppExecutor;
import com.example.practiceproject.InternetConnection.InternetUtil;
import com.example.practiceproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayersActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    List<Players> playersList = new ArrayList<>();
    Bitmap playersImage;

    RecyclerView recyclerView;
    PlayersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new PlayersAdapter(this);

        recyclerView.setAdapter(adapter);
        Log.d("Data", playersList.iterator().toString());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPlayersData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.players_activity_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int SelectedItemId = item.getItemId();
        if (SelectedItemId == R.id.players_activity_settings_item) {
            startActivity(new Intent(this, PlayersActivitySettings.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchPlayersData() {
        Random random = new Random();
        for (int i = 0; i<10; i++) {
            Uri uri = InternetUtil.builtUri(String.valueOf(random.nextInt(777)));
            final URL url = InternetUtil.builtUrl(uri);

            AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = InternetUtil.getResponseFromHTTPUrl(url);
                        Log.d("Data", result);
                        processJsonData(result);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setPlayersList(playersList);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void processJsonData(String result)  {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonImageObject = jsonObject.getJSONObject("image");
            String url = jsonImageObject.getString("url");
            String name = jsonObject.getString("name");
            String id = jsonObject.getString("id");
            JSONObject appearance = jsonObject.getJSONObject("appearance");
            JSONArray heightArray = appearance.getJSONArray("height");
            String height = heightArray.getString(0);
            JSONArray weightArray = appearance.getJSONArray("weight");
            String weight = weightArray.getString(1);

            Players player = new Players(url, name, id, height, weight);
            playersList.add(player);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("color_green")) {
            adapter.setColor(sharedPreferences.getBoolean(key, true));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}




/*
{
  "response": "success",
  "id": "10",
  "name": "Agent Bob",
  "powerstats": {
    "intelligence": "10",
    "strength": "8",
    "speed": "13",
    "durability": "5",
    "power": "5",
    "combat": "20"
  },
  "biography": {
    "full-name": "Bob",
    "alter-egos": "No alter egos found.",
    "aliases": [
      "Bob",
      "agent of Hydra",
      "Bob",
      "agent of A.I.M"
    ],
    "place-of-birth": "-",
    "first-appearance": "Cable & Deadpool #38 (May, 2007)",
    "publisher": "Marvel Comics",
    "alignment": "good"
  },
  "appearance": {
    "gender": "Male",
    "race": "Human",
    "height": [
      "5'10",
      "178 cm"
    ],
    "weight": [
      "181 lb",
      "81 kg"
    ],
    "eye-color": "Brown",
    "hair-color": "Brown"
  },
  "work": {
    "occupation": "Mercenary, janitor; former pirate, terrorist",
    "base": "Mobile; formerly Manhattan, Hellcarrier"
  },
  "connections": {
    "group-affiliation": "A.I.M., Deadpool; formerly Agency X, Hydra",
    "relatives": "Allison (ex-wife); Terry and Howie (sons)"
  },
  "image": {
    "url": "https://www.superherodb.com/pictures2/portraits/10/100/10255.jpg"
  }
}
 */