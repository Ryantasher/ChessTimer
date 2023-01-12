package com.example.chesstimer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ChessTimerActivity extends AppCompatActivity {

    //initalize timers
    private CountDownTimer countDownTimer1;
    private CountDownTimer countDownTimer2;

    //initialize code interface to player layouts
    private TextView player1CountdownTextView;
    private TextView player2CountdownTextView;

    //init player start times
    private long player1startTime = 300;
    private long player2startTime = 300;

    //init player remaining times
    private long player1RemainingTime;
    private long player2RemainingTime;

    //init player turn boolean
    private boolean isPlayer1TimerRunning;
    private boolean isPlayer2TimerRunning;


    @Override //onCreate saves instance state in case of damage to activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets activity_main as the xml for the layout of this activity
        setContentView(R.layout.activity_main);

        //creates objects for the player layouts
        player1CountdownTextView = findViewById(R.id.player1_countdown);
        player2CountdownTextView = findViewById(R.id.player2_countdown);
        //sets initial text to player layouts
        player1CountdownTextView.setText(formatTime(player1startTime));
        player2CountdownTextView.setText(formatTime(player2startTime));

        //player 1 listener
        player1CountdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            //when player1 layout is clicked
            public void onClick(View v) {
                //if player1 isn't currently running when clicked (if clickable),
                //start player1's clock, set player2 to un-clickable, and change the color to active
                if (!isPlayer1TimerRunning) {
                    player1RemainingTime = player1startTime;
                    countDownTimer1 = new CountDownTimer(player1RemainingTime * 1000,
                            1000) {
                        public void onTick(long millisUntilFinished) {
                            player1RemainingTime = millisUntilFinished / 1000;
                            player1CountdownTextView.setText(formatTime(player1RemainingTime));
                            player1startTime = player1RemainingTime;
                        }
                        public void onFinish() {
                            player1CountdownTextView.setText(R.string.TimeEnd);
                        }
                    }.start();
                    isPlayer1TimerRunning=true;
                    player1CountdownTextView.setClickable(true);
                    player1CountdownTextView.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    player2CountdownTextView.setClickable(false);

                } else { //if player1 layout is clicked while it is already running, switch to
                         // player2's clock, switch colors and clickable status
                    countDownTimer1.cancel();
                    isPlayer1TimerRunning = false;
                    player1CountdownTextView.setClickable(false);
                    player2CountdownTextView.setClickable(true);
                    player1CountdownTextView.setBackgroundColor(getResources().getColor(R.color.black));
                    player2CountdownTextView.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    player2RemainingTime = player2startTime;
                    countDownTimer2 = new CountDownTimer(player2RemainingTime * 1000,
                            1000) {
                        public void onTick(long millisUntilFinished) {
                            player2RemainingTime = millisUntilFinished / 1000;
                            player2CountdownTextView.setText(formatTime(player2RemainingTime));
                            player2startTime = player2RemainingTime;
                        }
                        public void onFinish() {
                            player2CountdownTextView.setText(R.string.TimeEnd);
                        }
                    }.start();
                    isPlayer2TimerRunning = true;
                }
            }
        });

        //player 2 listener
        player2CountdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            //when player2 layout is clicked
            public void onClick(View v) {
                //if player2 isn't currently running when clicked (if clickable),
                //start player2's clock, set player1 to un-clickable, and change the color to active
                if (!isPlayer2TimerRunning) {
                    player2RemainingTime = player2startTime;
                    countDownTimer2 = new CountDownTimer(player2RemainingTime * 1000,
                            1000) {
                        public void onTick(long millisUntilFinished) { //updates time at tick
                            player2RemainingTime = millisUntilFinished / 1000;
                            player2CountdownTextView.setText(formatTime(player2RemainingTime));
                            player2startTime = player2RemainingTime;
                        }
                        public void onFinish() { //prints text at finish
                            player2CountdownTextView.setText(R.string.TimeEnd);
                        }
                    }.start();
                    isPlayer2TimerRunning = true;
                    player2CountdownTextView.setClickable(true);
                    player2CountdownTextView.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    player1CountdownTextView.setClickable(false);
                } else { //if player2 layout is clicked while it is already running, switch to
                         // player1's clock, switch colors and clickable status
                    countDownTimer2.cancel();
                    isPlayer2TimerRunning = false;
                    player2CountdownTextView.setClickable(false);
                    player2CountdownTextView.setBackgroundColor(getResources().getColor(R.color.black));
                    player1CountdownTextView.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    player1RemainingTime = player1startTime;
                    countDownTimer1 = new CountDownTimer(player1RemainingTime * 1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            player1RemainingTime = millisUntilFinished / 1000;
                            player1CountdownTextView.setText(formatTime(player1RemainingTime));
                            player1startTime = player1RemainingTime;
                        }
                        public void onFinish() {
                            player1CountdownTextView.setText(R.string.TimeEnd);
                        }
                    }.start();
                    isPlayer1TimerRunning = true;
                    player1CountdownTextView.setClickable(true);
                }
            }
        });
    }

    //function to translate time to readable HH:MM:SS (want to update to be prettier)
    private String formatTime(long timeInSeconds) {
        int hours = (int) (timeInSeconds / 3600);
        int minutes = (int) ((timeInSeconds % 3600) / 60);
        int seconds = (int) (timeInSeconds % 60);

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

}
