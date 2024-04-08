package com.licenta.aplicatieLicenta.repository;

import com.licenta.aplicatieLicenta.classes.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
