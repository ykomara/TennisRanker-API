package com.dyma.tennis.service;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerToSave;
import com.dyma.tennis.Rank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankingCalculator {

    private final List<Player> currentPlayersRanking;
    private final PlayerToSave playerToRegister;

    public RankingCalculator(List<Player> currentPlayersRanking, PlayerToSave playerToSave) {
        this.currentPlayersRanking = currentPlayersRanking;
        this.playerToRegister = playerToSave;
    }

    public List<Player> getNewPlayersRanking() {
        List<Player> newRankingList = new ArrayList<>(currentPlayersRanking);
        newRankingList.add(new Player(
                playerToRegister.id(),
                playerToRegister.firstName(),
                playerToRegister.lastName(),
                playerToRegister.birthDate(),
                new Rank(99999999, playerToRegister.points())
        ));

        newRankingList.sort((player1, player2) -> Integer.compare(player2.rank().points(), player1.rank().points()));

        /*List<Player> sortedPlayers = newRankingList.stream()
                .sorted(Comparator.comparing(player -> player.rank().points()))
                .toList();
         */

        List<Player> updatedPlayers = new ArrayList<>();

        for (int i = 0; i < newRankingList.size(); i++) {
            Player player = newRankingList.get(i);
            Player updatedPlayer = new Player(
                    player.id(),
                    player.firstName(),
                    player.lastName(),
                    player.birthDate(),
                    new Rank(i + 1, player.rank().points())
            );
            updatedPlayers.add(updatedPlayer);
        }

        return updatedPlayers;
    }
}
