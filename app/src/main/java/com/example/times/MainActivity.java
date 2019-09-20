package com.example.times;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SeekBar timersProgressBar;
    TextView timersTextView;
    Button controllerButton;
    CountDownTimer countDownTimer;
    boolean counterIsActive = true;

    private void resetTime() {
        controllerButton.setText("0:30");
        timersProgressBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Start!");
        timersProgressBar.setEnabled(true);
        counterIsActive = true;
    }

    @SuppressLint("SetTextI18n")
    private void updateTimerTextView(int progress) {
        int minutes = progress / 60;
        int seconds = progress - minutes * 60;
        String stringSeconds = Integer.toString(seconds);
        if (seconds < 10) {
            stringSeconds = "0" + stringSeconds;
        }
        timersTextView.setText(String.valueOf(minutes) + ":" + stringSeconds);
    }

    public void controlTimer(View view) {
        if (counterIsActive) {
            counterIsActive = false;
            timersProgressBar.setEnabled(false);
            controllerButton.setText("Stop");

            countDownTimer = new CountDownTimer(timersProgressBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimerTextView((int) millisUntilFinished / 1000);
                    timersProgressBar.setProgress((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTime();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.playeregg);
                    mediaPlayer.start();


                }
            }.start();

        } else {
            resetTime();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timersProgressBar = findViewById(R.id.timersSeekBar);
        timersTextView = findViewById(R.id.timertTextView);
        controllerButton = findViewById(R.id.startStopButton);

        timersProgressBar.setMax(600);
        timersProgressBar.setProgress(30);

        timersProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimerTextView(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
