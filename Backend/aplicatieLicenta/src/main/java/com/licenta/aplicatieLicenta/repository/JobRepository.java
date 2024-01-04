package com.licenta.aplicatieLicenta.repository;

import com.licenta.aplicatieLicenta.classes.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
}
