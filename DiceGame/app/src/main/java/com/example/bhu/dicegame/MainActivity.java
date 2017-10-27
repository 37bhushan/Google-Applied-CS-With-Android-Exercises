package com.example.bhu.dicegame;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.util.Timer;


public class MainActivity extends AppCompatActivity {

    public int userTotalScore = 0 ;
    public int compTotalScore = 0;
    public int userCurrentScore = 0;
    public int compCurrtentScore = 0;
    public int secs = 120 ;
    public Handler handler;	//Post message to start roll
    Timer timer=new Timer();	//Used to implement feedback to user
    Thread thread;

//    Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            tempToast();
//            handler.postDelayed(runnable,10000);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final Button holdButton = (Button) findViewById(R.id.Holdbutton);
       final Button rollButton = (Button) findViewById(R.id.RollButton);
       Button resetButton = (Button) findViewById(R.id.ResetButton);
       final TextView textView = (TextView) findViewById(R.id.PlayerScore );
        handler=new Handler();
        if (rollButton != null) {
            rollButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   userCurrentScore = rollDice();
                   userTotalScore += userCurrentScore;
                   textView.setText(String.valueOf(userTotalScore));
                   resultCalculator();
                   if (userCurrentScore == 1) {
                       DelayRoutine(120);
                       userCurrentScore = 0;
                       rollButton.setEnabled(false);
                       holdButton.setEnabled(false);

                       while (compCurrtentScore !=1){
                           DelayRoutine(120);
                           compRoll();
                       }
                       if (compCurrtentScore == 1) {
                           DelayRoutine(120);
                           compCurrtentScore = 0;
                           rollButton.setEnabled(true);
                           holdButton.setEnabled(true);
                       }
                   }

                   }
            });
        }
        assert holdButton != null;
        holdButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            while (compCurrtentScore !=1){
              compRoll();
            }
            if (compCurrtentScore == 1)
                compCurrtentScore = 0;

           }
       });

        assert resetButton != null;
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAll();
            }
        });

    }
    void  resultCalculator(){
        if (userTotalScore >=100){
            Toast.makeText(this,"You Win!",Toast.LENGTH_LONG).show();
            resetAll();
        }
        if (compTotalScore>=100){
            Toast.makeText(this,"Better Luck Next Time !",Toast.LENGTH_SHORT).show();
            resetAll();
        }

    }
    void tempToast(){
        Toast.makeText(this,"Computer Turn !",Toast.LENGTH_LONG).show();
    }

    void  resetAll(){
        TextView textView = (TextView) findViewById(R.id.CompScore);
        textView.setText("0");
        textView = (TextView) findViewById(R.id.PlayerScore);
        textView.setText("0");
        userCurrentScore = 0;
        compCurrtentScore = 0;
        userTotalScore = compTotalScore = 0;
    }

    void compRoll(){
        if (userTotalScore-compTotalScore >=10) {
            compCurrtentScore = rollDice();
            compTotalScore += compCurrtentScore;
            TextView textView1 = (TextView) findViewById(R.id.CompScore);
            textView1.setText(String.valueOf(compTotalScore));
            resultCalculator();
        }
        else {
            ImageView imageView = (ImageView) findViewById(R.id.DiceDrawable);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice1 ));
            compCurrtentScore = 1;
            compTotalScore +=compCurrtentScore;
            TextView textView1 = (TextView) findViewById(R.id.CompScore);
            textView1.setText(String.valueOf(compTotalScore));
            resultCalculator();
        }
     //   handler.postDelayed(runnable,1000);
    }

    public  void DelayRoutine(int Time){
        try {
            tempToast();
            thread.sleep(Time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int rollDice(){
        Random random = new Random();
        int randomNumber = random.nextInt(6);
        randomNumber ++;
        ImageView imageView = (ImageView) findViewById(R.id.DiceDrawable);


        switch (randomNumber){
            case 1: imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                    return 1;
            case 2: imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                    return 2;
            case 3: imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                    return 3;
            case 4: imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                    return 4;
            case 5: imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                    return 5;
            case 6: imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
                    return 6;

        }
            return 0;
    }


}
