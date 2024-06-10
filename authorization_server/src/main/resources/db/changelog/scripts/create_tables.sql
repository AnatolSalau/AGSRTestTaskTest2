-- clients
CREATE TABLE if not exists auth_sch.oauth2_registered_client
(
    id                            varchar(100)                            NOT NULL,
    client_id                     varchar(100)                            NOT NULL,
    client_id_issued_at           timestamp     DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar(200)  DEFAULT NULL,
    client_secret_expires_at      timestamp     DEFAULT NULL,
    client_name                   varchar(200)                            NOT NULL,
    client_authentication_methods varchar(1000)                           NOT NULL,
    authorization_grant_types     varchar(1000)                           NOT NULL,
    redirect_uris                 varchar(1000) DEFAULT NULL,
    post_logout_redirect_uris     varchar(1000) DEFAULT NULL,
    scopes                        varchar(1000)                           NOT NULL,
    client_settings               varchar(2000)                           NOT NULL,
    token_settings                varchar(2000)                           NOT NULL,
    PRIMARY KEY (id)
);

create table if not exists auth_sch.users
(
    username varchar(200) not null primary key,
    password varchar(500) not null,
    enabled  boolean      not null
);

create type auth_sch.roletype as enum ('Patient', 'Practitioner');

create table if not exists auth_sch.authorities
(
      username  varchar(200) not null,
      authority auth_sch.roletype  not null,
      constraint fk_authorities_users foreign key (username) references auth_sch.users (username),
      constraint username_authority UNIQUE (username, authority)
);