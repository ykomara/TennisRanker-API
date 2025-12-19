package com.dyma.tennis.service;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerToSave;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test") // pour utiliser le profile de test (application-test.properties) ;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD ) // pour reinitialiser le contexte entre les tests; cela permet d'eviter les effets de bord entre les tests; i.e si un test modifie la base de donnees, le test suivant aura une base de donnees propre
public class PlayerServiceIntegrationTest {

   @Autowired
    private PlayerService playerService;

   @Test
   public void shouldCreatePlayer(){
       // given
       PlayerToSave playerToSave = new PlayerToSave(
                "Yaya",
                "Komara",
                LocalDate.of(1996, 8, 8),
                10000
       );

         // when
       playerService.create(playerToSave);
       Player createdPlayer = playerService.getPlayerByLastName(playerToSave.lastName());

         // then
       Assertions.assertThat(createdPlayer.firstName()).isEqualTo("Yaya");
       Assertions.assertThat(createdPlayer.lastName()).isEqualTo("Komara");
       Assertions.assertThat(createdPlayer.rank().points()).isEqualTo(10000);
       Assertions.assertThat(createdPlayer.rank().position()).isEqualTo(1);

   }

   @Test
   public void shouldUpdatePlayer(){
       //given
       PlayerToSave playerToSave = new PlayerToSave(
               "Rafael",
               "NadalTest",
               LocalDate.of(1986, 6, 3),
               2000
       );

       // when
       playerService.update(playerToSave);
       Player updatedPlayer = playerService.getPlayerByLastName(playerToSave.lastName());

       // then
       Assertions.assertThat(updatedPlayer.rank().position()).isEqualTo(3);

   }

   @Test
    public void shouldDeletePlayer(){
       // given
       String playerToDelete = "FedererTest";

         // when
       playerService.delete(playerToDelete);
       List<Player> allPlayers = playerService.getAllPlayers();

         // then
       Assertions.assertThat(allPlayers)
               .extracting("lastName", "rank.position")
               .containsExactly( Tuple.tuple("NadalTest", 1), Tuple.tuple("DjokovicTest", 2) );
   }

   @Test
    public void shouldFailToDelete_whenPlayerDoesNotExist() {
       // given
       String playerToDelete = "ExistentTest";

       // when / then
       Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
               PlayerNotFoundException.class,
               () -> playerService.delete(playerToDelete)
       );

       Assertions.assertThat(exception.getMessage()).isEqualTo("Player with last name 'ExistentTest' not found.");
   }

}
