package com.mak.springbootasynchronousapi.service;

import com.mak.springbootasynchronousapi.model.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerService {

    Mono<Player> getPlayerById(Long id);
    Flux<Player> getAllPlayers();
    Flux<Player> getPlayersByClub(String club);
    Flux<Player> getPlayersByNationality(String nationality);
    Mono<Player> addPlayer(Player playerInput);
    Mono<Player> updatePlayer(Long id, Player player);
    Mono<Player> deletePlayerById(Long id);
}
