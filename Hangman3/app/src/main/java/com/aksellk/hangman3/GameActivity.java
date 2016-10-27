package com.aksellk.hangman3;


import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    private ArrayList<String> words;
    private ArrayList<Letter> letters;
    private TypedArray picture_ids;
    private ImageView hangmanImage;
    private int turns = 0;
    private int correctLetters = 0;
    private TextView letterAE;
    private TextView letterOE;
    private TextView letterAA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        letterAE = (TextView) findViewById(R.id.letterAE);
        letterOE = (TextView) findViewById(R.id.letterOE);
        letterAA = (TextView) findViewById(R.id.letterAA);


        Resources resources = getResources();
        Locale locale = resources.getConfiguration().locale;
        if(!(locale.toString().equals("nb"))) { // if not norwegian
            letterAE.setVisibility(View.GONE);
            letterOE.setVisibility(View.GONE);
            letterAA.setVisibility(View.GONE);
        } else {
            letterAE.setVisibility(View.VISIBLE);
            letterOE.setVisibility(View.VISIBLE);
            letterAA.setVisibility(View.VISIBLE);
        }

        // create the image:
        picture_ids = resources.obtainTypedArray(R.array.pictures);
        hangmanImage = (ImageView) findViewById(R.id.hangmanImageView);
        turns = picture_ids.length()-1;
        updateImage();

        // create the word:
        letters = new ArrayList<Letter>();
        words = new ArrayList<String>(Arrays.asList(resources.getStringArray(R.array.words)));
        newWord();
    }

    // restart by starting the main activity:
    private void restart() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void onLetterClicked(View view) {
        TextView letterTextView = (TextView) view;
        String letter = letterTextView.getText().toString().trim();
        Log.i("onLetterClicked letter ",letter);
        int found = 0; // counts correctly guessed letters
        int id = -1;
        for (Letter l : letters) {
            if (letter.equalsIgnoreCase(l.getLetter())) {
                found++;
                id = l.getId();
                Log.i("onLetterClicked id ", Integer.toString(id));
                TextView t = l.getTextView();
                t.setText(letter);
            }
        }
        for (int i = 0; i < found; i++) { // runs as many times as there are letters
            incrementCorrect(); // increments the number of correctly guessed letters
            if (!(getCorrectLetters() < letters.size())) { // game ended the player won
                Toast.makeText(getApplicationContext(), "you won", Toast.LENGTH_LONG).show();
                restart();
            }
        }
        if (found == 0) updateImage(); // no found letters

        letterTextView.setVisibility(View.INVISIBLE);
    }

    private void updateImage() {
        if (turns>0) {
            Drawable d = getResources().getDrawable(picture_ids.peekValue(turns).resourceId, null);
            hangmanImage.setImageDrawable(d);
            turns--;
        } else {
            Toast.makeText(getApplicationContext(), "out of turns", Toast.LENGTH_LONG).show();
            restart();
        }

    }

    private void newWord() {

        Random random = new Random();
        int index = random.nextInt(words.size());
        String word = words.get(index);
        Log.i("randomWord() ",word);
        String[] tmp = word.split("");
        int c = 0;
        for(int i = 1; i < tmp.length; i++) {
            TextView letterTextView = new TextView(this);
            letters.add(new Letter(tmp[i].trim(),c,letterTextView));
            c++;
        }
        LinearLayout container = (LinearLayout) findViewById(R.id.layout_letter_line);
        for (Letter letter : letters) {
            TextView tv = letter.getTextView();
            tv.setId(letter.getId());
            if(letter.getLetter().equals(" ")) {
                tv.setText(" ");
                incrementCorrect(); // because of the counted whitespace
            }
            else tv.setText("_");
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(15, 0, 15, 0);
            tv.setLayoutParams(lp);
            container.addView(tv);

            Log.i("newWord id ", Integer.toString(letter.getId()));
            Log.i("letter: ",letter.getLetter());
            Log.i("newWord() letter ",tv.getText().toString());
        }
        Log.i("correcLetters() ",Integer.toString(getCorrectLetters()));
        Log.i("letters.size() ",Integer.toString(letters.size()));
    }

    private void incrementCorrect() {
        this.correctLetters = getCorrectLetters() + 1;
    }

    public int getCorrectLetters() {
        return correctLetters;
    }
}
