create schema university;

alter schema university owner to postgres;

create table if not exists addresses
(
	country varchar(30),
	city varchar(30),
	address varchar(30),
	id serial not null
		constraint addresses_pk
			primary key
);

alter table addresses owner to postgres;

create table if not exists library_abonaments
(
	status varchar(20) not null,
	start_date date,
	end_date date,
	id serial not null
		constraint library_abonaments_pk
			primary key
);

alter table library_abonaments owner to postgres;

create table if not exists persons
(
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	date_of_birth date,
	gender char not null,
	picture bytea,
	mail varchar(30),
	address_id integer not null
		constraint persons_addresses_id_fk
			references addresses
				on update cascade on delete cascade,
	library_abonament_id integer not null
		constraint persons_library_abonaments_id_fk
			references library_abonaments
				on update cascade on delete cascade,
	id serial not null
		constraint persons_pk
			primary key
);

alter table persons owner to postgres;

create table if not exists teachers
(
	salary double precision,
	id serial not null
		constraint teachers_pk
			primary key
		constraint teachers_persons_id_fk
			references persons
				on update cascade on delete cascade
);

alter table teachers owner to postgres;

create table if not exists disciplines
(
	title varchar(50) not null,
	teacher_id integer not null
		constraint disciplines_teachers_id_fk
			references teachers,
	id serial not null
		constraint disciplines_pk
			primary key
);

alter table disciplines owner to postgres;

create table if not exists groups
(
	name varchar(20) not null,
	id serial not null
		constraint groups_pk
			primary key
);

alter table groups owner to postgres;

create table if not exists students
(
	group_id integer not null
		constraint students_groups_id_fk
			references groups,
	id serial not null
		constraint students_pk
			primary key
		constraint students_persons_id_fk
			references persons
				on update cascade on delete cascade
);

alter table students owner to postgres;

create table if not exists averages
(
	student_id integer not null
		constraint averages_students_id_fk
			references students
				on delete cascade,
	discipline_id integer not null
		constraint averages_disciplines_id_fk
			references disciplines,
	value numeric(4,2) not null,
	id serial not null
		constraint averages_pk
			primary key
);

alter table averages owner to postgres;

create table if not exists marks
(
	student_id integer not null
		constraint marks_students_id_fk
			references students
				on delete cascade,
	discipline_id integer not null
		constraint marks_disciplines_id_fk
			references disciplines,
	teacher_id integer not null
		constraint marks_teachers_id_fk
			references teachers,
	value numeric(4,2) not null,
	create_data date not null,
	id serial not null
		constraint marks_pk
			primary key
);

alter table marks owner to postgres;

create table if not exists disciplines_to_students
(
	student_id integer not null
		constraint disciplines_to_students_students_id_fk
			references students,
	discipline_id integer not null
		constraint disciplines_to_students_disciplines_id_fk
			references disciplines
				on delete cascade
);

alter table disciplines_to_students owner to postgres;

create table if not exists phone_types
(
	name varchar(10) not null,
	id serial not null
		constraint phone_typs_pk
			primary key
);

alter table phone_types owner to postgres;

create table if not exists phones
(
	type_id integer not null
		constraint phones_phone_typs_id_fk
			references phone_types,
	value varchar(25) not null,
	id serial not null
		constraint phones_pk
			primary key
);

alter table phones owner to postgres;

create table if not exists persons_to_phones
(
	person_id integer not null
		constraint persons_to_phones_persons_id_fk
			references persons
				on update cascade on delete cascade,
	phone_id integer not null
		constraint persons_to_phones_phones_id_fk
			references phones
				on update cascade on delete cascade
);

alter table persons_to_phones owner to postgres;

