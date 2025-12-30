package com.benhan;

import com.benhan.database.DatabaseManager;
import com.benhan.ui.Frame1;

import javax.swing.*;

public class BenhAnDienTu {
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Initialize database
        DatabaseManager dbManager = new DatabaseManager();
        
        // Launch main frame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Frame1 frame1 = new Frame1(dbManager);
                frame1.setVisible(true);
            }
        });
        
        // Add shutdown hook to close database connection
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                dbManager.close();
            }
        });
    }
}
