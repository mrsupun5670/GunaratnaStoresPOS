package com.pos.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePanel extends JFrame {

    // Main panels
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel rightHeaderPanel;
    private JPanel customerPanel;
    private JPanel itemPanel;
    private JPanel tablePanel;
    private JPanel totalPanel;
    private JPanel buttonPanel;

    // Header components
    private JLabel titleLabel;
    private JLabel addressLabel;
    private JLabel invoiceLabel;
    private JLabel dateTimeLabel;

    // Customer section components
    private JTextField customerNameField;
    private JComboBox<String> customerTypeCombo;

    // Summary components (right side)
    private JLabel subtotalValueLabel;
    private JLabel discountValueLabel;
    private JLabel totalValueLabel;

    // Item section components
    private JTextField itemNameField;
    private JComboBox<String> itemTypeCombo;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField discountPercentField;
    private JButton addItemButton;

    // Table
    private JTable itemTable;
    private DefaultTableModel tableModel;

    // Total section
    private JTextField finalAmountField;
    private JLabel grandTotalLabel;

    // Action buttons
    private JButton printButton;
    private JButton deleteButton;

    // Font for Sinhala text
    private Font sinhalaFont;

    public HomePanel() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultValues();
    }

    private void initializeComponents() {
        // Try to load a Unicode font that supports Sinhala
        try {
            sinhalaFont = new Font("Iskoola Pota", Font.PLAIN, 12);
        } catch (Exception e) {
            sinhalaFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        }

        // Initialize main frame
        setTitle("ගුණරත්න සේවා (ගැ) - Billing System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Customer panel
        customerPanel = new JPanel(new GridBagLayout());
        customerPanel.setBackground(Color.WHITE);
        customerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Item panel
        itemPanel = new JPanel(new GridBagLayout());
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Summary panel (right side)
        totalPanel = new JPanel(new GridBagLayout());
        totalPanel.setBackground(Color.WHITE);
        totalPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Customer section components
        customerNameField = new JTextField(20);
        customerNameField.setFont(sinhalaFont);
        customerNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        customerTypeCombo = new JComboBox<>(new String[]{"ණික පරීක්ෂනිත නිවරන"});
        customerTypeCombo.setFont(sinhalaFont);
        customerTypeCombo.setPreferredSize(new Dimension(200, 35));

        // Summary labels
        subtotalValueLabel = new JLabel("රු. 0.00");
        subtotalValueLabel.setFont(sinhalaFont);
        subtotalValueLabel.setForeground(new Color(128, 128, 128));

        discountValueLabel = new JLabel("රු. 0.00");
        discountValueLabel.setFont(sinhalaFont);
        discountValueLabel.setForeground(new Color(0, 128, 0));

        totalValueLabel = new JLabel("රු. 0.00");
        totalValueLabel.setFont(new Font(sinhalaFont.getName(), Font.BOLD, 16));
        totalValueLabel.setForeground(new Color(0, 100, 200));

        // Item section components
        itemNameField = new JTextField(15);
        itemNameField.setFont(sinhalaFont);
        itemNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        itemTypeCombo = new JComboBox<>(new String[]{"නික්ෂල්යකරණ අනිත නිවරන"});
        itemTypeCombo.setFont(sinhalaFont);
        itemTypeCombo.setPreferredSize(new Dimension(180, 35));

        quantityField = new JTextField("1", 8);
        quantityField.setFont(sinhalaFont);
        quantityField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        priceField = new JTextField("0.00", 10);
        priceField.setFont(sinhalaFont);
        priceField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        discountPercentField = new JTextField("0", 8);
        discountPercentField.setFont(sinhalaFont);
        discountPercentField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        addItemButton = new JButton("අයිතම එක කරන්න");
        addItemButton.setFont(sinhalaFont);
        addItemButton.setBackground(new Color(66, 133, 244));
        addItemButton.setForeground(Color.WHITE);
        addItemButton.setFocusPainted(false);
        addItemButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addItemButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Table setup
        String[] columnNames = {"අයිතම නම", "ප්රමාණ", "ඒකක මිල", "වට්ටම", "මුළු මිල", "ක්රියාව"};
        tableModel = new DefaultTableModel(columnNames, 0);
        itemTable = new JTable(tableModel);
        itemTable.setFont(sinhalaFont);
        itemTable.setRowHeight(35);
        itemTable.getTableHeader().setFont(new Font(sinhalaFont.getName(), Font.BOLD, 12));
        itemTable.getTableHeader().setBackground(new Color(248, 249, 250));
        itemTable.setSelectionBackground(new Color(232, 240, 254));

        // Total section
        finalAmountField = new JTextField("0.00", 10);
        finalAmountField.setFont(sinhalaFont);
        finalAmountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        grandTotalLabel = new JLabel("රු. 0.00");
        grandTotalLabel.setFont(new Font(sinhalaFont.getName(), Font.BOLD, 16));

        // Action buttons
        printButton = new JButton("බිල්පත මුද්රනය කරන්න");
        printButton.setFont(sinhalaFont);
        printButton.setBackground(new Color(52, 168, 83));
        printButton.setForeground(Color.WHITE);
        printButton.setFocusPainted(false);
        printButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        printButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        deleteButton = new JButton("බිල්පත මකන්න");
        deleteButton.setFont(sinhalaFont);
        deleteButton.setBackground(new Color(95, 99, 104));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupLayout() {
        GridBagConstraints gbc = new GridBagConstraints();

        // Customer section layout
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        customerPanel.add(new JLabel("පාරිභோගික නාමය:", SwingConstants.LEFT) {{
            setFont(new Font(sinhalaFont.getName(), Font.BOLD, 14));
        }}, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        customerPanel.add(new JLabel("මිල පාරිභෝගික:", SwingConstants.LEFT) {{
            setFont(new Font(sinhalaFont.getName(), Font.BOLD, 14));
        }}, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        customerPanel.add(customerNameField, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.gridwidth = 1;
        customerPanel.add(customerTypeCombo, gbc);

        // Summary section (right side)
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 4; gbc.gridy = 0;
        customerPanel.add(new JLabel("මිල් සාරංශ:", SwingConstants.LEFT) {{
            setFont(new Font(sinhalaFont.getName(), Font.BOLD, 14));
        }}, gbc);

        gbc.gridx = 4; gbc.gridy = 1;
        customerPanel.add(new JLabel("උප එකතුව:") {{ setFont(sinhalaFont); }}, gbc);
        gbc.gridx = 5; gbc.gridy = 1;
        customerPanel.add(subtotalValueLabel, gbc);

        gbc.gridx = 4; gbc.gridy = 2;
        customerPanel.add(new JLabel("මුළු වට්ටම:") {{ setFont(sinhalaFont); }}, gbc);
        gbc.gridx = 5; gbc.gridy = 2;
        customerPanel.add(discountValueLabel, gbc);

        gbc.gridx = 4; gbc.gridy = 3;
        customerPanel.add(new JLabel("මුළු මිල:", SwingConstants.LEFT) {{
            setFont(new Font(sinhalaFont.getName(), Font.BOLD, 14));
            setForeground(new Color(0, 100, 200));
        }}, gbc);
        gbc.gridx = 5; gbc.gridy = 3;
        customerPanel.add(totalValueLabel, gbc);

        // Item section layout
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 0;
        itemPanel.add(new JLabel("නිෂ්පාදන අයිතම කරීම:", SwingConstants.LEFT) {{
            setFont(new Font(sinhalaFont.getName(), Font.BOLD, 14));
        }}, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        itemPanel.add(new JLabel("නාරකතය") {{ setFont(sinhalaFont); }}, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        itemPanel.add(new JLabel("නික්ෂල්යකරණ") {{ setFont(sinhalaFont); }}, gbc);
        gbc.gridx = 2; gbc.gridy = 1;
        itemPanel.add(new JLabel("ප්රමාණවත") {{ setFont(sinhalaFont); }}, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        itemPanel.add(itemNameField, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        itemPanel.add(itemTypeCombo, gbc);
        gbc.gridx = 2; gbc.gridy = 2;
        itemPanel.add(quantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        itemPanel.add(new JLabel("ඒකක මිල (රු.)") {{ setFont(sinhalaFont); }}, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        itemPanel.add(new JLabel("වට්ටම (%)") {{ setFont(sinhalaFont); }}, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        itemPanel.add(priceField, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        itemPanel.add(discountPercentField, gbc);
        gbc.gridx = 2; gbc.gridy = 4;
        itemPanel.add(addItemButton, gbc);

        // Table section
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel tableTitle = new JLabel("බිල් අයිතම");
        tableTitle.setFont(new Font(sinhalaFont.getName(), Font.BOLD, 14));
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JLabel emptyTableMessage = new JLabel("තවම අයිතම එකතු කර නැත", SwingConstants.CENTER);
        emptyTableMessage.setFont(sinhalaFont);
        emptyTableMessage.setForeground(Color.GRAY);
        tablePanel.add(emptyTableMessage, BorderLayout.SOUTH);

        // Final total and buttons section
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0; gbc.gridy = 0;
        buttonPanel.add(new JLabel("නිගමන:", SwingConstants.LEFT) {{
            setFont(new Font(sinhalaFont.getName(), Font.BOLD, 14));
        }}, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        buttonPanel.add(new JLabel("නිගමන ගණ මිලදෑ (රු.)") {{ setFont(sinhalaFont); }}, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        buttonPanel.add(finalAmountField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        buttonPanel.add(new JLabel("නිර්මාණ:") {{ setFont(sinhalaFont); }}, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        buttonPanel.add(grandTotalLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 10, 0);
        buttonPanel.add(printButton, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(deleteButton, gbc);

        // Main layout
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(customerPanel, BorderLayout.NORTH);
        leftPanel.add(itemPanel, BorderLayout.CENTER);
        leftPanel.add(tablePanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(totalPanel, BorderLayout.NORTH);
        rightPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(leftPanel, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void setupEventHandlers() {
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToTable();
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomePanel.this,
                        "බිල්පත මුද්රනය සඳහා යවන ලදී!",
                        "මුද්රනය",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBill();
            }
        });
    }

    private void addItemToTable() {
        try {
            String itemName = itemNameField.getText().trim();
            if (itemName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "කරුණාකර අයිතම නම ඇතුළත් කරන්න!", "දෝෂය", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String itemType = (String) itemTypeCombo.getSelectedItem();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            double discountPercent = Double.parseDouble(discountPercentField.getText());

            double subtotal = quantity * price;
            double discountAmount = subtotal * (discountPercent / 100);
            double total = subtotal - discountAmount;

            Object[] row = {
                    itemName,
                    String.valueOf(quantity),
                    String.format("%.2f", price),
                    String.format("%.1f%%", discountPercent),
                    String.format("%.2f", total),
                    "මකන්න"
            };

            tableModel.addRow(row);
            updateTotals();
            clearItemFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "කරුණාකර වලංගු සංඛ්යා ඇතුළත් කරන්න!", "දෝෂය", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTotals() {
        double subtotal = 0.0;
        double totalDiscount = 0.0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            double itemTotal = Double.parseDouble(tableModel.getValueAt(i, 4).toString());
            subtotal += itemTotal;
        }

        subtotalValueLabel.setText(String.format("රු. %.2f", subtotal));
        discountValueLabel.setText(String.format("රු. %.2f", totalDiscount));
        totalValueLabel.setText(String.format("රු. %.2f", subtotal));
        finalAmountField.setText(String.format("%.2f", subtotal));
        grandTotalLabel.setText(String.format("රු. %.2f", subtotal));
    }

    private void clearItemFields() {
        itemNameField.setText("");
        quantityField.setText("1");
        priceField.setText("0.00");
        discountPercentField.setText("0");
    }

    private void clearBill() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "ඔබට බිල්පත මකා දැමීමට අවශ්යද?",
                "තහවුරු කරන්න",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setRowCount(0);
            customerNameField.setText("");
            clearItemFields();
            updateTotals();
        }
    }

    private void setDefaultValues() {
        customerNameField.setText("පාරිභොගිකයාගේ නම ඇතුළත් කරන්න");
        updateTotals();
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

//    public static void main(String[] args) {
//        // Set system look and feel
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new HomePanel().setVisible(true);
//            }
//        });
//    }
}