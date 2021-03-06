insert into users (id, password, secret) values ('thomas-test', 'password', '123456789');
insert into users (id, password, secret) values ('roger-test', 'password', '987654321');
insert into users (id, password, secret) values ('irsauditer-test', 'password', '2145551212');
insert into users (id, password, secret) values ('sean-test', 'password', '3015551212');
insert into users (id, password, secret) values ('cook-test', 'password', '4125551212');
insert into users (id, password, secret) values ('guest-test', 'password', '9125551212');

insert into roles (id, role) values (1, 'admin');
insert into roles (id, role) values (2, 'Guiberest-Reader');
insert into roles (id, role) values (3, 'Guiberest-Writer');
insert into roles (id, role) values (4, 'limited');
insert into roles (id, role) values (5, 'audit');
insert into roles (id, role) values (6, 'manager');
insert into roles (id, role) values (7, 'decreaser');

insert into roleusers (roleid, username) values (1, 'thomas-test');
insert into roleusers (roleid, username) values (2, 'thomas-test');
insert into roleusers (roleid, username) values (3, 'thomas-test');
insert into roleusers (roleid, username) values (4, 'roger-test');
insert into roleusers (roleid, username) values (2, 'roger-test');
insert into roleusers (roleid, username) values (3, 'roger-test');
insert into roleusers (roleid, username) values (5, 'irsauditer-test');
insert into roleusers (roleid, username) values (2, 'irsauditer-test');
insert into roleusers (roleid, username) values (6, 'sean-test');
insert into roleusers (roleid, username) values (3, 'sean-test');
insert into roleusers (roleid, username) values (4, 'guest-test');
insert into roleusers (roleid, username) values (7, 'cook-test');
insert into roleusers (roleid, username) values (3, 'cook-test');

insert into acl (id, username, role_id, access, class, pk) values (1,  'thomas-test',  null, 'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (2,  'thomas-test',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (3,  'thomas-test',  null, 'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (4,  'thomas-test',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (5,  null,           5,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (6,  null,           6,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (7,  null,           6,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (8,  'thomas-test',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '32');
insert into acl (id, username, role_id, access, class, pk) values (9,  'thomas-test',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '33');
insert into acl (id, username, role_id, access, class, pk) values (10, 'thomas-test',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '34');
insert into acl (id, username, role_id, access, class, pk) values (11, 'thomas-test',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '35');
insert into acl (id, username, role_id, access, class, pk) values (12, 'thomas-test',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '36');
insert into acl (id, username, role_id, access, class, pk) values (13, 'thomas-test',  null, 'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '36');
insert into acl (id, username, role_id, access, class, pk) values (14, 'thomas-test',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '37');
insert into acl (id, username, role_id, access, class, pk) values (15, 'thomas-test',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '38');
insert into acl (id, username, role_id, access, class, pk) values (16,  null,          7,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (17,  null,          7,    'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (18,  null,          7,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (19,  null,          7,    'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');

insert into store (store_id, name) values (11, 'QBRC Market Grill');
insert into store (store_id, name) values (12, '14th Floor Cafeteria');
insert into store (store_id, name) values (13, 'North Campus Commons');

insert into customer (customer_id, preferred_store_id, name) values (21, 11, 'Maurice Mango');
insert into customer (customer_id, preferred_store_id, name) values (22, 11, 'Randall Rhodes');
insert into customer (customer_id, preferred_store_id, name) values (23, 12, 'Chloe Stanley');
insert into customer (customer_id, preferred_store_id, name) values (24, 13, 'Morgan Mosley');
insert into customer (customer_id, preferred_store_id, name) values (25, 12, 'Jon Miller');

insert into sale (sale_id, store_id, customer_id, total) values (31, 11, 21,  99.99);
insert into sale (sale_id, store_id, customer_id, total) values (32, 11, 22,  59.65);
insert into sale (sale_id, store_id, customer_id, total) values (33, 12, 23,   8.95);
insert into sale (sale_id, store_id, customer_id, total) values (34, 12, 23, 128.35);
insert into sale (sale_id, store_id, customer_id, total) values (35, 13, 23, 146.36);
insert into sale (sale_id, store_id, customer_id, total) values (36, 12, 23,  14.66);
insert into sale (sale_id, store_id, customer_id, total) values (37, 11, 25,  69.65);
insert into sale (sale_id, store_id, customer_id, total) values (38, 12, 25,   9.95);

