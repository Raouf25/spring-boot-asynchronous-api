Feature: Player API

  Background:
    * url baseUrl

  Scenario Outline: Retrieve players by nationality <nationality>

    Given path '/api/v1/players/nationality/<nationality>'
    When method GET
    Then status 200
    And match $ == <result>

    Examples:
      | nationality | result                                                                                                                                                                                                                                                      |
      | England     | [{"id":4,"name":"Ivan Toney","age":25,"club":"Brentford","nationality":"England"},{"id":5,"name":"Harry Kane","age":32,"club":"Tottenham Hotspur","nationality":"England"},{"id":6,"name":"Mason Mount","age":22,"club":"Chelsea","nationality":"England"}] |
      | Finland     | [{"id":3,"name":"Teemu Pukki","age":31,"club":"Norwich City","nationality":"Finland"}]                                                                                                                                                                      |
      | Egypt       | [{"id":2,"name":"Mohamed Salah","age":29,"club":"Liverpool","nationality":"Egypt"}]                                                                                                                                                                         |
      | South-Korea | [{"id":1,"name":"Son Heung-Min","age":29,"club":"Tottenham Hotspur","nationality":"South-Korea"}]                                                                                                                                                           |
