Feature: Product Rest API

  Background:
    #Given a product with id 1 exists in the database
    * url baseUrl

  Scenario: Get all products
    Given path '/products'
    When method GET
    Then status 200

  Scenario: Get page of products
    * param page = '0'
    * param size = '3'
    Given path '/products/all'
    When method GET
    Then status 200
    And match $ ==
    """
    {
    "content": [
        {
            "id": 15,
            "code": "P015",
            "name": "Product 15",
            "description": "Description of Product 15",
            "price": 49.99,
            "quantity": 8,
            "inventoryStatus": "Out of Stock",
            "category": "Category 2",
            "image": "image15.jpg",
            "rating": 3.6
        },
        {
            "id": 5,
            "code": "P005",
            "name": "Product 5",
            "description": "Description of Product 5",
            "price": 39.99,
            "quantity": 15,
            "inventoryStatus": "In Stock",
            "category": "Category 2",
            "image": "image5.jpg",
            "rating": 3.5
        },
        {
            "id": 18,
            "code": "P018",
            "name": "Product 18",
            "description": "Description of Product 18",
            "price": 35.99,
            "quantity": 6,
            "inventoryStatus": "Out of Stock",
            "category": "Category 2",
            "image": "image18.jpg",
            "rating": 3.7
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "unsorted": true,
            "sorted": false
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 3,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 7,
    "totalElements": 20,
    "last": false,
    "size": 3,
    "number": 0,
    "sort": {
        "empty": true,
        "unsorted": true,
        "sorted": false
    },
    "numberOfElements": 3,
    "first": true,
    "empty": false
}
    """

  Scenario: Get product by id
    Given path '/products/1'
    When method GET
    Then status 200
    And match $ contains { id: "#notnull" }
    And match $ contains { id: 1 }

  Scenario: Get product by id with invalid id
    Given path '/products/111'
    When method GET
    Then status 400
    And the response body should contain an error message

  Scenario: Add a product
    * def productToAdd = karate.read('classpath:json/new-product.json')
    Given path '/products'
    And request productToAdd
    When method POST
    Then status 201
    And match $ contains { id: "#notnull" }

  Scenario: Add a product with missing required field
    When a POST request is sent to "/products" with request body:
      """
      {
        "name": "Product 3"
      }
      """
    Then the response status code should be 400
    And the response body should contain an error message

  Scenario: Update a product
    When a PATCH request is sent to "/products/1" with request body:
      """
      {
        "name": "Updated Product 1"
      }
      """
    Then the response status code should be 200
    And the response body should contain the updated product

  Scenario: Update a product with invalid id
    When a PATCH request is sent to "/products/invalid-id" with request body:
      """
      {
        "name": "Updated Product"
      }
      """
    Then the response status code should be 400
    And the response body should contain an error message

  Scenario: Delete a product
    Given path '/products/1'
    When method DELETE
    Then status 204

  Scenario: Delete a product with invalid id
    Given path '/products/222'
    When method DELETE
    Then status 400
    And the response body should contain an error message
