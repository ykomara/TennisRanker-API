package com.dyma.tennis.service;


import com.dyma.tennis.Player;
import com.dyma.tennis.data.PlayerEntity;
import com.dyma.tennis.data.PlayerEntityList;
import com.dyma.tennis.data.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.ThrowsException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository);
    }

    @Test
    public void shouldReturnPlayersRanking(){
        //bonne pratique : given-when-then; pour telle condition d'entrée, quand on fait telle action, alors on obtient tel résultat

        //given -> c'est le moment où on prépare les données d'entrée et les comportements attendus des dépendances mockées
        //lorsque j'appelle mon repository de cette façon, je veux qu'il me retourne cette liste
        Mockito.when(playerRepository.findAll()).thenReturn(PlayerEntityList.ALL);

        //when -> c'est le moment où on exécute l'action que l'on veut tester
        //j'appelle la méthode que je veux tester
        List<Player> allPlayers = playerService.getAllPlayers();

        //then -> c'est le moment où on vérifie que les résultats obtenus correspondent aux attentes
        //je vérifie que le résultat est bien celui attendu
        Assertions.assertThat(allPlayers)
                    .extracting("lastName")
                    .containsExactly("Nadal", "Djokovic", "Federer", "Murray");
    }

    @Test
    public void shouldRetrievePlayerByLastName(){
        //given
        String playerToRetrieve = "Nadal";
        Mockito.when(playerRepository.findOneByLastNameIgnoreCase(playerToRetrieve))
                .thenReturn(Optional.of(PlayerEntityList.RAFAEL_NADAL));

        //when
        Player retrievedPlayer = playerService.getPlayerByLastName(playerToRetrieve);

        //then
        Assertions.assertThat(retrievedPlayer.lastName()).isEqualTo("Nadal");

    }

    //Test des cas d'erreur
    @Test
    public void shouldFailToRetrieve_WhenPlayerDoesNotExist() {
        //given
        String unknownPlayer = "Doe";
        Mockito.when(playerRepository.findOneByLastNameIgnoreCase(unknownPlayer)).thenReturn(Optional.empty());

        //when & then -> pour les exceptions, on peut combiner les deux étapes
        Exception exception = assertThrows(PlayerNotFoundException.class, () -> {
            playerService.getPlayerByLastName(unknownPlayer);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Player with last name 'Doe' not found.");





    }


}
