package com.example.paradice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import java.util.Random;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    int userTotalScore = 0;
    int userTurnScore = 0;
    int compTotalScore = 0;
    int compTurnScore = 0;
    boolean compTurn = false;
    boolean win = false;
    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            GifImageView rollgif = (GifImageView) findViewById(R.id.rollgif);
            rollgif.setVisibility(View.GONE);
            ImageView coverup = (ImageView) findViewById(R.id.coverup);
            coverup.setVisibility(View.GONE);
        }
    };

    Handler compHandler = new Handler();
    Runnable compRunnable = new Runnable() {

        @Override
        public void run() {
            compTurn();
        }
    };

    Handler ouchHandler = new Handler();
    Runnable ouchRunnable = new Runnable() {

        @Override
        public void run() {
            endTurnHandler((Button)findViewById(R.id.endTurn));
        }
    };

    Handler youchHandler = new Handler();
    Runnable youchRunnable = new Runnable() {

        @Override
        public void run() {
            endTurn();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GifImageView rollgif = (GifImageView) findViewById(R.id.rollgif);
        rollgif.setVisibility(View.GONE);
        ImageView coverup = (ImageView) findViewById(R.id.coverup);
        coverup.setVisibility(View.GONE);
    }

    public void rollHandler(View view){
        if(!compTurn&&!win) {
            TextView label = (TextView)findViewById(R.id.turninfo);
            label.setText("your turn!");
            int score = roll() + 1;
            TextView turnScore = (TextView) findViewById(R.id.turnScore);
            if (score == 1) {
                userTurnScore = 0;
                turnScore.setText("turn score: " + userTurnScore);

                startTime = System.currentTimeMillis();
                label.setText("You rolled a one :(");
                ouchHandler.postDelayed(ouchRunnable, 750);
            } else {
                userTurnScore = userTurnScore + score;
                turnScore.setText("turn score: " + userTurnScore);
                Button rolling = (Button) findViewById(R.id.roll);
                rolling.setText("REROLL");
            }
        }
    }


    public int roll(){
        GifImageView rollgif = (GifImageView) findViewById(R.id.rollgif);
        rollgif.setVisibility(View.VISIBLE);
        ImageView coverup = (ImageView) findViewById(R.id.coverup);
        coverup.setVisibility(View.VISIBLE);
        ImageView prevIMG = (ImageView)findViewById(R.id.imageView);
        int randNum = new Random().nextInt(6);
        String img = "d"+randNum;
        int resID = getResources().getIdentifier(img , "drawable", getPackageName());
        Drawable image = getResources().getDrawable(resID);
        prevIMG.setImageDrawable(image);
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 1000);
        return randNum;
    }

    public void endTurnHandler(View view){
        userTotalScore = userTotalScore+userTurnScore;
        TextView score = (TextView)findViewById(R.id.userScore);
        score.setText("your score: "+userTotalScore);
        userTurnScore = 0;
        TextView turnScore = (TextView)findViewById(R.id.turnScore);
        turnScore.setText("turn score: "+userTurnScore);
        Button rolling = (Button)findViewById(R.id.roll);
        rolling.setText("ROLL");
        if(userTotalScore<100){
            compTurn();
        }
        else{
            TextView label = (TextView)findViewById(R.id.turninfo);
            label.setText("You win!");
            win = true;
        }

    }

    public void endTurn(){
        compTotalScore = compTotalScore+compTurnScore;
        TextView score = (TextView)findViewById(R.id.compScore);
        score.setText("computer score: "+compTotalScore);
        compTurnScore = 0;
        TextView turnScore = (TextView)findViewById(R.id.turnScore);
        turnScore.setText("turn score: ");
        Button rolling = (Button)findViewById(R.id.roll);
        rolling.setText("ROLL");
        if(compTotalScore>=100){
            TextView label = (TextView)findViewById(R.id.turninfo);
            label.setText("You lose :(");
            win = true;
        }
    }

    public void newGameHandler(View view){
        userTotalScore = 0;
        userTurnScore = 0;
        compTotalScore = 0;
        compTurnScore = 0;
        Button rolling = (Button)findViewById(R.id.roll);
        rolling.setText("ROLL");
        TextView turnScore = (TextView)findViewById(R.id.turnScore);
        turnScore.setText("turn score:");
        TextView uScore = (TextView)findViewById(R.id.userScore);
        uScore.setText("your score:");
        TextView cScore = (TextView)findViewById(R.id.compScore);
        cScore.setText("computer score:");
        ImageView prevIMG = (ImageView)findViewById(R.id.imageView);
        String img = "d0";
        int resID = getResources().getIdentifier(img , "drawable", getPackageName());
        Drawable image = getResources().getDrawable(resID);
        prevIMG.setImageDrawable(image);
        TextView label = (TextView)findViewById(R.id.turninfo);
        label.setText("your turn!");

    }

    public void compTurn(){
        compTurn = true;
        compRoll();
        if(compTurn){
            TextView label = (TextView)findViewById(R.id.turninfo);
            label.setText("It's the computer's turn");
            startTime = System.currentTimeMillis();
            compHandler.postDelayed(compRunnable, 2750);
        }
    }

    public void compRoll(){
        int tempScore = roll()+1;
        compTurnScore = compTurnScore + tempScore;
        if(tempScore == 1){
            compTurn = false;
            compTurnScore = 0;
            TextView turnScore = (TextView)findViewById(R.id.turnScore);
            turnScore.setText("turn score: 0");
            TextView label = (TextView)findViewById(R.id.turninfo);
            startTime = System.currentTimeMillis();

            label.setText("The computer rolled a one!");
            youchHandler.postDelayed(youchRunnable, 750);
        }
        else{
            TextView turnScore = (TextView)findViewById(R.id.turnScore);
            turnScore.setText("turn score: "+compTurnScore);
        }
        if(compTurnScore>9||compTotalScore>=100){
            compTurn = false;
            TextView label = (TextView)findViewById(R.id.turninfo);
            label.setText("The computer has ended its turn.");
            endTurn();
        }
    }

}
