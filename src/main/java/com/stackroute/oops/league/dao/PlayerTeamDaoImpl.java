package com.stackroute.oops.league.dao;

import com.stackroute.oops.league.exception.PlayerNotFoundException;
import com.stackroute.oops.league.model.Player;
import com.stackroute.oops.league.model.PlayerTeam;

import java.io.FileWriter;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class implements the PlayerTeamDao interface
 * This class has two fields playerTeamSet,playerDao and a String constant for storing file name.
 */
public class PlayerTeamDaoImpl implements PlayerTeamDao {
    private static final String TEAM_FILE_NAME = "src/main/resources/finalteam.csv";

    String playerTeamSet;
    PlayerDao playerDao;

    /**
     * Constructor to initialize an empty TreeSet and PlayerDao object
     */
    public PlayerTeamDaoImpl() {
        TreeSet<PlayerDao> set= new TreeSet<PlayerDao>();
        PlayerDao playerDao=new PlayerDaoImpl();
    }
    public PlayerTeamDaoImpl(String playerTeamSet, PlayerDao playerDao) {
        this.playerTeamSet = playerTeamSet;
        this.playerDao = playerDao;
    }
    /*
    Returns the list of players belonging to a particular teamTitle by reading
    from the file finalteam.csv
     */
    @Override
    public Set<PlayerTeam> getPlayerSetByTeamTitle(String teamTitle) {
        return null;
    }

    //Add the given PlayerTeam Object to finalteam.csv file
    @Override
    public boolean addPlayerToTeam(Player player) throws PlayerNotFoundException {
        
        return false;
       
      
    }

    //Return the set of all PlayerTeam by reading the file content from finalteam.csv file
    @Override
    public Set<PlayerTeam> getAllPlayerTeams() {
        return null;
    }

   
}
