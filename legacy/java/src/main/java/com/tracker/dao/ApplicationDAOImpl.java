package com.tracker.dao;

import com.tracker.db.DatabaseConfig;
import com.tracker.model.Application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAOImpl implements ApplicationDAO {

    @Override
    public void addApplication(Application app) {
        String sql = "INSERT INTO applications (company, role, date_applied, status, deadline, notes) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, app.getCompany());
            pstmt.setString(2, app.getRole());
            pstmt.setDate(3, app.getDateApplied() != null ? Date.valueOf(app.getDateApplied()) : null);
            pstmt.setString(4, app.getStatus());
            pstmt.setDate(5, app.getDeadline() != null ? Date.valueOf(app.getDeadline()) : null);
            pstmt.setString(6, app.getNotes());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateApplication(Application app) {
        String sql = "UPDATE applications SET company=?, role=?, date_applied=?, status=?, deadline=?, notes=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, app.getCompany());
            pstmt.setString(2, app.getRole());
            pstmt.setDate(3, app.getDateApplied() != null ? Date.valueOf(app.getDateApplied()) : null);
            pstmt.setString(4, app.getStatus());
            pstmt.setDate(5, app.getDeadline() != null ? Date.valueOf(app.getDeadline()) : null);
            pstmt.setString(6, app.getNotes());
            pstmt.setInt(7, app.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteApplication(int id) {
        String sql = "DELETE FROM applications WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Application> getAllApplications() {
        return searchApplications("", "All");
    }

    @Override
    public List<Application> searchApplications(String query, String status) {
        List<Application> apps = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM applications WHERE (company LIKE ? OR role LIKE ?)");
        
        if (status != null && !status.equals("All")) {
            sql.append(" AND status = ?");
        }
        
        sql.append(" ORDER BY date_applied DESC");

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            String searchPattern = "%" + query + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            
            if (status != null && !status.equals("All")) {
                pstmt.setString(3, status);
            }
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCompany(rs.getString("company"));
                app.setRole(rs.getString("role"));
                Date dateApplied = rs.getDate("date_applied");
                if (dateApplied != null) app.setDateApplied(dateApplied.toLocalDate());
                app.setStatus(rs.getString("status"));
                Date deadline = rs.getDate("deadline");
                if (deadline != null) app.setDeadline(deadline.toLocalDate());
                app.setNotes(rs.getString("notes"));
                apps.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apps;
    }
}
