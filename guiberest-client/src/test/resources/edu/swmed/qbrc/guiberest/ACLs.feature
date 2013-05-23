Feature: Test ACL Helper Methods

    In order to verify that ACL validation is working properly, we're following the example
    scripted at https://github.com/jonyoder/ACL-Brainstorming/blob/master/Example.md to create,
    read, update, and delete sale information as various users. 

    Here, we're testing while attempting some of CasHmac's ACL helper methods.

    Background:
        Given I'm ready to go
    
    Scenario: Insert ACLs
        When I insert the following ACL data as user thomas:
            | SELF               | read     | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | SELF,manager,audit | update   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | manager            | delete   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | audit              | DECREASE | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
        And I request ACL data for the 500 id and the sale class as user thomas
        Then I see the following ACL results:
            | <any> | thomas | NULL | read     | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | <any> | thomas | NULL | update   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | <any> | NULL   |    6 | update   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | <any> | NULL   |    5 | update   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | <any> | NULL   |    6 | delete   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | <any> | NULL   |    5 | DECREASE | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |

    Scenario: Delete ACLs
        When I delete the following ACL data as user thomas:            
            | SELF               | read     | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | SELF,manager,audit | update   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | manager            | delete   | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
            | audit              | DECREASE | edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale | 500 |
        And I request ACL data for the 500 id and the sale class as user thomas
        Then I see no more than 0 ACL results
        