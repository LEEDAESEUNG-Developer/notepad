use note;

show tables;

insert into member value
(
    'test', '1234'
    );

insert into note(memberId, type, title, description) value
    (
     'test', '10', '제목 : test', '내용 : test'
        );

select * from member, note;

select * from note where memberId = 'test';

select * from note;

update note set type = 30 where noteId = 2 and memberId = 'test';