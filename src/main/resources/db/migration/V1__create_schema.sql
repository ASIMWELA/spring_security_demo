create sequence id_sequence_generator start 1 increment 1;

create table roles (
                       id int8 not null,
                       uuid varchar(12) not null,
                       role varchar(255),
                       primary key (id)
);

create table user_roles (
                            user_id int8 not null,
                            role_id int8 not null
);

create table users (
                       id int8 not null,
                       uuid varchar(12) not null,
                       is_user_enabled boolean,
                       first_name varchar(90) not null,
                       last_name varchar(90) not null,
                       password varchar(200) not null,
                       user_name varchar(90) not null,
                       primary key (id)
);

alter table if exists roles
    add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (role);

alter table if exists roles
    add constraint UK_bdys1vaxs0jqndxmixeragus8 unique (uuid);
create index user_uuid_index on users (uuid);

alter table if exists users
    add constraint UK_6km2m9i3vjuy36rnvkgj1l61s unique (uuid);

alter table if exists user_roles
    add constraint FKh8ciramu9cc9q3qcqiv4ue8a6
    foreign key (role_id)
    references roles;

alter table if exists user_roles
    add constraint FKhfh9dx7w3ubf1co1vdev94g3f
    foreign key (user_id)
    references users;