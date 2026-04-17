package com.tracker.dao;

import com.tracker.model.Application;
import java.util.List;

public interface ApplicationDAO {
    void addApplication(Application app);
    void updateApplication(Application app);
    void deleteApplication(int id);
    List<Application> getAllApplications();
    List<Application> searchApplications(String query, String status);
}
