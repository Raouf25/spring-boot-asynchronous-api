package com.mak.springbootasynchronousapi.service;

import com.mak.springbootasynchronousapi.model.Player;
import com.mak.springbootasynchronousapi.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Flux<Player> getAllPlayers() {
        final String errorMessage = "There is an issue getting all of players.";
        return Utils.processWithErrorCheck(this.playerRepository.findAll(), errorMessage);
    }

    public Mono<Player> getPlayerById(Long id) {
        final String errorMessage = String.format("There is no player with id: '%d'", id);
        return Utils.processWithErrorCheck(this.playerRepository.findById(id), errorMessage);
    }

    public Flux<Player> getPlayersByClub(String club) {
        final String errorMessage = String.format("There is no players for club: '%s'", club);
        return Utils.processWithErrorCheck(this.playerRepository.findByClub(club), errorMessage);
    }

    public Flux<Player> getPlayersByNationality(String nationality) {
        final String errorMessage = String
                .format("There is no players with nationality: '%s'", nationality);
        return Utils.processWithErrorCheck(this.playerRepository.findByNationality(nationality),
                errorMessage);
    }

    public Mono<Player> addPlayer(Player player) {
        final String errorMessage = "Unable to add player with input:" + player;
        return Utils.processWithErrorCheck(this.playerRepository.save(new Player(player)), errorMessage);
    }

    public Mono<Player> updatePlayer(Long id, Player playerInput) {
        final String errorMessage =
                "Unable to update player with id" + id + "input:" + playerInput;
        return Utils.processWithErrorCheck(this.playerRepository.findById(Objects.requireNonNull(id)),
                errorMessage)
                .flatMap(player -> {
                    player
                            .setName(Objects.requireNonNull(player.getName()))
                            .setAge(Objects.requireNonNull(player.getAge()))
                            .setClub(Objects.requireNonNull(player.getClub()));
                    return this.playerRepository.save(player).log();
                });
    }

    @Override
    public Mono<Player> deletePlayerById(Long id) {
        return getPlayerById(id).map(player -> {
            this.playerRepository.deleteById(id).subscribe();
            return player;
        });
    }
}
