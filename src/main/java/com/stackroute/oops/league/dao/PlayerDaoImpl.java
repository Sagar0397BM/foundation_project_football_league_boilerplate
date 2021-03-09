package com.stackroute.oops.league.dao;

import com.stackroute.oops.league.exception.PlayerAlreadyExistsException;
import com.stackroute.oops.league.exception.PlayerNotFoundException;
import com.stackroute.oops.league.model.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is implementing the PlayerDao interface This class has one field
 * playerList and a String constant for storing file name
 */
public class PlayerDaoImpl implements PlayerDao {
    private static final String PLAYER_FILE_NAME = "src/main/resources/player.csv";
    private List<Player> playerList;

    /**
     * Constructor to initialize an empty ArrayList for playerList
     */
    public PlayerDaoImpl() {
        playerList = new ArrayList<Player>();
    }

    /**
     * Return true if player object is stored in "player.csv" as comma separated
     * fields successfully when password length is greater than six and yearExpr is
     * greater than zero
     */
    @Override
    public boolean addPlayer(Player player) throws PlayerAlreadyExistsException {

        try {
            FileWriter fw = new FileWriter(PLAYER_FILE_NAME);
            fw.append("PlayerId,");
            fw.append("Player Name,");
            fw.append("Password,");
            fw.append("YearOfExperience");
            fw.append("\n");
            if (player.getPassword().length() > 6 && player.getYearExpr() > 0) {
                fw.append(player.getPlayerId()).append(",").append(player.getPlayerName()).append(",")
                        .append(player.getPassword()).append(",").append(String.valueOf(player.getYearExpr()));
                fw.close();
                return true;
            } else
                return false;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Return the list of player objects by reading data from the file "player.csv"
    @Override
    public List<Player> getAllPlayers() {
        File file = new File(PLAYER_FILE_NAME);
        // int i = 0;
        Player[] playerObj = new Player[500];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            String[] worspltdata = null;
            int j = 0;
            while ((line = br.readLine()) != null) {
                worspltdata = line.split(",");
                playerObj[j] = new Player();
                playerObj[j].setPlayerId(worspltdata[0]);
                playerObj[j].setPlayerName(worspltdata[1]);
                playerObj[j].setPassword(worspltdata[2]);
                playerObj[j].setYearExpr(Integer.parseInt(worspltdata[3]));
                // playerList=new ArrayList<Player>();
                playerList.add(playerObj[j]);
                for (int l = 0; l < playerList.size(); l++) {
                    System.out.println("list are" + playerList.get(l));
                }
                j++;
            }

        } catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
        return playerList;
    }

    /**
     * Return Player object given playerId to search
     * 
     * @throws FileNotFoundException
     */
    @Override
    public Player findPlayer(String playerId) throws PlayerNotFoundException {

     List<Player> playersList = getAllPlayers();
    for (int i=0;i<playersList.size();i++) {
        System.out.println(playersList.get(i));
    }
        if (playerId == null || playerId==" ") {
            System.out.println("inside empty");
            throw new PlayerNotFoundException();
        }

            for (Player player : playersList) {
                if(player.getPlayerId().equalsIgnoreCase(playerId)){
                    return player;
                }
                else{
                    throw new PlayerNotFoundException();
                }
            }
        
           

        // for (int i = 0; i < playersList.size(); i++) {
        //     System.out.println(playersList.get(i) + "No data");
        //     if (playerId.equalsIgnoreCase(playersList.get(i).getPlayerId()) || playerId.equalsIgnoreCase(" ")) {
        //         return playersList.get(i);
        //     } else {
        //         throw new PlayerNotFoundException();
        //     }
        // }
        return null;

    }
}
