package com.aksellk.hangman3;


import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

/**
 * Activity class for the game itself.
 * This Activity handles one game of hangman.
 * The Activity is started and finished for each round of the hangman game.
 */
public class GameActivity extends AppCompatActivity {


    private ArrayList<String> words;
    private ArrayList<Letter> letters;
    private TypedArray picture_ids;
    private ImageView hangmanImage;
    private int turns = 0;
    private int correctLetters = 0;
    private String answer;
    private TextView letterAE;
    private TextView letterOE;
    private TextView letterAA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem settings = (MenuItem) menu.findItem(R.id.action_settings);
        settings.setVisible(false);
        this.invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.exit){
            finishAffinity();
            return true;
        }
        if (id==R.id.help) {
            startActivity(new Intent(getApplicationContext(), HelpActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // restart by starting the main activity:
    private void restart() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    /**
     * Handles input letters from the user.
     * Controlls the game by updating the number of correctly guessed letters and incorrectly guessed letters.
     * Ends the game and the Activity if the player has found the solution or if the hangman is complete.
     * @param view the TextView containing the pressed letter
     */
    public void onLetterClicked(View view) {
        TextView letterTextView = (TextView) view;
        String letter = letterTextView.getText().toString().trim();
        //Log.i("onLetterClicked letter ",letter);
        int found = 0; // counts correctly guessed letters
        //int id = -1;
        for (Letter l : letters) {
            if (letter.equalsIgnoreCase(l.getLetter())) {
                found++;
                TextView t = l.getTextView();
                t.setText(letter);
            }
        }
        for (int i = 0; i < found; i++) { // runs as many times as there are letters
            incrementCorrect(); // increments the number of correctly guessed letters
            if (!(getCorrectLetters() < letters.size())) { // game ended the player won
                showDialog(true);
            }
        }
        if (found == 0) updateImage(); // no found letters

        letterTextView.setVisibility(View.INVISIBLE);
    }


    /**
     * updates the hangman ImageView by setting a new picture and ends the game if the hangman drawing is complete
     */
    private void updateImage() {
        if (turns>0) {
            Drawable d = getResources().getDrawable(picture_ids.peekValue(turns).resourceId, null);
            hangmanImage.setImageDrawable(d);
            turns--;
        } else {
            Drawable d = getResources().getDrawable(picture_ids.peekValue(turns).resourceId, null);
            hangmanImage.setImageDrawable(d);
            showDialog(false);
        }

    }

    /**
     * Selects a random word from the resource array of words
     */
    private void newWord() {

        Random random = new Random();
        int index = random.nextInt(words.size());
        String word = words.get(index);
        setAnswer(word);
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
        }
    }

    /**
     * Displays a dialog and ends the activity when the game is over
     * @param status true if the player has won. false if the player has lost
     */
    private void showDialog(boolean status) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = 800;
        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);

        TextView statusTV = (TextView) dialog.findViewById(R.id.status);
        TextView answerTV = (TextView) dialog.findViewById(R.id.answer);

        if (status) statusTV.setText(R.string.won);
        else statusTV.setText(R.string.lose);
        answerTV.setText(getAnswer().toString());

        Button toMain = (Button) dialog.findViewById(R.id.toMain);
        toMain.setOnClickListener(
            new Button.OnClickListener(){

                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    finish();
                }
            }
        );
        dialog.show();
    }

    private void incrementCorrect() {
        this.correctLetters = getCorrectLetters() + 1;
    }

    public int getCorrectLetters() {
        return correctLetters;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
