Feature: Create New Sale ACLS

    In order to verify that ACL validation is working properly, we're following the example
    scripted at https://github.com/jonyoder/ACL-Brainstorming/blob/master/Example.md to create,
    read, update, and delete sale information as various users. 

    Here, we're testing while attempting to insert, modify, and delete new sales that were
    not included in our import.sql.

    Background:
        Given I'm ready to go
    
    Scenario: Insert Sales, retrieve as owner, and Check ACLs
        When I insert the following sales:
            | 21 | 50 | 12 |   38.92 |
            
    Scenario: Retrieve a New Sale as another User without an ACL
        When I request sales as user roger for the following sale ids I receive a NoAclException:
            | 50 |
