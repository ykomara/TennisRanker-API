package com.dyma.tennis.service;
import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerList;
import com.dyma.tennis.PlayerToSave;
import com.dyma.tennis.Rank;
import com.dyma.tennis.data.PlayerEntity;
import com.dyma.tennis.data.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class PlayerService {

    @Autowired
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        // Implementation goes here
        // Rank(position, points) -> PlayerEntity stores points then rank, so pass (rank, points)
        return playerRepository.findAll().stream()
                .map(playerEntity -> new Player(
                        playerEntity.getFirstName(),
                        playerEntity.getLastName(),
                        playerEntity.getBirthDate(),
                        new Rank(playerEntity.getRank(), playerEntity.getPoints())
                ))
                .sorted(Comparator.comparing(player -> player.rank().position()))
                .toList();
    }

    public Player getPlayerByLastName(String lastName){
        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(lastName); // Rechercher le joueur par son nom de famille
        if(player.isEmpty()){
            throw new PlayerNotFoundException(lastName);
        }
        PlayerEntity playerEntity = player.get(); // Récupérer l'entité PlayerEntity de l'Optional

        // Rank(position, points)
        return new Player(
                playerEntity.getFirstName(),
                playerEntity.getLastName(),
                playerEntity.getBirthDate(),
                new Rank(playerEntity.getRank(), playerEntity.getPoints())
        );
    }

    public Player create(PlayerToSave playerToSave){
        Optional<PlayerEntity> playerToCreate = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
        if(playerToCreate.isPresent()){
            throw new PlayerAlreadyExistsException(playerToSave.lastName());
        }

        // les setters permettent d'initialiser les propriétés de l'entité PlayerEntity avant de la sauvegarder dans la base de données
        PlayerEntity playerEntity = new PlayerEntity(playerToSave.lastName(), playerToSave.firstName(), playerToSave.birthDate(), playerToSave.points(), 999999999);

        playerRepository.save(playerEntity);

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> updatedPlayers = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(updatedPlayers);

        return getPlayerByLastName(playerEntity.getLastName());
    }

    public Player update(PlayerToSave playerToSave){
        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
        if(player.isEmpty()){
            throw new PlayerNotFoundException(playerToSave.lastName());
        }

        player.get().setFirstName(playerToSave.firstName());
        player.get().setBirthDate(playerToSave.birthDate());
        player.get().setPoints(playerToSave.points());
        playerRepository.save(player.get());

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> updatedPlayers = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(updatedPlayers);

        return getPlayerByLastName(playerToSave.lastName());
    }

    public void delete(String lastName){
        Optional<PlayerEntity> playerToDelete = playerRepository.findOneByLastNameIgnoreCase(lastName);
        if(playerToDelete.isEmpty()){
            throw new PlayerNotFoundException(lastName);
        }

        playerRepository.delete(playerToDelete.get());

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> deletePlayer = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(deletePlayer);


    }


}
