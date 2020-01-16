package com.test.taskback.DAO;

import com.test.taskback.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserDAO extends MongoRepository<User, Integer> {

    @Query("{ 'username' : {$regex: ?0, $options: 'i' }}")
    User loadByName(String name);

    User findTopByOrderByIdDesc();
    User findByUserKey(String key);
    User getById(int id);
}
