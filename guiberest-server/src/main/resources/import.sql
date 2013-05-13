insert into roles (id, username, role) values (1, 'thomas', 'admin');
insert into roles (id, username, role) values (2, 'thomas', 'manager');
insert into roles (id, username, role) values (3, 'thomas', 'Guiberest-Reader');
insert into roles (id, username, role) values (4, 'thomas', 'Guiberest-Writer');
insert into roles (id, username, role) values (5, 'roger', 'limited');
insert into roles (id, username, role) values (6, 'roger', 'audit');
insert into users (id, password, secret) values ('thomas', 'password', '123456789');
insert into users (id, password, secret) values ('roger', 'password', '987654321');

insert into acl (id, username, role_id, access, class, pk) values (1, 'thomas', null, 'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '1');
insert into acl (id, username, role_id, access, class, pk) values (2, 'thomas', null, 'read',  'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '5');
insert into acl (id, username, role_id, access, class, pk) values (3, 'thomas', null, 'read', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '2');
insert into acl (id, username, role_id, access, class, pk) values (4, 'thomas', null, 'read', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '3');
insert into acl (id, username, role_id, access, class, pk) values (5, 'thomas', null, 'read', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '4');

insert into store (store_id, name) values (1, 'QBRC Market Grill');
insert into store (store_id, name) values (5, '14th Floor Cafeteria');
insert into customer (customer_id, preferred_store_id, name) values (1, 1, 'Maurice Mango');
insert into customer (customer_id, preferred_store_id, name) values (2, 1, 'Randall Rhodes');
insert into customer (customer_id, preferred_store_id, name) values (6, 5, 'Chloe Stanley');
insert into sale (sale_id, store_id, customer_id, total) values (25, 1, 1,  99.99);
insert into sale (sale_id, store_id, customer_id, total) values (2,  1, 2,  59.65);
insert into sale (sale_id, store_id, customer_id, total) values (3,  5, 6,   8.95);
insert into sale (sale_id, store_id, customer_id, total) values (4,  5, 6, 128.35);

