package com.example.chatbot.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.example.chatbot.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    Preference darkModePreference;
    SharedPreferences modeSharedPreference;
    Boolean mode;
    Switch modeSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mode = false;
        modeSharedPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (modeSharedPreference != null) {
            mode = modeSharedPreference.getBoolean("mode", false);
        }

        if (mode) {
            getActivity().setTheme(R.style.Dark_Theme);
            Toast.makeText(getActivity(), "Dark Theme", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Light Theme", Toast.LENGTH_SHORT).show();
            getActivity().setTheme(R.style.AppTheme);
        }

        addPreferencesFromResource(R.xml.preferences);

        darkModePreference = findPreference("DarkMode");
        darkModePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = modeSharedPreference.edit();
                boolean switched = ((SwitchPreference) preference).isChecked();
                if (!switched) {
                    Toast.makeText(getActivity(), "Enabled", Toast.LENGTH_SHORT).show();
                    getActivity().setTheme(R.style.Dark_Theme);
                    //modeSharedPreference.edit().putBoolean("mode",true).apply();
                    editor.putBoolean("mode", true);
                    editor.commit();
                } else {
                    Toast.makeText(getActivity(), "Disabled", Toast.LENGTH_SHORT).show();
                    getActivity().setTheme(R.style.AppTheme);
                    //modeSharedPreference.edit().putBoolean("mode",false).apply();
                    editor.putBoolean("mode", false);
                    editor.commit();
                }
                return true;
            }
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
