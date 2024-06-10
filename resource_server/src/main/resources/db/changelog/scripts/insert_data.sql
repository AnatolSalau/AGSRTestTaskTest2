insert into res_sch.patients (patient_id, name, birthday, created_at, updated_at, gender)
values (gen_random_uuid(), 'patient1',
        '1990-01-01', current_timestamp, current_timestamp, 'MALE'),
       (gen_random_uuid(), 'patient2',
        '1991-02-02', current_timestamp, current_timestamp, 'MALE');


