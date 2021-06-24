Feature: Sample feature file
  Desc: This feature contains few sample test scenarios

  Background:
    Given I navigate to DuckGO Application
  @Test
  Scenario: Test case for AC1
    Given I am on the homepage
    Then I expect to see the duckduckgo logo on the page
    When I type "super" into the search box
    Then I expect to see exactly 10 suggestions in the typeahead dropdown

  @Test1
  Scenario Outline: Test case for AC6 for searching <Data>
    Given I am on the homepage
    When I go to the homepage and type Then click the magnifying glass
      |Data  |
      |<Data>|
    Then I should get 10 results on the results page
    Examples:
      | Data                     |
      | Back to the future       |
      | BMX Bandits              |
      | Rocky IV                 |
      | Short Circuit            |
      | The Terminator           |
      | Ferris Bueller's day off |


