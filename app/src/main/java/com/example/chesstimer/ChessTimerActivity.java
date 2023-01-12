package com.example.chesstimer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class ChessTimerActivity extends AppCompatActivity {

    //NOTE: for some reason the initialization of the timers CANT be in a function
    //      it needs to be placed directly under the onClick

    //EXPERIMENT: experiment with moving timers before the onClick function, that way you
    //            don't have to update player start times. Timer should just start where it
    //            left off since it will be the same instance when you do .cancel() then .start()

    //initialize timers
    private CountDownTimer countDownTimer1;
    private CountDownTimer countDownTimer2;

    //initialize code interface to player layouts
    private TextView player1CountdownTextView;
    private TextView player2CountdownTextView;

    //init player start times
    //maybe make 2 more time variables to track time updates, so that start times can be saved for reset?
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
                //if player1 isn't currently running when clicked (if clickable), start player1's clock
                if (!isPlayer1TimerRunning) {
                    player1RemainingTime = player1startTime;
                    countDownTimer1 = new CountDownTimer(player1RemainingTime * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            player1RemainingTime = millisUntilFinished / 1000;
                            player1CountdownTextView.setText(formatTime(player1RemainingTime));
                            player1startTime = player1RemainingTime;
                        }

                        @Override
                        public void onFinish() {
                            player1CountdownTextView.setText(R.string.TimeEnd);
                        }
                    };
                    startTimer(countDownTimer1);

                } else { //if player1 layout is clicked while it is already running, switch to player2's clock
                    pauseTimer(countDownTimer1);
                    player2RemainingTime = player2startTime;
                    countDownTimer2 = new CountDownTimer(player2RemainingTime * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            player2RemainingTime = millisUntilFinished / 1000;
                            player2CountdownTextView.setText(formatTime(player2RemainingTime));
                            player2startTime = player2RemainingTime;
                        }

                        @Override
                        public void onFinish() {
                            player2CountdownTextView.setText(R.string.TimeEnd);
                        }
                    };
                    startTimer(countDownTimer2);
                }
            }
        });

        //player 2 listener
        player2CountdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            //when player2 layout is clicked
            public void onClick(View v) {
                //if player2 isn't currently running when clicked (if clickable), start player2's clock
                if (!isPlayer2TimerRunning) {
                    player2RemainingTime = player2startTime;
                    countDownTimer2 = new CountDownTimer(player2RemainingTime * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            player2RemainingTime = millisUntilFinished / 1000;
                            player2CountdownTextView.setText(formatTime(player2RemainingTime));
                            player2startTime = player2RemainingTime;
                        }

                        @Override
                        public void onFinish() {
                            player2CountdownTextView.setText(R.string.TimeEnd);
                        }
                    };
                    startTimer(countDownTimer2);
                } else { //if player2 layout is clicked while it is already running, switch to player1's clock
                    pauseTimer(countDownTimer2);
                    player1RemainingTime = player1startTime;
                    countDownTimer1 = new CountDownTimer(player1RemainingTime * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            player1RemainingTime = millisUntilFinished / 1000;
                            player1CountdownTextView.setText(formatTime(player1RemainingTime));
                            player1startTime = player1RemainingTime;
                        }

                        @Override
                        public void onFinish() {
                            player1CountdownTextView.setText(R.string.TimeEnd);
                        }
                    };
                    startTimer(countDownTimer1);
                }
            }
        });
    }

    //function to translate time to readable HH:MM:SS (want to update to be prettier)
    private String formatTime(long timeInSeconds) {
        int hours = (int) (timeInSeconds / 3600);
        int minutes = (int) ((timeInSeconds % 3600) / 60);
        int seconds = (int) (timeInSeconds % 60);

        if (hours == 0) {
            if (minutes < 10) {
                return String.format(Locale.getDefault(), "%1d:%02d", minutes, seconds);
            }
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    //function that starts a CountDownTimer object based on the player
    private void startTimer(CountDownTimer cdt) {
        if (cdt.equals(countDownTimer1)) {
            cdt.start();
            isPlayer1TimerRunning=true;
            player1CountdownTextView.setClickable(true);
            player2CountdownTextView.setClickable(false);
            player1CountdownTextView.setBackgroundColor(getResources().getColor(R.color.purple_200));
        } else {
            cdt.start();
            isPlayer2TimerRunning=true;
            player2CountdownTextView.setClickable(true);
            player1CountdownTextView.setClickable(false);
            player2CountdownTextView.setBackgroundColor(getResources().getColor(R.color.purple_200));
        }
    }

    //function that stops a CountDownTimer object based on the player
    private void pauseTimer(CountDownTimer cdt) {
        if (cdt.equals(countDownTimer1)) {
            cdt.cancel();
            isPlayer1TimerRunning = false;
            player1CountdownTextView.setClickable(false);
            player2CountdownTextView.setClickable(true);
            player1CountdownTextView.setBackgroundColor(getResources().getColor(R.color.black));
            player2CountdownTextView.setBackgroundColor(getResources().getColor(R.color.purple_200));
        } else {
            cdt.cancel();
            isPlayer2TimerRunning = false;
            player2CountdownTextView.setClickable(false);
            player1CountdownTextView.setClickable(true);
            player2CountdownTextView.setBackgroundColor(getResources().getColor(R.color.black));
            player1CountdownTextView.setBackgroundColor(getResources().getColor(R.color.purple_200));
        }
    }

}
