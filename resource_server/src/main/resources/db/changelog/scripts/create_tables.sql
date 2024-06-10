create type res_sch.gendertype as enum ('FEMALE', 'MALE');

create table res_sch.patients
(
    patient_id uuid primary key,
    name       varchar(255) not null unique,
    birthday   date         not null,
    gender     res_sch.gendertype,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null
);


