package com.pos.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReportsPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable reportsTable;
    private JComboBox<String> statusFilter;
    private JComboBox<String> typeFilter;

    public ReportsPanel() {
        setLayout(new BorderLayout());
        
        initializeComponents();
        loadSampleData();
    }

    private void initializeComponents() {
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Statistics Panel
        JPanel statsPanel = createStatsPanel();
        mainPanel.add(statsPanel, BorderLayout.NORTH);

        // Filter Panel
        JPanel filterPanel = createFilterPanel();
        mainPanel.add(filterPanel, BorderLayout.CENTER);

        // Table Panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.SOUTH);

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
        statsPanel.add(createStatCard("මුල් ගිණුම්", "6", new Color(33, 150, 243)));
        statsPanel.add(createStatCard("මුල් අදටම", "Rs. 15,000", new Color(255, 193, 7)));
        statsPanel.add(createStatCard("මුළු වියදම්", "Rs. 9,850", new Color(76, 175, 80)));
        statsPanel.add(createStatCard("නිර් වියදම්", "Rs. 5,150", new Color(156, 39, 176)));

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

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Filter title and controls
        JPanel topFilterPanel = new JPanel(new BorderLayout());
        topFilterPanel.setOpaque(false);

        JLabel filterTitle = new JLabel("ගපරන්");
        filterTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));

        JPanel filterControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterControls.setOpaque(false);

        // Search field
        JTextField searchField = new JTextField("ගිණුම් අධ නම අමර ගණනකරුවර අම්...");
        searchField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        searchField.setForeground(Color.GRAY);
        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Status filter
        statusFilter = new JComboBox<>(new String[]{"සියලුම"});
        statusFilter.setFont(new Font("Arial Unicode MS", Font.PLAIN, 11));
        statusFilter.setPreferredSize(new Dimension(100, 30));

        // Type filter
        typeFilter = new JComboBox<>(new String[]{"සියලුම"});
        typeFilter.setFont(new Font("Arial Unicode MS", Font.PLAIN, 11));
        typeFilter.setPreferredSize(new Dimension(100, 30));

        filterControls.add(searchField);
        filterControls.add(new JLabel("තත්ත්වය:"));
        filterControls.add(statusFilter);
        filterControls.add(new JLabel("වර්ගය:"));
        filterControls.add(typeFilter);

        topFilterPanel.add(filterTitle, BorderLayout.WEST);
        topFilterPanel.add(filterControls, BorderLayout.EAST);

        filterPanel.add(topFilterPanel, BorderLayout.NORTH);

        return filterPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);

        JLabel tableTitle = new JLabel("වි්පස් ගින්හගර");
        tableTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        // Create table
        String[] columnNames = {"ගිණුම් කේතය", "තාරිඛය", "පාරිබඕගිකයා", "සමුදාය", "තත්ත්වය", "ක්‍රියා"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only action column
            }
        };

        reportsTable = new JTable(tableModel);
        reportsTable.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        reportsTable.getTableHeader().setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        reportsTable.setRowHeight(50);
        reportsTable.setShowGrid(false);
        reportsTable.setIntercellSpacing(new Dimension(0, 1));

        // Custom renderer for status column
        reportsTable.getColumnModel().getColumn(4).setCellRenderer(new StatusRenderer());

        // Custom renderer for action buttons
        reportsTable.getColumnModel().getColumn(5).setCellRenderer(new ActionButtonRenderer());
        reportsTable.getColumnModel().getColumn(5).setCellEditor(new ActionButtonEditor(new JCheckBox()));

        // Set column widths
        reportsTable.getColumnModel().getColumn(0).setPreferredWidth(120); // Bill code
        reportsTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Date
        reportsTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Customer
        reportsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Amount
        reportsTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Status
        reportsTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Actions

        JScrollPane scrollPane = new JScrollPane(reportsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        scrollPane.setPreferredSize(new Dimension(0, 300));

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void loadSampleData() {
        Object[][] sampleData = {
                {"B001-240123-001", "2024-01-23 | 14:30", "පුන්චී ගුණරත්න", "Rs. 2,850", "මුළු", ""},
                {"B001-240123-002", "2024-01-23 | 15:45", "නමල පිය්සේන", "Rs. 1,750", "නිර", ""},
                {"B001-240122-015", "2024-01-22 | 16:20", "ප්‍රිරත්න සිරිඒරා", "Rs. 4,200", "මුළු", ""},
                {"B001-240122-014", "2024-01-22 | 12:15", "නිමල් සප්තපර", "Rs. 950", "මුළු", ""},
                {"B001-240121-009", "2024-01-21 | 10:30", "සරත් චරිතසේන", "Rs. 3,400", "නිර", ""},
                {"B001-240120-003", "2024-01-20 | 09:45", "රතර් ගුණරත්න", "Rs. 1,850", "මුළු", ""}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    // Custom renderer for status column
    class StatusRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private JLabel statusLabel;

        public StatusRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
            statusLabel = new JLabel();
            statusLabel.setOpaque(true);
            statusLabel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
            statusLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 10));
            add(statusLabel);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            String status = (String) value;
            statusLabel.setText(status);

            if ("මුළු".equals(status)) {
                statusLabel.setBackground(new Color(76, 175, 80));
                statusLabel.setForeground(Color.WHITE);
            } else if ("නිර".equals(status)) {
                statusLabel.setBackground(new Color(255, 152, 0));
                statusLabel.setForeground(Color.WHITE);
            }

            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }

    // Custom renderer for action buttons
    class ActionButtonRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private JButton viewButton;
        private JButton printButton;

        public ActionButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

            viewButton = new JButton("👁");
            viewButton.setBackground(new Color(33, 150, 243));
            viewButton.setForeground(Color.WHITE);
            viewButton.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
            viewButton.setFont(new Font("Arial", Font.PLAIN, 12));
            viewButton.setToolTipText("බලන්න");

            printButton = new JButton("🖨");
            printButton.setBackground(new Color(76, 175, 80));
            printButton.setForeground(Color.WHITE);
            printButton.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
            printButton.setFont(new Font("Arial", Font.PLAIN, 12));
            printButton.setToolTipText("මුද්‍රණය කරන්න");

            add(viewButton);
            add(printButton);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }

    // Custom editor for action buttons
    class ActionButtonEditor extends DefaultCellEditor {
        protected JPanel panel;
        protected JButton viewButton;
        protected JButton printButton;
        private int currentRow;

        public ActionButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

            viewButton = new JButton("👁");
            viewButton.setBackground(new Color(33, 150, 243));
            viewButton.setForeground(Color.WHITE);
            viewButton.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
            viewButton.setFont(new Font("Arial", Font.PLAIN, 12));
            viewButton.addActionListener(e -> {
                showBillDetails(currentRow);
                fireEditingStopped();
            });

            printButton = new JButton("🖨");
            printButton.setBackground(new Color(76, 175, 80));
            printButton.setForeground(Color.WHITE);
            printButton.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
            printButton.setFont(new Font("Arial", Font.PLAIN, 12));
            printButton.addActionListener(e -> {
                printBill(currentRow);
                fireEditingStopped();
            });

            panel.add(viewButton);
            panel.add(printButton);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        public Object getCellEditorValue() {
            return "";
        }

        private void showBillDetails(int row) {
            String billCode = (String) tableModel.getValueAt(row, 0);
            String customer = (String) tableModel.getValueAt(row, 2);
            String amount = (String) tableModel.getValueAt(row, 3);

            JOptionPane.showMessageDialog(panel,
                    "ගිණුම් කේතය: " + billCode + "\n" +
                            "පාරිභෝගිකයා: " + customer + "\n" +
                            "සමුදාය: " + amount,
                    "ගිණුම් විස්තර",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        private void printBill(int row) {
            String billCode = (String) tableModel.getValueAt(row, 0);
            JOptionPane.showMessageDialog(panel,
                    "ගිණුම් " + billCode + " මුද්‍රණය කරනු ලැබේ...",
                    "මුද්‍රණය",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}