Feature: adoption requests

  Scenario: adding an adoption request
    Given I'm signed up with username "joanne" and password "joanne1975"
    And I log in with username "joanne" and password "joanne1975"
    When I click "Add adoption request" on "Chocobo" card
    And I specify my email "joanne1975@example.com" and add note "She is so cute!"
    Then I see 1 pending adoption request(s) for "Chocobo"

