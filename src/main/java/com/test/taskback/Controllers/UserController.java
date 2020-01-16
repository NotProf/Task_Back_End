package com.test.taskback.Controllers;

import com.test.taskback.DAO.UserDAO;
import com.test.taskback.Models.User;
import com.test.taskback.Services.MailService;
import com.test.taskback.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;


    @PostMapping("/registration")
    public boolean registration(@RequestBody User user) {
        user.setId(userService.generateId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String message = "<h1>Hello " + user.getUsername() + "</h1>" +
                         "<h3> Visit link: link: http://ec2-3-19-242-222.us-east-2.compute.amazonaws.com:8080/complete-register/" + user.getUserKey() + "</h3>";
        mailService.send(user, message, "Complete Registration");
        userDAO.insert(user);
        System.out.println(userDAO.findAll().toString());

        return true;
    }
    @GetMapping("/check")
    public boolean check (){
        boolean check = false;
        System.out.println(getAuthentication());
        if(getAuthentication() != "anonymousUser") {
            check = true;
        }
        return check;
    }
    @GetMapping("/activate/{key}")
    public boolean completeReiterations(@PathVariable String key){
        return userService.activateAccount(key);
    }
    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return userDAO.findAll();
    }
    @PostMapping("/checkUsername")
    public boolean checkUsername(@RequestBody String name){
       return userService.validateUsername(name);

    }
    public String getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
