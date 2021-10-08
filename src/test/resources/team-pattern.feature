#curl --location --request POST 'http://localhost:8080/team/532'


Feature: create team pattern

  @happy
  Scenario: Happy case  - create team pattern successfully
    When  I send request with pattern "532"
    Then I verify status code 200 after request


  @conner
  Scenario Outline: Conner case - Request otp un-successfully
    When I send request with pattern "<pattern>"
    Then I verify status code <statusCode> after request
    And I verify  response body of request otp API

      | message | <message> |

    Examples:
      | description | pattern | statusCode | message                              |
      | alphabet    | abc     | 400        | TeamException : Invalid team pattern |
      | sum > 10    | 999     | 400        | TeamException : Invalid team pattern |
      | sum < 10    | 441     | 400        | TeamException : Invalid team pattern |
      | length > 3  | 1234    | 400        | TeamException : Invalid team pattern |
      | length < 3  | 12      | 400        | TeamException : Invalid team pattern |