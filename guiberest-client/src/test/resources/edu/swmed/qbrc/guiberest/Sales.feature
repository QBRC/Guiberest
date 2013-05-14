Feature: Sales

    Background:
        Given I'm ready to go
    
    Scenario: View Sales
        When I request sales for the following sale ids:
            | 32 |
            | 33 |
        Then I see the following full sale results:
            | 22 | 32 | 11 |  59.65 |
            | 23 | 33 | 12 |   8.95 |

        When I request sales for the 23 customer
        Then I see the following full sale results:
            | 23 | 33 | 12 |    8.95 |
            | 23 | 34 | 12 |  128.35 |


    Scenario: Insert Sales
        When I insert the following sales:
            | 21 | 50 | 12 |   38.92 |
            | 23 | 51 | 11 |  123.57 |
        When I request sales for the following sale ids:
            | 50 |
            | 51 |
        Then I see the following full sale results:
            | 21 | 50 | 12 |   38.92 |
            | 23 | 51 | 11 |  123.57 |

    Scenario: Update Sales
    
        When I update the following sales:
            | 23 | 50 | 11 |   78.92 |
            | 21 | 51 | 12 |  121.57 |
        When I request sales for the following sale ids:
            | 50 |
            | 51 |
        Then I see the following full sale results:
            | 23 | 50 | 11 |   78.92 |
            | 21 | 51 | 12 |  121.57 |

    Scenario: Delete Sales

        When I delete the following sales:
            | 50 |
            | 51 |
        When I request sales for the following sale ids:
            | 50 |
            | 51 |
        Then I see no more than 0 sale results
