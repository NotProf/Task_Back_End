package com.test.taskback.Services;

import com.test.taskback.DAO.UserDAO;
import com.test.taskback.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public int generateId() {
        User user = userDAO.findTopByOrderByIdDesc();
        if (user != null)
            return user.getId() + 1;
        else
            return 1;
    }

    public boolean activateAccount(String key) {
        User byUserKey = userDAO.findByUserKey(key);
        if(byUserKey != null) {
            byUserKey.setEnabled(true);
            byUserKey.setUserKey("");
            userDAO.save(byUserKey);
            return true;
        } else return false;
    }
    public boolean validateUsername(String name) {
        User user = userDAO.loadByName(name);
        boolean check = true;
        if(user != null)
            check = false;
        return check;
    }

}
