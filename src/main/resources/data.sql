create table users (
	username varchar(100) not null primary key,    
	password varchar(200) not null,    
	enabled boolean not null
) engine = InnoDb;

create table authorities (    
	username varchar(100) not null,    
	authority varchar(20) not null,    
	foreign key (username) references users (username),    
	unique index authorities_idx_1 (username, authority)
) engine = InnoDb;

INSERT INTO users (username, password, enabled)
	VALUES ('demo', '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C', true);