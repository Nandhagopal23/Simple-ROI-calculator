package com.example.invoicingroisimulator.dto;

public class SimulationRequest {
    public String scenario_name;
    public int monthly_invoice_volume;
    public int num_ap_staff;
    public double avg_hours_per_invoice;
    public double hourly_wage;
    public double error_rate_manual;
    public double error_cost;
    public int time_horizon_months;
    public double one_time_implementation_cost;
}
