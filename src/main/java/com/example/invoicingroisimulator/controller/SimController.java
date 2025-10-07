package com.example.invoicingroisimulator.controller;

import com.example.invoicingroisimulator.dto.SimulationRequest;
import com.example.invoicingroisimulator.dto.SimulationResult;
import com.example.invoicingroisimulator.model.Scenario;
import com.example.invoicingroisimulator.repo.ScenarioRepository;
import com.example.invoicingroisimulator.service.SimulationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SimController {

    private final SimulationService simulationService;
    private final ScenarioRepository scenarioRepository;

    public SimController(SimulationService simulationService, ScenarioRepository scenarioRepository) {
        this.simulationService = simulationService;
        this.scenarioRepository = scenarioRepository;
    }

    @PostMapping(path = "/simulate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SimulationResult simulate(@RequestBody SimulationRequest req) {
        return simulationService.simulate(req);
    }

    @PostMapping(path = "/scenarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Scenario saveScenario(@RequestBody Scenario s) {
        return scenarioRepository.save(s);
    }

    @GetMapping(path = "/scenarios", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Scenario> listScenarios() {
        return scenarioRepository.findAll();
    }

    @GetMapping(path = "/scenarios/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Scenario> getScenario(@PathVariable Long id) {
        return scenarioRepository.findById(id);
    }

    @DeleteMapping(path = "/scenarios/{id}")
    public void deleteScenario(@PathVariable Long id) {
        scenarioRepository.deleteById(id);
    }

    @PostMapping(path = "/report/generate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String generateReport(@RequestBody ReportRequest r) {
        // require email
        if (r.email == null || r.email.trim().isEmpty()) {
            return "<html><body><h3>email required</h3></body></html>";
        }
        // simple HTML report
        SimulationResult res = simulationService.simulate(r.simulationRequest);
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<h1>Invoicing ROI Report</h1>");
        sb.append("<p>Scenario: ").append(r.scenario_name).append("</p>");
        sb.append("<pre>").append("Monthly savings: ").append(String.format("%.2f", res.monthly_savings)).append("\n");
        sb.append("Payback months: ").append(String.format("%.2f", res.payback_months)).append("\n");
        sb.append("ROI (%): ").append(String.format("%.2f", res.roi_percentage)).append("\n");
        sb.append("</pre>");
        sb.append("</body></html>");
        return sb.toString();
    }

    public static class ReportRequest {
        public String email;
        public String scenario_name;
        public com.example.invoicingroisimulator.dto.SimulationRequest simulationRequest;
    }
}
