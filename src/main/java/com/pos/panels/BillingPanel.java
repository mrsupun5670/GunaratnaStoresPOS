package com.pos.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillingPanel extends JPanel {
    private JTextField customerNameField;
    private JComboBox<String> customerTypeCombo;
    private JTextField itemNameField;
    private JComboBox<String> itemTypeCombo;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField discountField;
    private JTable billTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private double totalAmount = 0.0;

    public BillingPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initializeComponents();
    }

    private void initializeComponents() {
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left Panel - Customer and Item Entry
        JPanel leftPanel = createLeftPanel();
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Right Panel - Bill Summary
        JPanel rightPanel = createRightPanel();
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Center Panel - Bill Items Table
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("ගුණරත්න සම්ට්රේජ්");
        titleLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("විකුණම් කරනවා පද්ධතිය");
        subtitleLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);

        // Current location and time
        JLabel locationLabel = new JLabel("අන. 123, මුන් යල, සන්සර");
        locationLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        locationLabel.setForeground(Color.GRAY);

        JLabel timeLabel = new JLabel("7/15/2025 1:41:25 PM");
        timeLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        timeLabel.setForeground(Color.GRAY);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.add(locationLabel, BorderLayout.NORTH);
        infoPanel.add(timeLabel, BorderLayout.SOUTH);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        leftPanel.setPreferredSize(new Dimension(400, 0));

        // Customer Section
        JPanel customerSection = createCustomerSection();
        leftPanel.add(customerSection, BorderLayout.NORTH);

        // Item Entry Section
        JPanel itemSection = createItemSection();
        leftPanel.add(itemSection, BorderLayout.CENTER);

        return leftPanel;
    }

    private JPanel createCustomerSection() {
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));
        customerPanel.setOpaque(false);
        customerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel customerTitle = new JLabel("පාරිසර කන්ගනවර");
        customerTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        customerPanel.add(customerTitle);
        customerPanel.add(Box.createVerticalStrut(15));

        // Customer Name
        JLabel nameLabel = new JLabel("පාරිසරකර නම");
        nameLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        customerPanel.add(nameLabel);

        customerNameField = new JTextField("පාරිසරකර නම අනසලන් සරත න");
        customerNameField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        customerNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        customerPanel.add(customerNameField);
        customerPanel.add(Box.createVerticalStrut(10));

        // Customer Type
        JLabel typeLabel = new JLabel("නිර්දේශ පාරිසරකර පරිර්වන");
        typeLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        customerPanel.add(typeLabel);

        customerTypeCombo = new JComboBox<>(new String[]{"නිර්දේශ පාරිසරකර ගරන්ත"});
        customerTypeCombo.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        customerPanel.add(customerTypeCombo);

        return customerPanel;
    }

    private JPanel createItemSection() {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setOpaque(false);

        JLabel itemTitle = new JLabel("නිර්මපදන් අඩුමල් නින්");
        itemTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        itemPanel.add(itemTitle);
        itemPanel.add(Box.createVerticalStrut(15));

        // Item Name
        JLabel itemNameLabel = new JLabel("සාරණිර");
        itemNameLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        itemPanel.add(itemNameLabel);

        itemNameField = new JTextField("සාරණිර සියො අනර කරන්න");
        itemNameField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        itemNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        itemPanel.add(itemNameField);
        itemPanel.add(Box.createVerticalStrut(10));

        // Item Type
        JLabel itemTypeLabel = new JLabel("නිර්මපදන");
        itemTypeLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        itemPanel.add(itemTypeLabel);

        itemTypeCombo = new JComboBox<>(new String[]{"නිර්මපදන අරින් ගරන්ර"});
        itemTypeCombo.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        itemPanel.add(itemTypeCombo);
        itemPanel.add(Box.createVerticalStrut(10));

        // Quantity and Price Row
        JPanel quantityPricePanel = new JPanel(new GridLayout(2, 2, 10, 5));
        quantityPricePanel.setOpaque(false);

        quantityPricePanel.add(new JLabel("ප්‍රමාණ"));
        quantityPricePanel.add(new JLabel("එකක මිල (රු.)"));

        quantityField = new JTextField("1");
        quantityField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        priceField = new JTextField("0.00");
        priceField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));

        quantityPricePanel.add(quantityField);
        quantityPricePanel.add(priceField);

        itemPanel.add(quantityPricePanel);
        itemPanel.add(Box.createVerticalStrut(10));

        // Discount
        JLabel discountLabel = new JLabel("වරණ (%)");
        discountLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        itemPanel.add(discountLabel);

        discountField = new JTextField("0");
        discountField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        itemPanel.add(discountField);
        itemPanel.add(Box.createVerticalStrut(15));

        // Add Item Button
        JButton addItemButton = new JButton("අරනම් සරදර කරන්න");
        addItemButton.setBackground(new Color(33, 150, 243));
        addItemButton.setForeground(Color.WHITE);
        addItemButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        addItemButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToBill();
            }
        });
        itemPanel.add(addItemButton);

        return itemPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel tableTitle = new JLabel("ගිණ් අධම");
        tableTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        centerPanel.add(tableTitle, BorderLayout.NORTH);

        // Create table
        String[] columnNames = {"ගධන නම", "ප්‍රමාණ", "එකක මිල", "වරණ", "මුළු මිල", "නිනම"};
        tableModel = new DefaultTableModel(columnNames, 0);
        billTable = new JTable(tableModel);
        billTable.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        billTable.getTableHeader().setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        billTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(billTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // No items message
        JLabel noItemsLabel = new JLabel("සමරම අධන නානර සර හයනර");
        noItemsLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        noItemsLabel.setForeground(Color.GRAY);
        noItemsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noItemsLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        centerPanel.add(noItemsLabel, BorderLayout.SOUTH);

        return centerPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        rightPanel.setPreferredSize(new Dimension(300, 0));

        JLabel summaryTitle = new JLabel("ගිණ් සාරගණ");
        summaryTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        rightPanel.add(summaryTitle, BorderLayout.NORTH);

        // Summary Panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setOpaque(false);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Summary rows
        summaryPanel.add(createSummaryRow("ලම එකාගසය:", "රු. 0.00"));
        summaryPanel.add(createSummaryRow("මුල් ටම්:", "රු. 0.00"));
        summaryPanel.add(Box.createVerticalStrut(15));

        // Total
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setOpaque(false);
        totalPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 0, 0, 0)
        ));

        JLabel totalTitleLabel = new JLabel("මුල් මිල:");
        totalTitleLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));

        totalLabel = new JLabel("රු. 0.00");
        totalLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 18));
        totalLabel.setForeground(new Color(33, 150, 243));

        totalPanel.add(totalTitleLabel, BorderLayout.WEST);
        totalPanel.add(totalLabel, BorderLayout.EAST);

        summaryPanel.add(totalPanel);
        summaryPanel.add(Box.createVerticalStrut(20));

        // Payment section
        JLabel paymentLabel = new JLabel("ගතරිම");
        paymentLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        summaryPanel.add(paymentLabel);
        summaryPanel.add(Box.createVerticalStrut(10));

        JLabel amountLabel = new JLabel("ගතරම ලම මුදල (රු.)");
        amountLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        summaryPanel.add(amountLabel);

        JTextField amountField = new JTextField("0.00");
        amountField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        summaryPanel.add(amountField);
        summaryPanel.add(Box.createVerticalStrut(10));

        JLabel changeLabel = new JLabel("ගරය:");
        changeLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        summaryPanel.add(changeLabel);

        JLabel changeAmount = new JLabel("රු. 0.00");
        changeAmount.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        summaryPanel.add(changeAmount);

        rightPanel.add(summaryPanel, BorderLayout.CENTER);

        // Action buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton printButton = new JButton("බිල්පත මුද්‍රණ කරන්න");
        printButton.setBackground(new Color(76, 175, 80));
        printButton.setForeground(Color.WHITE);
        printButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        printButton.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JButton clearButton = new JButton("මින්ම කරන්න");
        clearButton.setBackground(new Color(96, 125, 139));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        clearButton.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        clearButton.addActionListener(e -> clearBill());

        buttonPanel.add(printButton);
        buttonPanel.add(clearButton);

        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        return rightPanel;
    }

    private JPanel createSummaryRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));

        row.add(labelLabel, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);

        return row;
    }

    private void addItemToBill() {
        try {
            String itemName = itemNameField.getText().trim();
            if (itemName.isEmpty() || itemName.equals("සාරණිර සියො අනර කරන්න")) {
                JOptionPane.showMessageDialog(this, "කරුණාකර යම් නමක් ඇතුළත් කරන්න");
                return;
            }

            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            double discount = Double.parseDouble(discountField.getText());

            double itemTotal = quantity * price;
            double discountAmount = itemTotal * (discount / 100);
            double finalAmount = itemTotal - discountAmount;

            Object[] rowData = {
                    itemName,
                    quantity,
                    String.format("Rs. %.2f", price),
                    discount + "%",
                    String.format("Rs. %.2f", finalAmount),
                    "ධිපප්‍යිකර වරනසර ගයරන්ර"
            };

            tableModel.addRow(rowData);
            totalAmount += finalAmount;
            updateTotal();

            // Clear fields
            itemNameField.setText("සාරණිර සියො අනර කරන්න");
            quantityField.setText("1");
            priceField.setText("0.00");
            discountField.setText("0");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "කරුණාකර නිවරම් සංඛිත වම් ඇතුළත් කරන්න");
        }
    }

    private void updateTotal() {
        totalLabel.setText(String.format("රු. %.2f", totalAmount));
    }

    private void clearBill() {
        tableModel.setRowCount(0);
        totalAmount = 0.0;
        updateTotal();
        customerNameField.setText("පාරිසරකර නම අනසලන් සරත න");
        itemNameField.setText("සාරණිර සියො අනර කරන්න");
        quantityField.setText("1");
        priceField.setText("0.00");
        discountField.setText("0");
    }
}