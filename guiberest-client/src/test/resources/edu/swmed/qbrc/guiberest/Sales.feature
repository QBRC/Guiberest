Feature: Sales

    Background:
        Given I'm ready to go

    Scenario: View Sales
        When I request sale data for the following ids as user thomas:
            | 32 |
            | 33 |
        Then I see the following sale results:
            | 32 | 11 | 22 |  59.65 |
            | 33 | 12 | 23 |   8.95 |

        When I request sale data for the 25 id as user thomas
        Then I see the following sale results:
            | 37 | 11 | 25 |   69.65 |
            | 38 | 12 | 25 |    9.95 |

    Scenario: Insert Sales
        When I insert the following sale data as user thomas:
            | 50 | 12 | 21 |   38.92 |
            | 51 | 11 | 23 |  123.57 |
        When I request sale data for the following ids as user thomas:
            | 50 |
            | 51 |
        Then I see the following sale results:
            | 50 | 12 | 21 |   38.92 |
            | 51 | 11 | 23 |  123.57 |

    Scenario: Update Sales
    
        When I update the following sale data as user thomas:
            | 50 | 11 | 23 |   78.92 |
            | 51 | 12 | 21 |  125.57 |
        When I request sale data for the following ids as user thomas:
            | 50 |
            | 51 |
        Then I see the following sale results:
            | 50 | 11 | 23 |   78.92 |
            | 51 | 12 | 21 |  125.57 |

    Scenario: Delete Sales

        When I delete the following sale data as user thomas:
            | 50 |
            | 51 |
        When I request sale data for the following ids as user thomas:
            | 50 |
            | 51 |
        Then I see no more than 0 sale results
