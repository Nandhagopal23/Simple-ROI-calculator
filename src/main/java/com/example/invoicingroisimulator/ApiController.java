package com.example.invoicingroisimulator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping(path = "/api/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> health() {
        Map<String, Object> m = new HashMap<>();
        m.put("status", "ok");
        m.put("app", "invoicing-roi-simulator");
        return m;
    }

    @PostMapping(path = "/api/calc", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> calc(@RequestBody Map<String, Object> payload) {
        // Expected payload keys: manualCost, automatedCost
        double manual = toDouble(payload.getOrDefault("manualCost", 0));
        double automated = toDouble(payload.getOrDefault("automatedCost", 0));

        double savings = manual - automated;
        double roiPct = (automated != 0) ? (savings / automated) * 100.0 : 0.0;

        Map<String, Object> out = new HashMap<>();
        out.put("manualCost", manual);
        out.put("automatedCost", automated);
        out.put("savings", savings);
        out.put("roiPercentage", roiPct);
        return out;
    }

    private double toDouble(Object o) {
        if (o == null) {
            return 0.0;
        }
        if (o instanceof Number n) {
            return n.doubleValue();
        }
        String s = o.toString().trim();
        if (s.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            // fallback: remove commas like 1,234.56
            try {
                return Double.parseDouble(s.replaceAll(",", ""));
            } catch (NumberFormatException ex) {
                return 0.0;
            }
        }
    }
}
