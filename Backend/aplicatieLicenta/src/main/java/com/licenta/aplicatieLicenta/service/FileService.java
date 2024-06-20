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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileService {

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

    private static final Logger logger = Logger.getLogger(FileService.class.getName());

    public void processAndSaveFile(MultipartFile file) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            int nbJobs = 0;
            int nbMchs = 0;
            int nbOp = 0;
            List<List<Integer>> ops = new ArrayList<>();
            List<List<Integer>> modes = new ArrayList<>();

            logger.setLevel(Level.ALL);

            while ((line = br.readLine()) != null) {
                if (line.startsWith("nbJobs")) {
                    nbJobs = Integer.parseInt(line.split("=")[1].trim().replace(";", ""));
                    logger.fine("Number of jobs: " + nbJobs);
                } else if (line.startsWith("nbMchs")) {
                    nbMchs = Integer.parseInt(line.split("=")[1].trim().replace(";", ""));
                    logger.fine("Number of machines: " + nbMchs);
                } else if (line.startsWith("nbOp")) {
                    nbOp = Integer.parseInt(line.split("=")[1].trim().replace(";", ""));
                    logger.fine("Number of operations: " + nbOp);
                } else if (line.startsWith("Ops")) {
                    while (!(line = br.readLine()).startsWith("]")) {
                        Pattern pattern = Pattern.compile("<(\\d+),(\\d+)>");
                        Matcher matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            int task = Integer.parseInt(matcher.group(1));
                            int machine = Integer.parseInt(matcher.group(2));
                            List<Integer> pair = List.of(task, machine);
                            ops.add(pair);
                            logger.fine("Added to ops: Task " + task + ", Machine " + machine);
                        }
                    }
                } else if (line.startsWith("Modes")) {
                    while (!(line = br.readLine()).startsWith("}")) {
                        Pattern pattern = Pattern.compile("<(\\d+),(\\d+),(\\d+)>");
                        Matcher matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            int taskId = Integer.parseInt(matcher.group(1));
                            int energy = Integer.parseInt(matcher.group(2));
                            int processingTime = Integer.parseInt(matcher.group(3));
                            List<Integer> triplet = List.of(taskId, energy, processingTime);
                            modes.add(triplet);
                            logger.fine("Added to modes: Task " + taskId + ", Energy " + energy + ", ProcessingTime " + processingTime);
                        }
                    }
                }
            }

            saveJobs(nbJobs);
            saveMachines(nbMchs);
            saveTasks(ops, nbJobs);
            saveModes(modes);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while processing the file", e);
            throw e;
        }
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

    private void saveTasks(List<List<Integer>> ops, int nbJobs) {
        int taskCounter = 0;
        int jobId = 1;

        for (int i = 1; i <= nbJobs; i++) {
            for (List<Integer> op : ops) {
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
}
