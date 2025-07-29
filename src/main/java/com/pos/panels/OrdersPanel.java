package com.pos.panels;

import javax.swing.*;
import java.awt.*;

public class OrdersPanel extends JPanel {

    public OrdersPanel() {
        setLayout(new BorderLayout());
        
        initializeComponents();
    }

    private void initializeComponents() {
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Statistics Panel
        JPanel statsPanel = createStatsPanel();
        mainPanel.add(statsPanel, BorderLayout.NORTH);

        // Charts Panel
        JPanel chartsPanel = createChartsPanel();
        mainPanel.add(chartsPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("ගුණරත්න වෙළඳසැල");
        titleLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("නවීන POS පද්ධතිය");
        subtitleLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        return headerPanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Create stat cards
        statsPanel.add(createStatCard("අද අළුutul", "Rs. 25,800", new Color(255, 193, 7)));
        statsPanel.add(createStatCard("ගණනය යම්", "45", new Color(33, 150, 243)));
        statsPanel.add(createStatCard("වීම්jä පාණුම", "128", new Color(76, 175, 80)));
        statsPanel.add(createStatCard("සම්පාම වික්‍රිම", "Rs. 573", new Color(156, 39, 176)));

        return statsPanel;
    }

    private JPanel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 18));
        valueLabel.setForeground(Color.BLACK);

        // Add accent color indicator
        JPanel indicatorPanel = new JPanel();
        indicatorPanel.setBackground(accentColor);
        indicatorPanel.setPreferredSize(new Dimension(4, 0));

        card.add(indicatorPanel, BorderLayout.WEST);
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createChartsPanel() {
        JPanel chartsPanel = new JPanel(new BorderLayout());
        chartsPanel.setOpaque(false);

        // Sales Chart Panel
        JPanel salesChartPanel = createSalesChartPanel();
        chartsPanel.add(salesChartPanel, BorderLayout.CENTER);

        // Bottom panels
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel monthlyPanel = createMonthlyPanel();
        JPanel categoryPanel = createCategoryPanel();

        bottomPanel.add(monthlyPanel);
        bottomPanel.add(categoryPanel);

        chartsPanel.add(bottomPanel, BorderLayout.SOUTH);

        return chartsPanel;
    }

    private JPanel createSalesChartPanel() {
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel chartTitle = new JLabel("පාට්මණන්ත වියදම් ප්‍රවාරරණ");
        chartTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        chartPanel.add(chartTitle, BorderLayout.NORTH);

        // Simulated bar chart
        JPanel barsPanel = new JPanel(new GridLayout(7, 1, 0, 5));
        barsPanel.setOpaque(false);
        barsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        String[] categories = {"සදු", "අනෛකර්වර", "බද", "ක්‍රාගනතන්ත", "සිසරට", "ගපග්‍රර", "ගරිුර"};
        int[] values = {18500, 22300, 19700, 26800, 31200, 28900, 25800};
        int maxValue = 35000;

        for (int i = 0; i < categories.length; i++) {
            JPanel barRow = createBarRow(categories[i], values[i], maxValue);
            barsPanel.add(barRow);
        }

        chartPanel.add(barsPanel, BorderLayout.CENTER);

        return chartPanel;
    }

    private JPanel createBarRow(String category, int value, int maxValue) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setPreferredSize(new Dimension(0, 35));

        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        categoryLabel.setPreferredSize(new Dimension(80, 0));

        JPanel barContainer = new JPanel(new BorderLayout());
        barContainer.setOpaque(false);
        barContainer.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JPanel bar = new JPanel();
        bar.setBackground(new Color(33, 150, 243));
        bar.setOpaque(true);

        int barWidth = (int) ((double) value / maxValue * 400);
        bar.setPreferredSize(new Dimension(barWidth, 15));

        JLabel valueLabel = new JLabel("Rs. " + String.format("%,d", value));
        valueLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 11));
        valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        barContainer.add(bar, BorderLayout.WEST);
        barContainer.add(valueLabel, BorderLayout.CENTER);

        row.add(categoryLabel, BorderLayout.WEST);
        row.add(barContainer, BorderLayout.CENTER);

        return row;
    }

    private JPanel createMonthlyPanel() {
        JPanel monthlyPanel = new JPanel(new BorderLayout());
        monthlyPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("මාසික සාරගණ්ක");
        title.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        monthlyPanel.add(title, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        statsPanel.add(createMonthlyStatRow("මුල් අධ මම:", "Rs. 486,500"));
        statsPanel.add(createMonthlyStatRow("මුල් ගණ්ත:", "892"));
        statsPanel.add(createMonthlyStatRow("වියදම්:", "Rs. 342,100"));
        statsPanel.add(createMonthlyStatRow("අධි ප්‍රමාර:", "Rs. 144,400", new Color(76, 175, 80)));

        monthlyPanel.add(statsPanel, BorderLayout.CENTER);

        return monthlyPanel;
    }

    private JPanel createMonthlyStatRow(String label, String value) {
        return createMonthlyStatRow(label, value, Color.BLACK);
    }

    private JPanel createMonthlyStatRow(String label, String value, Color valueColor) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        valueLabel.setForeground(valueColor);

        row.add(labelLabel, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);

        return row;
    }

    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel title = new JLabel("ජනප්‍රිය සාණ්ර්");
        title.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        categoryPanel.add(title, BorderLayout.NORTH);

        JPanel itemsPanel = new JPanel(new GridLayout(5, 1, 0, 8));
        itemsPanel.setOpaque(false);
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        itemsPanel.add(createCategoryRow("ජුර සමිර", "145 විකම්", "Rs. 21,750"));
        itemsPanel.add(createCategoryRow("සද සිර්", "128 විකම්", "Rs. 23,040"));
        itemsPanel.add(createCategoryRow("කර් පණ්දර්", "89 විකම්", "Rs. 75,650"));
        itemsPanel.add(createCategoryRow("ගතර්", "67 විකම්", "Rs. 30,150"));
        itemsPanel.add(createCategoryRow("රර මතর්", "56 විකම්", "Rs. 6,720"));

        categoryPanel.add(itemsPanel, BorderLayout.CENTER);

        return categoryPanel;
    }

    private JPanel createCategoryRow(String item, String quantity, String amount) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);

        JLabel itemLabel = new JLabel(item);
        itemLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));

        JLabel quantityLabel = new JLabel(quantity);
        quantityLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 10));
        quantityLabel.setForeground(Color.GRAY);

        leftPanel.add(itemLabel, BorderLayout.NORTH);
        leftPanel.add(quantityLabel, BorderLayout.SOUTH);

        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        amountLabel.setForeground(new Color(76, 175, 80));

        row.add(leftPanel, BorderLayout.WEST);
        row.add(amountLabel, BorderLayout.EAST);

        return row;
    }
}