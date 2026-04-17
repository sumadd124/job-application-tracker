package com.tracker.ui.dialogs;

import com.tracker.model.Application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ApplicationFormDialog extends JDialog {
    private JTextField companyField;
    private JTextField roleField;
    private JTextField dateAppliedField;
    private JComboBox<String> statusBox;
    private JTextField deadlineField;
    private JTextArea notesArea;
    private boolean succeeded = false;
    private Application application;

    public ApplicationFormDialog(Frame parent, String title, Application app) {
        super(parent, title, true);
        this.application = app != null ? app : new Application();
        
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);

        // Company
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Company:"), gbc);
        gbc.gridx = 1;
        companyField = new JTextField(application.getCompany(), 20);
        mainPanel.add(companyField, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        roleField = new JTextField(application.getRole(), 20);
        mainPanel.add(roleField, gbc);

        // Date Applied
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Date Applied (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        String dateStr = application.getDateApplied() != null ? application.getDateApplied().toString() : LocalDate.now().toString();
        dateAppliedField = new JTextField(dateStr, 20);
        mainPanel.add(dateAppliedField, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        statusBox = new JComboBox<>(new String[]{"Applied", "Screening", "Interview", "Offer", "Rejected"});
        statusBox.setSelectedItem(application.getStatus() != null ? application.getStatus() : "Applied");
        mainPanel.add(statusBox, gbc);

        // Deadline
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Deadline (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        String deadlineStr = application.getDeadline() != null ? application.getDeadline().toString() : "";
        deadlineField = new JTextField(deadlineStr, 20);
        mainPanel.add(deadlineField, gbc);

        // Notes
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1;
        notesArea = new JTextArea(4, 20);
        notesArea.setText(application.getNotes());
        notesArea.setLineWrap(true);
        mainPanel.add(new JScrollPane(notesArea), gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private void onSave() {
        if (companyField.getText().trim().isEmpty() || roleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Company and Role are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            application.setCompany(companyField.getText().trim());
            application.setRole(roleField.getText().trim());
            application.setDateApplied(LocalDate.parse(dateAppliedField.getText().trim()));
            application.setStatus((String) statusBox.getSelectedItem());
            if (!deadlineField.getText().trim().isEmpty()) {
                application.setDeadline(LocalDate.parse(deadlineField.getText().trim()));
            } else {
                application.setDeadline(null);
            }
            application.setNotes(notesArea.getText().trim());
            
            succeeded = true;
            dispose();
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSucceeded() { return succeeded; }
    public Application getApplication() { return application; }
}
