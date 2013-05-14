Feature: Sale ACLS

    In order to verify that ACL validation is working properly, we're following the example
    scripted at https://github.com/jonyoder/ACL-Brainstorming/blob/master/Example.md to create,
    read, update, and delete sale information as various users. 

    Here, we're testing while attempting to insert, modify, and delete new sales that were
    not included in our import.sql.

    Background:
        Given I'm ready to go
    
    Scenario: Insert Sales and Check ACLs
        When I insert the following sales:
            | 21 | 50 | 12 |   38.92 |
        Then if I check ACLs for sale 50 I see the following ACLs:
            | read     | thomas | NULL    |
            | update   | thomas | NULL    |
            | read     | NULL   | audit   |
            | delete   | thomas | NULL    |
            | delete   | NULL   | manager |
            | DECREASE | NULL   | manager |
            
    Scenario: Insert Sale without READ ACL to Sales's Store
        When I insert the following sales:
            | 23 | 51 | 13 |   38.92 |
        Then I receive a NoAclException

    Scenario: Insert Sale without READ ACL to Sale's Customer's Store
        When I insert the following sales:
            | 24 | 51 | 12 |   38.92 |
        Then I receive a NoAclException
            
    Scenario: Retrieve a News Sale as the Sale Owner
        When I request sales for the following sale ids:
            | 50 |
        Then I see the following full sale results:
            | 21 | 50 | 12 |   38.92 |
            
    Scenario: Retrieve a New Sale as another User without an ACL
        When I request sales as user roger for the following sale ids:
            | 50 |
        Then I receive a NoAclException
        
    Scenario: Retrieve a New Sale as an IRS auditer:
        When I request sales as user irsauditer for the following sale ids:
            | 50 |
        Then I see the following full sale results:
            | 21 | 50 | 12 |   38.92 |

    Scenario: Increase Sale Total as another User without an ACL
        When I update the following sales:
            | 21 | 50 | 12 |   40.95 |
        Then I receive a NoAclException

    Scenario: Increase Sale Total as the Sale Owner:
        When I update the following sales:
            | 21 | 50 | 12 |   40.95 |
        And I request sales for the following sale ids:
            | 50 |
        Then I see the following full sale results:
            | 21 | 50 | 12 |   40.95 |

    Scenario: Decrease Sale Total as a non manager user:
        When I update the following sales:
            | 21 | 50 | 12 |   34.95 |
        Then I receive a NoAclException

    Scenario: Decrease Sale Total as a valid manager user:
        When I update the following sales as the sean user:
            | 21 | 50 | 12 |   34.95 |
        And I request sales for the following sale ids:
            | 50 |
        Then I see the following full sale results:
            | 21 | 50 | 12 |   34.95 |

    Scenario: Delete Sale as a non-owner, non-manager:
        When I delete the following sales as the roger user:
            | 50 |
        Then I receive a NoAclException

    Scenario: Delete Sale as a Manager and Verify that no ACLs remain for Sale:
        When I delete the following sales as the sean user:
            | 50 |
        And I request sales for the following sale ids:
            | 50 |
        Then I see no more than 0 sale results
        And if I check ACLs for sale 50 I see no more than 0 ACL results.
