/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.SQLite;
import Model.Product;
import Model.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author beepxD
 */
public class MgmtProduct extends javax.swing.JPanel {

    public SQLite sqlite;
    public DefaultTableModel tableModel;
    private User user;
    
    public MgmtProduct(SQLite sqlite) {
        initComponents();
        this.sqlite = sqlite;
        tableModel = (DefaultTableModel)table.getModel();
        table.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));

//        UNCOMMENT TO DISABLE BUTTONS
        purchaseBtn.setVisible(false);
        addBtn.setVisible(false);
        editBtn.setVisible(false);
        deleteBtn.setVisible(false);
    }

    public void init(User user){
        this.user = user;
        
        if(this.user.getRole() == 2){
            purchaseBtn.setVisible(true);
        }
        else if(this.user.getRole() == 3 || this.user.getRole() == 4){
            addBtn.setVisible(true);
            editBtn.setVisible(true);
            deleteBtn.setVisible(true);
        }
        //      CLEAR TABLE
        for(int nCtr = tableModel.getRowCount(); nCtr > 0; nCtr--){
            tableModel.removeRow(0);
        }
        
//      LOAD CONTENTS
        ArrayList<Product> products = sqlite.getProduct();
        for(int nCtr = 0; nCtr < products.size(); nCtr++){
            tableModel.addRow(new Object[]{
                products.get(nCtr).getName(), 
                products.get(nCtr).getStock(), 
                products.get(nCtr).getPrice()});
        }
    }
    
    public void designer(JTextField component, String text){
        component.setSize(70, 600);
        component.setFont(new java.awt.Font("Tahoma", 0, 18));
        component.setBackground(new java.awt.Color(240, 240, 240));
        component.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        component.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), text, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
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
        purchaseBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Stock", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(50);
            table.getColumnModel().getColumn(1).setMaxWidth(100);
            table.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        purchaseBtn.setBackground(new java.awt.Color(255, 255, 255));
        purchaseBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        purchaseBtn.setText("PURCHASE");
        purchaseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchaseBtnActionPerformed(evt);
            }
        });

        addBtn.setBackground(new java.awt.Color(255, 255, 255));
        addBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        addBtn.setText("ADD");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        editBtn.setBackground(new java.awt.Color(255, 255, 255));
        editBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        editBtn.setText("EDIT");
        editBtn.setToolTipText("");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(purchaseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(purchaseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void purchaseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchaseBtnActionPerformed
        if(table.getSelectedRow() >= 0){
            JTextField stockFld = new JTextField("0");
            designer(stockFld, "PRODUCT STOCK");
            String name = tableModel.getValueAt(table.getSelectedRow(), 0).toString();

            Object[] message = {
                "How many " + name + " do you want to purchase?", stockFld
            };

            int result = JOptionPane.showConfirmDialog(null, message, "PURCHASE PRODUCT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
            
            int stock = Integer.parseInt(stockFld.getText());
            if (result == JOptionPane.OK_OPTION && stock != 0) {
                boolean success = SQLite.purchaseProduct(name, stock);
                
                if(success){
                    JOptionPane.showMessageDialog(this, "Purchase was successful.");
                    SQLite.addHistory(this.user.getUsername(), name, stock, (new Timestamp(new Date().getTime())).toString());
                }
                else{
                    JOptionPane.showMessageDialog(this, "Purchase Failed.");
                }
                System.out.println(stockFld.getText());
            }
        }
    }//GEN-LAST:event_purchaseBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        JTextField nameFld = new JTextField();
        JTextField stockFld = new JTextField();
        JTextField priceFld = new JTextField();

        designer(nameFld, "PRODUCT NAME");
        designer(stockFld, "PRODUCT STOCK");
        designer(priceFld, "PRODUCT PRICE");

        Object[] message = {
            "Insert New Product Details:", nameFld, stockFld, priceFld
        };

        int result = JOptionPane.showConfirmDialog(null, message, "ADD PRODUCT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        String name = nameFld.getText();
        int stock = Integer.parseInt(stockFld.getText());
        double price = Double.parseDouble(priceFld.getText());
        
        if (result == JOptionPane.OK_OPTION && !(name.isBlank()) && stock > 0 && price > 0.0) {
//            System.out.println(nameFld.getText());
//            System.out.println(stockFld.getText());
//            System.out.println(priceFld.getText());
            
            SQLite.addProduct(name, stock, price);
            JOptionPane.showMessageDialog(this, "Product Added.");
            SQLite.addLogs("ADD PRODUCT", this.user.getUsername(), name + " (x" + stock + ") added to database.", (new Timestamp(new Date().getTime())).toString());
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        if(table.getSelectedRow() >= 0){
            JTextField nameFld = new JTextField(tableModel.getValueAt(table.getSelectedRow(), 0) + "");
            JTextField stockFld = new JTextField(tableModel.getValueAt(table.getSelectedRow(), 1) + "");
            JTextField priceFld = new JTextField(tableModel.getValueAt(table.getSelectedRow(), 2) + "");

            designer(nameFld, "PRODUCT NAME");
            designer(stockFld, "PRODUCT STOCK");
            designer(priceFld, "PRODUCT PRICE");

            Object[] message = {
                "Edit Product Details:", nameFld, stockFld, priceFld
            };

            int result = JOptionPane.showConfirmDialog(null, message, "EDIT PRODUCT", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
            
            String name = nameFld.getText();
            int stock = Integer.parseInt(stockFld.getText());
            double price = Double.parseDouble(priceFld.getText());

            if (result == JOptionPane.OK_OPTION) {
//                System.out.println(nameFld.getText());
//                System.out.println(stockFld.getText());
//                System.out.println(priceFld.getText());

                  boolean success = SQLite.editProduct(name, stock, price);
                  if(success){
                      JOptionPane.showMessageDialog(this, "Product Edited Successfully.");
                      SQLite.addLogs("EDIT PRODUCT", this.user.getUsername(), "Edited Product: " + name, (new Timestamp(new Date().getTime())).toString());
                  }
                  else{
                      JOptionPane.showMessageDialog(this, "Failed to Edit Product.");
                      SQLite.addLogs("EDIT PRODUCT", this.user.getUsername(), "Failed Edit Attempt For: " + name, (new Timestamp(new Date().getTime())).toString());
                  }
            }
        }
    }//GEN-LAST:event_editBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        if(table.getSelectedRow() >= 0){
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + tableModel.getValueAt(table.getSelectedRow(), 0) + "?", "DELETE PRODUCT", JOptionPane.YES_NO_OPTION);
            
            String name = tableModel.getValueAt(table.getSelectedRow(), 0).toString();
            
            if (result == JOptionPane.YES_OPTION) {
                //System.out.println(tableModel.getValueAt(table.getSelectedRow(), 0));
                SQLite.deleteProduct(name);
                JOptionPane.showMessageDialog(this, "Product Deleted.");
                SQLite.addLogs("EDIT PRODUCT", this.user.getUsername(), "Product Deleted: " + name, (new Timestamp(new Date().getTime())).toString());
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton purchaseBtn;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
