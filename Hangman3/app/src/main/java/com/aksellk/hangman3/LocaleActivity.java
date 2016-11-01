package com.aksellk.hangman3;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by aksel on 25.10.16.
 *
 * Activity loads the locale preference fragment
 */
public class LocaleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,new LocaleFragment()).commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
