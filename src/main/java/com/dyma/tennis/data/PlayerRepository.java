package com.dyma.tennis.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findOneByLastNameIgnoreCase(String lastName); // c'est une query method (methode de requête) qui permet de rechercher un joueur par son nom de famille; Optional est utilisé pour gérer le cas où aucun joueur n'est trouvé avec le nom de famille donné; IgnoreCase signifie que la recherche n'est pas sensible à la casse

}
