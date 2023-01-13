show databases ;

create database aLong;

use aLong;

show tables;

create table member(
--     name varchar(20) primary key,
    memberId varchar(20) primary key,
    memberPwd varchar(20) not null
);

create table aLong(
    noteId integer auto_increment primary key,
    memberId varchar(20),
    type int not null,
    title varchar(15) not null,
    description text not null,
    regDate datetime default now(),

    CONSTRAINT note_memberId_fk FOREIGN KEY (memberId) REFERENCES member(memberId)
);

select * from member;
select * from aLong;

