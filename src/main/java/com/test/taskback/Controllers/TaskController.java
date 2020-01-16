package com.test.taskback.Controllers;

import com.test.taskback.DAO.TaskDAO;
import com.test.taskback.DAO.UserDAO;
import com.test.taskback.Models.Task;
import com.test.taskback.Models.User;
import com.test.taskback.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    UserDAO userDAO;
    @PostMapping("/addTask")
    public Task addTask (@RequestBody Task task) {
        task.setId(taskService.generateId());
        List<String> users = task.getUsers();
        users.add(getAuthentication());
        task.setUsers(users);
        taskDAO.insert(task);
        User user = userDAO.loadByName(getAuthentication());
        List<Integer> userTasks = user.getTasks();
        userTasks.add(task.getId());
        user.setTasks(userTasks);
        userDAO.save(user);
        System.out.println(task);
        return task;
    }
    @GetMapping("/getTasks")
    public List<Task> getTasks (){
        List<Integer> tasks = userDAO.loadByName(getAuthentication()).getTasks();
        return taskService.getListTasks(tasks);
    }
    @PostMapping("/removeTask")
    public List<Task> removeTasks (@RequestBody int id){
       taskService.removeTask(id);
        return getTasks();
    }
    @PostMapping("/updateTask")
    public boolean updateTask (@RequestBody Task task){
        taskDAO.save(task);
        return true;
    }
    @PostMapping("/shareTask/{task}")
    public boolean shareTask (@PathVariable int task,
                             @RequestBody int id){
        taskService.shareTask(task, id, getAuthentication());

        return true;
    }
    public String getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
