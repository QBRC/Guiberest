Feature: Create New Sale ACLS

    In order to verify that ACL validation is working properly, we're following the example
    scripted at https://github.com/jonyoder/ACL-Brainstorming/blob/master/Example.md to create,
    read, update, and delete sale information as various users. 

    Here, we're testing while attempting to insert, modify, and delete new sales that were
    not included in our import.sql.

    Background:
        Given I'm ready to go
    
    Scenario: Insert Sales without UPDATE ACL to Sale's Store (although user has READ access to Sale's Store)
        When I insert the following sale data as user sean:
            | 51 | 12 | 23 |   38.92 |
        Then I receive a NoAclException
            
