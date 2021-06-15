package com.example.practiceproject.Preferences;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.practiceproject.R;

public class PlayersDBSettingsPreferenceFragment extends PreferenceFragmentCompat
    implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.players_db_settings_preference_fragment);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();

        for (int i=0; i<preferenceScreen.getPreferenceCount(); i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (!(preference instanceof CheckBoxPreference)) {
                String preferenceValue = sharedPreferences.getString(preference.getKey(), "");
                if (preference instanceof ListPreference) {
                    ListPreference listPreference = (ListPreference) preference;
                    if (listPreference.findIndexOfValue(preferenceValue) >= 0) {
                        listPreference.setSummary(listPreference.getEntries()[listPreference.findIndexOfValue(preferenceValue)]);
                    }
                }
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null) {
            if (!(preference instanceof CheckBoxPreference)) {
                String preferenceValue = sharedPreferences.getString(key, "");
                if (preference instanceof ListPreference) {
                    ListPreference listPreference = (ListPreference) preference;
                    if (listPreference.findIndexOfValue(sharedPreferences.getString(key, ""))>=0) {
                        listPreference.setSummary(listPreference.getEntries()[listPreference.findIndexOfValue(sharedPreferences.getString(key, ""))]);
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
