package com.aksellk.hangman3;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by aksel on 25.10.16.
 */

public class LocaleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,new LocaleFragment()).commit();
    }
}
