package soccerscore.android.example.com.soccerscore;

import java.io.Serializable;

/**
 * Created by Cristhian on 19/01/2018.
 */

public class Team implements Serializable{

    private Integer goal = 0;
    private Integer foul = 0;
    private Integer yellowCard = 0;
    private Integer redCard = 0;

    public Team(Integer goal, Integer foul, Integer yellowCard, Integer redCard) {
        this.goal = goal;
        this.foul = foul;
        this.yellowCard = yellowCard;
        this.redCard = redCard;
    }

    public Integer getGoal() {
        return goal;
    }

    public void addGoal() {
        if (goal != null) {
            goal = goal + 1;
        }
    }

    public Integer getFoul() {
        return foul;
    }

    public void addFoul() {
        if (foul != null) {
            foul = foul+ 1;
        }
    }

    public Integer getYellowCard() {
        return yellowCard;
    }

    public void addYellowCard() {
        if (yellowCard != null) {
            yellowCard = yellowCard+ 1;
        }
    }

    public Integer getRedCard() {
        return redCard;
    }

    public void addRedCard() {
        if (redCard != null) {
            redCard = redCard+ 1;
        }
    }
}
