package com.mak.springbootasynchronousapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record Player(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("age") Integer age,
        @JsonProperty("club") String club,
        @JsonProperty("nationality") String nationality) {

    public Player {
        Objects.requireNonNull(name);
        Objects.requireNonNull(age);
        Objects.requireNonNull(club);
        Objects.requireNonNull(nationality);
    }
}
