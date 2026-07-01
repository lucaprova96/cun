package com.luce.fragments.settings.about

import android.os.Bundle
import androidx.leanback.preference.LeanbackPreferenceFragmentCompat
import androidx.preference.Preference
import com.luce.BuildConfig
import com.luce.R

class SettingsAboutTvFragment : LeanbackPreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_about_tv, rootKey)

        displaySettingsAbout()
    }

    private fun displaySettingsAbout() {
        findPreference<Preference>("p_settings_about_version")?.apply {
            summary = getString(R.string.settings_about_version_name, BuildConfig.VERSION_NAME)
        }

    }
}