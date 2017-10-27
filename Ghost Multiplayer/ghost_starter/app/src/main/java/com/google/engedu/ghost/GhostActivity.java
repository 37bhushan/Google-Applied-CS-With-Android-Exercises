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

public class GhostActivity extends AppCompatActivity {
    private static final String USER2_TURN = "Player 2 Turn !";
    private static final String USER1_TURN = "Player 1 Turn !";
    private FastDictionary dictionary;
    private boolean user1Turn = false;
    private boolean user2Turn = false;
    private TextView text;
    public TextView label;
    public FastDictionary fastDictionary ;
    public int keyPressedCounter = 0;
    public static int intPlayerScore1 = 0;
    public static int intPlayerScore2 = 0;
    private String GHOST = "GHOST";
    public TextView scoreOfPlsyer1 ;
    public TextView scoreOfPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        user1Turn  = true;

        try {

            InputStream inputStream = assetManager.open("words.txt");
            fastDictionary = new FastDictionary(inputStream);

        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary (Fast Dictionary)", Toast.LENGTH_LONG);
            toast.show();
        }

       Button restartButton = (Button) findViewById(R.id.buttonrestart);
       restartButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              changeUser();
              autoReset();
           }
       });

        Button challengeButton = (Button) findViewById(R.id.buttonChallenge);
        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = text.getText().toString();
                text.setText("");
                if (fastDictionary.isWord(string)) {
                    if (user1Turn) {
                        label.setText("Challenged by Player 1 ! Complete Word ! Player 1 Win");
                        intPlayerScore1 +=1;
                        updateScores();
                    } else {
                        label.setText("Challenged by Player 2 ! Complete Word ! Player 2 Win");
                        intPlayerScore2 +=1;
                        updateScores();
                    }

                }else if(fastDictionary.getAnyWordStartingWith(string)!= null){
                    if (user1Turn) {
                        label.setText("Challenged by Player 1 Valid Prefix ! Player 2 Win");
                        intPlayerScore2 +=1;
                        updateScores();
                    } else {
                        label.setText("Challenged by Player 2 Valid Prefix ! Player 1 Win");
                        intPlayerScore1 +=1;
                        updateScores();
                    }
                } else if (fastDictionary.getAnyWordStartingWith(string) == null ) {
                    if (user1Turn) {
                        label.setText("Challenged by Player 1 No Such Word ! Player 1 Win");
                        intPlayerScore1 +=1;
                        updateScores();
                    } else {
                        label.setText("Challenged by Player 2 No Such Word ! Player 2 Win");
                        intPlayerScore2 +=1;
                        updateScores();
                    }
                }
            }

        });

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
        scoreOfPlayer2 = (TextView) findViewById(R.id.player2Score);
        scoreOfPlsyer1 = (TextView) findViewById(R.id.player1Score);
        text.setText("");
        label = (TextView) findViewById(R.id.gameStatus);
        if (user1Turn) {
            label.setText(USER1_TURN);
        } else {
            label.setText(USER2_TURN);
        }
        return true;
    }

    private void changeUser() {
        if(user1Turn)
        {
            user1Turn = false;
            user2Turn = true;
        } else {
            user2Turn = false;
            user1Turn = true;
        }

        if (user1Turn) {
            label.setText(USER1_TURN);
        } else {
            label.setText(USER2_TURN);
        }
    }
//
    void updateScores(){
        int tempLength =0;
        scoreOfPlsyer1.setText(GHOST.substring(0,intPlayerScore1));
        scoreOfPlayer2.setText(GHOST.substring(0,intPlayerScore2));

        if(scoreOfPlsyer1.getText().toString().equalsIgnoreCase("ghost")){
            label.setText("User 2 Wins !!");
            autoReset();
        }else if(scoreOfPlayer2.getText().toString().equalsIgnoreCase("ghost")){
            label.setText("User 1 Wins !!");
            autoReset();
        }
//        scoreOfPlsyer1.setText(String.valueOf(intPlayerScore1));
//        scoreOfPlayer2.setText(String.valueOf(intPlayerScore2));
    }

    void autoReset(){
        intPlayerScore1 = intPlayerScore2 = 0;
        text.setText("");
        scoreOfPlayer2.setText("");
        scoreOfPlsyer1.setText("");
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyPressedCounter == 0) {
            if (keyCode >= 29 && keyCode <= 54) {
                char pressedKey = (char) event.getUnicodeChar();
                text.append(Character.toString(pressedKey));
                String word = text.getText().toString();
                changeUser();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

}
