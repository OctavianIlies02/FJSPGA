package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.classes.Task;
import com.licenta.aplicatieLicenta.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById (long id) {
        return taskRepository.findById(id);
    }


}
