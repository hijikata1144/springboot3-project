package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.DatabaseException;
import com.example.demo.mapper.TaskMapper;
import com.example.demo.model.Task;

@Service
public class TaskService {
  @Autowired
  private TaskMapper taskMapper;

  public List<Task> getTasks() {
    try {
      taskMapper.findAll();
    } catch (Exception e) {
        throw new DatabaseException("タスクの取得に失敗しました", e);
    }
    List<Task> tasks = taskMapper.findAll();
    return tasks;
  }

  public Task getTaskById(int id) {
    try {
      taskMapper.findById(id);
    } catch (Exception e) {
        throw new DatabaseException("タスクの取得に失敗しました", e);
    }
    return taskMapper.findById(id);
  }

  public void updateTask(Task task) {
    try {
      taskMapper.update(task);
    } catch (Exception e) {
        throw new DatabaseException("タスクの更新に失敗しました", e);
    }
  }

  public Integer addTask(Task task) {
    try {
      Integer id = taskMapper.insert(task);
      return id;
      //taskMapper.insert(task);
    } catch (Exception e) {
        throw new DatabaseException("タスクの追加に失敗しました", e);
    }
    //gradleビルドエラー解消
    //taskMapper.insert(task);
    //return task.getId();
  }

  public void deleteTask(int id) {
    try {
      taskMapper.delete(id);
    } catch (Exception e) {
        throw new DatabaseException("タスクの削除に失敗しました", e);
    }
  }

}