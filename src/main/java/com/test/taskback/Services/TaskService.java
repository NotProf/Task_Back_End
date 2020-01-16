package com.test.taskback.Services;

import com.test.taskback.DAO.TaskDAO;
import com.test.taskback.DAO.UserDAO;
import com.test.taskback.Models.Task;
import com.test.taskback.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
  @Autowired
    TaskDAO taskDAO;
  @Autowired
    UserDAO userDAO;
  @Autowired
  MailService mailService;
    public int generateId() {
        Task task = taskDAO.findTopByOrderByIdDesc();
        if (task != null)
            return task.getId() + 1;
        else
            return 1;
    }
    public List<Task> getListTasks(List<Integer> listIds) {
        ArrayList<Task> tasks = new ArrayList<>();
        if (listIds != null) {
            for (Integer listId : listIds) {
                tasks.add(taskDAO.getById(listId));
            }
        }
        return tasks;
    }

    public void removeTask(int id) {
        Task byId = taskDAO.getById(id);
        List<String> users = byId.getUsers();
        for (String user : users) {
            User usersObj = userDAO.loadByName(user);
            List<Integer> tasks = usersObj.getTasks();
            Object ids = (Integer) id;
            tasks.remove(ids);
            usersObj.setTasks(tasks);
            userDAO.save(usersObj);
        }
        taskDAO.delete(taskDAO.getById(id));
    }

    public void shareTask(int taskId, int id, String senderName) {
        User sender = userDAO.loadByName(senderName);
        User recipient = userDAO.getById(id);
        List<Integer> tasks = recipient.getTasks();
        tasks.add(taskId);
        recipient.setTasks(tasks);
        userDAO.save(recipient);
        Task task = taskDAO.getById(taskId);
        List<String> users = task.getUsers();
        users.add(recipient.getUsername());
        task.setUsers(users);
        taskDAO.save(task);
        String message = "<h1>Hello," + recipient.getUsername() +"</h1>" + " User " + senderName + " shared task with you <hr/>" +
                "<a href=\"http://ec2-3-19-242-222.us-east-2.compute.amazonaws.com:8080/\">Click to View your tasks</a>";
        String subject = senderName + " Shared task";
            mailService.send(recipient, message, subject);

    }
}
