package com.tracker.ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel totalLabel;
    private JLabel interviewLabel;
    private JLabel offerLabel;
    private JLabel rejectionRateLabel;

    public DashboardPanel() {
        setLayout(new GridLayout(1, 4, 20, 0));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 251)); // Light gray/blue background

        add(createCard("Total Applications", totalLabel = new JLabel("0"), new Color(63, 81, 181))); // Indigo
        add(createCard("Interviews", interviewLabel = new JLabel("0"), new Color(33, 150, 243))); // Blue
        add(createCard("Offers", offerLabel = new JLabel("0"), new Color(76, 175, 80))); // Green
        add(createCard("Rejection Rate", rejectionRateLabel = new JLabel("0%"), new Color(244, 67, 54))); // Red
    }

    private JPanel createCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 100, 100));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(accentColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    public void updateStats(int total, int interviews, int offers, double rejectionRate) {
        totalLabel.setText(String.valueOf(total));
        interviewLabel.setText(String.valueOf(interviews));
        offerLabel.setText(String.valueOf(offers));
        rejectionRateLabel.setText(String.format("%.1f%%", rejectionRate));
    }
}
