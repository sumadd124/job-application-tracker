package com.tracker.model;

import java.time.LocalDate;

public class Application {
    private int id;
    private String company;
    private String role;
    private LocalDate dateApplied;
    private String status;
    private LocalDate deadline;
    private String notes;

    public Application() {}

    public Application(int id, String company, String role, LocalDate dateApplied, String status, LocalDate deadline, String notes) {
        this.id = id;
        this.company = company;
        this.role = role;
        this.dateApplied = dateApplied;
        this.status = status;
        this.deadline = deadline;
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getDateApplied() { return dateApplied; }
    public void setDateApplied(LocalDate dateApplied) { this.dateApplied = dateApplied; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "Application{" +
                "company='" + company + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
