package com.example.myapplication.util

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingActivityFragment: PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(com.example.myapplication.R.xml.preference_screen)

    }
}