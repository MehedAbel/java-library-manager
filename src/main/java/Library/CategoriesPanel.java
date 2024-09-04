/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abel
 */
public class CategoriesPanel extends javax.swing.JPanel {

    /**
     * Creates new form CategoriesPanel
     */
    public CategoriesPanel() {
        initComponents();
        dbConnect();
        loadCategories();
        catBtnDelete.setEnabled(false);
        catBtnEdit.setEnabled(false);
    }
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public void dbConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void loadCategories() {
        try {
            pst = conn.prepareStatement("select * from category");
            rs = pst.executeQuery();
            
            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel)catTableCategories.getModel();
            d.setRowCount(0);
            
            while (rs.next()) {
                Vector v = new Vector();
                
                for (int i = 1; i < c; i++) {
                    v.add(rs.getString("id"));
                    v.add(rs.getString("name"));
                    v.add(rs.getString("status"));
                }
                
                d.addRow(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        panelCategories = new javax.swing.JPanel();
        catLabelTitle = new javax.swing.JLabel();
        catTxtCategoryName = new javax.swing.JTextField();
        catLabelCategory = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        catLabelStatus = new javax.swing.JLabel();
        catComboStatus = new javax.swing.JComboBox<>();
        catBtnAdd = new javax.swing.JButton();
        catBtnEdit = new javax.swing.JButton();
        catBtnDelete = new javax.swing.JButton();
        catBtnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        catTableCategories = new javax.swing.JTable();

        panelCategories.setBackground(new java.awt.Color(51, 51, 51));

        catLabelTitle.setFont(new java.awt.Font("Liberation Serif", 1, 36)); // NOI18N
        catLabelTitle.setForeground(new java.awt.Color(204, 204, 204));
        catLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        catLabelTitle.setText("Categories");
        catLabelTitle.setToolTipText("");

        catTxtCategoryName.setBackground(new java.awt.Color(80, 80, 80));
        catTxtCategoryName.setForeground(new java.awt.Color(255, 255, 255));

        catLabelCategory.setForeground(new java.awt.Color(204, 204, 204));
        catLabelCategory.setText("Category Name:");

        catLabelStatus.setForeground(new java.awt.Color(204, 204, 204));
        catLabelStatus.setText("Status:");

        catComboStatus.setBackground(new java.awt.Color(80, 80, 80));
        catComboStatus.setForeground(new java.awt.Color(255, 255, 255));
        catComboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));
        catComboStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catComboStatusActionPerformed(evt);
            }
        });

        catBtnAdd.setBackground(new java.awt.Color(80, 80, 80));
        catBtnAdd.setForeground(new java.awt.Color(255, 255, 255));
        catBtnAdd.setText("Add");
        catBtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catBtnAddActionPerformed(evt);
            }
        });

        catBtnEdit.setBackground(new java.awt.Color(80, 80, 80));
        catBtnEdit.setForeground(new java.awt.Color(255, 255, 255));
        catBtnEdit.setText("Edit");
        catBtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catBtnEditActionPerformed(evt);
            }
        });

        catBtnDelete.setBackground(new java.awt.Color(80, 80, 80));
        catBtnDelete.setForeground(new java.awt.Color(255, 255, 255));
        catBtnDelete.setText("Delete");
        catBtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catBtnDeleteActionPerformed(evt);
            }
        });

        catBtnClear.setBackground(new java.awt.Color(80, 80, 80));
        catBtnClear.setForeground(new java.awt.Color(255, 255, 255));
        catBtnClear.setText("Clear");
        catBtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catBtnClearActionPerformed(evt);
            }
        });

        catTableCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Category Name", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        catTableCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                catTableCategoriesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(catTableCategories);

        javax.swing.GroupLayout panelCategoriesLayout = new javax.swing.GroupLayout(panelCategories);
        panelCategories.setLayout(panelCategoriesLayout);
        panelCategoriesLayout.setHorizontalGroup(
            panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategoriesLayout.createSequentialGroup()
                .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCategoriesLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel3))
                    .addGroup(panelCategoriesLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(catLabelTitle)
                            .addComponent(catComboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelCategoriesLayout.createSequentialGroup()
                                .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(catBtnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(catBtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(catBtnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(catBtnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(catLabelCategory)
                            .addComponent(catTxtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(catLabelStatus))))
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        panelCategoriesLayout.setVerticalGroup(
            panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCategoriesLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCategoriesLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(47, Short.MAX_VALUE))
                    .addGroup(panelCategoriesLayout.createSequentialGroup()
                        .addComponent(catLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(catLabelCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(catTxtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(catLabelStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(catComboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(catBtnEdit)
                            .addComponent(catBtnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(catBtnDelete)
                            .addComponent(catBtnClear))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1840, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelCategories, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1115, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelCategories, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void catComboStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catComboStatusActionPerformed
        // TODO add your handling code here:z
    }//GEN-LAST:event_catComboStatusActionPerformed

    private void catBtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catBtnAddActionPerformed
        // TODO add your handling code here:
        String category_name = catTxtCategoryName.getText();
        String status = catComboStatus.getSelectedItem().toString();

        try {
            pst = conn.prepareStatement("insert into category(name, status) values(?, ?)");
            pst.setString(1, category_name);
            pst.setString(2, status);
            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Category created");
                catTxtCategoryName.setText("");
                catComboStatus.setSelectedIndex(-1);
                catTxtCategoryName.requestFocus();
                loadCategories();
            } else {
                JOptionPane.showMessageDialog(this, "Error creating category");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_catBtnAddActionPerformed

    private void catBtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catBtnEditActionPerformed
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)catTableCategories.getModel();
        int selectIndex = catTableCategories.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());

        String category_name = catTxtCategoryName.getText();
        String status = catComboStatus.getSelectedItem().toString();

        try {
            pst = conn.prepareStatement("update category set name = ?, status = ? where id = ?");
            pst.setString(1, category_name);
            pst.setString(2, status);
            pst.setInt(3, id);
            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Category updated");
                catTxtCategoryName.setText("");
                catComboStatus.setSelectedIndex(-1);
                catTxtCategoryName.requestFocus();
                loadCategories();
                catBtnAdd.setEnabled(true);
                catBtnDelete.setEnabled(false);
                catBtnEdit.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error updating category");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_catBtnEditActionPerformed

    private void catBtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catBtnDeleteActionPerformed
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)catTableCategories.getModel();
        int selectIndex = catTableCategories.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());

        try {
            pst = conn.prepareStatement("delete from category where id = ?");
            pst.setInt(1, id);
            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Category deleted");
                catTxtCategoryName.setText("");
                catComboStatus.setSelectedIndex(-1);
                catTxtCategoryName.requestFocus();
                loadCategories();
                catBtnAdd.setEnabled(true);
                catBtnDelete.setEnabled(false);
                catBtnEdit.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting category");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_catBtnDeleteActionPerformed

    private void catBtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catBtnClearActionPerformed
        // TODO add your handling code here:
        catTableCategories.clearSelection();
        catTxtCategoryName.setText("");
        catComboStatus.setSelectedIndex(-1);
        catTxtCategoryName.requestFocus();
        loadCategories();
        catBtnAdd.setEnabled(true);
        catBtnDelete.setEnabled(false);
        catBtnEdit.setEnabled(false);
    }//GEN-LAST:event_catBtnClearActionPerformed

    private void catTableCategoriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_catTableCategoriesMouseClicked
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)catTableCategories.getModel();

        int selectIndex = catTableCategories.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());
        catTxtCategoryName.setText(d.getValueAt(selectIndex, 1).toString());
        catComboStatus.setSelectedItem(d.getValueAt(selectIndex, 2).toString());

        catBtnAdd.setEnabled(false);
        catBtnDelete.setEnabled(true);
        catBtnEdit.setEnabled(true);
    }//GEN-LAST:event_catTableCategoriesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton catBtnAdd;
    private javax.swing.JButton catBtnClear;
    private javax.swing.JButton catBtnDelete;
    private javax.swing.JButton catBtnEdit;
    private javax.swing.JComboBox<String> catComboStatus;
    private javax.swing.JLabel catLabelCategory;
    private javax.swing.JLabel catLabelStatus;
    private javax.swing.JLabel catLabelTitle;
    private javax.swing.JTable catTableCategories;
    private javax.swing.JTextField catTxtCategoryName;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelCategories;
    // End of variables declaration//GEN-END:variables
}
