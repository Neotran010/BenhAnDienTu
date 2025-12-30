package com.benhan.ui;

import com.benhan.database.DatabaseManager;
import com.benhan.models.Patient;
import com.benhan.models.DieuTri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Frame4 extends JFrame {
    private DatabaseManager dbManager;
    private Patient patient;
    private String date;
    private List<DieuTri> dateRecords;
    
    public Frame4(DatabaseManager dbManager, Patient patient, String date, List<DieuTri> dateRecords) {
        this.dbManager = dbManager;
        this.patient = patient;
        this.date = date;
        this.dateRecords = dateRecords;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("HỒ SƠ ĐIỀU TRỊ - NGÀY " + date);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Info label
        JLabel lblInfo = new JLabel("Bệnh nhân: " + patient.getName() + " - Ngày: " + date);
        lblInfo.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(lblInfo, BorderLayout.NORTH);
        
        // Timestamp buttons panel
        JPanel timestampPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        timestampPanel.setBorder(BorderFactory.createTitledBorder("Các lần điều trị trong ngày"));
        
        for (DieuTri dt : dateRecords) {
            JButton btnTimestamp = new JButton(dt.getTimestamp());
            btnTimestamp.setFont(new Font("Arial", Font.PLAIN, 12));
            btnTimestamp.setPreferredSize(new Dimension(200, 40));
            btnTimestamp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openTreatmentDetails(dt);
                }
            });
            timestampPanel.add(btnTimestamp);
        }
        
        // Add new treatment button
        JButton btnAddNew = new JButton("+ Thêm điều trị mới");
        btnAddNew.setFont(new Font("Arial", Font.BOLD, 12));
        btnAddNew.setBackground(new Color(100, 200, 100));
        btnAddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewTreatment();
            }
        });
        timestampPanel.add(btnAddNew);
        
        JScrollPane scrollPane = new JScrollPane(timestampPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnBack = new JButton("Quay lại");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        bottomPanel.add(btnBack);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void openTreatmentDetails(DieuTri dieuTri) {
        Frame5 frame5 = new Frame5(dbManager, patient, dieuTri, false);
        frame5.setVisible(true);
    }
    
    private void addNewTreatment() {
        // Create timestamp in format YYYY-MM-DD HH:MM:SS
        String timestamp = date + " " + java.time.LocalTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        
        DieuTri newDieuTri = new DieuTri(patient.getId(), date, timestamp);
        Frame5 frame5 = new Frame5(dbManager, patient, newDieuTri, true);
        frame5.setVisible(true);
        
        // Refresh this frame when Frame5 closes
        frame5.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                // Reload records
                dateRecords.clear();
                dateRecords.addAll(dbManager.getDieuTriByPatientId(patient.getId()));
                dateRecords.removeIf(dt -> !dt.getDate().equals(date));
                
                // Refresh UI
                getContentPane().removeAll();
                initComponents();
                revalidate();
                repaint();
            }
        });
    }
}
