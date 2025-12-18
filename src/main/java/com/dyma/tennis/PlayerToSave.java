package com.dyma.tennis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record PlayerToSave(
                     //ajout des contraintes de validation et des messages d'erreur personnalisés
                     @NotBlank(message= " le prenom est obligatoire " ) String firstName,
                     @NotBlank(message= "le nom est obligatoire") String lastName,
                     @PastOrPresent LocalDate birthDate,
                     @PositiveOrZero (message = "le point doit etre superieur a zero") int points){
}
