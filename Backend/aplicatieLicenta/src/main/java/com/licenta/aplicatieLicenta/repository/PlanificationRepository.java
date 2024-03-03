package com.licenta.aplicatieLicenta.repository;

import com.licenta.aplicatieLicenta.classes.Planification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanificationRepository extends JpaRepository<Planification,Long> {
}
