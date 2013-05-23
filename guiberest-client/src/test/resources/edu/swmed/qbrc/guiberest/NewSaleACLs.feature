Feature: Create New Sale ACLS

    In order to verify that ACL validation is working properly, we're following the example
    scripted at https://github.com/jonyoder/ACL-Brainstorming/blob/master/Example.md to create,
    read, update, and delete sale information as various users. 

    Here, we're testing while attempting to insert, modify, and delete new sales that were
    not included in our import.sql.

    Background:
        Given I'm ready to go
    
    Scenario: Insert Sales, retrieve as owner, and Check ACLs
        When I insert the following sale data as user thomas:
            | 50 | 12 | 21 |   38.92 |
        When I request sale data for the following ids as user thomas:
            | 50 |
        Then I see the following sale results:
            | 50 | 12 | 21 |   38.92 |
        And I request ACL data for the 50 id and the sale class as user thomas
        Then I see the following ACL results:
            | <any> | thomas | NULL | read     | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 50 |
            | <any> | NULL   |    5 | read     | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 50 |
            | <any> | NULL   |    6 | read     | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 50 |
            | <any> | thomas | NULL | update   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 50 |
            | <any> | NULL   |    6 | update   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 50 |
            | <any> | thomas | NULL | delete   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 50 |
            | <any> | NULL   |    6 | delete   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 50 |
            | <any> | NULL   |    6 | DECREASE | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 50 |
            
    Scenario: Insert Sales without READ ACL to Sales's Store
        When I insert the following sale data as user thomas:
            | 51 | 13 | 23 |   38.92 |
        Then I receive a NoAclException
            
    Scenario: Insert Sale without READ ACL to Sale's Customer's Store
        When I insert the following sale data as user thomas:
            | 51 | 12 | 24 |   38.92 |
        Then I receive a NoAclException

    Scenario: Retrieve a New Sale as another User without an ACL
        When I request sale data for the following ids as user roger:
            | 50 |
        Then I receive a NoAclException

    Scenario: Retrieve a New Sale as an IRS auditer:
        When I request sale data for the following ids as user irsauditer:
            | 50 |
        Then I see the following sale results:
            | 50 | 12 | 21 |   38.92 |

    Scenario: Increase Sale Total as another User (Roger) without an ACL
        When I update the following sale data as user roger:
            | 50 | 12 | 21 |   40.95 |
        Then I receive a NoAclException
 
    Scenario: Increase Sale Total as the Sale Owner:
        When I update the following sale data as user thomas:
            | 50 | 12 | 21 |   40.95 |
        When I request sale data for the following ids as user thomas:
            | 50 |
        Then I see the following sale results:
            | 50 | 12 | 21 |   40.95 |
 
    Scenario: Decrease Sale Total as a non manager user:
        When I update the following sale data as user thomas:
            | 50 | 12 | 21 |   34.95 |
        Then I receive a NoAclException

    Scenario: Decrease Sale Total as a valid manager user:
        When I update the following sale data as user sean:
            | 50 | 12 | 21 |   34.95 |
        When I request sale data for the following ids as user thomas:
            | 50 |
        Then I see the following sale results:
            | 50 | 12 | 21 |   34.95 |
                        
    Scenario: Delete Sale as a non-owner, non-manager:
        When I delete the following sale data as user roger:
            | 50 |
        Then I receive a NoAclException
            
    Scenario: Delete Sale as a Manager and Verify that no ACLs remain for Sale:
        When I delete the following sale data as user sean:
            | 50 |
        When I request sale data for the following ids as user thomas:
            | 50 |
        And I request ACL data for the 50 id and the sale class as user thomas
        Then I see no more than 0 ACL results
 