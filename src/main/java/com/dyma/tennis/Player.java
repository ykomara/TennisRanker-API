package com.dyma.tennis;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record Player(
                     //ajout des contraintes de validation et des messages d'erreur personnalisés
                     @PositiveOrZero(message = "identifiant obligatoire") String id,
                     @NotBlank(message= " le prenom est obligatoire " ) String firstName,
                     @NotBlank(message= "le nom est obligatoire") String lastName,
                     @PastOrPresent LocalDate birthDate,
                     @Valid Rank rank) {
}
