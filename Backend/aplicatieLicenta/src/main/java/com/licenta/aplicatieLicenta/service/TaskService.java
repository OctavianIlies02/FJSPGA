package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.classes.Machine;
import com.licenta.aplicatieLicenta.classes.Task;
import com.licenta.aplicatieLicenta.repository.JobRepository;
import com.licenta.aplicatieLicenta.repository.MachineRepository;
import com.licenta.aplicatieLicenta.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private JobRepository jobRepository;

    public Task createTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById (Long id) {
        return taskRepository.findById(id);
    }

    public Task addMachineToTask(Long taskId, Long machineId, Long jobId) {
        Optional<Machine> optionalMachine = machineRepository.findById(machineId);
        if (!optionalMachine.isPresent()) {
            throw new IllegalArgumentException("Machine not found");
        }

        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (!optionalJob.isPresent()) {
            throw new IllegalArgumentException("Job not found");
        }

        Machine machine = optionalMachine.get();
        Job job = optionalJob.get();
        Task task;

        if (taskId != null) {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent()) {
                task = optionalTask.get();
                task.setMachine(machine);
                task.setJob(job);
            } else {
                task = new Task();
                task.setId(taskId);
                task.setMachine(machine);
                task.setJob(job);
            }
        } else {
            task = new Task();
            task.setMachine(machine);
            task.setJob(job);
        }

        taskRepository.save(task);
        return task;
    }



}
