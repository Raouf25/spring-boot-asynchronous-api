package com.mak.springbootasynchronousapi.controller;

import com.mak.springbootasynchronousapi.model.Player;
import com.mak.springbootasynchronousapi.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.logging.Level;

@Slf4j
@RestController
@RequestMapping("/api/v1/players/")
@Api(tags = "PlayerRestController", value = "Player Rest API")
public class PlayerRestController {

    private final PlayerService playerService;

    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("all")
    @ApiOperation(value = "Get all players", notes = "Retrieve all players from the database")
    Flux<Player> getAllPlayers() {
        log.info("Retrieving all players");
        return processWithLog(this.playerService.getAllPlayers());
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Get player by id", notes = "Retrieve a player from the database by id")
    Mono<Player> getPlayerById(@PathVariable Long id) {
        log.info("Retrieving player with id: '{}'", id);
        return processWithLog(this.playerService.getPlayerById(id));
    }

    @GetMapping("club/{club}")
    @ApiOperation(value = "Get players by club", notes = "Retrieve all players from a specific club")
    Flux<Player> getPlayersByClub(@PathVariable String club) {
        log.info("Retrieving players for club: '{}'", club);
        return processWithLog(this.playerService.getPlayersByClub(club));
    }

    @GetMapping("nationality/{nationality}")
    @ApiOperation(value = "Get players by nationality", notes = "Retrieve all players with a specific nationality")
    Flux<Player> getPlayersByNationality(@PathVariable String nationality) {
        log.info("Retrieving players with nationality: '{}'", nationality);
        return processWithLog(this.playerService.getPlayersByNationality(nationality));
    }

    @PostMapping(value="add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add a player", notes = "Add a new player to the database")
    Mono<Player> addPlayer(@RequestBody Player player) {
        log.info("Adding player to repository");
        return processWithLog(this.playerService.addPlayer(player));
    }

    @PostMapping("update/{id}")
    @ApiOperation(value = "Update a player", notes = "Update an existing player in the database")
    Mono<Player> updatePlayer(@Validated @PathVariable Long id, @RequestBody Player player) {
        log.info("Updating player with id: {}", id);
        return processWithLog(this.playerService.updatePlayer(id, player));
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "Delete a player", notes = "Delete a player from the database by id")
    Mono<Player> deletePlayerById(@PathVariable Long id) {
        log.info("Removing player with id: {}", id);
        return processWithLog(this.playerService.deletePlayerById(id));
    }

    private <T> Mono<T> processWithLog(Mono<T> monoToLog) {
        return monoToLog
                .log("PlayerRestController.", Level.INFO, SignalType.ON_NEXT, SignalType.ON_COMPLETE);
    }

    private <T> Flux<T> processWithLog(Flux<T> fluxToLog) {
        return fluxToLog
                .log("PlayerRestController.", Level.INFO, SignalType.ON_NEXT, SignalType.ON_COMPLETE);
    }
}
