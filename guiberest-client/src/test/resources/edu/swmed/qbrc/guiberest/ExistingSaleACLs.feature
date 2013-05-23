Feature: Existing Sale ACLS

    In order to verify that ACL validation is working properly, we're following the example
    scripted at https://github.com/jonyoder/ACL-Brainstorming/blob/master/Example.md to create,
    read, update, and delete sale information as various users.
    
    Here, we're testing while attempting to retrieve and modify existing sales that were
    imported in import.sql 

    Background:
        Given I'm ready to go
    
    Scenario: Verify that we can correctly retrieve an existing sale.
        When I request sale data for the following ids as user thomas:
            | 36 |
        Then I see the following sale results:
            | 36 | 12 | 23 |   14.66 |
    
    Scenario: Retrieve an existing Sale as a User with a Sale ACL but No READ Permission to the Sale's Store
        When I request sale data for the following ids as user thomas:
            | 35 |
        Then I receive a NoAclException
        
    Scenario: Retrieve an existing Sale as a User with a Sale ACL but No READ Permission to the Sale's Store (with Pre-Authorization)
        When I request sales with preauthorization for the following sale ids as user thomas:
            | 35 |
        Then I see the following sale results:
            | 35 | 13 | 23 |   146.36 |

    Scenario: Move an existing Sale to a different Store without a READ ACL for the New Store
        When I update the following sale data as user thomas:
            | 36 | 13 | 23 |   14.66 |
        Then I receive a NoAclException

    Scenario: Move an existing Sale to a different Store with a valid READ ACL for the New Store
        When I update the following sale data as user thomas:
            | 36 | 11 | 23 |   14.66 |
        And I request sale data for the following ids as user thomas:
            | 36 |
        Then I see the following sale results:
            | 36 | 11 | 23 |   14.66 |

    Scenario: Move a Sale to a different Customer without a WRITE ACL for the New Customer's Store
        When I update the following sale data as user thomas:
            | 36 | 11 | 24 |   14.66 |
        Then I receive a NoAclException

    Scenario: Move a Sale to a different Customer with a valid WRITE ACL for the New Customer's Store
        When I update the following sale data as user thomas:
            | 36 | 11 | 22 |   14.66 |
        And I request sale data for the following ids as user thomas:
            | 36 |
        Then I see the following sale results:
            | 36 | 11 | 22 |   14.66 |

    Scenario: Reset Sales to previous values
        When I update the following sale data as user thomas:
            | 36 | 12 | 23 |   14.66 |
        When I request sale data for the following ids as user thomas:
            | 36 |
        Then I see the following sale results:
            | 36 | 12 | 23 |   14.66 |
