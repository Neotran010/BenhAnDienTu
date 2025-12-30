package com.benhan.ui;

import com.benhan.database.DatabaseManager;
import com.benhan.models.Patient;
import com.benhan.models.DieuTri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Frame3 extends JFrame {
    private DatabaseManager dbManager;
    private Patient patient;
    private int month;
    private List<DieuTri> monthRecords;
    
    public Frame3(DatabaseManager dbManager, Patient patient, int month, List<DieuTri> monthRecords) {
        this.dbManager = dbManager;
        this.patient = patient;
        this.month = month;
        this.monthRecords = monthRecords;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("HỒ SƠ ĐIỀU TRỊ - THÁNG " + month);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Info label
        JLabel lblInfo = new JLabel("Bệnh nhân: " + patient.getName() + " - Mã: " + patient.getId());
        lblInfo.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(lblInfo, BorderLayout.NORTH);
        
        // Date buttons panel
        JPanel datePanel = new JPanel(new GridLayout(0, 3, 10, 10));
        datePanel.setBorder(BorderFactory.createTitledBorder("Các ngày khám trong tháng"));
        
        // Group by date
        Map<String, List<DieuTri>> dateMap = new TreeMap<>();
        for (DieuTri dt : monthRecords) {
            String date = dt.getDate();
            if (!dateMap.containsKey(date)) {
                dateMap.put(date, new ArrayList<>());
            }
            dateMap.get(date).add(dt);
        }
        
        for (Map.Entry<String, List<DieuTri>> entry : dateMap.entrySet()) {
            final String date = entry.getKey();
            final List<DieuTri> dateRecords = entry.getValue();
            
            JButton btnDate = new JButton(date);
            btnDate.setFont(new Font("Arial", Font.PLAIN, 12));
            btnDate.setPreferredSize(new Dimension(150, 40));
            btnDate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openDateRecords(date, dateRecords);
                }
            });
            datePanel.add(btnDate);
        }
        
        JScrollPane scrollPane = new JScrollPane(datePanel);
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
    
    private void openDateRecords(String date, List<DieuTri> dateRecords) {
        Frame4 frame4 = new Frame4(dbManager, patient, date, dateRecords);
        frame4.setVisible(true);
    }
}
