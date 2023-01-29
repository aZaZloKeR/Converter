create table users(
    id  serial,
    login varchar(50) not null,
    password varchar(100),
    primary key(id)
);

create table roles(
    id serial,
    name varchar(50) not null,
    primary key(id)
);

create table users_roles(
    user_id int not null,
    role_id int not null,
    primary key(user_id,role_id),
    foreign key(user_id) references users(id),
    foreign key(role_id) references roles(id)
);

create table logs(
    id serial,
    user_id int,
    type varchar(50),
    inner_value varchar(255),
    outer_value varchar(255),
    conversion_date timestamp,
    primary key(id),
    foreign key(user_id) references users(id)
);

insert into roles (name) values ('ROLE_USER');