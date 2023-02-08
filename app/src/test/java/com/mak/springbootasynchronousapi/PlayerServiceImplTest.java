package com.mak.springbootasynchronousapi;

import com.mak.springbootasynchronousapi.model.Player;
import com.mak.springbootasynchronousapi.repository.PlayerRepository;
import com.mak.springbootasynchronousapi.service.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;


    @Test
    void testGetAllPlayers_shouldReturnFluxOfPlayers() {
        // given
        Player player1 = new Player(1L, "Messi", 32, "Barcelona", "Argentina");
        Player player2 = new Player(2L, "Ronaldo", 36, "Juventus", "Portugal");
        when(playerRepository.findAll())
                .thenReturn(Flux.just(player1, player2));

        // when
        Flux<Player> result = playerService.getAllPlayers();

        // then
        StepVerifier.create(result).expectNext(player1).expectNext(player2).verifyComplete();
    }

    @Test
    void testGetPlayerById() {
        // Given
        Long id = 1L;
        Player player = new Player();
        player.setId(id);

        // When
        when(playerRepository.findById(id)).thenReturn(Mono.just(player));
        Mono<Player> playerMono = playerService.getPlayerById(id);

        // Then
        StepVerifier.create(playerMono).expectNext(player).verifyComplete();
    }

    @Test
    void testGetAllPlayers() {
        // Given
        Player player = new Player();
        Flux<Player> playerFlux = Flux.just(player);

        // When
        when(playerRepository.findAll()).thenReturn(playerFlux);
        Flux<Player> playersFlux = playerService.getAllPlayers();

        // Then
        StepVerifier.create(playersFlux).expectNext(player).verifyComplete();
    }

    @Test
    void testGetPlayersByClub() {
        // Given
        String club = "Barcelona";
        Player player = new Player();
        player.setClub(club);
        Flux<Player> playerFlux = Flux.just(player);

        // When
        when(playerRepository.findByClub(club)).thenReturn(playerFlux);
        Flux<Player> playersFlux = playerService.getPlayersByClub(club);

        // Then
        StepVerifier.create(playersFlux).expectNext(player).verifyComplete();
    }

    @Test
    void getPlayersByNationality_whenNationalityExists_returnsPlayers() {
        // arrange
        when(playerRepository.findByNationality("France"))
                .thenReturn(
                        Flux.just(new Player(1L, "Zinedine Zidane", 48, "Real Madrid", "France"),
                                new Player(2L, "Thierry Henry", 42, "Barcelona", "France")));

        // act
        Flux<Player> players = playerService.getPlayersByNationality("France");

        // assert
        StepVerifier.create(players).expectNext(new Player(1L, "Zinedine Zidane", 48, "Real Madrid", "France"))
                .expectNext(new Player(2L, "Thierry Henry", 42, "Barcelona", "France"))
                .verifyComplete();
    }

    @Test
    void addPlayer_whenPlayerIsValid_addsPlayer() {
        // arrange
        Player player = new Player(null, "Cristiano Ronaldo", 36, "Juventus", "Portugal");
        when(playerRepository.save(player)).thenReturn(Mono.just(new Player(1L, player.getName(), player.getAge(), player.getClub(), player.getNationality())));

        // act
        Mono<Player> addedPlayer = playerService.addPlayer(player);

        // assert
        StepVerifier.create(addedPlayer).expectNextMatches(p -> p.getId() != null && p.getName().equals("Cristiano Ronaldo")).verifyComplete();
    }

    @Test
    void updatePlayerTest() {
        //Arrange
        Long id = 1L;
        Player playerInput = new Player(1L, "Lionel Messi", 33, "Barcelona", "Argentinian");
        Player expectedPlayer = new Player(1L, "Lionel Messi", 33, "Barcelona", "Argentinian");
        when(playerRepository.findById(id)).thenReturn(Mono.just(expectedPlayer));
        when(playerRepository.save(expectedPlayer)).thenReturn(Mono.just(expectedPlayer));

        //Act
        Mono<Player> actualPlayer = playerService.updatePlayer(id, playerInput);

        //Assert
        StepVerifier.create(actualPlayer).expectNext(expectedPlayer).verifyComplete();
        verify(playerRepository, times(1)).findById(id);
        verify(playerRepository, times(1)).save(expectedPlayer);
    }

    @Test
    void testDeletePlayerById() {
        //Arrange
        Long id = 1L;
        Player player = new Player(id, "John Doe", 25, "Manchester United", "England");
        when(playerRepository.findById(id)).thenReturn(Mono.just(player));
        when(playerRepository.deleteById(id)).thenReturn(Mono.empty());

        //Act
        Mono<Player> deletedPlayerMono = playerService.deletePlayerById(1L);

        //Assert
        assertNotNull(deletedPlayerMono);
        assertEquals(player, deletedPlayerMono.block());
        verify(playerRepository).findById(id);
        verify(playerRepository).deleteById(id);
        StepVerifier.create(deletedPlayerMono).expectNext(player).verifyComplete();
    }

}
