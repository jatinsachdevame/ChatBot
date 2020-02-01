package com.example.chatbot.Settings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.chatbot.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    Preference darkModePreference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        darkModePreference = findPreference("DarkMode");
        darkModePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean switched = ((SwitchPreference) preference).isChecked();
                if (switched) {
                    Toast.makeText(getActivity(), "Disabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Enabled", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
