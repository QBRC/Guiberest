Feature: Users

	Background:
    	Given I'm ready to go
    
    Scenario: View Users
        When I request users with the following user ids:
          | thomas |
        Then I see the following full user results:
          | thomas | password | 123456789 |

        When I request all users
        Then I see at least 2 user results
