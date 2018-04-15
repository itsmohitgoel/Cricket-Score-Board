package com.example.mohgoel.cricketscoreboard;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {
    private int teamAScore = 0;
    private int teamAWicketsCount = 0;
    private int teamAOversCount = 0;
    private int teamBScore = 0;
    private int teamBWicketsCount = 0;
    private int teamBOversCount = 0;
    private TextView tvTeamAScore;
    private TextView tvTeamBScore;
    private Handler homeActivityHandler;

    private static final int TEAM_A = 0;
    private static final int TEAM_B = 1;
    private static final int SINGLE_RUN = 1;
    private static final int FOUR = 4;
    private static final int SIX = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvTeamAScore = (TextView) findViewById(R.id.team_a_score_text_view);
        tvTeamBScore = (TextView) findViewById(R.id.team_b_score_text_view);
        homeActivityHandler = new Handler();
    }

    public void addSixForTeamA(View view) {
        addScoresinBackground(TEAM_A, SIX);
    }

    public void addFourForTeamA(View view) {
        addScoresinBackground(TEAM_A, FOUR);
    }

    public void addOneForTeamA(View view) {
        addScoresinBackground(TEAM_A, SINGLE_RUN);
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

    public void displayScore(int teamName) {
        if (teamName == TEAM_A) {
            tvTeamAScore.setText("" + teamAScore);
        } else if (teamName == TEAM_B) {
            tvTeamBScore.setText("" + teamBScore);
        }
    }

    private void addScoresinBackground(final int teamName, final int scoreValue) {
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

                    if (teamName == TEAM_A) {
                        ++teamAScore;
                    }else if (teamName == TEAM_B){
                        ++teamBScore;
                    }

                    homeActivityHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            displayScore(teamName);
                        }
                    });

                    ++i;
                } while (i < scoreValue);
            }
        });

        increaseScoreSequentially.start();
    }
}
