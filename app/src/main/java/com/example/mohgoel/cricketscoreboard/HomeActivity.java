package com.example.mohgoel.cricketscoreboard;

import android.os.Handler;
import android.os.PersistableBundle;
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
    private TextView tvTeamAWicketsCount;
    private TextView tvTeamAOversCount;
    private TextView tvTeamBScore;
    private TextView tvTeamBWicketsCount;
    private TextView tvTeamBOversCount;
    private Handler homeActivityHandler;

    private static final int TEAM_A = 0;
    private static final int TEAM_B = 1;
    private static final int SINGLE_RUN = 1;
    private static final int FOUR = 4;
    private static final int SIX = 6;

    public static final String TEAM_A_SCORE = "TeamAScore";
    public static final String TEAM_A_WICKETS_COUNT = "TeamAWicketsCount";
    public static final String TEAM_A_OVERS_COUNT = "TeamAOversCount";
    public static final String TEAM_B_SCORE = "TeamBScore";
    public static final String TEAM_B_WICKETS_COUNT = "TeamBWicketsCount";
    public static final String TEAM_B_OVERS_COUNT = "TeamBOversCount";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvTeamAScore = (TextView) findViewById(R.id.team_a_score_text_view);
        tvTeamBScore = (TextView) findViewById(R.id.team_b_score_text_view);
        tvTeamAWicketsCount = (TextView) findViewById(R.id.team_a_wickets_text_view);
        tvTeamAOversCount = (TextView) findViewById(R.id.team_a_overs_text_view);
        tvTeamBWicketsCount = (TextView) findViewById(R.id.team_b_wickets_text_view);
        tvTeamBOversCount = (TextView) findViewById(R.id.team_b_overs_text_view);
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
            homeActivityHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvTeamAWicketsCount.setText("" + teamAWicketsCount);
                }
            }, 250);
        } else {
            Toast.makeText(this, "Team A's inning is over", Toast.LENGTH_SHORT).show();
        }
    }

    public void addOverForTeamA(View view) {
        if (teamAOversCount < 20) {
            ++teamAOversCount;
            homeActivityHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvTeamAOversCount.setText("" + teamAOversCount);
                }
            }, 100);
        } else {
            Toast.makeText(this, "Team A's inning is over", Toast.LENGTH_SHORT).show();
        }
    }

    public void addSixForTeamB(View view) {
        addScoresinBackground(TEAM_B, SIX);
    }

    public void addFourForTeamB(View view) {
        addScoresinBackground(TEAM_B, FOUR);
    }

    public void addOneForTeamB(View view) {
        addScoresinBackground(TEAM_B, SINGLE_RUN);
    }

    public void dropWicketForTeamB(View view) {
        if (teamBWicketsCount < 10) {
            ++teamBWicketsCount;
            homeActivityHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvTeamBWicketsCount.setText("" + teamBWicketsCount);
                }
            }, 250);
        } else {
            Toast.makeText(this, "Team B's inning is over", Toast.LENGTH_SHORT).show();
        }
    }

    public void addOverForTeamB(View view) {
        if (teamBOversCount < 20) {
            ++teamBOversCount;
            homeActivityHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvTeamBOversCount.setText("" + teamBOversCount);
                }
            }, 250);
        } else {
            Toast.makeText(this, "Team B's inning is over", Toast.LENGTH_SHORT).show();
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
                int i = 0;
                do {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (teamName == TEAM_A) {
                        ++teamAScore;
                    } else if (teamName == TEAM_B) {
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

    public void resetScores(View view) {
        teamAOversCount = 0;
        teamAWicketsCount = 0;
        teamAScore = 0;
        teamBOversCount = 0;
        teamBWicketsCount = 0;
        teamBScore = 0;

        updateUI();
    }

    private void updateUI() {
        tvTeamAScore.setText("" + teamAScore);
        tvTeamAWicketsCount.setText("" + teamAWicketsCount);
        tvTeamAOversCount.setText("" + teamAOversCount);
        tvTeamBScore.setText("" + teamBScore);
        tvTeamBWicketsCount.setText("" + teamBWicketsCount);
        tvTeamBOversCount.setText("" + teamBOversCount);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the state of class members
        outState.putInt(TEAM_A_SCORE, teamAScore);
        outState.putInt(TEAM_A_WICKETS_COUNT, teamAWicketsCount);
        outState.putInt(TEAM_A_OVERS_COUNT, teamAOversCount);
        outState.putInt(TEAM_B_SCORE, teamBScore);
        outState.putInt(TEAM_B_WICKETS_COUNT, teamBWicketsCount);
        outState.putInt(TEAM_B_OVERS_COUNT, teamBOversCount);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        teamAScore = savedInstanceState.getInt(TEAM_A_SCORE);
        teamAWicketsCount = savedInstanceState.getInt(TEAM_A_WICKETS_COUNT);
        teamAOversCount = savedInstanceState.getInt(TEAM_A_OVERS_COUNT);
        teamBScore = savedInstanceState.getInt(TEAM_B_SCORE);
        teamBWicketsCount = savedInstanceState.getInt(TEAM_B_WICKETS_COUNT);
        teamBOversCount = savedInstanceState.getInt(TEAM_B_OVERS_COUNT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    public void declareWinner(View view) {
        int winner = (teamAScore >= teamBScore) ? TEAM_A : TEAM_B;
        String message = "";
        if (winner == TEAM_A) {
            message = "TEAM A is WINNER!";
        } else {
            message = "TEAM B is WINNER";
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        resetScores(view);
    }
}
