create table databasetype (
	id bigint not null auto_increment,
	description varchar(100) not null,
	type varchar(100) not null,
	jdbcprefix varchar(100) not null,
	jdbcdriver varchar(100),
	registered datetime not null,
	updated datetime not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;

create table connector (
	id bigint not null auto_increment,
	name varchar(100) not null,
	hostname varchar(100) not null,
	port bigint,
	databasename varchar(100),
	username varchar(80),
	password varchar(80),
	databasetype_id bigint not null,
	registered datetime not null,
	updated datetime not null,
	
	primary key (id)
) engine=InnoDB default charset=utf8;

alter table connector add constraint fk_connector_databasetype
foreign key (databasetype_id) references databasetype (id);
