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
        
        // Add "Thêm Mới" button
        JButton btnAddNew = new JButton("Thêm Mới");
        btnAddNew.setFont(new Font("Arial", Font.BOLD, 12));
        btnAddNew.setBackground(new Color(100, 200, 100));
        btnAddNew.setPreferredSize(new Dimension(150, 40));
        btnAddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewTreatmentWithDate();
            }
        });
        datePanel.add(btnAddNew);
        
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
    
    private void addNewTreatmentWithDate() {
        // Create a dialog for date input
        JDialog dialog = new JDialog(this, "Nhập ngày điều trị", true);
        dialog.setSize(350, 150);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Ngày (dd/MM/yyyy):"), gbc);
        
        gbc.gridx = 1;
        JTextField txtDate = new JTextField(15);
        panel.add(txtDate, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Hủy");
        
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dateStr = txtDate.getText().trim();
                
                if (dateStr.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                        "Vui lòng nhập ngày!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Validate date format
                if (!dateStr.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    JOptionPane.showMessageDialog(dialog,
                        "Định dạng ngày không hợp lệ! Vui lòng nhập dd/MM/yyyy",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Convert dd/MM/yyyy to yyyy-MM-dd
                String[] parts = dateStr.split("/");
                String dbDate = parts[2] + "-" + parts[1] + "-" + parts[0];
                
                // Create new treatment record
                java.time.LocalDateTime now = java.time.LocalDateTime.now();
                java.time.format.DateTimeFormatter formatter = 
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timestamp = now.format(formatter);
                
                DieuTri newDieuTri = new DieuTri(patient.getId(), dbDate, timestamp);
                
                if (dbManager.saveDieuTri(newDieuTri)) {
                    JOptionPane.showMessageDialog(dialog,
                        "Đã tạo hồ sơ điều trị mới!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    
                    // Reload patient and refresh frame
                    patient.setToDieuTri(dbManager.getDieuTriByPatientId(patient.getId()));
                    dispose();
                    
                    // Reopen Frame3 with updated data
                    List<DieuTri> updatedMonthRecords = patient.getToDieuTri().stream()
                        .filter(dt -> dt.getMonth() == month)
                        .collect(java.util.stream.Collectors.toList());
                    Frame3 newFrame = new Frame3(dbManager, patient, month, updatedMonthRecords);
                    newFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(dialog,
                        "Lỗi khi tạo hồ sơ điều trị!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancel);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}
