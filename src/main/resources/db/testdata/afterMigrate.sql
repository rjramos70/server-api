set foreign_key_checks = 0;

delete from databasetype;

set foreign_key_checks = 1;

alter table databasetype auto_increment = 1;


INSERT INTO databasetype (description, type, jdbcprefix, jdbcdriver, registered, updated) VALUES ('MySQL database instance','MySQL','jdbc:mysql://','com.mysql.cj.jdbc.Driver', utc_timestamp, utc_timestamp);

