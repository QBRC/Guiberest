Feature: Method Permissions

    Here, we're testing the RESTful methods annotated with @RolesRequired to be sure
    that users are restricted to the roles required.

    Background:
        Given I'm ready to go
    
    Scenario: View Sales
        When I request sales for the following sale ids:
            | 32 |
            | 33 |
        Then I see the following full sale results:
            | 22 | 32 | 11 |  59.65 |
            | 23 | 33 | 12 |   8.95 |

    Scenario: View Sales as a User without RESTful Method Permissions
        When I request sales for the following sale ids as a guest I'm denied:
            | 32 |
            | 33 |

