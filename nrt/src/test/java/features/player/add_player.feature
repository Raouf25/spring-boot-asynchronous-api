Feature: Player API

  Background:
    * url baseUrl

  Scenario: Retrieve players list
    * def playerToAdd = karate.read('classpath:json/new-player.json')
    Given path '/api/v1/players/add'
    And request playerToAdd
    When method POST
    Then status 200
    And match $ contains { id: "#notnull" }
