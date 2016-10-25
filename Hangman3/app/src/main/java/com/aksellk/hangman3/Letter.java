package com.aksellk.hangman3;

import android.widget.TextView;

/**
 * Created by aksel on 22.10.16.
 */

public class Letter {

    private String letter;
    private int id;
    private TextView textView;

    public Letter(String letter,int id,TextView t) {
        this.letter=letter;
        this.id = id;
        this.textView = t;
    }

    public int getId() {
        return id;
    }

    public String getLetter() {
        return letter;
    }

    public TextView getTextView() {
        return textView;
    }
}

