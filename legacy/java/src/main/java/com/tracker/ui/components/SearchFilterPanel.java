package com.tracker.ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SearchFilterPanel extends JPanel {
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    public SearchFilterPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setBackground(Color.WHITE);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Search company or role...");
        
        statusFilter = new JComboBox<>(new String[]{"All", "Applied", "Screening", "Interview", "Offer", "Rejected"});

        leftPanel.add(new JLabel("Search:"));
        leftPanel.add(searchField);
        leftPanel.add(new JLabel("Status:"));
        leftPanel.add(statusFilter);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(Color.WHITE);

        addButton = new JButton("Add Application");
        addButton.setBackground(new Color(63, 81, 181));
        addButton.setForeground(Color.WHITE);

        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        rightPanel.add(addButton);
        rightPanel.add(editButton);
        rightPanel.add(deleteButton);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    public JTextField getSearchField() { return searchField; }
    public JComboBox<String> getStatusFilter() { return statusFilter; }
    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getDeleteButton() { return deleteButton; }
}
