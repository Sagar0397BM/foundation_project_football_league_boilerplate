package com.stackroute.oops.league.model;

import java.io.Serializable;

/**
 * This class contains four fields playerId,teamTitle,season and experience.
 * This is a subclass of Serializable and Comparable interface
 */
public class PlayerTeam implements Serializable, Comparable {

    private String playerId;
    private String teamTitle;
    private String season;
    private int experience;

    
    //Parameterized Constructor to initialize all properties
  
    public PlayerTeam(String playerId, String teamTitle) {
        this.playerId = playerId;
        this.teamTitle = teamTitle;
    }


    public PlayerTeam() {
    }


    /**
     * This overridden method should return player details in below format
     * Player{playerId=xxxxx, teamTitle=xxxxxx}
     */
    @Override
    public String toString() {
        return "Player{playerId=" + playerId + ", teamTitle=" + teamTitle + "}";
    }

    //Overridden compare method based on playerId
    @Override
    public int compareTo(Object object) {
        return 0;
    }


    public String getPlayerId() {
        return playerId;
    }


    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }


    public String getTeamTitle() {
        return teamTitle;
    }


    public void setTeamTitle(String teamTitle) {
        this.teamTitle = teamTitle;
    }


    public String getSeason() {
        return season;
    }


    public void setSeason(String season) {
        this.season = season;
    }


    public int getExperience() {
        return experience;
    }


    public void setExperience(int experience) {
        this.experience = experience;
    }


   
}
