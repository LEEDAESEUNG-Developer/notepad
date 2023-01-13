use aLong;

show tables;

insert into member value
(
    'test', '1234'
    );

insert into aLong(memberId, type, title, description) value
    (
     'test', '10', '제목 : test', '내용 : test'
        );

select * from member, aLong;

select * from aLong where memberId = 'test';

select * from aLong;

update aLong set type = 30 where noteId = 2 and memberId = 'test';