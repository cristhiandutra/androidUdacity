package soccerscore.android.example.com.soccerscore;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Team teamA;
    private Team teamB;

    private TextView goalViewTeamA;
    private TextView foulViewTeamA;
    private TextView yellowCardViewTeamA;
    private TextView redCardViewTeamA;
    private TextView goalViewTeamB;
    private TextView foulViewTeamB;
    private TextView yellowCardViewTeamB;
    private TextView redCardViewTeamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goalViewTeamA = (TextView) findViewById(R.id.txt_team_a_goal);
        foulViewTeamA = (TextView) findViewById(R.id.team_a_goal);
        yellowCardViewTeamA = (TextView) findViewById(R.id.team_a_yellow_card);
        redCardViewTeamA = (TextView) findViewById(R.id.team_a_red_card);
        goalViewTeamB = (TextView) findViewById(R.id.txt_team_b_goal);
        foulViewTeamB = (TextView) findViewById(R.id.team_b_goal);
        yellowCardViewTeamB = (TextView) findViewById(R.id.team_b_yellow_card);
        redCardViewTeamB = (TextView) findViewById(R.id.team_b_red_card);

        if (savedInstanceState != null) {
            teamA = (Team) savedInstanceState.getSerializable("teamA");
            teamB = (Team) savedInstanceState.getSerializable("teamB");
            displayTeamA();
            displayTeamB();
        } else {
            teamA = new Team(0,0,0,0);
            teamB = new Team(0,0,0,0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("teamA", teamA);
        outState.putSerializable("teamB", teamB);
    }

    public void add(View view) {
        switch (view.getId()) {
            case R.id.btn_team_a_goal:
                teamA.addGoal();
                displayTeamA();
                break;
            case R.id.btn_team_b_goal:
                teamB.addGoal();
                displayTeamB();
                break;
            case R.id.btn_team_a_foul:
                teamA.addFoul();
                displayTeamA();
                break;
            case R.id.btn_team_b_foul:
                teamB.addFoul();
                displayTeamB();
                break;
            case R.id.btn_team_a_yellow_card:
                teamA.addYellowCard();
                displayTeamA();
                break;
            case R.id.btn_team_b_yellow_card:
                teamB.addYellowCard();
                displayTeamB();
                break;
            case R.id.btn_team_a_red_card:
                teamA.addRedCard();
                displayTeamA();
                break;
            case R.id.btn_team_b_red_card:
                teamB.addRedCard();
                displayTeamB();
                break;
        }
    }

    public void reset(View view) {
        teamA = new Team(0,0,0,0);
        teamB = new Team(0,0,0,0);
        displayTeamA();
        displayTeamB();
    }

    private void displayTeamA() {
        goalViewTeamA.setText(String.valueOf(teamA.getGoal()));
        foulViewTeamA.setText(String.valueOf(teamA.getFoul()));
        yellowCardViewTeamA.setText(String.valueOf(teamA.getYellowCard()));
        redCardViewTeamA.setText(String.valueOf(teamA.getRedCard()));
    }

    private void displayTeamB() {
        goalViewTeamB.setText(String.valueOf(teamB.getGoal()));
        foulViewTeamB.setText(String.valueOf(teamB.getFoul()));
        yellowCardViewTeamB.setText(String.valueOf(teamB.getYellowCard()));
        redCardViewTeamB.setText(String.valueOf(teamB.getRedCard()));
    }
}
