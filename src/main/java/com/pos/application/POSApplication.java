package com.pos.application;

import com.pos.panels.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class POSApplication extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel headerPanel;
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JPanel statusPanel;

    // Modern Color Scheme
    private static final Color PRIMARY_COLOR = new Color(45, 125, 210);          // Professional Blue
    private static final Color PRIMARY_DARK = new Color(25, 105, 190);           // Darker Blue
    private static final Color ACCENT_COLOR = new Color(76, 175, 80);            // Success Green
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);      // Very Light Blue-Gray
    private static final Color SURFACE_COLOR = Color.WHITE;                      // Pure White
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);             // Dark Gray
    private static final Color TEXT_SECONDARY = new Color(108, 117, 125);        // Medium Gray
    private static final Color BORDER_COLOR = new Color(220, 227, 235);          // Light Gray Border
    private static final Color TAB_SELECTED = new Color(235, 245, 255);          // Light Blue
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 15);            // Subtle Shadow

    public POSApplication() {
        initializeApplication();
        createHeader();
        createTabbedPane();
        createStatusBar();
        setupLayout();
        applyModernStyling();
    }

    private void initializeApplication() {
        setTitle("ගුණරත්න වෙළඳසැල - Gunaratna Stores POS System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1200, 800));

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Customize UI defaults for modern appearance
            UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
            UIManager.put("TabbedPane.tabAreaInsets", new Insets(0, 0, 0, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set main background
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());
    }

    private void createHeader() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(SURFACE_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Add subtle shadow effect
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                new EmptyBorder(20, 30, 20, 30)
        ));

        // Left side - Title and subtitle
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(SURFACE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        // Main title
        titleLabel = new JLabel("ගුණරත්න වෙළඳසැල");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);

        // Subtitle
        subtitleLabel = new JLabel("Gunaratna Stores - Point of Sale System");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        titlePanel.add(titleLabel, gbc);
        gbc.gridy = 1; gbc.insets = new Insets(5, 0, 0, 0);
        titlePanel.add(subtitleLabel, gbc);

        // Right side - User info and time
        JPanel userPanel = new JPanel(new GridBagLayout());
        userPanel.setBackground(SURFACE_COLOR);
        GridBagConstraints ugbc = new GridBagConstraints();

        JLabel userLabel = new JLabel("පරිශීලක: Admin");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(TEXT_SECONDARY);

        JLabel timeLabel = new JLabel(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(TEXT_SECONDARY);

        ugbc.gridx = 0; ugbc.gridy = 0; ugbc.anchor = GridBagConstraints.EAST;
        userPanel.add(userLabel, ugbc);
        ugbc.gridy = 1; ugbc.insets = new Insets(3, 0, 0, 0);
        userPanel.add(timeLabel, ugbc);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(userPanel, BorderLayout.EAST);
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(TEXT_PRIMARY);

        // Custom UI for modern tabs
        tabbedPane.setUI(new ModernTabbedPaneUI());

        // Add panels with icons and styled tabs
        addStyledTab("🏠 මුල් පිටුව", "Home", new HomePanel().getMainPanel());
        addStyledTab("📋 ඇණවුම්", "Orders", new OrdersPanel());
        addStyledTab("💰 ගණන් කරන්න", "Billing", new BillingPanel());
        addStyledTab("📦 භාණ්ඩ", "Inventory", new InventoryPanel());
        addStyledTab("📊 වාර්තා", "Reports", new ReportsPanel());

        // Style the tab area
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        // Add hover effects
        addTabHoverEffects();
    }

    private void addStyledTab(String title, String tooltip, Component component) {
        tabbedPane.addTab(title, component);
        int index = tabbedPane.getTabCount() - 1;
        tabbedPane.setToolTipTextAt(index, tooltip);

        // Create custom tab component for better styling
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        tabPanel.setOpaque(false);

        JLabel tabLabel = new JLabel(title);
        tabLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabLabel.setForeground(TEXT_PRIMARY);
        tabPanel.add(tabLabel);

        tabbedPane.setTabComponentAt(index, tabPanel);
    }

    private void addTabHoverEffects() {
        tabbedPane.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int tabIndex = tabbedPane.indexAtLocation(e.getX(), e.getY());
                if (tabIndex >= 0) {
                    tabbedPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    tabbedPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private void createStatusBar() {
        statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(SURFACE_COLOR);
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
                new EmptyBorder(8, 20, 8, 20)
        ));

        JLabel statusLabel = new JLabel("● සම්බන්ධිත | Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(ACCENT_COLOR);

        JLabel versionLabel = new JLabel("Version 1.0.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        versionLabel.setForeground(TEXT_SECONDARY);

        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(versionLabel, BorderLayout.EAST);
    }

    private void setupLayout() {
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void applyModernStyling() {
        // Add subtle shadow to the main window
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, BORDER_COLOR));

        // Modern window decorations
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            try {
                // Enable modern Windows styling if available
                System.setProperty("sun.awt.noerasebackground", "true");
            } catch (Exception e) {
                // Ignore if not supported
            }
        }
    }

    // Custom TabbedPane UI for modern appearance
    private class ModernTabbedPaneUI extends BasicTabbedPaneUI {

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabAreaInsets = new Insets(10, 0, 0, 0);
            contentBorderInsets = new Insets(15, 0, 0, 0);
        }

        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Paint tab area background
            g2d.setColor(SURFACE_COLOR);
            g2d.fillRect(0, 0, tabPane.getWidth(), calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight));

            super.paintTabArea(g2d, tabPlacement, selectedIndex);
            g2d.dispose();
        }

        @Override
        protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
                                Rectangle iconRect, Rectangle textRect) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Rectangle tabRect = rects[tabIndex];
            boolean isSelected = tabIndex == tabPane.getSelectedIndex();
            boolean isRollover = getRolloverTab() == tabIndex;

            // Tab background
            if (isSelected) {
                g2d.setColor(TAB_SELECTED);
                g2d.fillRoundRect(tabRect.x, tabRect.y, tabRect.width, tabRect.height, 8, 8);

                // Selected tab border
                g2d.setColor(PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(tabRect.x, tabRect.y, tabRect.width - 1, tabRect.height - 1, 8, 8);
            } else if (isRollover) {
                g2d.setColor(new Color(245, 248, 250));
                g2d.fillRoundRect(tabRect.x, tabRect.y, tabRect.width, tabRect.height, 8, 8);
            }

            super.paintTab(g2d, tabPlacement, rects, tabIndex, iconRect, textRect);
            g2d.dispose();
        }

        @Override
        protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics,
                                 int tabIndex, String title, Rectangle textRect, boolean isSelected) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g2d.setFont(font);
            g2d.setColor(isSelected ? PRIMARY_COLOR : TEXT_PRIMARY);

            int x = textRect.x;
            int y = textRect.y + metrics.getAscent();
            g2d.drawString(title, x, y);
            g2d.dispose();
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = tabPane.getWidth();
            int height = tabPane.getHeight();
            int tabAreaHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);

            // Content area background
            g2d.setColor(BACKGROUND_COLOR);
            g2d.fillRoundRect(0, tabAreaHeight, width, height - tabAreaHeight, 12, 12);

            // Subtle border
            g2d.setColor(BORDER_COLOR);
            g2d.setStroke(new BasicStroke(1f));
            g2d.drawRoundRect(0, tabAreaHeight, width - 1, height - tabAreaHeight - 1, 12, 12);

            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        // Enable system-specific optimizations
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        System.setProperty("swing.plaf.metal.controlFont", "Segoe UI");
        System.setProperty("swing.plaf.metal.userFont", "Segoe UI");
        System.out.println("Hello World 3!");

        SwingUtilities.invokeLater(() -> {
            try {
                // Set modern Windows look if available
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Windows".equals(info.getName()) || "Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }

                new POSApplication().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}