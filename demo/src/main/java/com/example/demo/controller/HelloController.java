package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.exception.DatabaseException;
import com.example.demo.model.Task;
import com.example.demo.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


//gradlewビルドエラー対応
//import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {
  @Autowired
  private TaskService taskService;

  @GetMapping("/")
  public String hello(Model model) {
    // タスクを取得
    List<Task> tasks = taskService.getTasks();

    // フォーム入力のための空のタスクインスタンスを渡す
    model.addAttribute("task", new Task());

    // テンプレートエンジンにtask一覧を渡す
    model.addAttribute("tasks", tasks);
    return "index";
  }

  @PostMapping("/tasks")
  public String addTask(@Valid @ModelAttribute Task task, BindingResult result, HttpServletRequest request,
  Model model) {

    if (result.hasErrors()) {
      List<Task> tasks = taskService.getTasks();
      model.addAttribute("tasks", tasks);
      model.addAttribute("task", task);      
      return "index";
    }
    try {
      taskService.addTask(task);
    } catch (DatabaseException e) {
      model.addAttribute("errorMessage", e.getMessage());
      return "index";
    }

    //taskService.addTask(task);

    // CodeSpaceではredirect:/で正しく動かないので、動的にURLを取得してリダイレクトする。
    String redirectUrl = getBaseUrl(request) + "/";
    return "redirect:" + redirectUrl;
  }


  @GetMapping("/tasks/edit/{id}")
  public String editTask(Model model, @PathVariable("id") int id) {
    Task task = taskService.getTaskById(id);

    model.addAttribute("task", task);
    return "edit";
  }

  @PostMapping("/tasks/edit")
  public String editTask(@Valid @ModelAttribute Task task, BindingResult result, Model model, HttpServletRequest request) {
    if (result.hasErrors()) {
    model.addAttribute("task", task);
    model.addAttribute("org.springframework.validation.BindingResult.task", result);

    return "edit";
    }

    try {
      taskService.updateTask(task);
    } catch (DatabaseException e) {
      model.addAttribute("errorMessage", e.getMessage());
      return "index";
    }

    taskService.updateTask(task);

    String redirectUrl = getBaseUrl(request) + "/";
    return "redirect:" + redirectUrl;
  }

  @PostMapping("/tasks/delete")
  public String deleteTask(@ModelAttribute Task task, HttpServletRequest request, Model model) {
    try {
      taskService.deleteTask(task.getId());
    } catch (DatabaseException e) {
      model.addAttribute("errorMessage", e.getMessage());
      return "index";
    }
    taskService.deleteTask(task.getId());

    // 動的にリダイレクトURLを取得
    String redirectUrl = getBaseUrl(request) + "/";
    return "redirect:" + redirectUrl;
  }

  private String getBaseUrl(HttpServletRequest request) {
    String scheme = request.getHeader("X-Forwarded-Proto");
    if (scheme == null) {
      scheme = request.getScheme();
    }

    String serverName = request.getHeader("X-Forwarded-Host");
    if (serverName == null) {
      serverName = request.getServerName();
    }

    String contextPath = request.getContextPath();

    // ベースURLを生成
    return scheme + "://" + serverName + contextPath;
  }
}

  /*@PostMapping("/tasks")
  public String addTask(@ModelAttribute Task task, HttpServletRequest request) {
    taskService.addTask(task);
    return "redirect:/"; 
  }*/

