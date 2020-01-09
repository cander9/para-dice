package com.example.paradice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int userTotalScore = 0;
    int userTurnScore = 0;
    int compTotalScore = 0;
    int compTurnScore = 0;
    boolean compTurn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void rollHandler(View view){
        if(!compTurn) {
            int score = roll() + 1;
            TextView turnScore = (TextView) findViewById(R.id.turnScore);
            if (score == 1) {
                userTurnScore = 0;
                turnScore.setText("turn score: " + userTurnScore);
                endTurn();
                compTurn();
            } else {
                userTurnScore = userTurnScore + score;
                turnScore.setText("turn score: " + userTurnScore);
                Button rolling = (Button) findViewById(R.id.roll);
                rolling.setText("REROLL");
            }
        }
    }


    public int roll(){
        ImageView prevIMG = (ImageView)findViewById(R.id.imageView);
        int randNum = new Random().nextInt(6);
        String img = "d"+randNum;
        int resID = getResources().getIdentifier(img , "drawable", getPackageName());
        Drawable image = getResources().getDrawable(resID);
        prevIMG.setImageDrawable(image);
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
        compTurn();
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

    }

    public void compTurn(){
        compTurn = true;
        compRoll();
        if(compTurn){
            
            compRoll();
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
            endTurn();
        }
        else{
            TextView turnScore = (TextView)findViewById(R.id.turnScore);
            turnScore.setText("turn score: "+compTurnScore);
        }
        if(compTurnScore>9){
            compTurn = false;
            endTurn();
        }
    }

}
