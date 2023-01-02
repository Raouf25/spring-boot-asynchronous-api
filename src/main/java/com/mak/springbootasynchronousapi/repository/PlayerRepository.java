package com.mak.springbootasynchronousapi.repository;

import com.mak.springbootasynchronousapi.model.Player;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Long> {

    Flux<Player> findByClub(String club);
    Flux<Player> findByNationality(String nationality);

}
