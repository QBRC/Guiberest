Feature: Stores

	Background:
    	Given I'm ready to go
    
    Scenario: View Stores
        When I request stores with the following store ids:
    	    | 12 |
        Then I see the following full store results:
	        | 12 | 14th Floor Cafeteria |

        When I request all stores
        Then I see at least 2 store results

	Scenario: Insert Stores
        When I insert the following stores:
	        | 6 | Test Store 6 | 
	        | 7 | Test Store 7 |
        And I request stores with the following store ids:
        	| 6 |
        	| 7 |
        Then I see the following full store results:
            | 6 | Test Store 6 | 
            | 7 | Test Store 7 |

	Scenario: Update Stores
	
        When I update the following stores:
	        | 6 | Test Store 66aa | 
            | 7 | Test Store 77bb | 
        And I request stores with the following store ids:
            | 6 |
            | 7 |
        Then I see the following full store results:
            | 6 | Test Store 66aa | 
            | 7 | Test Store 77bb | 

	Scenario: Delete Stores

		When I delete the following stores:
            | 6 |
            | 7 |
        And I request stores with the following store ids:
            | 6 |
            | 7 |
        Then I see no more than 0 store results
	