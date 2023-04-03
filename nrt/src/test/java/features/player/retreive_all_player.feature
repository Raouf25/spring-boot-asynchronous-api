Feature: Player API

  Background:
    * url baseUrl

  Scenario: Retrieve players list

    Given path '/api/v1/players'
    When method GET
    Then status 200
    And match $ ==
    """
    [
       {
          "id":1,
          "name":"Son Heung-Min",
          "age":29,
          "club":"Tottenham Hotspur",
          "nationality":"South-Korea"
       },
       {
          "id":2,
          "name":"Mohamed Salah",
          "age":29,
          "club":"Liverpool",
          "nationality":"Egypt"
       },
       {
          "id":3,
          "name":"Teemu Pukki",
          "age":31,
          "club":"Norwich City",
          "nationality":"Finland"
       },
       {
          "id":4,
          "name":"Ivan Toney",
          "age":25,
          "club":"Brentford",
          "nationality":"England"
       },
       {
          "id":5,
          "name":"Harry Kane",
          "age":32,
          "club":"Tottenham Hotspur",
          "nationality":"England"
       },
       {
          "id":6,
          "name":"Mason Mount",
          "age":22,
          "club":"Chelsea",
          "nationality":"England"
       }
    ]
    """
