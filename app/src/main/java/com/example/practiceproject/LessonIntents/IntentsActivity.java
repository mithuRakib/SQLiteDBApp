package com.example.practiceproject.LessonIntents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.practiceproject.R;

import java.net.MalformedURLException;
import java.net.URL;

public class IntentsActivity extends AppCompatActivity {
    EditText searchUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents);
        searchUrl = findViewById(R.id.inputUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.players_activity_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedItem = item.getItemId();
        if (selectedItem == R.id.search_button) {
            String testUrl = "www.facebook.com";
            openWebPage(testUrl);
        } else if (selectedItem == R.id.openGeoLocation) {
            openGeoLocation();
            Log.d("Data: ", "resolveActivity for Geo wasn't found.");
        }
        return super.onOptionsItemSelected(item);
    }

    private void openGeoLocation() {
        String scheme = "Geo:";
        String path = "0, 0";
        String address = "Tongi, Gazipur, Bangladesh";

        Uri.Builder builder = new Uri.Builder().scheme(scheme).path(path).query(address);
        Uri uriAddress = builder.build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uriAddress);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.d("Data", "ResolveActivity was found");
            startActivity(intent);
        } else {
            Log.d("Data", "ResolveActivity was not found");
        }
    }
}