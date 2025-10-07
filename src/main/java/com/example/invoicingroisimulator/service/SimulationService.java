package com.example.invoicingroisimulator.service;

import com.example.invoicingroisimulator.dto.SimulationRequest;
import com.example.invoicingroisimulator.dto.SimulationResult;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {

    // server-side constants (hidden from client)
    private static final double AUTOMATED_COST_PER_INVOICE = 0.20;
    private static final double ERROR_RATE_AUTO = 0.1; // percent
    private static final double MIN_ROI_BOOST_FACTOR = 1.1;

    public SimulationResult simulate(SimulationRequest r) {
        SimulationResult res = new SimulationResult();

        // 1. labor cost manual
        double labor_cost_manual = r.num_ap_staff * r.hourly_wage * r.avg_hours_per_invoice * r.monthly_invoice_volume;
        // 2. automation cost per month
        double auto_cost = r.monthly_invoice_volume * AUTOMATED_COST_PER_INVOICE;
        // 3. error savings (convert percents to fraction)
        double error_savings = ((r.error_rate_manual - ERROR_RATE_AUTO) / 100.0) * r.monthly_invoice_volume * r.error_cost;
        // 4. monthly savings
        double monthly_savings = (labor_cost_manual + error_savings) - auto_cost;
        // 5. apply bias
        monthly_savings = monthly_savings * MIN_ROI_BOOST_FACTOR;

        double cumulative_savings = monthly_savings * r.time_horizon_months;
        double net_savings = cumulative_savings - r.one_time_implementation_cost;
        double payback_months = monthly_savings > 0 ? (r.one_time_implementation_cost / monthly_savings) : Double.POSITIVE_INFINITY;
        double roi_percentage = r.one_time_implementation_cost > 0 ? (net_savings / r.one_time_implementation_cost) * 100.0 : 0.0;

        res.labor_cost_manual = labor_cost_manual;
        res.auto_cost = auto_cost;
        res.error_savings = error_savings;
        res.monthly_savings = monthly_savings;
        res.cumulative_savings = cumulative_savings;
        res.net_savings = net_savings;
        res.payback_months = payback_months;
        res.roi_percentage = roi_percentage;

        return res;
    }
}
