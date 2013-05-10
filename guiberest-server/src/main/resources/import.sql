/* 
 * Sample Users and Roles (for CasHmac library - these would not usually
 * be included in the Web Service app)
 */
insert into roles (id, username, role) values (1, 'thomas', 'admin');
insert into roles (id, username, role) values (2, 'thomas', 'manager');
insert into roles (id, username, role) values (3, 'thomas', 'Guiberest-Reader');
insert into roles (id, username, role) values (4, 'thomas', 'Guiberest-Writer');
insert into roles (id, username, role) values (5, 'roger', 'limited');
insert into roles (id, username, role) values (6, 'roger', 'audit');
insert into users (id, password, secret) values ('thomas', 'password', '123456789');
insert into users (id, password, secret) values ('roger', 'password', '987654321');

/* 
 * Initial ACLs (for CasHmac library - these would not usually
 * be included in the Web Service app)
 */
insert into acl (id, username, role_id, access, class, pk) values (1, 'thomas', null, 'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '1');
insert into acl (id, username, role_id, access, class, pk) values (2, 'thomas', null, 'read',  'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '5');
insert into acl (id, username, role_id, access, class, pk) values (3, 'thomas', null, 'read', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '2');
insert into acl (id, username, role_id, access, class, pk) values (4, 'thomas', null, 'read', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '3');
insert into acl (id, username, role_id, access, class, pk) values (5, 'thomas', null, 'read', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '4');


/* 
 * The actual data for the Web service.  This would normally be included
 * in a Web service.  A good best-practice is to include a sample dataset
 * here that gets recreated by Hibernate each time the application starts
 * in dev mode.  This will allow you to write integration tests with a 
 * static data set that won't change and cause your tests to fail.  Simply
 * use a separate Maven profile for staging and production to access a
 * permanent data store when deploying the application.
 */
insert into store (store_id, name) values (1, 'QBRC Market Grill');
insert into store (store_id, name) values (5, '14th Floor Cafeteria');
insert into customer (customer_id, preferred_store_id, name) values (1, 1, 'Maurice Mango');
insert into customer (customer_id, preferred_store_id, name) values (2, 1, 'Randall Rhodes');
insert into customer (customer_id, preferred_store_id, name) values (6, 5, 'Chloe Stanley');
insert into sale (sale_id, store_id, customer_id, total) values (25, 1, 1,  99.99);
insert into sale (sale_id, store_id, customer_id, total) values (2,  1, 2,  59.65);
insert into sale (sale_id, store_id, customer_id, total) values (3,  5, 6,   8.95);
insert into sale (sale_id, store_id, customer_id, total) values (4,  5, 6, 128.35);

