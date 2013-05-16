Specifications
================

```
+-----------------+
| Legend          |
+-----------------+
| + Primary Key   |
| * Foreign Key   |
+-----------------+
```

Assume the following database structure
-----------------------------------------
```
                                                       +-----------------+
     +-----------------+      +-----------------+      |     Store       |
     |      Sale       |      |    Customer     |      +-----------------+
     +-----------------+      +-----------------+  +---| + Store_ID      |--+  
     | + Sale_ID       |  +---| + Customer_ID   |  |   |   Name          |  |
  +--| * Store_ID      |  |   | * Pref_Store_ID |--+   |                 |  |
  |  | * Customer_ID   |--+   |   Name          |      |                 |  |
  |  |   Total         |      |                 |      |                 |  |
  |  |                 |      |                 |      +-----------------+  |
  |  +-----------------+      +-----------------+                           |
  +-------------------------------------------------------------------------+           
```

More Assumptions
-----------------

  Assume that sale with Sale_ID = 25 has the following properties:
  
  	Sale_ID     =    25
  	Store_ID    =     1
  	Customer_ID =     1
  	Total       = 99.99

  Assume that we want to completely change the sale like this:
  
  	Sale_ID     =    25
  	Store_ID    =     5
  	Customer_ID =     6
  	Total       = 23.99

Assume the following:
  
1. The user needs a WRITE Sale ACL to update the sale (change the sale total).
2. The user needs a special ACL to decrease the sale total.
3. The user needs READ access to the old store to load the sale
4. The user needs READ access to the new store to change the store in the sale
5. The user needs WRITE access to the new customer to change the customer in the sale. However, the customer object bases its permissions on the user's privileges to the store, which means that the user needs WRITE access to the store to change the customer in the sale
6. The user needs READ access to the store to insert a new sale.
7. The user needs READ access to the customer to insert the sale.
8. A WRITE Sale ACL should be created for the user for the new sale.
9. A READ Sale ACL should be created for the "audit" role for the new sale.
10. A DELETE Sale ACL should be created for the user and for the "manager" role for the new sale.
11. When a user attempts to load a sale with a specific ID, pre-authorize based on the ID parameter.
12. When a user attempts to load a sale any other way, user needs a READ Sale ACL.
13. A user needs DELETE access to the sale to delete it.


Other specifications
----------------------
1. When a method uses @CasHmacPreAuth, we may need to force skipping of other authorization checks to speed things up.


Code Example
================

```java

@Inject SaleRestService saleRestService;

// Load a sale
Sale sale = saleRestService.loadSale(25);

// Update the sale
sale.setStoreID(5);
sale.setCustomerID(6);
sale.setTotal(23.99);
saleRestService.putSale(sale);

// Create a new sale
Sale newSale = new Sale();
sale.setStoreID(3);
sale.setCustomerID(4);
sale.setTotal(100.00);
saleRestService.putSale(sale);

// Delete a sale
saleRestService.deleteSale(25);

public static class SaleRestService {

  @Inject SaleDao saleDao;

  /* Loads an existing sale */
  public Sale loadSale(Integer saleId) {
    CasHmacValidation.preAuthAcl(                                            // Fulfill Assumption #11
        READ, Sale.class, saleId
    );
    saleDao.load(saleId);
  }

  /* Adds or updates a sale */
  public putSale(Sale sale) {
    Sale saleOrig = saleDao.load(sale.saleId);
                                                 
    if (saleOrig.total > sale.total) {
      CasHmacValidation.verifyCustomAcl(                                     // Fulfill Assumption #2
        "DECREASE", Sale.class, sale
      );
    }
    saleDao.put(sale);
  }

  /*
     Deletes a sale.
  */
  public deleteSale(Integer saleId) {
    saleDao.delete(saleId);
  }

}

@CasHmacObjectAcl                                                            // Fulfill Assumption #12
@CasHmacObjectRead(accessLevel=READ, objectClass=Sale.class)                 // Fulfill Assumption #12
@CasHmacObjectUpdate(accessLevel=WRITE, objectClass=Sale.class)              // Fulfill Assumption #1
@CasHmacObjectDelete(accessLevel=DELETE, objectClass-=Sale.class)            // Fulfill Assumption #13 
public static class Sale {
  @CasHmacPKField                                                            // Fulfill Assumption #1
  @CasHmacWriteACL({
    @CasHmacWriteAclParameter(access=READ, roles={}),
    @CasHmacWriteAclParameter(access=WRITE, roles={}),                       // Fulfill Assumption #8
    @CasHmacWriteAclParameter(access=READ, roles={ "audit" }),               // Fulfill Assumption #9
    @CasHmacWriteAclParameter(access=DELETE, roles={ SELF, "manager" }),     // Fulfill Assumption #10
    @CasHmacWriteAclParameter(access="DECREASE", roles={ "manager" })        // Fulfill Assumption #2
  })                                               
  public Integer saleID;
  @CasHmacForeignFieldRead(
    accessLevel=READ,
    objectClass=Store.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #3
  @CasHmacForeignFieldUpdate(
    accessLevel=READ,
    objectClass=Store.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #4
  @CasHmacForeignFieldCreate(
    accessLevel=READ,
    objectClass=Store.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #6
  public Integer storeID;
  
  @CasHmacForeignFieldUpdate(
    accessLevel=WRITE,
    objectClass=Customer.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #5
  @CasHmacForeignFieldCreate(
    accessLevel=READ,
    objectClass=Customer.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #7
  public Integer customerID;
  public Float   total;
}

/*
   We don't need the @CasHmacObjectAcl annotation
   here, since we don't have any ACLs stored
   specifically with the Customer object
   (ACL calculated through store)
*/
public static class Customer {
  @CasHmacPKField                                                            // Fulfill Assumption #5
  public Integer customerID;
  @CasHmacForeignFieldUpdate(
    accessLevel=WRITE,
    objectClass=Store.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #5
  @CasHmacForeignFieldRead(
    accessLevel=READ,
    objectClass=Store.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #7
  public Integer preferredStoreID;
  public String  name;
}

@CasHmacObjectAcl                                                            // Fulfill Assumptions #3,4,6,7
public static class Store {
  @CasHmacPKField                                                            // Fulfill Assumptions #3,4
  public Integer storeID;
  public String name;
}

```


Scenarios Based on Assumptions:
---------------

1. The user is attempting to update a sale.  The `@CasHmacObjectUpdate` annotation tells us that the user needs a WRITE ACL for the Sale object in order to update it.  We use reflection to look for the Sale class field annotated with `@CasHmacPKField`, and then we look for a match in the database (to the user or one of the user's roles).
2. Also while the user is attempting to update the sale, we include custom code to verify that the user has a custom "DECREASE" ACL for the Sale class and object.  We use reflection, as in #1, to look for the Sale class PK field and look for a match in the database.
3. Also while the user is attempting to update the sale, the `@CasHmacForeignFieldRead` annotation on the "storeID" field tells us that we need a READ ACL for the Store object in order to read the original Sale into an object.  As in #1 and #2, we use reflection to find the PK of the Store object and look up the ACL for the store.  The `foreignEntityManager` field of the `@CasHmacForeignFieldRead` annotation field tells the annotation with which the `Provider<EntityManager>` is exposed in the server's Guice module (to support multiple entity managers for multiple data sources).
4. Next, the `@CasHmacForeignFieldUpdate` annotation tells us that we need a READ ACL for the Store object in order to update the existing Sale object.  We use reflection, like we do in 3, to find the ACL field and look of the store ACL
5. Next, the `@CasHmacForeignFieldWrite` annotation tells us that we need a WRITE ACL for the Customer object in order to update the existing Sale object.  We use reflection to look at the Customer class (using the customerID field value from the Sale object).  However, we notice that the Customer class is not annotated with the `@CasHmacObjectAcl` annotation, which means that there will not be any ACLs specific to the Customer class in the database.  But, while introspecting, we also notice that the Customer class contains a field annotated with `@CasHmacForeignFieldRead`.  This annotation tells us that we need a READ ACL on the Store object specified by the "preferredStoreID" foreign key.  So, we use reflection to inspect the Store class, find its `@CasHmacPKField` annotated primary key field, and look up the ACL for the PK value specified in the Customer class.  NOTE: We also need to look up the Customer object, which we can easily do by finding the Customer class's `@CasHmacPKField` annotated field. even though the Customer class isn't annotated with `@CasHmacObjectAcl`.
6. Here, the user is attempting to insert a new sale.  While using reflection to introspect on the fields of the Sale class, we notice the `@CasHmacForeignFieldCreate` annotation on the "storeID" field.  This tells us that we need a READ ACL for the Store object referenced by the storeID field. 
7. We also notice, while attempting to insert a sale, the `@CasHmacForeignFieldCreate` annotation on the Sale's customerID field.  So, we know that we also need a READ ACL for the Customer object referenced by the Sale's customerID field.
8. For assumptions 8, 9, and 10, we notice the multi-line `@CasHmacWriteACL` annotation that is applied to the Sale class's PK field (saleID).  The `@CasHmacWriteACL` annotation tells us that, when a new Sale object is saved, we need to create the specified ACLs.  Here, we define READ and WRITE ACLs for the current user, a READ ACL for the "audit" role, DELETE ACLs for the current user and the "manager" role, and a DECREASE custom ACL for the "manager" role.
9. See number 8, above.
10. See number 8, above.
11. Since it may be inefficient to completely load a persisted object before checking an ACL, especially when we're in a situation where an attempt to load an item without the proper ACLs is likely, we `CasHmacValidation.PreAuthAcl` helper method that allows us to specify an ACCESS level, a class, and a parameter value.  This helper method ensures that the specified ACL exists before we continue.
    NOTE: When we pre-authorize, we need to cache the ACL for the preauthorized object.  Since the object is likely to have its ACL specified otherwise, we can check the cache on subsequent ACL lookups to avoid multiple lookups for the same ACL.  We will also include a method parameter that allows us to force all other validation to be skipped. (See "Other specifications" #1.)
12. When a user attempts to load a sale in a method without the `@CasHmacPreAuth` annotation, we still need to ensure that the user has the appropriate ACL for the sale.  Since the Sale class is annotated with the `@CasHmacObjectAcl` annotation, we're aware that we need to look up ACLs for the Sale class. Then, the `@CasHmacObjectRead` annotation tells us that we need a READ ACL for the Sale class in order to read a Sale object.
13. Very similar to #12, the `@CasHmacObjectDelete` annotation tells us that we need a DELETE ACL for the Sale class in order to delete the Sale object.


Classes, Methods Needed:
===========================

Class Annotations:
------------------
```java
  @CasHmacObjectAcl                                                          // Fulfill Assumptions #3,4,6,7,12
  @CasHmacObjectRead(accessLevel=READ, objectClass=Sale.class)               // Fulfill Assumption #12
  @CasHmacObjectUpdate(accessLevel=WRITE, objectClass=Sale.class)            // Fulfill Assumption #1
  @CasHmacObjectDelete(accessLevel=DELETE, objectClass-=Sale.class)          // Fulfill Assumption #13 

```

  These class annotations are considered when performing CRUD operations.  We'll need to create an interface that gets   implemented in our entity classes, and we'll need to ensure that the appropriate helper methods are called to perform the ACL checks specified by these annotations.

PK Field Annotations:
---------------------
```java
  @CasHmacPKField                                                            // Fulfill Assumption #1,3,4,5
  @CasHmacWriteACL({
    @CasHmacWriteAclParameter(access=READ, roles={}),
    @CasHmacWriteAclParameter(access=WRITE, roles={}),                       // Fulfill Assumption #8
    @CasHmacWriteAclParameter(access=READ, roles={ "audit" }),               // Fulfill Assumption #9
    @CasHmacWriteAclParameter(access=DELETE, roles={ SELF, "manager" }),     // Fulfill Assumption #10
    @CasHmacWriteAclParameter(access="DECREASE", roles={ "manager" })        // Fulfill Assumption #2
  })                                               
```

  These field annotations are considered, as well, when  performing CRUD operations, and will be used by the helper methods called when we implement the interface referenced above (under Class Annotations).

Foreign Key Field Annotations:
------------------------------
```java

  @CasHmacForeignFieldCreate(
    accessLevel=READ,
    objectClass=Store.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #6,7
  @CasHmacForeignFieldRead(
    accessLevel=READ,
    objectClass=Store.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #3,7
  @CasHmacForeignFieldUpdate(
    accessLevel=READ,
    objectClass=Store.class,
    foreignEntityManager=MyDataSource.class)                                 // Fulfill Assumption #4,5
  @CasHmacForeignFieldDelete(
    accessLevel=READ,
    objectClass=Store.class,
    foreignEntityManager=MyDataSource.class)                                 //
```

  These field annotations, too, will be considered
  during CRUD operations.


Helper Functions:
------------------
```java
  CasHmacValidation.preAuthAcl(READ, Sale.class, sale);                      // Fulfill Assumption #11
  CasHmacValidation.verifyAcl("DECREASE", Sale.class, sale);                 // Fulfill Assumption #2
  CasHmacValidation.addAcl("DECREASE", Sale.class, sale);                    // 
  CasHmacValidation.deleteAcl("DECREASE", Sale.class, sale);                 // 
```
