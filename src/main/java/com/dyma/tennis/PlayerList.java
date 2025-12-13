package com.dyma.tennis;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class PlayerList {

    public static Player rafaelNadal() {
        return new Player(
                "1",
                "Rafael",
                "Nadal",
                LocalDate.of(1986, 6, 3),
                new Rank(2, 9850)
        );
    }

    public static Player novakDjokovic() {
        return new Player(
                "2",
                "Novak",
                "Djokovic",
                LocalDate.of(1987, 5, 22),
                new Rank(1, 12030)
        );
    }

    public static Player rogerFederer() {
        return new Player(
                "3",
                "Roger",
                "Federer",
                LocalDate.of(1981, 8, 8),
                new Rank(5, 6630)
        );
    }

    public static List<Player> ALL = new ArrayList<>(Arrays.asList( // Utiliser une ArrayList mutable pour permettre les mises à jour
            rogerFederer(),
            rafaelNadal(),
            novakDjokovic()

    ));

}
