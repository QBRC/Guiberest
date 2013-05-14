Feature: Customer

	Background:
    	Given I'm ready to go
    
    Scenario: View Customers
        When I request customers with the following customer ids:
            | 21 |
    	    | 23 |
        Then I see the following full customer results:
	        | 21 | 11 | Maurice Mango |
            | 23 | 12 | Chloe Stanley |

	Scenario: Insert Customers
        When I insert the following customers:
            | 8 | 12 | Test Customer 8 |
            | 9 | 11 | Test Customer 9 |
        And I request customers with the following customer ids:
            | 8 |
            | 9 |
        Then I see the following full customer results:
            | 8 | 12 | Test Customer 8 |
            | 9 | 11 | Test Customer 9 |

	Scenario: Update Customers
	
        When I update the following customers:
            | 8 | 12 | Test Customer 8b |
            | 9 | 11 | Test Customer 9a |
        And I request customers with the following customer ids:
            | 8 |
            | 9 |
        Then I see the following full customer results:
            | 8 | 12 | Test Customer 8b |
            | 9 | 11 | Test Customer 9a |

	Scenario: Delete Customers

		When I delete the following customers:
            | 8 |
            | 9 |
        And I request customers with the following customer ids:
            | 8 |
            | 9 |
        Then I see no more than 0 customer results
	