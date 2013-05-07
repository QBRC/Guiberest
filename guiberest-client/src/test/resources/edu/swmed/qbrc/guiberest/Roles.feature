Feature: Roles

    Background:
        Given I'm ready to go
    
    Scenario: View Roles
        When I request roles for the following users:
	        | thomas |
        Then I see the following full role results:
			| 1 | thomas | admin            |
			| 2 | thomas | manager          |
			| 3 | thomas | Guiberest-Reader |
			| 4 | thomas | Guiberest-Writer |

	Scenario: Insert Roles
        When I insert the following roles:
			| 6 | thomas | testrole1 |
			| 7 | thomas | testrole2 |
			| 8 | thomas | testrole3 |
			| 9 | thomas | testrole4 |
        When I request roles for the following users:
	        | thomas |
        Then I see the following full role results:
			| 1 | thomas | admin            |
			| 2 | thomas | manager          |
			| 3 | thomas | Guiberest-Reader |
			| 4 | thomas | Guiberest-Writer |
			| 6 | thomas | testrole1        |
			| 7 | thomas | testrole2        |
			| 8 | thomas | testrole3        |
			| 9 | thomas | testrole4        |

	Scenario: Update Roles
	
        When I update the following roles:
			| 6 | thomas | abtestrole1 |
			| 7 | thomas | cdtestrole2 |
			| 8 | thomas | detestrole3 |
			| 9 | thomas | fgtestrole4 |
        When I request roles for the following users:
	        | thomas |
        Then I see the following full role results:
			| 1 | thomas | admin            |
			| 2 | thomas | manager          |
			| 3 | thomas | Guiberest-Reader |
			| 4 | thomas | Guiberest-Writer |
			| 6 | thomas | abtestrole1      |
			| 7 | thomas | cdtestrole2      |
			| 8 | thomas | detestrole3      |
			| 9 | thomas | fgtestrole4      |

	Scenario: Delete Roles

		When I delete the following roles:
        	| 6 |
        	| 7 |
        	| 8 |
        	| 9 |
        When I request roles for the following users:
	        | thomas |
        Then I see no more than 4 role results
