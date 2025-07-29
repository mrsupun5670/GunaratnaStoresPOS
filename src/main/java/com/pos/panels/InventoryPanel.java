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
        initializeComponents();
        loadSampleData();
    }

    private void initializeComponents() {

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top Panel with Add Button and Search
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Bottom Panel with Low Stock Warning



        add(mainPanel, BorderLayout.CENTER);
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
        addButton.setContentAreaFilled(true);
        addButton.setOpaque(true);
        addButton.setForeground(Color.WHITE);
        addButton.setBorderPainted(false);                      // Optional: remove border
        addButton.setFocusPainted(false);
        addButton.setContentAreaFilled(true);
        addButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        addButton.addActionListener(e -> showAddItemDialog());

        titlePanel.add(titleInfo, BorderLayout.WEST);
        titlePanel.add(addButton, BorderLayout.EAST);

        topPanel.add(titlePanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        searchField = new JTextField("භාණ්ඩයක් සෙවීමට");
        searchField.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        searchField.setForeground(Color.GRAY); // Placeholder color
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));


        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("භාණ්ඩයක් සෙවීමට")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("භාණ්ඩයක් සෙවීමට");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });



        // Filter dropdowns
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setOpaque(false);

        Font labelFont = new Font("Arial Unicode MS", Font.PLAIN, 14);

        JLabel categoryLabel = new JLabel("කාණ්ඩ:");
        categoryLabel.setFont(labelFont);

        JLabel statusLabel = new JLabel("තත්ත්වය:");
        statusLabel.setFont(labelFont);

        JComboBox<String> categoryFilter = new JComboBox<>(new String[]{"සියලුම"});
        categoryFilter.setFont(labelFont);

        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"සියලුම"});
        statusFilter.setFont(labelFont);

        filterPanel.add(categoryLabel);
        filterPanel.add(categoryFilter);
        filterPanel.add(statusLabel);
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
        String[] columnNames = {"භාණ්ඩය්", "Bar කේතය", "මිල", "ප්‍රමානය", "", ""};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; // Only edit and delete columns
            }
        };

        inventoryTable = new JTable(tableModel);
        inventoryTable.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        inventoryTable.getTableHeader().setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
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

        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
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
        dialog.setSize(400, 430);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("Arial Unicode MS", Font.PLAIN, 15);

        // Barcode scan button
        JButton scanBarcodeButton = new JButton("බාර්කෝඩ් ස්කෑන් කරන්න");
        scanBarcodeButton.setFont(font);
        scanBarcodeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        scanBarcodeButton.setBackground(new Color(255, 193, 7)); // Amber
        scanBarcodeButton.setForeground(Color.BLACK);
        scanBarcodeButton.setFocusPainted(false);
        scanBarcodeButton.setBorderPainted(false);
        scanBarcodeButton.setOpaque(true);
        scanBarcodeButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        scanBarcodeButton.addActionListener(e -> showScanProductDialog());

        panel.add(scanBarcodeButton);
        panel.add(Box.createVerticalStrut(13));

        JLabel codeLabel = new JLabel("භාණ්ඩ කේතය:");
        codeLabel.setFont(font);
        codeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(codeLabel);

        JTextField barcodeField = new JTextField();
        barcodeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        barcodeField.setFont(font);
        barcodeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(barcodeField);
        panel.add(Box.createVerticalStrut(13));

        // Item name
        JLabel nameLabel = new JLabel("භාණ්ඩ නම:");
        nameLabel.setFont(font);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        nameField.setFont(font);
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(nameField);
        panel.add(Box.createVerticalStrut(13));



        // Price
        JLabel priceLabel = new JLabel("මිල:");
        priceLabel.setFont(font);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        priceField.setFont(font);
        priceField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(priceField);
        panel.add(Box.createVerticalStrut(13));

        // Quantity
        JLabel quantityLabel = new JLabel("ප්‍රමාණය:");
        quantityLabel.setFont(font);
        quantityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(quantityLabel);

        JTextField quantityField = new JTextField();
        quantityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        quantityField.setFont(font);
        quantityField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(quantityField);
        panel.add(Box.createVerticalStrut(20));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton addButton = new JButton("එකතු කරන්න");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setOpaque(true);
        addButton.setForeground(Color.WHITE);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);
        addButton.setFont(font);
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        addButton.setContentAreaFilled(true);

        JButton cancelButton = new JButton("අවලංගු කරන්න");
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(font);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        cancelButton.setContentAreaFilled(true);

        addButton.addActionListener(e -> {
            if (!nameField.getText().trim().isEmpty() &&

                    !priceField.getText().trim().isEmpty() &&
                    !quantityField.getText().trim().isEmpty() &&
                    !barcodeField.getText().trim().isEmpty()) {

                Object[] newRow = {
                        nameField.getText().trim(),
                        barcodeField.getText().trim(),
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

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showScanProductDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "බාර්කෝඩ් ස්කෑන් කරන්න", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("Arial Unicode MS", Font.PLAIN, 15);

        JLabel instructionLabel = new JLabel("කරුණාකර බාර්කෝඩ් ස්කෑනරය භාවිතා කරන්න");
        instructionLabel.setFont(font);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(instructionLabel);
        panel.add(Box.createVerticalStrut(10));

        JTextField barcodeField = new JTextField();
        barcodeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        barcodeField.setFont(font);
        barcodeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(barcodeField);

        JButton scanButton = new JButton("ප්‍රවේශ වන්න");
        scanButton.setFont(font);
        scanButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        scanButton.setBackground(new Color(33, 150, 243));
        scanButton.setForeground(Color.WHITE);
        scanButton.setFocusPainted(false);
        scanButton.setBorderPainted(false);
        scanButton.setOpaque(true);
        scanButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        scanButton.addActionListener(e -> {
            String barcode = barcodeField.getText().trim();
            if (!barcode.isEmpty()) {
                // TODO: Search product by barcode from your database or tableModel
                JOptionPane.showMessageDialog(dialog, "බාර්කෝඩ්: " + barcode + " සෙවීමක් සිදු විය යුතුය");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "කරුණාකර බාර්කෝඩ් එකක් ඇතුළත් කරන්න");
            }
        });

        barcodeField.addActionListener(e -> scanButton.doClick());

        panel.add(Box.createVerticalStrut(15));
        panel.add(scanButton);
        dialog.add(panel);
        dialog.setVisible(true);

        SwingUtilities.invokeLater(() -> barcodeField.requestFocusInWindow());
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
                setText("සංස්කරණය කරන්න");
                setBackground(new Color(100, 181, 246));
                setForeground(Color.WHITE);

            } else {
                setText("ඉවත් කරන්න");
                setBackground(new Color(255, 138, 128));
                setForeground(Color.WHITE);

            }

            setOpaque(true);
            setContentAreaFilled(true);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(new Font("Segoe", Font.BOLD, 14));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

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
                setBackground(new Color(100, 181, 246));
                button.setForeground(Color.WHITE);
            } else {
                button.setText("🗑");
                setBackground(new Color(255, 138, 128));
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