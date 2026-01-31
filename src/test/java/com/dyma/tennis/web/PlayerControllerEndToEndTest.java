package com.dyma.tennis.web;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerToSave;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //son paramètre permet de lancer le serveur web sur un port aléatoire
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayerControllerEndToEndTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate; //permet d'envoyer des requêtes HTTP dans les tests

    @Test
    public void shouldCreatePlayer() {
        // Given
        PlayerToSave playerToCreate = new PlayerToSave(
                "Carlos",
                "Alcaraz",
                LocalDate.of(2003, Month.MAY, 5),
                4500
        );

        // When
        ResponseEntity<Player> playerResponseEntity = restTemplate.postForEntity(
                "/players",
                playerToCreate,
                Player.class
        );

        // Then
        Assertions.assertThat(playerResponseEntity.getBody().lastName()).isEqualTo("Alcaraz");
        Assertions.assertThat(playerResponseEntity.getBody().rank().position()).isEqualTo(2);
    }


}
