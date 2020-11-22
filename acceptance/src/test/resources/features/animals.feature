Feature: Animals list

  Background:
    As Martin, I can see the full list of animals on the main page,
    So that I can decide which one I would like to adopt

  Scenario: displaying animals list
    Given I'm an anonymous user
    When I navigate to the main page
    Then I see Chocobo
    And I see Brody

  Scenario: hiding animals already pending for adoption
    Given I'm an anonymous user
    When I navigate to the main page
    Then I don't see Georgie

  Scenario: displaying description and rescue date
    Given I'm an anonymous user
    When I navigate to the main page
    Then I see Chocobo with description including "She is chubby, lazy, but always polite" and rescue date 25 Dec 2019
    And I see Brody with description including "Brody has lots of energy and loves the outdoors" and rescue date 14 Jan 2020