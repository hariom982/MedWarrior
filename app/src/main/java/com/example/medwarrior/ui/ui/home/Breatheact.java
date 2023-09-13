package com.example.medwarrior.ui.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medwarrior.R;
import com.example.medwarrior.ui.MainActivity2;

public class Breatheact extends AppCompatActivity {
    private TextView instructionTextView;
    private Button startButton;
    private ImageView breathAnimationImageView;

    private Animation inhaleAnimation;
    private Animation exhaleAnimation;
    private boolean isAnimating = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breatheact);
        instructionTextView = findViewById(R.id.instructionTextView);
        startButton = findViewById(R.id.startButton);
        breathAnimationImageView = findViewById(R.id.breathAnimationImageView);
        inhaleAnimation = AnimationUtils.loadAnimation(this, R.anim.inhale_animation);
        exhaleAnimation = AnimationUtils.loadAnimation(this, R.anim.exhale_animation);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimating) {
                    startBreathingAnimation();
                    startButton.setText("Quit");
                }else {
                    quitactivity();
                }

            }
        });

    }
    private void quitactivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);
    }


    private void startBreathingAnimation() {
        isAnimating = true;
        instructionTextView.setText("Inhale deeply through your nose.");
        breathAnimationImageView.setImageResource(R.drawable.inhale_animation);
        breathAnimationImageView.setVisibility(View.VISIBLE);
        inhaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation finished, switch to exhale animation
                instructionTextView.setText("Exhale slowly through your mouth.");
                breathAnimationImageView.setImageResource(R.drawable.exhale_animation);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                 vibrator.vibrate(100);
                breathAnimationImageView.startAnimation(exhaleAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeated (not used)
            }
        });

        exhaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation finished, switch to exhale animation
                instructionTextView.setText("Exhale slowly through your mouth.");
                breathAnimationImageView.setImageResource(R.drawable.exhale_animation);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                breathAnimationImageView.startAnimation(exhaleAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        exhaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation finished, switch back to inhale animation
                instructionTextView.setText("Inhale deeply through your nose.");
                breathAnimationImageView.setImageResource(R.drawable.inhale_animation);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                breathAnimationImageView.startAnimation(inhaleAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        breathAnimationImageView.startAnimation(inhaleAnimation);

    }
}