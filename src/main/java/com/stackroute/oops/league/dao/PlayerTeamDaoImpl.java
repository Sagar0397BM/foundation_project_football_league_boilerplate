package com.stackroute.oops.league.dao;

import com.stackroute.oops.league.exception.PlayerNotFoundException;
import com.stackroute.oops.league.model.Player;
import com.stackroute.oops.league.model.PlayerTeam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class implements the PlayerTeamDao interface This class has two fields
 * playerTeamSet,playerDao and a String constant for storing file name.
 */
public class PlayerTeamDaoImpl implements PlayerTeamDao {
    private static final String TEAM_FILE_NAME = "src/main/resources/finalteam.csv";

    private static final String PLAYER_FILE_NAME = "src/main/resources/player.csv";

    String playerTeamSet;
    PlayerDao playerDao;
    Set<PlayerTeam> playerList;
    /**
     * Constructor to initialize an empty TreeSet and PlayerDao object
     */
    public PlayerTeamDaoImpl() {
         playerList= new TreeSet<PlayerTeam>();
        PlayerDao playerDao = new PlayerDaoImpl();
    }

    public PlayerTeamDaoImpl(String playerTeamSet, PlayerDao playerDao) {
        this.playerTeamSet = playerTeamSet;
        this.playerDao = playerDao;
    }

    /*
     * Returns the list of players belonging to a particular teamTitle by reading
     * from the file finalteam.csv
     */
    @Override
    public Set<PlayerTeam> getPlayerSetByTeamTitle(String teamTitle) {
        File file = new File(TEAM_FILE_NAME);
        PlayerTeam[] playerTeamObj = new PlayerTeam[500];
        // Set<PlayerTeam> playerList= new TreeSet<PlayerTeam>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            String[] worspltdata = null;
            int j = 0;
            while ((line = br.readLine()) != null) {
                worspltdata = line.split(",");
                playerTeamObj[j] = new PlayerTeam();
                playerTeamObj[j].setPlayerId(worspltdata[0]);
                playerTeamObj[j].setTeamTitle(worspltdata[1]);
                playerTeamObj[j].setSeason(worspltdata[2]);
                playerTeamObj[j].setExperience(Integer.parseInt(worspltdata[3]));
                // playerList=new ArrayList<Player>();
                playerList.add(playerTeamObj[j]);
                j++;
            }

            
        } catch (IOException ie) {
            System.out.println(ie.getMessage());
        }

        Iterator<PlayerTeam> itr=playerList.iterator();
        while(itr.hasNext())
        {
            if(itr.next().getTeamTitle().equalsIgnoreCase(teamTitle)){
                return (Set<PlayerTeam>) itr.next();
            }
        }
        return playerList;
    }

    // Add the given PlayerTeam Object to finalteam.csv file
    @Override
    public boolean addPlayerToTeam(Player player) throws PlayerNotFoundException {

        List<Player> playersList = playerDao.getAllPlayers();


        for (int i = 0; i < playersList.size(); i++) {
            if (player.getPlayerId().equalsIgnoreCase(playersList.get(i).getPlayerId())) {
                PlayerTeam playerTeam = new PlayerTeam();
                try {
                    FileWriter fw = new FileWriter(TEAM_FILE_NAME);
                    fw.append("PlayerId,");
                    fw.append("Team Title,");
                    fw.append("Season,");
                    fw.append("YearOfExperience");
                    fw.append("\n");
                    fw.append(playerTeam.getPlayerId()).append(",").append(playerTeam.getTeamTitle()).append(",")
                            .append(playerTeam.getSeason()).append(",")
                            .append(String.valueOf(playerTeam.getExperience()));
                    fw.close();
                    return true;

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                return false;
            }
        }

        return false;

    }

    // Return the set of all PlayerTeam by reading the file content from
    // finalteam.csv file
    @Override
    public Set<PlayerTeam> getAllPlayerTeams() {
        File file = new File(TEAM_FILE_NAME);
        PlayerTeam[] playerTeamObj = new PlayerTeam[500];
        // Set<PlayerTeam> playerList= new TreeSet<PlayerTeam>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            String[] worspltdata = null;
            int j = 0;
            while ((line = br.readLine()) != null) {
                worspltdata = line.split(",");
                playerTeamObj[j] = new PlayerTeam();
                playerTeamObj[j].setPlayerId(worspltdata[0]);
                playerTeamObj[j].setTeamTitle(worspltdata[1]);
                playerTeamObj[j].setSeason(worspltdata[2]);
                playerTeamObj[j].setExperience(Integer.parseInt(worspltdata[3]));
                // playerList=new ArrayList<Player>();
                playerList.add(playerTeamObj[j]);
                j++;
            }

            
        } catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
        return playerList;
    }

}
