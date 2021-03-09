package com.stackroute.oops.league.model;

import java.io.Serializable;

/**
 * This class contains five fields playerId,playerName,password,yearExpr and
 * teamTitle It is a subclass of Comparable interface
 */

public class Player extends Thread implements Comparable<Player>{
    //Parameterized Constructor to initialize all properties
    
    private String playerId;
    private String playerName;
    private String password;
    private int yearExpr;
    private String teamTitle;
   
public Player()
{

}


    public Player(String playerId, String playerName, String password, int yearExpr) {
        this.playerId=playerId;
        this.playerName=playerName;
        this.password=password;
        this.yearExpr=yearExpr;
       
        
    }
    // public Player(String playerId, String playerName, String password, int yearExpr,LeagueTeamTitles teamTitle) {
    //     this.playerId=playerId;
    //     this.playerName=playerName;
    //     this.password=password;
    //     this.yearExpr=yearExpr;
    //     this.teamTitle = teamTitle;

    // }

    // Return teamTitle if it is present and not empty
    public String getTeamTitle() {
        if ("".equals(this.teamTitle) || this.teamTitle == null)
            return null;
        else
            return this.teamTitle;
    }

    public void setTeamTitle(String teamTitle) {

        this.teamTitle = teamTitle;
    }

    /**
     * This overridden method should return player details in below format
     * Player{playerId=xxxxx, playerName=xxxxxx,
     * yearExpr=xxxxxx,teamTitle=xxxxxxxx}-> if league team title is set
     * Player{playerId=xxxxx, playerName=xxxxxx, yearExpr=xxxxxx}-> if league team
     * title not set
     */
    // @Override
    // public String toString() {
    // return null;
    // }

    // Overridden equals method
    @Override
    public boolean equals(Object object) {
        return false;
    }

    // Overridden hashcode method
    @Override
    public int hashCode() {
        return 0;
    }

    // // compares player object based on playerId
    // @Override
    // public int compareTo(Object o) {
    //     return 0;
    // }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getYearExpr() {
        return yearExpr;
    }

    public void setYearExpr(int yearExpr) {
        this.yearExpr = yearExpr;
    }

    // @Override
    // public String toString() {
    // return "Player{playerId=" + playerId + ", playerName=" + this.playerName + ",
    // yearExpr=" + this.yearExpr
    // + ", teamTitle=" + this.teamTitle + "}";
    // }

    @Override
    public String toString() {
        if ("".equals(this.teamTitle) || this.teamTitle == null) {
            return "Player{playerId=" + this.playerId + ", playerName=" + this.playerName + ", yearExpr="
                    + this.yearExpr + "}";
        } else {
            return "Player{playerId=" + playerId + ", playerName=" + this.playerName + ", yearExpr=" + this.yearExpr
                    + ", teamTitle=" + this.teamTitle + "}";
        }
    }


    @Override
    public int compareTo(Player o) {
        if(this.playerId == o.playerId){
            return 0;
        }
        else{
            return -1;
        }
        
    }

 
    
}
