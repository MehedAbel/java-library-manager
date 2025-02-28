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
public class AuthorsPanel extends javax.swing.JPanel {

    /**
     * Creates new form AuthorsPanel
     */
    public AuthorsPanel() {
        initComponents();
        dbConnect();
        loadAuthors();
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public void dbConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthorsPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AuthorsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void loadAuthors() {
        try {
            pst = conn.prepareStatement("select * from author");
            rs = pst.executeQuery();
            
            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel)tableAuthors.getModel();
            d.setRowCount(0);
            
            while (rs.next()) {
                Vector v = new Vector();
                
                for (int i = 1; i < c; i++) {
                    v.add(rs.getString("id"));
                    v.add(rs.getString("name"));
                    v.add(rs.getString("address"));
                    v.add(rs.getString("phone"));
                }
                
                d.addRow(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorsPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        panelAuthors = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        txtAuthor = new javax.swing.JTextField();
        labelAuthor = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelAddress = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAuthors = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        labelPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1832, 1115));

        panelAuthors.setBackground(new java.awt.Color(51, 51, 51));
        panelAuthors.setPreferredSize(new java.awt.Dimension(1218, 850));

        labelTitle.setFont(new java.awt.Font("Liberation Serif", 1, 36)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(204, 204, 204));
        labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitle.setText("Authors");
        labelTitle.setToolTipText("");

        txtAuthor.setBackground(new java.awt.Color(80, 80, 80));
        txtAuthor.setForeground(new java.awt.Color(255, 255, 255));

        labelAuthor.setForeground(new java.awt.Color(204, 204, 204));
        labelAuthor.setText("Author Name:");

        labelAddress.setForeground(new java.awt.Color(204, 204, 204));
        labelAddress.setText("Address:");

        btnAdd.setBackground(new java.awt.Color(80, 80, 80));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(80, 80, 80));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(80, 80, 80));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(80, 80, 80));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        tableAuthors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Author Name", "Address", "Phone Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableAuthors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAuthorsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableAuthors);

        txtAddress.setBackground(new java.awt.Color(80, 80, 80));
        txtAddress.setColumns(20);
        txtAddress.setForeground(new java.awt.Color(255, 255, 255));
        txtAddress.setRows(5);
        jScrollPane2.setViewportView(txtAddress);

        labelPhone.setForeground(new java.awt.Color(204, 204, 204));
        labelPhone.setText("Phone");

        txtPhone.setBackground(new java.awt.Color(80, 80, 80));
        txtPhone.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelAuthorsLayout = new javax.swing.GroupLayout(panelAuthors);
        panelAuthors.setLayout(panelAuthorsLayout);
        panelAuthorsLayout.setHorizontalGroup(
            panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAuthorsLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAuthorsLayout.createSequentialGroup()
                        .addGroup(panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(labelAddress)
                    .addComponent(labelPhone)
                    .addGroup(panelAuthorsLayout.createSequentialGroup()
                        .addGroup(panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                            .addComponent(txtAuthor, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3))
                    .addComponent(labelAuthor)
                    .addComponent(labelTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        panelAuthorsLayout.setVerticalGroup(
            panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAuthorsLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAuthorsLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelAuthorsLayout.createSequentialGroup()
                        .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelAuthor)
                        .addGroup(panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAuthorsLayout.createSequentialGroup()
                                .addGroup(panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelAuthorsLayout.createSequentialGroup()
                                        .addGap(203, 203, 203)
                                        .addComponent(jLabel3))
                                    .addGroup(panelAuthorsLayout.createSequentialGroup()
                                        .addGap(74, 74, 74)
                                        .addComponent(labelAddress)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(labelPhone)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEdit)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panelAuthorsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnDelete)
                                    .addComponent(btnClear))
                                .addGap(576, 576, 576))
                            .addGroup(panelAuthorsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAuthors, javax.swing.GroupLayout.DEFAULT_SIZE, 1840, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAuthors, javax.swing.GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableAuthorsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAuthorsMouseClicked
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)tableAuthors.getModel();

        int selectIndex = tableAuthors.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());
        txtAuthor.setText(d.getValueAt(selectIndex, 1).toString());
        txtAddress.setText(d.getValueAt(selectIndex, 2).toString());
        txtPhone.setText(d.getValueAt(selectIndex, 3).toString());

        btnAdd.setEnabled(false);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
    }//GEN-LAST:event_tableAuthorsMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        txtAuthor.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtAuthor.requestFocus();
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        tableAuthors.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)tableAuthors.getModel();
        int selectIndex = tableAuthors.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());

        try {
            pst = conn.prepareStatement("delete from author where id = ?");
            pst.setInt(1, id);
            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Author deleted");
                txtAuthor.setText("");
                txtAddress.setText("");
                txtPhone.setText("");
                txtAuthor.requestFocus();
                loadAuthors();
                btnAdd.setEnabled(true);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting author");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AuthorsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)tableAuthors.getModel();
        int selectIndex = tableAuthors.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());

        String author_name = txtAuthor.getText();
        String address = txtAddress.getText();
        String phone = txtPhone.getText();

        try {
            pst = conn.prepareStatement("update author set name = ?, address = ?, phone = ? where id = ?");
            pst.setString(1, author_name);
            pst.setString(2, address);
            pst.setString(3, phone);
            pst.setInt(4, id);
            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Author updated");
                txtAuthor.setText("");
                txtAddress.setText("");
                txtPhone.setText("");
                txtAuthor.requestFocus();
                loadAuthors();
                btnAdd.setEnabled(true);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error updating author");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AuthorsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        String name = txtAuthor.getText();
        String address = txtAddress.getText();
        String phone = txtPhone.getText();

        try {
            pst = conn.prepareStatement("insert into author(name, address, phone) values(?, ?, ?)");
            pst.setString(1, name);
            pst.setString(2, address);
            pst.setString(3, phone);

            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Author created");
                txtAuthor.setText("");
                txtAddress.setText("");
                txtPhone.setText("");
                txtAuthor.requestFocus();
                loadAuthors();
            } else {
                JOptionPane.showMessageDialog(this, "Error creating author");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AuthorsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAddress;
    private javax.swing.JLabel labelAuthor;
    private javax.swing.JLabel labelPhone;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JPanel panelAuthors;
    private javax.swing.JTable tableAuthors;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
