insert into users (id, password, secret) values ('thomas', 'password', '123456789');
insert into users (id, password, secret) values ('roger', 'password', '987654321');
insert into users (id, password, secret) values ('irsauditer', 'password', '2145551212');
insert into users (id, password, secret) values ('sean', 'password', '3015551212');
insert into users (id, password, secret) values ('cook', 'password', '4125551212');
insert into users (id, password, secret) values ('guest', 'password', '9125551212');
insert into users (id, password, secret) values ('jyode1', 'password', '8145551212');

insert into roles (id, role) values (1, 'admin');
insert into roles (id, role) values (2, 'Guiberest-Reader');
insert into roles (id, role) values (3, 'Guiberest-Writer');
insert into roles (id, role) values (4, 'limited');
insert into roles (id, role) values (5, 'audit');
insert into roles (id, role) values (6, 'manager');
insert into roles (id, role) values (7, 'decreaser');

insert into roleusers (roleid, username) values (1, 'thomas');
insert into roleusers (roleid, username) values (2, 'thomas');
insert into roleusers (roleid, username) values (3, 'thomas');
insert into roleusers (roleid, username) values (4, 'roger');
insert into roleusers (roleid, username) values (2, 'roger');
insert into roleusers (roleid, username) values (3, 'roger');
insert into roleusers (roleid, username) values (5, 'irsauditer');
insert into roleusers (roleid, username) values (2, 'irsauditer');
insert into roleusers (roleid, username) values (6, 'sean');
insert into roleusers (roleid, username) values (3, 'sean');
insert into roleusers (roleid, username) values (4, 'guest');
insert into roleusers (roleid, username) values (7, 'cook');
insert into roleusers (roleid, username) values (3, 'cook');

insert into acl (id, username, role_id, access, class, pk) values (1,  'thomas',  null, 'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (2,  'thomas',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (3,  'thomas',  null, 'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (4,  'thomas',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (5,  null,      5,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (6,  null,      6,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (7,  null,      6,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (8,  'thomas',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '32');
insert into acl (id, username, role_id, access, class, pk) values (9,  'thomas',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '33');
insert into acl (id, username, role_id, access, class, pk) values (10, 'thomas',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '34');
insert into acl (id, username, role_id, access, class, pk) values (11, 'thomas',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '35');
insert into acl (id, username, role_id, access, class, pk) values (12, 'thomas',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '36');
insert into acl (id, username, role_id, access, class, pk) values (13, 'thomas',  null, 'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '36');
insert into acl (id, username, role_id, access, class, pk) values (14, 'thomas',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '37');
insert into acl (id, username, role_id, access, class, pk) values (15, 'thomas',  null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Sale', '38');
insert into acl (id, username, role_id, access, class, pk) values (16,  null,     7,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (17,  null,     7,    'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');
insert into acl (id, username, role_id, access, class, pk) values (18,  null,     7,    'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (19,  null,     7,    'update', 'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (20,  'jyode1', null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '11');
insert into acl (id, username, role_id, access, class, pk) values (21,  'jyode1', null, 'read',   'edu.swmed.qbrc.guiberest.shared.domain.guiberest.Store', '12');

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

