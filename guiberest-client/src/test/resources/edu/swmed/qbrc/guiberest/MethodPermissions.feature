Feature: Method Permissions

    Here, we're testing the RESTful methods annotated with @RolesRequired to be sure
    that users are restricted to the roles required.

    Background:
        Given I'm ready to go
    
    Scenario: View Sales
        When I request sale data for the following ids as user thomas:
            | 32 |
            | 33 |
        Then I see the following sale results:
            | 32 | 11 | 22 |  59.65 |
            | 33 | 12 | 23 |   8.95 |

    Scenario: View Sales as a User without RESTful Method Permissions
        When I request sale data for the following ids as user guest:
            | 32 |
            | 33 |
        Then I receive a NoAclException

