use cafedemetro;

create table users(
	userId			int not null primary key auto_increment,
    usercode 		varchar(255) not null unique,
    `password`		varchar(255) not null
);

create table roles(
	rId				int not null primary key auto_increment,
    usercode		varchar(255) not null,
    rolename		varchar(255) not null,
    constraint ak_roles unique(usercode, rolename),
    constraint fk_roles_usercode_users foreign key (usercode) 
		references users(usercode)
);

select usercode, `password`
from users;

insert into users (usercode, password) values ('admin', '579f0af7c8ca854aa37b6d9cba1e400b1ada83fcae5b875d115de27ff480df01'); 
insert into users (usercode, password) values ('manager', '579f0af7c8ca854aa37b6d9cba1e400b1ada83fcae5b875d115de27ff480df01'); 
insert into users (usercode, password) values ('staff', '579f0af7c8ca854aa37b6d9cba1e400b1ada83fcae5b875d115de27ff480df01'); 

insert into roles (usercode, rolename) values ('admin', 'superuser');
insert into roles (usercode, rolename) values ('admin', 'manager');
insert into roles (usercode, rolename) values ('admin', 'staff');
insert into roles (usercode, rolename) values ('manager', 'manager');
insert into roles (usercode, rolename) values ('manager', 'staff');
insert into roles (usercode, rolename) values ('staff', 'staff');

select
    usercode, rolename
from roles;