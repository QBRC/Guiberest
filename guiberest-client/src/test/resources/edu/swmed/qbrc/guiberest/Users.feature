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

	Scenario: Insert Users
        When I insert the following users:
	        | tbcrowe | password123 | 987654321 | 
	        | rlwhite | password456 | 987654320 |
        And I request users with the following user ids:
        	| tbcrowe |
        	| rlwhite |
        Then I see the following full user results:
			| tbcrowe | password123 | 987654321 | 
			| rlwhite | password456 | 987654320 |

	Scenario: Update Users
	
        When I update the following users:
	        | tbcrowe | password123456 | 987654321bb | 
	        | rlwhite | password456789 | 987654320dd |
        And I request users with the following user ids:
        	| tbcrowe |
        	| rlwhite |
        Then I see the following full user results:
	        | tbcrowe | password123456 | 987654321bb | 
	        | rlwhite | password456789 | 987654320dd |

	Scenario: Delete Users

		When I delete the following users:
        	| tbcrowe |
        	| rlwhite |
        And I request users with the following user ids:
        	| tbcrowe |
        	| rlwhite |
        Then I see no more than 0 user results
	