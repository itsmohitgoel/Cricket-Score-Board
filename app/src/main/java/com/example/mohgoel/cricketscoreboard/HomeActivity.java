package com.example.mohgoel.cricketscoreboard;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private int teamAScore = 0;
    private int teamAWicketsCount = 0;
    private int teamAOversCount = 0;
    private int teamBScore = 0;
    private int teamBWicketsCount = 0;
    private int teamBOversCount = 0;
    private TextView tvTeamAScore;
    private Handler homeActivityHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvTeamAScore = (TextView) findViewById(R.id.team_a_score_text_view);
        homeActivityHandler = new Handler();
    }

    public void addSixForTeamA(View view) {
        Thread increaseScoreSequentially = new Thread(new Runnable() {
            @Override
            public void run() {
                int i =0;
                do {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ++teamAScore;
                    homeActivityHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            displayTeamAScore();
                        }
                    });

                    ++i;
                } while (i < 6);
            }
        });

        increaseScoreSequentially.start();

    }

    public void addFourForTeamA(View view) {
        Thread increasScoreByFour = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                do {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ++teamAScore;
                    homeActivityHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            displayTeamAScore();
                        }
                    });
                    ++i;
                } while (i < 4);
            }
        });

        increasScoreByFour.start();
    }

    public void addOneForTeamA(View view) {
        teamAScore += 1;
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        displayTeamAScore();
    }

    public void dropWicketForTeamA(View view) {
        if (teamAWicketsCount < 10) {
            ++teamAWicketsCount;
            final TextView tvTeamAWicketsCount = (TextView) findViewById(R.id.team_a_wickets_text_view);
            tvTeamAWicketsCount.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvTeamAWicketsCount.setText("/" + teamAWicketsCount);
                }
            }, 250);
        }else {
            Toast.makeText(this, "Team A's inning is over", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayTeamAScore() {
        tvTeamAScore.setText("" + teamAScore);
    }
}
