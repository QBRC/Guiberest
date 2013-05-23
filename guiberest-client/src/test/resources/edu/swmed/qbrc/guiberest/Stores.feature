Feature: Stores

	Background:
    	Given I'm ready to go
    
    Scenario: View Stores
        When I request store data for the following ids as user thomas:
    	    | 12 |
        Then I see the following store results:
	        | 12 | 14th Floor Cafeteria |

	Scenario: Insert Stores
        When I insert the following store data as user thomas:
	        | 6 | Test Store 6 | 
	        | 7 | Test Store 7 |
        When I request store data for the following ids as user thomas:
        	| 6 |
        	| 7 |
        Then I see the following store results:
            | 6 | Test Store 6 | 
            | 7 | Test Store 7 |

	Scenario: Update Stores
	
        When I update the following store data as user thomas:
	        | 6 | Test Store 66aa | 
            | 7 | Test Store 77bb | 
        When I request store data for the following ids as user thomas:
            | 6 |
            | 7 |
        Then I see the following store results:
            | 6 | Test Store 66aa | 
            | 7 | Test Store 77bb | 

    Scenario: Delete Stores

		When I delete the following store data as user thomas:
            | 6 |
            | 7 |
        When I request store data for the following ids as user thomas:
            | 6 |
            | 7 |
        Then I see no more than 0 store results
    
