package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.classes.EnergyProcessingTime;
import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.classes.Machine;
import com.licenta.aplicatieLicenta.classes.Task;
import com.licenta.aplicatieLicenta.repository.EnergyProcessingTimeRepository;
import com.licenta.aplicatieLicenta.repository.JobRepository;
import com.licenta.aplicatieLicenta.repository.MachineRepository;
import com.licenta.aplicatieLicenta.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WriteInDBService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EnergyProcessingTimeRepository energyProcessingTimeRepository;
    @Autowired
    private TaskService taskService;

    private static final Logger logger = Logger.getLogger(WriteInDBService.class.getName());

    public void generate(int nbJobs, int nbMchs, List<List<List<Integer>>> ops, List<List<Integer>> modes) {
        saveJobs(nbJobs);
        saveMachines(nbMchs);
        saveTasks(ops,nbMchs);
        saveModes(modes);
    }

    private void saveJobs(int nbJobs) {
        for (int i = 1; i <= nbJobs; i++) {
            Job job = new Job();
            job.setId(i);
            jobRepository.save(job);
            logger.fine("Saved job with id: " + job.getId());
        }
    }

    private void saveMachines(int nbMchs) {
        for (int i = 0; i < nbMchs; i++) {
            Machine machine = new Machine();
            machine.setId((long) i);
            machineRepository.save(machine);
            logger.fine("Saved machine with id: " + machine.getId());
        }
    }

    // Modificăm aici pentru a accepta List<List<List<Integer>>>
    private void saveTasks(List<List<List<Integer>>> ops, int nbJobs) {
        int taskCounter = 0;
        int jobId = 1;

        for (List<List<Integer>> jobOps : ops) {
            for (List<Integer> op : jobOps) {
                int taskId = op.get(0);
                int machineId = op.get(1);

                if (taskCounter == 5) {
                    taskCounter = 0;
                    jobId++;
                }

                try {
                    taskService.addMachineToTask((long) taskId, (long) machineId, (long) jobId);
                    logger.fine("Associated task " + taskId + " with machine " + machineId + " and job " + jobId);
                    taskCounter++;
                } catch (IllegalArgumentException e) {
                    logger.severe("Task, Machine or Job not found for taskId: " + taskId + ", machineId: " + machineId + ", jobId: " + jobId);
                }
            }
        }
    }


    private void saveModes(List<List<Integer>> modes) {
        for (List<Integer> mode : modes) {
            int taskId = mode.get(0);
            int energy = mode.get(1);
            int processingTime = mode.get(2);

            Task task = taskRepository.findById((long) taskId).orElse(null);

            if (task != null) {
                EnergyProcessingTime modeEntity = new EnergyProcessingTime();
                modeEntity.setTask(task);
                modeEntity.setEnergy(energy);
                modeEntity.setProcessingTime(processingTime);
                energyProcessingTimeRepository.save(modeEntity);
                logger.fine("Saved mode for task " + taskId + " with energy " + energy + " and processing time " + processingTime);
            } else {
                logger.severe("Task not found for taskId: " + taskId);
            }
        }
    }


    public void deleteAll() {
        energyProcessingTimeRepository.deleteAll();
        taskRepository.deleteAll();
        jobRepository.deleteAll();
        machineRepository.deleteAll();
        logger.info("All data deleted from the database.");
    }
}
