package com.portfolio.notepad.note.repository;

import com.portfolio.notepad.note.NoteEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("insert into note(memberId, type, title, description) value(#{note.memberID}, #{note.type}, #{note.title}, #{note.description})")
    void addUserNote(@Param("note") NoteEntity note);

    @Select("select * from note where memberId = #{note.memberID}")
    List<NoteEntity> getUserNotes(@Param("note") NoteEntity note);

    @Update("update note set type = #{note.type} where noteId = #{note.noteId} and memberId = #{note.memberID}")
    void setUpdateType(@Param("note") NoteEntity note);

    @Delete("delete from note where noteID = #{note.noteId} and memberID = #{note.memberID}")
    void deleteUserNote(@Param("note") NoteEntity note);

}
