package com.example.myapplication.models;

public class Teams_model {


    private String team_id,team_name,team_matches,team_won,team_lost;

    public Teams_model(String team_id, String team_name, String team_matches, String team_won, String team_lost) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.team_matches = team_matches;
        this.team_won = team_won;
        this.team_lost = team_lost;
    }

    public Teams_model() {

    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_matches() {
        return team_matches;
    }

    public void setTeam_matches(String team_matches) {
        this.team_matches = team_matches;
    }

    public String getTeam_won() {
        return team_won;
    }

    public void setTeam_won(String team_won) {
        this.team_won = team_won;
    }

    public String getTeam_lost() {
        return team_lost;
    }

    public void setTeam_lost(String team_lost) {
        this.team_lost = team_lost;
    }
}