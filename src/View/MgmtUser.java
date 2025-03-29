/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.SQLite;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.DriverManager;


/**
 *
 * @author beepxD
 */
public class MgmtUser extends javax.swing.JPanel {

    public SQLite sqlite;
    public DefaultTableModel tableModel;
    private User user;
    
    public MgmtUser(SQLite sqlite) {
        initComponents();
        this.sqlite = sqlite;
        tableModel = (DefaultTableModel)table.getModel();
        table.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        
//        UNCOMMENT TO DISABLE BUTTONS
        editRoleBtn.setVisible(false);
        deleteBtn.setVisible(false);
        lockBtn.setVisible(false);
        chgpassBtn.setVisible(false);
    }
    
    public void init(User user){
        this.user = user;
        
        // Only Admin (role 5) can manage users
        if(this.user.getRole() == 5){
            editRoleBtn.setVisible(true);
            deleteBtn.setVisible(true);
            lockBtn.setVisible(true);
            chgpassBtn.setVisible(true);
        } else {
            editRoleBtn.setVisible(false);
            deleteBtn.setVisible(false);
            lockBtn.setVisible(false);
            chgpassBtn.setVisible(false);
        }
        
        //      CLEAR TABLE
        for(int nCtr = tableModel.getRowCount(); nCtr > 0; nCtr--){
            tableModel.removeRow(0);
        }
        
        //      LOAD CONTENTS based on role (RBAC)
        ArrayList<User> users = new ArrayList();
        if(this.user.getRole() == 5){
            users = sqlite.getUsers(); // Admin can see all users
        }
        
        for(int nCtr = 0; nCtr < users.size(); nCtr++){
            tableModel.addRow(new Object[]{
                users.get(nCtr).getUsername(), 
                users.get(nCtr).getPassword(), 
                users.get(nCtr).getRole(), 
                users.get(nCtr).getLocked()});
        }
    }

    public void designer(JTextField component, String text){
        component.setSize(70, 600);
        component.setFont(new java.awt.Font("Tahoma", 0, 18));
        component.setBackground(new java.awt.Color(240, 240, 240));
        component.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        component.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), text, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
    }
    
    // Helper method to sanitize input
    private String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Replace potentially harmful characters with their Unicode equivalents
        return input.replace("'", "\\u0027")
                   .replace("\"", "\\u0022")
                   .replace("<", "\\u003C")
                   .replace(">", "\\u003E");
    }
    
    // Helper method to validate password
    private boolean validatePassword(String password) {
        // Check length (8-64 chars)
        if (password == null || password.length() < 8 || password.length() > 64) {
            return false;
        }
        
        // Check if password contains both letters and numbers
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            
            if (hasLetter && hasDigit) {
                return true;
            }
        }
        
        return false;
    }
    
    // Helper method to reset login attempts when unlocking an account
    private void resetLoginAttempts(String username) {
        String sql = "UPDATE users SET login_attempts = 0 WHERE LOWER(username) = LOWER(?);";
        
        try (Connection conn = DriverManager.getConnection(SQLite.driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        editRoleBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        lockBtn = new javax.swing.JButton();
        chgpassBtn = new javax.swing.JButton();

        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Username", "Password", "Role", "Locked"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(160);
            table.getColumnModel().getColumn(1).setPreferredWidth(400);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        editRoleBtn.setBackground(new java.awt.Color(255, 255, 255));
        editRoleBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        editRoleBtn.setText("EDIT ROLE");
        editRoleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRoleBtnActionPerformed(evt);
            }
        });

        deleteBtn.setBackground(new java.awt.Color(255, 255, 255));
        deleteBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        deleteBtn.setText("DELETE");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        lockBtn.setBackground(new java.awt.Color(255, 255, 255));
        lockBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lockBtn.setText("LOCK/UNLOCK");
        lockBtn.setToolTipText("");
        lockBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lockBtnActionPerformed(evt);
            }
        });

        chgpassBtn.setBackground(new java.awt.Color(255, 255, 255));
        chgpassBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chgpassBtn.setText("CHANGE PASS");
        chgpassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chgpassBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editRoleBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(lockBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(chgpassBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chgpassBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editRoleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lockBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editRoleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRoleBtnActionPerformed
        // Check if user has admin role
        if (this.user.getRole() != 5) {
            JOptionPane.showMessageDialog(this, "You do not have permission to edit user roles.");
            SQLite.addLogs("SECURITY", this.user.getUsername(), 
                "Unauthorized attempt to edit user role.", 
                (new Timestamp(new Date().getTime())).toString());
            return;
        }
        
        if (table.getSelectedRow() >= 0) {
            String[] options = {"1-DISABLED", "2-CLIENT", "3-STAFF", "4-MANAGER", "5-ADMIN"};
            JComboBox optionList = new JComboBox(options);
            
            String usernameToModify = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
            int currentRole = (int)tableModel.getValueAt(table.getSelectedRow(), 2);
            
            // Prevent modification of own role (self-protection)
            if (usernameToModify.equals(this.user.getUsername())) {
                JOptionPane.showMessageDialog(this, "You cannot modify your own role for security reasons.");
                return;
            }
            
            optionList.setSelectedIndex(currentRole - 1);
            
            String result = (String) JOptionPane.showInputDialog(null, "USER: " + usernameToModify, 
                "EDIT USER ROLE", JOptionPane.QUESTION_MESSAGE, null, options, options[currentRole - 1]);
            
            if (result != null) {
                int newRole = Character.getNumericValue(result.charAt(0));
                
                // Validate role is in valid range
                if (newRole < 1 || newRole > 5) {
                    JOptionPane.showMessageDialog(this, "Invalid role selection.");
                    return;
                }
                
                // Apply the role change
                sqlite.editRole(usernameToModify, newRole);
                JOptionPane.showMessageDialog(this, "Role changed successfully for " + usernameToModify);
                sqlite.addLogs("EDIT ROLE", this.user.getUsername(), 
                    "Edited Role for User " + usernameToModify + " from " + currentRole + " to " + newRole + ".", 
                    (new Timestamp(new Date().getTime())).toString());
                
                // Refresh user list
                init(this.user);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        }
    }//GEN-LAST:event_editRoleBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // Check if user has admin role
        if (this.user.getRole() != 5) {
            JOptionPane.showMessageDialog(this, "You do not have permission to delete users.");
            SQLite.addLogs("SECURITY", this.user.getUsername(), 
                "Unauthorized attempt to delete user.", 
                (new Timestamp(new Date().getTime())).toString());
            return;
        }
        
        if (table.getSelectedRow() >= 0) {
            String usernameToDelete = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
            
            // Prevent self-deletion
            if (usernameToDelete.equals(this.user.getUsername())) {
                JOptionPane.showMessageDialog(this, "You cannot delete your own account for security reasons.");
                return;
            }
            
            int result = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to delete " + usernameToDelete + "? This action cannot be undone.", 
                "DELETE USER", JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                sqlite.removeUser(usernameToDelete);
                JOptionPane.showMessageDialog(this, "User " + usernameToDelete + " has been deleted.");
                sqlite.addLogs("DELETE USER", this.user.getUsername(), 
                    "Deleted User " + usernameToDelete + ".", 
                    (new Timestamp(new Date().getTime())).toString());
                
                // Refresh user list
                init(this.user);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void lockBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lockBtnActionPerformed
        // Check if user has admin role
        if (this.user.getRole() != 5) {
            JOptionPane.showMessageDialog(this, "You do not have permission to lock/unlock users.");
            SQLite.addLogs("SECURITY", this.user.getUsername(), 
                "Unauthorized attempt to lock/unlock user.", 
                (new Timestamp(new Date().getTime())).toString());
            return;
        }
        
        if (table.getSelectedRow() >= 0) {
            String usernameToModify = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
            int currentUserRole = (int)tableModel.getValueAt(table.getSelectedRow(), 2);
            
            // Prevent locking admin accounts or self-locking
            if (currentUserRole == 5 || usernameToModify.equals(this.user.getUsername())) {
                JOptionPane.showMessageDialog(this, "Admin accounts cannot be locked for security reasons.");
                return;
            }
            
            String state = "lock";
            if ("1".equals(tableModel.getValueAt(table.getSelectedRow(), 3) + "")) {
                state = "unlock";
            }
            
            int result = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to " + state + " " + usernameToModify + "?", 
                state.toUpperCase() + " USER", 
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                int lock;
                
                if (state.equals("unlock")) {
                    lock = 0;
                    // When unlocking, reset to client role (2) and reset login attempts
                    SQLite.editRole(usernameToModify, 2);
                    resetLoginAttempts(usernameToModify);
                } else {
                    lock = 1;
                    // When locking, set to disabled role (1)
                    SQLite.editRole(usernameToModify, 1);
                }
                
                SQLite.lockUser(usernameToModify, lock);
                JOptionPane.showMessageDialog(this, "User " + usernameToModify + " has been " + state + "ed.");
                sqlite.addLogs(state.toUpperCase() + " USER", this.user.getUsername(), 
                    state.substring(0, 1).toUpperCase() + state.substring(1) + "ed user " + usernameToModify + ".", 
                    (new Timestamp(new Date().getTime())).toString());
                
                // Refresh the user list
                init(this.user);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        }
    }//GEN-LAST:event_lockBtnActionPerformed

    private void chgpassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chgpassBtnActionPerformed
        // Check if user has admin role
        if (this.user.getRole() != 5) {
            JOptionPane.showMessageDialog(this, "You do not have permission to change passwords.");
            SQLite.addLogs("SECURITY", this.user.getUsername(), 
                "Unauthorized attempt to change password.", 
                (new Timestamp(new Date().getTime())).toString());
            return;
        }
        
        if (table.getSelectedRow() >= 0) {
            JTextField password = new JPasswordField();
            JTextField confpass = new JPasswordField();
            designer(password, "PASSWORD");
            designer(confpass, "CONFIRM PASSWORD");
            
            String usernameToModify = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
            
            Object[] message = {
                "Enter New Password for " + usernameToModify + ":", password, confpass
            };

            int result = JOptionPane.showConfirmDialog(null, message, "CHANGE PASSWORD", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
            
            if (result == JOptionPane.OK_OPTION) {
                String pass = password.getText();
                String conf = confpass.getText();
                
                // Check if password fields are empty
                if (pass.isEmpty() || conf.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Password fields cannot be empty.");
                    return;
                }
                
                // Validate password complexity
                if (!validatePassword(pass)) {
                    JOptionPane.showMessageDialog(this, 
                        "Password must be 8-64 characters and contain both letters and numbers!");
                    return;
                }
                
                // Check passwords match
                if (!pass.equals(conf)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match.");
                    return;
                }
                
                // Sanitize password (remove potentially harmful characters)
                pass = sanitizeInput(pass);
                
                // Change password
                SQLite.changePassword(usernameToModify, pass);
                JOptionPane.showMessageDialog(this, "Password changed successfully for " + usernameToModify);
                
                // Log the action
                sqlite.addLogs("CHANGE PASSWORD", this.user.getUsername(), 
                    "Password Changed for User " + usernameToModify + ".", 
                    (new Timestamp(new Date().getTime())).toString());
                
                // Refresh the user list
                init(this.user);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user first.");
        }
    }//GEN-LAST:event_chgpassBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chgpassBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editRoleBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton lockBtn;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}