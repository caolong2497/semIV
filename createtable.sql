use CoupleDatabase
go
create table tbl_user(
	UserId int IDENTITY(1,1)  primary key,
	gmail varchar(255) not null,
	password varchar(255) not null,
	code int
)
go
create table tbl_userinfor(
	UserId int IDENTITY(1,1)  primary key,
	name nvarchar(255) not null,
	gender bit not null,
	birthday date not null,
	coupleId int not null,
	avatar varchar(500)
)
go
create table couple(
	coupleId int primary key,
	start date,
	image varchar(500)
)
go
drop table tbl_chat
go
create table tbl_chat(
	chatID int,
	content varchar(1000),
	time date,
	userid int FOREIGN KEY REFERENCES tbl_userinfor(userid),
	PRIMARY KEY NONCLUSTERED (chatID,userid)
)
go
create table tbl_memory(
	memoryID int not null,
	image varchar(500),
	dateofmemory date,
	caption nvarchar(1000),
	userid int FOREIGN KEY REFERENCES tbl_userinfor(userid),
	PRIMARY KEY NONCLUSTERED (memoryID,userid)
)
go
create table tbl_comment(
	commentID int ,
	content nvarchar(1000) not null,
	memoryId int ,
	userid int FOREIGN KEY REFERENCES tbl_userinfor(userid),
	datecreat date,
	PRIMARY KEY NONCLUSTERED (commentID,memoryId)
)