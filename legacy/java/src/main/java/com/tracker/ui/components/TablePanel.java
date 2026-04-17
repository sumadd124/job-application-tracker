package com.tracker.ui.components;

import com.tracker.model.Application;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public TablePanel() {
        setLayout(new BorderLayout());
        
        String[] columns = {"ID", "Company", "Role", "Date Applied", "Status", "Deadline", "Notes"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Hide ID column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        table.setDefaultRenderer(Object.class, new StatusRowRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setApplications(List<Application> apps) {
        tableModel.setRowCount(0);
        for (Application app : apps) {
            tableModel.addRow(new Object[]{
                app.getId(),
                app.getCompany(),
                app.getRole(),
                app.getDateApplied(),
                app.getStatus(),
                app.getDeadline(),
                app.getNotes()
            });
        }
    }

    public JTable getTable() {
        return table;
    }

    public int getSelectedId() {
        int row = table.getSelectedRow();
        if (row != -1) {
            return (int) table.getValueAt(row, 0);
        }
        return -1;
    }

    private static class StatusRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            String status = (String) table.getValueAt(row, 4);
            
            if (!isSelected) {
                switch (status) {
                    case "Offer":
                        c.setBackground(new Color(232, 245, 233)); // Very light green
                        c.setForeground(new Color(46, 125, 50));
                        break;
                    case "Rejected":
                        c.setBackground(new Color(255, 235, 238)); // Very light red
                        c.setForeground(new Color(198, 40, 40));
                        break;
                    case "Interview":
                        c.setBackground(new Color(227, 242, 253)); // Very light blue
                        c.setForeground(new Color(21, 101, 192));
                        break;
                    case "Screening":
                        c.setBackground(new Color(255, 248, 225)); // Very light yellow
                        c.setForeground(new Color(255, 143, 0));
                        break;
                    default:
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                        break;
                }
            }
            
            setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            return c;
        }
    }
}
