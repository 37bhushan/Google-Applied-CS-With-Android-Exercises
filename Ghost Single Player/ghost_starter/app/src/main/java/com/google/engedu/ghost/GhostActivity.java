package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn !";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
  //  private Random random = new Random();
    private TextView text;
    public TextView label;
    public SimpleDictionary simpleDictionary ;
    public String currentWord;
    public char[] currentWordArray = new char[20];
//    public int indexOfCurrentChar = 3;
    public int keyPressedCounter = 0;
    public int intPlayerScore = 0;
    public int intCompScore = 0;
    public String GHOST = "GHOST";
    public TextView tv_PlayerScore ;
    public TextView tv_CompScore;
    public Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        userTurn  = true;

        try {

            InputStream inputStream1 = assetManager.open("words.txt");
            //dictionary = new SimpleDictionary(inputStream1);
            simpleDictionary = new SimpleDictionary(inputStream1);

        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary (Simple Dictionary)", Toast.LENGTH_LONG);
            toast.show();
        }


       Button restartBurron = (Button) findViewById(R.id.buttonrestart);
       restartBurron.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               text.setText(randomStringGenerator());
               label.setText("Your Turn !");
               autoReseter();
           }
       });

        Button challengeButton = (Button) findViewById(R.id.buttonChallenge);
        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = text.getText().toString();
                if (simpleDictionary.isWord(string)){
                    label.setText("Challenged by Player ! Compete Word ! Game Over ! You Win");
                    intCompScore +=1;
                    scoreUpdate();
                }
                else if (simpleDictionary.getAnyWordStartingWith(string)==null){
                    label.setText("Challenged by Player !! Invalid Prefix ! Game Over ! You Win");
                    intCompScore +=1;
                    scoreUpdate();
                }else {
                    label.setText("Valid Prefix !");
                    intCompScore +=1;
                    scoreUpdate();
                }
                }

        });

        tv_PlayerScore = (TextView) findViewById(R.id.PlayerScore);
        tv_CompScore = (TextView) findViewById(R.id.CompScore);

        onStart(null);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */

    public boolean onStart(View view) {
       // userTurn = random.nextBoolean();
        text = (TextView) findViewById(R.id.ghostText);
        text.setText(randomStringGenerator());



         label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
            DelayRoutine(120);
        }
        return true;
    }

    private void computerTurn() {
        label.setText(COMPUTER_TURN);
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        String word = text.getText().toString();

        keyPressedCounter = 0;
        if (simpleDictionary.isWord(word)) {
            label.setText("Complete Word ! Game Over ! Comp Win ");
            intPlayerScore +=1;
            scoreUpdate();
        } else if (word.length() >= 4) {
            int length = word.length();
            String getAnyGoodWordHolder;
            getAnyGoodWordHolder = simpleDictionary.getAnyWordStartingWith(word);
            currentWordArray = getAnyGoodWordHolder.toCharArray();
            currentWord = getAnyGoodWordHolder;
            if(simpleDictionary.getAnyWordStartingWith(text.getText().toString())==null){
                label.setText("Challenged By Computer ! Invalid Prefix ! Game Over");
                intPlayerScore +=1;
                scoreUpdate();
            }else {
            String temp = String.valueOf(currentWordArray[length]);
            text.append(temp);
            if (simpleDictionary.isWord(text.getText().toString())) {
                label.setText("Complete Word ! Game Over ! You Win");
                intCompScore +=1;
                scoreUpdate();
            } else {
                randomStringGenerator();
                }
            }
            DelayRoutine(120);
            userTurn = true;
            label.setText(USER_TURN);
        }

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyPressedCounter == 0) {
            if (keyCode >= 29 && keyCode <= 54) {
                char pressedKey = (char) event.getUnicodeChar();
                text.append(Character.toString(pressedKey));
                String word = text.getText().toString();
                if (simpleDictionary.isWord(word)) {
                    label.setText("Complete Word ! Game Over ! Comp Win !");
                    intPlayerScore +=1;
                    scoreUpdate();
                }else if(simpleDictionary.getAnyWordStartingWith(text.getText().toString())==null){
                    label.setText("Challenged by Computer ! Invalid Prefix ! Game Over");
                    intPlayerScore +=1;
                    scoreUpdate();
                }
                else {
                    pressedKey = 1;
                    computerTurn();
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    void DelayRoutine(int Time){
        try {
            thread.sleep(Time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void scoreUpdate()
    {

        tv_PlayerScore.setText(GHOST.substring(0,intPlayerScore));
        tv_CompScore.setText(GHOST.substring(0, intCompScore));

        if (tv_CompScore.getText().toString().equalsIgnoreCase(GHOST)){
            label.setText("GHOST Copmlete ! You Win !");
            autoReseter();

        }
        else if(tv_PlayerScore.getText().toString().equalsIgnoreCase(GHOST)){
            label.setText("GHOST Complete ! Comp Win ! ");
            autoReseter();

        }

        text.setText(randomStringGenerator());
    }

    void autoReseter(){
        intCompScore = intPlayerScore =0;
        tv_CompScore.setText("");
        tv_PlayerScore.setText("");
    }


    public String  randomStringGenerator(){
        Random random = new Random();
        int ListSize =simpleDictionary.words.size();
        int intRandomNumber = random.nextInt(ListSize);
        String capturedString;
        capturedString = simpleDictionary.words.get(intRandomNumber);
        currentWord = capturedString;
        currentWordArray = currentWord.toCharArray();
        String returnedString = null;

        if(capturedString.length() < 4)
        {
            randomStringGenerator();
        } else {
             returnedString = capturedString.substring(0, 3);

        }

        return (returnedString);
    }


}
