package com.benhan.ui;

import com.benhan.database.DatabaseManager;
import com.benhan.models.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame1 extends JFrame {
    private JTextField txtPatientId;
    private JButton btnSearch;
    private JButton btnCreateNew;
    private DatabaseManager dbManager;
    
    public Frame1(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("TÌM KIẾM THÔNG TIN BỆNH ÁN");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel lblPatientId = new JLabel("Mã Số Bệnh Nhân:");
        lblPatientId.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPatientId = new JTextField(20);
        txtPatientId.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblPatientId);
        inputPanel.add(txtPatientId);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnSearch = new JButton("TÌM KIẾM");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setPreferredSize(new Dimension(120, 35));
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatient();
            }
        });
        
        btnCreateNew = new JButton("TẠO MỚI");
        btnCreateNew.setFont(new Font("Arial", Font.BOLD, 14));
        btnCreateNew.setPreferredSize(new Dimension(120, 35));
        btnCreateNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewPatient();
            }
        });
        
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnCreateNew);
        
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(buttonPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Enter key listener
        txtPatientId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatient();
            }
        });
    }
    
    private void searchPatient() {
        String patientId = txtPatientId.getText().trim();
        if (patientId.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập mã số bệnh nhân!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Patient patient = dbManager.findPatientById(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
        }
        
        Frame2 frame2 = new Frame2(dbManager, patient, false);
        frame2.setVisible(true);
    }
    
    private void createNewPatient() {
        String patientId = txtPatientId.getText().trim();
        if (patientId.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập mã số bệnh nhân!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Patient existing = dbManager.findPatientById(patientId);
        if (existing != null) {
            JOptionPane.showMessageDialog(this, 
                "Mã số bệnh nhân đã tồn tại!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Patient patient = new Patient(patientId);
        Frame2 frame2 = new Frame2(dbManager, patient, true);
        frame2.setVisible(true);
    }
}
