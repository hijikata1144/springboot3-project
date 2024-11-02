package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.Task;

@Mapper
public interface TaskMapper {

  @Select("SELECT * FROM tasks")
  List<Task> findAll();

  @Select("SELECT * FROM tasks WHERE id = #{id}")
  Task findById(int id);

  @Insert("INSERT INTO tasks(description) VALUES(#{description})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  int insert(Task task);//TODO

  @Update("UPDATE tasks SET description = #{description} WHERE id = #{id}")
  void update(Task task);

  @Delete("DELETE FROM tasks WHERE id = #{id}")
  void delete(int id);
}
