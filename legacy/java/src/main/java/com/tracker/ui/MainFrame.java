package com.tracker.ui;

import com.formdev.flatlaf.FlatLightLaf;
import com.tracker.dao.ApplicationDAO;
import com.tracker.dao.ApplicationDAOImpl;
import com.tracker.model.Application;
import com.tracker.ui.components.DashboardPanel;
import com.tracker.ui.components.SearchFilterPanel;
import com.tracker.ui.components.TablePanel;
import com.tracker.ui.dialogs.ApplicationFormDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class MainFrame extends JFrame {
    private ApplicationDAO dao;
    private DashboardPanel dashboardPanel;
    private SearchFilterPanel searchFilterPanel;
    private TablePanel tablePanel;

    public MainFrame() {
        dao = new ApplicationDAOImpl();
        
        setTitle("Job Application Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Header / Dashboard
        dashboardPanel = new DashboardPanel();
        add(dashboardPanel, BorderLayout.NORTH);

        // Content (Search + Table)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        searchFilterPanel = new SearchFilterPanel();
        contentPanel.add(searchFilterPanel, BorderLayout.NORTH);
        
        tablePanel = new TablePanel();
        contentPanel.add(tablePanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        setupListeners();
        refreshData();
    }

    private void setupListeners() {
        // Search & Filter
        searchFilterPanel.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                refreshData();
            }
        });
        
        searchFilterPanel.getStatusFilter().addActionListener(e -> refreshData());

        // Actions
        searchFilterPanel.getAddButton().addActionListener(e -> onAdd());
        searchFilterPanel.getEditButton().addActionListener(e -> onEdit());
        searchFilterPanel.getDeleteButton().addActionListener(e -> onDelete());
    }

    private void refreshData() {
        String query = searchFilterPanel.getSearchField().getText().trim();
        String status = (String) searchFilterPanel.getStatusFilter().getSelectedItem();
        
        List<Application> apps = dao.searchApplications(query, status);
        tablePanel.setApplications(apps);
        
        updateDashboard(apps);
    }

    private void updateDashboard(List<Application> apps) {
        int total = apps.size();
        int interviews = 0;
        int offers = 0;
        int rejected = 0;

        for (Application app : apps) {
            switch (app.getStatus()) {
                case "Interview": interviews++; break;
                case "Offer": offers++; break;
                case "Rejected": rejected++; break;
            }
        }

        double rejectionRate = total > 0 ? (double) rejected / total * 100 : 0;
        dashboardPanel.updateStats(total, interviews, offers, rejectionRate);
    }

    private void onAdd() {
        ApplicationFormDialog dialog = new ApplicationFormDialog(this, "Add New Application", null);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            dao.addApplication(dialog.getApplication());
            refreshData();
        }
    }

    private void onEdit() {
        int id = tablePanel.getSelectedId();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "Please select an application to edit.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Find the application in the current list
        Application selectedApp = null;
        List<Application> currentList = dao.getAllApplications();
        for (Application app : currentList) {
            if (app.getId() == id) {
                selectedApp = app;
                break;
            }
        }

        if (selectedApp != null) {
            ApplicationFormDialog dialog = new ApplicationFormDialog(this, "Edit Application", selectedApp);
            dialog.setVisible(true);
            if (dialog.isSucceeded()) {
                dao.updateApplication(dialog.getApplication());
                refreshData();
            }
        }
    }

    private void onDelete() {
        int id = tablePanel.getSelectedId();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "Please select an application to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this application?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dao.deleteApplication(id);
            refreshData();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();
            new MainFrame().setVisible(true);
        });
    }
}
