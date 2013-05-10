Feature: Customer

	Background:
    	Given I'm ready to go
    
    Scenario: View Customers
        When I request customers with the following customer ids:
            | 1 |
    	    | 6 |
        Then I see the following full customer results:
	        | 1 | 1 | Maurice Mango |
            | 6 | 5 | Chloe Stanley |

        When I request all customers
        Then I see at least 3 customer results

	Scenario: Insert Customers
        When I insert the following customers:
            | 8 | 5 | Test Customer 8 |
            | 9 | 1 | Test Customer 9 |
        And I request customers with the following customer ids:
            | 8 |
            | 9 |
        Then I see the following full customer results:
            | 8 | 5 | Test Customer 8 |
            | 9 | 1 | Test Customer 9 |

	Scenario: Update Customers
	
        When I update the following customers:
            | 8 | 1 | Test Customer 8b |
            | 9 | 5 | Test Customer 9a |
        And I request customers with the following customer ids:
            | 8 |
            | 9 |
        Then I see the following full customer results:
            | 8 | 1 | Test Customer 8b |
            | 9 | 5 | Test Customer 9a |

	Scenario: Delete Customers

		When I delete the following customers:
            | 8 |
            | 9 |
        And I request customers with the following customer ids:
            | 8 |
            | 9 |
        Then I see no more than 0 customer results
	