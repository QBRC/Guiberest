Feature: Test ACL Helper Methods

    In order to verify that ACL validation is working properly, we're following the example
    scripted at https://github.com/jonyoder/ACL-Brainstorming/blob/master/Example.md to create,
    read, update, and delete sale information as various users. 

    Here, we're testing while attempting some of CasHmac's ACL helper methods.

    Background:
        Given I'm ready to go
    
    Scenario: Insert ACLs
        When I insert the following sale acls:
            | read     | SELF               | 500 |
            | update   | SELF,manager,audit | 500 |
            | delete   | manager            | 500 |
            | DECREASE | audit              | 500 |
        And if I check ACLs for sale 500 I see the following ACLs:
            | read     | thomas | NULL |
            | update   | thomas | NULL |
            | update   | NULL   |    6 |
            | update   | NULL   |    5 |
            | delete   | NULL   |    6 |
            | DECREASE | NULL   |    5 |

    Scenario: Delete ACLs
        When I delete the following sale acls:            
            | read     | SELF               | 500 |
            | update   | SELF,manager,audit | 500 |
            | delete   | manager            | 500 |
            | DECREASE | audit              | 500 |
        Then if I check ACLs for sale 500 I see no more than 0 ACL results.
