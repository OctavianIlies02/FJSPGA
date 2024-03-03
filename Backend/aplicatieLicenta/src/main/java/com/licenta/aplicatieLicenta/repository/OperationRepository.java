package com.licenta.aplicatieLicenta.repository;

import com.licenta.aplicatieLicenta.classes.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
