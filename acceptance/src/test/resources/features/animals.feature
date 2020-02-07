Feature: Animals list

  Background:
    As Martin, I can see the full list of animals on the main page,
    So that I can decide which one I would like to adopt

  Scenario: displaying animals list
    Given I'm an anonymous user
    When I navigate to the main page
    Then I see Chocobo
    And I see Brody
