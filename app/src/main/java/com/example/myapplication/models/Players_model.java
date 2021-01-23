package com.example.myapplication.models;

public class Players_model {

    private String player_id,player_name,player_team_id,player_team_name,player_total_matches,player_total_innings;
    private  byte[] imageBytes;


    public Players_model(String player_id, String player_name, String player_team_id, String player_team_name, String player_total_matches, String player_total_innings, byte[] imageBytes) {
        this.player_id = player_id;
        this.player_name = player_name;
        this.player_team_id = player_team_id;
        this.player_team_name = player_team_name;
        this.player_total_matches = player_total_matches;
        this.player_total_innings = player_total_innings;
        this.imageBytes = imageBytes;
    }

    public Players_model() {

    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_team_id() {
        return player_team_id;
    }

    public void setPlayer_team_id(String player_team_id) {
        this.player_team_id = player_team_id;
    }

    public String getPlayer_team_name() {
        return player_team_name;
    }

    public void setPlayer_team_name(String player_team_name) {
        this.player_team_name = player_team_name;
    }

    public String getPlayer_total_matches() {
        return player_total_matches;
    }

    public void setPlayer_total_matches(String player_total_matches) {
        this.player_total_matches = player_total_matches;
    }

    public String getPlayer_total_innings() {
        return player_total_innings;
    }

    public void setPlayer_total_innings(String player_total_innings) {
        this.player_total_innings = player_total_innings;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}