package com.mak.springbootasynchronousapi;

import com.mak.springbootasynchronousapi.controller.PlayerRestController;
import com.mak.springbootasynchronousapi.model.Player;
import com.mak.springbootasynchronousapi.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
class PlayerRestControllerIntegrationTest {

    private WebTestClient webTestClient;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerRestController playerRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(playerRestController).build();
    }

    @Test
    void testGetAllPlayers() {
        Player player1 = new Player(1L, "John Doe", 25, "FC Barcelona", "Spain");
        Player player2 = new Player(2L, "Jane Smith", 27, "Real Madrid", "USA");

        when(playerService.getAllPlayers()).thenReturn(Flux.just(player1, player2));

        webTestClient.get().uri("/api/v1/players/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Player.class)
                .hasSize(2)
                .contains(player1, player2);
    }

    @Test
    void testGetPlayerById() {
        Player player = new Player(1L, "John Doe", 25, "FC Barcelona", "Spain");

        when(playerService.getPlayerById(1L)).thenReturn(Mono.just(player));

        webTestClient.get().uri("/api/v1/players/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Player.class)
                .isEqualTo(player);
    }

    @Test
    void testGetPlayersByClub() {
        Player player1 = new Player(1L, "John Doe", 25, "FC Barcelona", "Spain");
        Player player2 = new Player(2L, "Jane Smith", 27, "FC Barcelona", "USA");

        when(playerService.getPlayersByClub("FC Barcelona")).thenReturn(Flux.just(player1, player2));

        webTestClient.get().uri("/api/v1/players/club/{club}", "FC Barcelona")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Player.class)
                .hasSize(2)
                .contains(player1, player2);
    }

    @Test
    void testGetPlayersByNationality() {
        Player player1 = new Player(1L, "John Doe", 25, "FC Barcelona", "Spain");
        Player player2 = new Player(2L, "Jane Smith", 27, "Real Madrid", "Spain");

        when(playerService.getPlayersByNationality("Spain")).thenReturn(Flux.just(player1, player2));

        webTestClient.get().uri("/api/v1/players/nationality/{nationality}", "Spain")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Player.class)
                .hasSize(2)
                .contains(player1, player2);
    }

    @Test
    void testAddPlayer() {
        Player player = new Player(1L, "John", 20, "Club 1", "Country 1");
        when(playerService.addPlayer(any())).thenReturn(Mono.just(player));

        webTestClient.post()
                .uri("/api/v1/players/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(player), Player.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Player.class).isEqualTo(player);
    }

    @Test
    void testUpdatePlayer() {
        Long id = 1L;
        Player player = new Player(id, "John", 20, "Club 1", "Country 1");
        when(playerService.updatePlayer(anyLong(), any())).thenReturn(Mono.just(player));

        webTestClient.post()
                .uri("/api/v1/players/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(player), Player.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Player.class).isEqualTo(player);
    }

    @Test
    void testDeletePlayerById() {
        Long id = 1L;
        Player player = new Player(id, "John", 20, "Club 1", "Country 1");
        when(playerService.deletePlayerById(anyLong())).thenReturn(Mono.just(player));

        webTestClient.delete()
                .uri("/api/v1/players/delete/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Player.class).isEqualTo(player);
    }

}
