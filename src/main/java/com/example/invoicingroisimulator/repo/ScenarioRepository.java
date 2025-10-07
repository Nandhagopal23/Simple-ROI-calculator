package com.example.invoicingroisimulator.repo;

import com.example.invoicingroisimulator.model.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}
