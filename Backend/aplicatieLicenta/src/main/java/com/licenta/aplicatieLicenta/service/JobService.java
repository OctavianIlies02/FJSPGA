package com.licenta.aplicatieLicenta.service;

import com.licenta.aplicatieLicenta.classes.Job;
import com.licenta.aplicatieLicenta.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job createJob(Job job) {
        jobRepository.save(job);
        return job;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById (long id) {
        return jobRepository.findById(id);
    }
}
