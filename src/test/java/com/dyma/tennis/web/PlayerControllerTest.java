package com.dyma.tennis.web;

import com.dyma.tennis.PlayerList;
import com.dyma.tennis.service.PlayerNotFoundException;
import com.dyma.tennis.service.PlayerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlayerController.class) // permet en général de tester un controller sans démarrer le serveur
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc; // pour simuler des requêtes HTTP

    @MockitoBean
    private PlayerService playerService; // service mocké pour isoler le controller

    @Test
    public void shouldListAllPlayers() throws Exception {
        //given
        Mockito.when(playerService.getAllPlayers()).thenReturn(PlayerList.ALL);

        //when & then
        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))) // vérifie que la taille du tableau JSON est de 3
                .andExpect(jsonPath("$[0].lastName", CoreMatchers.is("Federer")))
                .andExpect(jsonPath("$[1].lastName", CoreMatchers.is("Nadal")))
                .andExpect(jsonPath("$[2].lastName", CoreMatchers.is("Djokovic")));

    }

    @Test
    public  void shouldRetrievePlayer() throws Exception {
        //given
        String lastName = "Nadal";
        Mockito.when(playerService.getPlayerByLastName(lastName)).thenReturn(PlayerList.rafaelNadal());

        //when & then
        mockMvc.perform(get("/players/Nadal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nadal")))
                .andExpect(jsonPath("$.rank.position", CoreMatchers.is(2)));

    }

    @Test
    public void shouldReturn404WhenPlayerNotFound() throws Exception {
        //given
        String lastName = "Unknown";
        Mockito.when(playerService.getPlayerByLastName(lastName))
                .thenThrow(new PlayerNotFoundException("Player Unknown does not exist"));

        //when & then
        mockMvc.perform(get("/players/Unknown"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", CoreMatchers.is("Player with last name 'Player Unknown does not exist' not found.")));

    }
}
