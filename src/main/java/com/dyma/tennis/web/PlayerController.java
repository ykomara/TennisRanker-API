package com.dyma.tennis.web;

import com.dyma.tennis.Player;
import com.dyma.tennis.PlayerList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "Tennis Player API")
@RestController
@RequestMapping("/players") //@RequestMapping permet de définir le chemin de base pour toutes les requêtes de ce contrôleur
public class PlayerController {

    @Operation(summary = "Finds all players", description = "Returns a list of all players")
    @ApiResponses( value = { //@ApiResponses permet de définir plusieurs réponses possibles pour une opération
        @ApiResponse(responseCode = "200", description = "List of players retrieved successfully",
             content = {
                    @io.swagger.v3.oas.annotations.media.Content(
                        mediaType = "application/json",
                        array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Player.class)
                        )
                    )
             })
    })
    @GetMapping
    public List<Player> list() {
        // Implementation goes here
        return PlayerList.ALL;
    }

    @Operation(summary="Finds a player by last name", description = "Returns a single player matching the provided last name")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Player retrieved successfully",
             content = {
                    @io.swagger.v3.oas.annotations.media.Content(
                        mediaType = "application/json",
                        schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Player.class) // cette syntaxe permet de spécifier que le schéma de la réponse est basé sur la classe Player
                    )
             }),
        @ApiResponse(responseCode = "404", description = "Player not found")
    })
    @GetMapping("{lastName}")
    public Player getByLastName(@PathVariable("lastName") String lastName) { //@PathVariable permet de lier la variable lastName de l'URL à la variable lastName de la méthode
        return PlayerList.ALL.stream().filter(player -> player.lastName().equals(lastName)).findFirst().orElseThrow(() -> new NoSuchElementException("Player not found"));
    }

    @Operation (summary="Creates a new player", description = "Creates a new player with the provided details")
    @ApiResponses ( value = {
        @ApiResponse(responseCode = "201", description = "Player created successfully",
             content = {
                    @io.swagger.v3.oas.annotations.media.Content(
                        mediaType = "application/json",
                        schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Player.class)
                    )
             })
    })
    @PostMapping
    public Player createPlayer(@RequestBody @Valid Player player) { //@RequestBody permet de lier le corps de la requête HTTP à l'objet player
        // Implementation goes here
        return player;

    }

    @Operation (summary="Updates an existing player", description = "Updates the details of an existing player")
    @ApiResponses ( value = {
        @ApiResponse(responseCode = "200", description = "Player updated successfully",
             content = {
                    @io.swagger.v3.oas.annotations.media.Content(
                        mediaType = "application/json",
                        schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Player.class)
                    )
             }),
        @ApiResponse(responseCode = "404", description = "Player not found")
    })

    @PutMapping
    public Player updatePlayer(@RequestBody @Valid Player player) {
        // Implementation goes here
        return player;

    }

    @Operation (summary="Deletes a player", description = "Deletes the player with the specified last name")
    @ApiResponses ( value = {
        @ApiResponse(responseCode = "204", description = "Player deleted successfully"), // 204 veut dire que la requête a réussi mais qu'il n'y a pas de contenu à renvoyer
        @ApiResponse(responseCode = "404", description = "Player not found") // 404 veut dire que la ressource demandée n'a pas été trouvée
    })
    @DeleteMapping("{lastName}")
    public void deletePlayer(@PathVariable("lastName") String lastName) {
        // Implementation goes here
    }
}
