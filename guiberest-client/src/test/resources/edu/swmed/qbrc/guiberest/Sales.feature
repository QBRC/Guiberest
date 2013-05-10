Feature: Sales

    Background:
        Given I'm ready to go
    
    Scenario: View Sales
        When I request sales for the following sale ids:
            | 2 |
            | 3 |
        Then I see the following full sale results:
            | 2 | 2 | 1 |  59.65 |
            | 6 | 3 | 5 |   8.95 |

        When I request sales for the 6 customer
        Then I see the following full sale results:
            | 6 | 3 | 5 |    8.95 |
            | 6 | 4 | 5 |  128.35 |

    Scenario: Insert Sales
        When I insert the following sales:
            | 6 | 50 | 5 |   38.92 |
            | 2 | 51 | 1 |  123.57 |
        When I request sales for the following sale ids:
            | 50 |
            | 51 |
        Then I see the following full sale results:
            | 6 | 50 | 5 |   38.92 |
            | 2 | 51 | 1 |  123.57 |

    Scenario: Update Sales
    
        When I update the following sales:
            | 2 | 50 | 1 |   78.92 |
            | 6 | 51 | 5 |  121.57 |
        When I request sales for the following sale ids:
            | 50 |
            | 51 |
        Then I see the following full sale results:
            | 2 | 50 | 1 |   78.92 |
            | 6 | 51 | 5 |  121.57 |

    Scenario: Delete Sales

        When I delete the following sales:
            | 50 |
            | 51 |
        When I request sales for the following sale ids:
            | 50 |
            | 51 |
        Then I see no more than 0 sale results
