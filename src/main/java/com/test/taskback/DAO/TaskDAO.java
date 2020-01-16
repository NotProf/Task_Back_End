package com.test.taskback.DAO;

import com.test.taskback.Models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskDAO extends MongoRepository<Task, Integer> {
    Task findById(int id);
    Task findTopByOrderByIdDesc();
    Task getById(int id);
}
