package com.pos.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable inventoryTable;
    private JTextField searchField;
    private JLabel lowStockLabel;

    public InventoryPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initializeComponents();
        loadSampleData();
    }

    private void initializeComponents() {
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top Panel with Add Button and Search
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Bottom Panel with Low Stock Warning
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

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

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Title and Add Button
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel inventoryTitle = new JLabel("භාණ්ඩ කළමනාකරණය");
        inventoryTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 18));

        JLabel totalItemsLabel = new JLabel("මුළු භාණ්ඩ: 5");
        totalItemsLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        totalItemsLabel.setForeground(Color.GRAY);

        JPanel titleInfo = new JPanel(new BorderLayout());
        titleInfo.setOpaque(false);
        titleInfo.add(inventoryTitle, BorderLayout.NORTH);
        titleInfo.add(totalItemsLabel, BorderLayout.SOUTH);

        JButton addButton = new JButton("+ නව භාණ්ඩයක් එකතු කරන්න");
        addButton.setBackground(new Color(33, 150, 243));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        addButton.addActionListener(e -> showAddItemDialog());

        titlePanel.add(titleInfo, BorderLayout.WEST);
        titlePanel.add(addButton, BorderLayout.EAST);

        topPanel.add(titlePanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        searchField = new JTextField("භාණ්ඩයක් නම් ගොත් ගණනකරුවර අම්...");
        searchField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Filter dropdowns
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setOpaque(false);

        JComboBox<String> categoryFilter = new JComboBox<>(new String[]{"සියලුම"});
        categoryFilter.setFont(new Font("Arial Unicode MS", Font.PLAIN, 11));

        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"සියලුම"});
        statusFilter.setFont(new Font("Arial Unicode MS", Font.PLAIN, 11));

        filterPanel.add(new JLabel("කාණ්ඩ:"));
        filterPanel.add(categoryFilter);
        filterPanel.add(new JLabel("තත්ත්වය:"));
        filterPanel.add(statusFilter);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(filterPanel, BorderLayout.EAST);

        topPanel.add(searchPanel, BorderLayout.SOUTH);

        return topPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);

        JLabel tableTitle = new JLabel("භාණ්ඩ ලැයිස්තුව");
        tableTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        // Create table
        String[] columnNames = {"භාණ්ඩ ලැයිස්තුව", "", "", "", "", ""};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; // Only edit and delete columns
            }
        };

        inventoryTable = new JTable(tableModel);
        inventoryTable.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        inventoryTable.getTableHeader().setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        inventoryTable.setRowHeight(60);
        inventoryTable.setShowGrid(false);
        inventoryTable.setIntercellSpacing(new Dimension(0, 1));

        // Custom renderer for action buttons
        inventoryTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("Edit"));
        inventoryTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "Edit"));
        inventoryTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("Delete"));
        inventoryTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "Delete"));

        // Set column widths
        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(200); // Item name
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Code
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Price
        inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(60);  // Quantity
        inventoryTable.getColumnModel().getColumn(4).setPreferredWidth(60);  // Edit
        inventoryTable.getColumnModel().getColumn(5).setPreferredWidth(60);  // Delete

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        scrollPane.setBackground(Color.WHITE);

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(255, 193, 7)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        bottomPanel.setBackground(new Color(255, 248, 225));
        bottomPanel.setOpaque(true);

        JLabel warningIcon = new JLabel("⚠");
        warningIcon.setFont(new Font("Arial", Font.BOLD, 16));
        warningIcon.setForeground(new Color(255, 152, 0));

        lowStockLabel = new JLabel("අසු ගනක අධාරු අගිටම");
        lowStockLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        lowStockLabel.setForeground(new Color(230, 81, 0));

        JLabel itemLabel = new JLabel("ගතර්");
        itemLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
        itemLabel.setForeground(Color.BLACK);

        JLabel quantityLabel = new JLabel("8 කමරර්");
        quantityLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
        quantityLabel.setForeground(new Color(255, 87, 34));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(warningIcon);
        leftPanel.add(lowStockLabel);
        leftPanel.add(itemLabel);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(quantityLabel, BorderLayout.EAST);

        return bottomPanel;
    }

    private void loadSampleData() {
        Object[][] sampleData = {
                {"සදු සිර්", "789123456790", "Rs. 180.00", "25", "", ""},
                {"සදු සම්සර", "789123456791", "Rs. 150.00", "50", "", ""},
                {"කර් පටවර්", "789123456792", "Rs. 850.00", "12", "", ""},
                {"රන මතර්", "789123456793", "Rs. 120.00", "15", "", ""},
                {"ගතර්", "789123456794", "Rs. 450.00", "8", "", ""}
        };

        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }

    private void showAddItemDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "නව භාණ්ඩයක් එකතු කරන්න", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Item name
        panel.add(new JLabel("භාණ්ඩ නම:"));
        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(10));

        // Item code
        panel.add(new JLabel("භාණ්ඩ කේතය:"));
        JTextField codeField = new JTextField();
        codeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(codeField);
        panel.add(Box.createVerticalStrut(10));

        // Price
        panel.add(new JLabel("මිල:"));
        JTextField priceField = new JTextField();
        priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(priceField);
        panel.add(Box.createVerticalStrut(10));

        // Quantity
        panel.add(new JLabel("ප්‍රමාණය:"));
        JTextField quantityField = new JTextField();
        quantityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.add(quantityField);
        panel.add(Box.createVerticalStrut(20));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("එකතු කරන්න");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> {
            if (!nameField.getText().trim().isEmpty() &&
                    !codeField.getText().trim().isEmpty() &&
                    !priceField.getText().trim().isEmpty() &&
                    !quantityField.getText().trim().isEmpty()) {

                Object[] newRow = {
                        nameField.getText().trim(),
                        codeField.getText().trim(),
                        "Rs. " + priceField.getText().trim(),
                        quantityField.getText().trim(),
                        "",
                        ""
                };
                tableModel.addRow(newRow);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "කරුණාකර සියලු ක්ෂේත්‍ර පුරවන්න");
            }
        });

        JButton cancelButton = new JButton("අවලංගු කරන්න");
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // Custom button renderer
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        private String buttonType;

        public ButtonRenderer(String buttonType) {
            this.buttonType = buttonType;
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (buttonType.equals("Edit")) {
                setText("✎");
                setBackground(new Color(33, 150, 243));
                setForeground(Color.WHITE);
                setToolTipText("සංස්කරණය කරන්න");
            } else {
                setText("🗑");
                setBackground(new Color(244, 67, 54));
                setForeground(Color.WHITE);
                setToolTipText("ඉවත් කරන්න");
            }
            return this;
        }
    }

    // Custom button editor
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String buttonType;
        private boolean isPushed;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox, String buttonType) {
            super(checkBox);
            this.buttonType = buttonType;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            currentRow = row;
            if (buttonType.equals("Edit")) {
                button.setText("✎");
                button.setBackground(new Color(33, 150, 243));
                button.setForeground(Color.WHITE);
            } else {
                button.setText("🗑");
                button.setBackground(new Color(244, 67, 54));
                button.setForeground(Color.WHITE);
            }
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                if (buttonType.equals("Edit")) {
                    // Edit functionality
                    JOptionPane.showMessageDialog(button, "සංස්කරණ සංවාදය");
                } else {
                    // Delete functionality
                    int confirm = JOptionPane.showConfirmDialog(button,
                            "මෙම භාණ්ඩය ඉවත් කිරීමට විශ්වාසද?",
                            "තහවුරු කරන්න",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(currentRow);
                    }
                }
            }
            isPushed = false;
            return buttonType;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}