package com.dyma.tennis;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record Rank(
        @Positive (message = "la position doit etre un nombre strictement positif") int position,
        @PositiveOrZero int points) {
}
