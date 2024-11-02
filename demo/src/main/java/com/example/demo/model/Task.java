package com.example.demo.model;

import jakarta.validation.constraints.Size;

public class Task {

  private int id;

  @Size(min = 1, max = 100, message = "タスクは1文字以上100文字以下で入力してください。")
  private String description;

  public Task(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public Task() { 
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

