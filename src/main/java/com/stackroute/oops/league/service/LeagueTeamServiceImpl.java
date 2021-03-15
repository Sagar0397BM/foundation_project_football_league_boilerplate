package com.stackroute.oops.league.service;

import com.stackroute.oops.league.dao.PlayerDao;
import com.stackroute.oops.league.dao.PlayerDaoImpl;
import com.stackroute.oops.league.dao.PlayerTeamDao;
import com.stackroute.oops.league.dao.PlayerTeamDaoImpl;
import com.stackroute.oops.league.exception.PlayerAlreadyAllottedException;
import com.stackroute.oops.league.exception.PlayerAlreadyExistsException;
import com.stackroute.oops.league.exception.PlayerNotFoundException;
import com.stackroute.oops.league.exception.TeamAlreadyFormedException;
import com.stackroute.oops.league.model.Player;
import com.stackroute.oops.league.model.PlayerTeam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class implements leagueTeamService This has four fields: playerDao,
 * playerTeamDao and registeredPlayerList and playerTeamSet
 */
public class LeagueTeamServiceImpl implements LeagueTeamService {
    PlayerDao playerDao;
    PlayerTeamDao playerTeamDao;
    Set<PlayerTeam> playerTeamSet;
    List<Player> registeredPlayerList;
    List<Player> playersList;

    private static final String PLAYER_FILE_NAME = "src/main/resources/player.csv";

    /**
     * Constructor to initialize playerDao, playerTeamDao empty ArrayList for
     * registeredPlayerList and empty TreeSet for playerTeamSet
     */
    public LeagueTeamServiceImpl() {
        playerTeamSet = new HashSet<PlayerTeam>();
        playerDao = new PlayerDaoImpl();
        playerTeamDao = new PlayerTeamDaoImpl();
        registeredPlayerList = new ArrayList<Player>();
        playersList = new ArrayList<Player>();
    }

    // Add player data to file using PlayerDao object
    @Override
    public boolean addPlayer(Player player) throws PlayerAlreadyExistsException {

        try {
            FileWriter fw = new FileWriter(PLAYER_FILE_NAME);
            fw.append("PlayerId,");
            fw.append("Player Name,");
            fw.append("Password,");
            fw.append("YearOfExperience,");
            fw.append("Team Title");
            fw.append("\n");
            if (player.getPassword().length() > 6 && player.getYearExpr() > 0) {
                fw.append(player.getPlayerId()).append(",").append(player.getPlayerName()).append(",")
                        .append(player.getPassword()).append(",").append(String.valueOf(player.getYearExpr()))
                        .append(",").append(player.getTeamTitle());
                fw.close();
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Register the player for the given teamTitle Throws PlayerNotFoundException if
     * the player does not exists Throws PlayerAlreadyAllottedException if the
     * player is already allotted to team Throws TeamAlreadyFormedException if the
     * maximum number of players has reached for the given teamTitle Returns null if
     * there no players available in the file "player.csv" Returns "Registered" for
     * successful registration Returns "Invalid credentials" when player credentials
     * are wrong
     */

    // HIPHOP, WIN2WIN,
    // HAPPYFEET, LUCKYSTRIKE;
    // if
    // (playerTeamDao.getAllPlayerTeams().stream().anyMatch(s->(s.getPlayerId().equals(playerId)&&
    // s.getTeamTitle()==
    // null))||(!playerTeamDao.getAllPlayerTeams().stream().anyMatch(s->s.getPlayerId().equals(playerId))))

    public synchronized String registerPlayerToLeague(String playerId, String password, LeagueTeamTitles teamTitle)
            throws PlayerNotFoundException, TeamAlreadyFormedException, PlayerAlreadyAllottedException {
        List<Player> players = playerDao.getAllPlayers();
        if (players.isEmpty()) {
            return null;
        }
        Player p;
        try {
            p = playerDao.findPlayer(playerId);
            if (p == null) {
                throw new PlayerNotFoundException();
            }
            if (p.getPassword().equals(password)) {
                playerTeamSet = playerTeamDao.getAllPlayerTeams();
                PlayerTeam playerTeam = new PlayerTeam(p.getPlayerId(), p.getTeamTitle());
                if (playerTeamSet.contains(playerTeam)) {
                    throw new PlayerAlreadyAllottedException();
                }
                if (playerTeamDao.getPlayerSetByTeamTitle(teamTitle.name()).size() < 11) {
                    p.setTeamTitle(teamTitle.name());
                    registeredPlayerList.add(p);
                    playerTeamSet.add(new PlayerTeam(playerId, teamTitle.name()));
                    return "Registered";
                } else {
                    throw new TeamAlreadyFormedException();
                }
            } else {
                return "Invalid credentials";
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
       
    }

    /**
     * Return the list of all registered players
     */
    @Override
    public List<Player> getAllRegisteredPlayers() {
        ArrayList<Player> regPlayers = new ArrayList<>();
        for (int i = 0; i < registeredPlayerList.size(); i++) {
            regPlayers.add(registeredPlayerList.get(i));
            System.out.println(regPlayers.get(i));
        }
        return regPlayers;
    }

    /**
     * Return the existing number of players for the given title
     */
    @Override
    public int getExistingNumberOfPlayersInTeam(LeagueTeamTitles teamTitle) {
        return 0;
    }

    /**
     * Admin credentials are authenticated and registered players are allotted to
     * requested teams if available If the requested teams are already formed,admin
     * randomly allocates to other available teams PlayerTeam object is added to
     * "finalteam.csv" file allotted by the admin using PlayerTeamDao Return "No
     * player is registered" when registeredPlayerList is empty Throw
     * TeamAlreadyFormedException when maximum number is reached for all teams
     * Return "Players allotted to teams" when registered players are successfully
     * allotted Return "Invalid credentials for admin" when admin credentials are
     * wrong
     */
    @Override
    public String allotPlayersToTeam(String adminName, String password, LeagueTeamTitles teamTitle)
            throws TeamAlreadyFormedException, PlayerNotFoundException {

        if (AdminCredentials.admin.equals(adminName) && AdminCredentials.password.equals(password)) {
            List<Player> playerList = playerDao.getAllPlayers();
            if (playerList.isEmpty()) {
                return "No player is registered";
            }
            int count = 0;
            for (Player player : playerList) {
                if (player.getTeamTitle() == null)
                    continue;
                if (player.getTeamTitle().equalsIgnoreCase(teamTitle.name())) {
                    count++;
                }
            }
            if (count >= 11)
                throw new TeamAlreadyFormedException();
            for (Player player : playerList) {
                if (player.getTeamTitle() == null) {
                    player.setTeamTitle(teamTitle.name());
                    playerTeamDao.addPlayerToTeam(player);
                }
            }
            return "Players allotted to teams";
        } else {
            return "Invalid credentials for admin";
        }
        // if (adminName.equals(AdminCredentials.admin) &&
        // password.equals(AdminCredentials.password)) {
        // List<Player> allregPlayers = new ArrayList<Player>();
        // int flag = 0;
        // allregPlayers = getAllRegisteredPlayers();
        // if (allregPlayers.size() == 0) {
        // return "No player is registered";
        // }
        // for (int i = 0; i < allregPlayers.size(); i++) {
        // if
        // (allregPlayers.get(i).getPlayerId().equalsIgnoreCase(registeredPlayerList.get(i).getPlayerId())
        // && allregPlayers.get(i).getTeamTitle() != null) {
        // flag = 1;
        // method1(allregPlayers);
        // }
        // }

        // if (flag == 1) {
        // return "Players allotted to teams";
        // }

        // } else {
        // return "Invalid credentials for admin";
        // }
        // return null;
    }

    public void method1(List<Player> player) {

        try {
            FileWriter fw = new FileWriter("src/main/resources/finalteam.csv");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Player p : player) {
                PlayerTeam playerTeam = new PlayerTeam(p.getPlayerId(), p.getTeamTitle());
                bw.append(playerTeam.toString() + "\n");
            }
            bw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * static nested class to initialize admin credentials admin name='admin' and
     * password='pass'
     */
    static class AdminCredentials {
        private static String admin = "admin";
        private static String password = "pass";
    }
}
