package com.aksellk.hangman3;

import android.os.Bundle;

/**
 * Created by aksel on 25.10.16.
 *
 * Fragment that loads locale preferences from an xml-file.
 */
public class LocaleFragment extends android.preference.PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load preference from xml-resource:
        addPreferencesFromResource(R.xml.preferences);
    }



}
