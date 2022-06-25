use note;

show tables;

insert into member value
(
    'test', '1234'
    );

insert into note(noteId, memberId, type, title, description) value
    (
     1, 'test', '10', '제목 : test', '내용 : test'
        );

select * from member, note;
