package com.example.practiceproject.Preferences;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.practiceproject.R;

public class PlayersSettingsPreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.players_settings_preference_fragment);
    }
}
