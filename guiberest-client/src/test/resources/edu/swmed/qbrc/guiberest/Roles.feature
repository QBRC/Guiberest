Feature: Roles

    Background:
        Given I'm ready to go
    
    Scenario: View Roles
        When I request roles for the following users:
          | thomas |
        Then I see the following full role results:
          | 1 | thomas | admin  |
          | 2 | thomas | manager |
