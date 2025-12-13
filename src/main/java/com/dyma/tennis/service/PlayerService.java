package com.dyma.tennis.service;
import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerList;
import com.dyma.tennis.PlayerToSave;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    public List<Player> getAllPlayers() {
        // Implementation goes here
        return PlayerList.ALL.stream().sorted(Comparator.comparing(player -> player.rank().position())).toList();
    }

    public Player getPlayerByLastName(String lastName){
        return PlayerList.ALL.stream().filter(player -> player.lastName().equals(lastName)).findFirst().orElseThrow(() -> new PlayerNotFoundException(lastName));
    }

    public Player create(PlayerToSave playerToSave){
        return getPlayerNewRanking(PlayerList.ALL, playerToSave);
    }

    public Player update(PlayerToSave playerToSave){
        getPlayerByLastName(playerToSave.lastName());

        // Exclude the player to update from the existing ranking list so the ranking calculator can reinsert the updated player
        List<Player> playersWithoutPlayerToUpdate = PlayerList.ALL.stream()
                .filter(player -> !player.lastName().equals(playerToSave.lastName())) // permet d'exclure le joueur à mettre à jour
                .toList();

        return getPlayerNewRanking(playersWithoutPlayerToUpdate, playerToSave);
    }

    public void delete(String lastName){
        Player playerToDelete = getPlayerByLastName(lastName);
        PlayerList.ALL = PlayerList.ALL.stream()
                .filter(player -> !player.lastName().equals(playerToDelete.lastName()))
                .collect(Collectors.toList());



    }

    private Player getPlayerNewRanking(List<Player> existingPlayers, PlayerToSave playerToSave){
        RankingCalculator rankingCalculator = new RankingCalculator(existingPlayers, playerToSave);
        List<Player> players = rankingCalculator.getNewPlayersRanking();
        return players.stream().filter(player -> player.lastName().equals(playerToSave.lastName())).findFirst().get();
    }
}
