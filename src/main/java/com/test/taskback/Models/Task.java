package com.test.taskback.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@Document(collection = "Task")
public class Task {
    @Id
    private int id;
    private String text;
    private Date date;
    List<String> users = new ArrayList<>();
}
