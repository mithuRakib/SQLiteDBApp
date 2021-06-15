package com.example.practiceproject.sqlitedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.practiceproject.InternetConnection.AppExecutor;
import com.example.practiceproject.InternetConnection.InternetUtil;
import com.example.practiceproject.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class AddPlayerToDBActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SQLiteDatabase database;
    private String result = null;

    String linkColorValue;

    ImageView playerImage;
    EditText imageUrl;
    EditText name;
    EditText jerseyNumber;
    EditText age;
    EditText category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player_to_d_b);

        playerImage =findViewById(R.id.loadPlayerImage);
        imageUrl = findViewById(R.id.loadInputImageUrl);

        name = findViewById(R.id.loadInputName);
        jerseyNumber = findViewById(R.id.loadInputJerseyNo);
        age = findViewById(R.id.loadInputAge);
        category = findViewById(R.id.loadInputCategory);

        PlayersDBOpenHelper dbOpenHelper = new PlayersDBOpenHelper(this);
        database = dbOpenHelper.getWritableDatabase();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void addPlayerToTheDatabase(View view) {
        ContentValues contentValues = new ContentValues();
        if (imageUrl.getText().length()>0){
            contentValues.put(PlayersDBContract.PlayersEntry.COLUMN_IMAGE_URL, imageUrl.getText().toString());
        }
        if (name.getText().length()>0){
            contentValues.put(PlayersDBContract.PlayersEntry.COLUMN_NAME, name.getText().toString());
        }
        if (jerseyNumber.getText().length()>0){
            contentValues.put(PlayersDBContract.PlayersEntry.COLUMN_JERSEY_NUMBER, Integer.parseInt(jerseyNumber.getText().toString()));
        }
        if (age.length()>0){
            contentValues.put(PlayersDBContract.PlayersEntry.COLUMN_HEIGHT, age.getText().toString());
        }
        if (category.getText().length()>0){
            contentValues.put(PlayersDBContract.PlayersEntry.COLUMN_WEIGHT, category.getText().toString());
        }

//        INSERT data into the database using Sqlite Database;
//        long success = database.insert(PlayersDBContract.PlayersEntry.TABLE_NAME, null, contentValues);
//        if (success > 0) {
//            Toast.makeText(this, "Player Inserted Successfully", Toast.LENGTH_LONG).show();
//            try {
//                Thread.sleep(1000);
//                finish();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Toast.makeText(this, "Error!!!", Toast.LENGTH_LONG).show();
//        }



//        INSERT DATA INTO THE DATABASE USING CONTENT PROVIDER
        Uri uri = null;
        try {
            uri = getContentResolver().insert(PlayersDBContract.PlayersEntry.CONTENT_URI, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Player Already Exists. \nTherefor not Inserted", Toast.LENGTH_LONG).show();
            finish();
        }

        if (uri != null) {
            Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_player_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int clickedOptionItemId = item.getItemId();
        if (clickedOptionItemId == R.id.loadPlayerData) {
            Uri uri = InternetUtil.builtUri(String.valueOf(new Random().nextInt(777)));
            final URL url = InternetUtil.builtUrl(uri);
            AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        result = InternetUtil.getResponseFromHTTPUrl(url);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ProcessJsonData();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (clickedOptionItemId == R.id.color_setting) {
            startActivity(new Intent(this, PlayersDBActivitySettings.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void ProcessJsonData() throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        String nameData = jsonObject.getString("name");
        JSONObject jsonObjectImage = jsonObject.getJSONObject("image");
        String imageUrlData = jsonObjectImage.getString("url");
        String idData = jsonObject.getString("id");
        JSONObject appearance = jsonObject.getJSONObject("appearance");
        JSONArray heightArray = appearance.getJSONArray("height");
        String heightData = heightArray.getString(0);
        JSONArray weightArray = appearance.getJSONArray("weight");
        String weightData = weightArray.getString(1);
        imageUrl.setText(imageUrlData);
        name.setText(nameData);
        jerseyNumber.setText(idData);
        age.setText(heightData);
        category.setText(weightData);
        Picasso.get().load(Uri.parse(imageUrl.getText().toString())).into(playerImage);
    }

    public void setLinkColorValue(String linkColorValue) {
        this.linkColorValue = linkColorValue;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("link_color")) {
            setLinkColorValue(sharedPreferences.getString("link_color", "red"));
        }
        if (linkColorValue.equals("red")) {
            imageUrl.setTextColor(Color.argb(255, 255, 0,0));
        } else if (linkColorValue.equals("green")) {
            imageUrl.setTextColor(Color.argb(255, 0, 255, 0));
        } else if (linkColorValue.equals("blue")) {
            imageUrl.setTextColor(Color.argb(255, 0, 0, 255));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}