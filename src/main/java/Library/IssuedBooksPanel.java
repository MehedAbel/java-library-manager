/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Library;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abel
 */
public class IssuedBooksPanel extends javax.swing.JPanel {

    /**
     * Creates new form IssuedBooksPanelPanel
     */
    public IssuedBooksPanel() {
        initComponents();
        dbConnect();
        loadComboMembers();
        loadComboBooks();
        loadIssuedBooksPanel();
        txtMemberID.setEnabled(false);
        comboMember.setEnabled(false);
        comboBook.setEnabled(false);
        issuedDateField.setEnabled(false);
        returnDateField.setEnabled(false);
        bookSearchField.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        comboMember.setSelectedIndex(-1);
        comboBook.setSelectedIndex(-1); 
        txtMemberID.setText("");
        bookSearchField.setText("");
    }
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public void dbConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IssuedBooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(IssuedBooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public class BookItem {
        int id;
        String name;
        
        public BookItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
    
    public class MemberItem {
        int id;
        String name;
        
        public MemberItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
   
   
    public void loadComboBooks() {
        try {
            pst = conn.prepareStatement("select * from book");
            rs = pst.executeQuery();
            comboBook.removeAllItems();
            
            while (rs.next()) {
                comboBook.addItem(new BookItem(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(IssuedBooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadComboMembers() {
        try {
            pst = conn.prepareStatement("select * from member");
            rs = pst.executeQuery();
            comboMember.removeAllItems();
            
            while (rs.next()) {
                comboMember.addItem(new MemberItem(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(IssuedBooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void loadIssuedBooksPanel() {
        try {
            pst = conn.prepareStatement("SELECT i.id, m.id, m.name, b.id, b.name, i.issued_date, i.return_date from issued_book i JOIN member m ON i.member_id = m.id JOIN book b ON i.book_id = b.id");
            rs = pst.executeQuery();
            
            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel)tableBooks.getModel();
            d.setRowCount(0);
            
            while (rs.next()) {
                Vector v = new Vector();
                
                for (int i = 1; i < c; i++) {
                    v.add(rs.getString("i.id"));
                    v.add(rs.getString("m.id"));
                    v.add(rs.getString("m.name"));
                    v.add(rs.getString("b.id"));
                    v.add(rs.getString("b.name"));
                    v.add(rs.getString("i.issued_date"));
                    v.add(rs.getString("i.return_date"));
                }
                
                d.addRow(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IssuedBooksPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        panelIssueBook = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        txtMemberID = new javax.swing.JTextField();
        labelMemberID = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelReturnDate = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBooks = new javax.swing.JTable();
        labelBookID = new javax.swing.JLabel();
        comboBook = new javax.swing.JComboBox();
        labelDate = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        issuedDateField = new com.toedter.calendar.JDateChooser();
        returnDateField = new com.toedter.calendar.JDateChooser();
        bookSearchField = new javax.swing.JTextField();
        comboMember = new javax.swing.JComboBox();

        panelIssueBook.setBackground(new java.awt.Color(51, 51, 51));
        panelIssueBook.setPreferredSize(new java.awt.Dimension(1218, 850));

        labelTitle.setFont(new java.awt.Font("Liberation Serif", 1, 36)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(204, 204, 204));
        labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitle.setText("Issued Books");
        labelTitle.setToolTipText("");

        txtMemberID.setBackground(new java.awt.Color(80, 80, 80));
        txtMemberID.setForeground(new java.awt.Color(255, 255, 255));
        txtMemberID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemberIDActionPerformed(evt);
            }
        });
        txtMemberID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMemberIDKeyPressed(evt);
            }
        });

        labelMemberID.setBackground(new java.awt.Color(80, 80, 80));
        labelMemberID.setForeground(new java.awt.Color(204, 204, 204));
        labelMemberID.setText("Member ID");

        labelReturnDate.setBackground(new java.awt.Color(80, 80, 80));
        labelReturnDate.setForeground(new java.awt.Color(204, 204, 204));
        labelReturnDate.setText("Return Date");

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

        tableBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Member ID", "Member", "Book ID", "Book", "Issued Date", "Return Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBooksMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBooks);

        labelBookID.setBackground(new java.awt.Color(80, 80, 80));
        labelBookID.setForeground(new java.awt.Color(204, 204, 204));
        labelBookID.setText("Book ID");

        comboBook.setBackground(new java.awt.Color(80, 80, 80));
        comboBook.setForeground(new java.awt.Color(204, 204, 204));
        comboBook.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBook.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBookItemStateChanged(evt);
            }
        });
        comboBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBookActionPerformed(evt);
            }
        });

        labelDate.setBackground(new java.awt.Color(80, 80, 80));
        labelDate.setForeground(new java.awt.Color(204, 204, 204));
        labelDate.setText("Issued Date");

        issuedDateField.setBackground(new java.awt.Color(80, 80, 80));
        issuedDateField.setForeground(new java.awt.Color(255, 255, 255));

        returnDateField.setBackground(new java.awt.Color(80, 80, 80));
        returnDateField.setForeground(new java.awt.Color(255, 255, 255));

        bookSearchField.setBackground(new java.awt.Color(80, 80, 80));
        bookSearchField.setForeground(new java.awt.Color(255, 255, 255));
        bookSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookSearchFieldActionPerformed(evt);
            }
        });
        bookSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bookSearchFieldKeyPressed(evt);
            }
        });

        comboMember.setBackground(new java.awt.Color(80, 80, 80));
        comboMember.setForeground(new java.awt.Color(204, 204, 204));
        comboMember.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboMember.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboMemberItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelIssueBookLayout = new javax.swing.GroupLayout(panelIssueBook);
        panelIssueBook.setLayout(panelIssueBookLayout);
        panelIssueBookLayout.setHorizontalGroup(
            panelIssueBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIssueBookLayout.createSequentialGroup()
                .addGroup(panelIssueBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIssueBookLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(panelIssueBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboMember, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMemberID)
                            .addGroup(panelIssueBookLayout.createSequentialGroup()
                                .addGroup(panelIssueBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelTitle)
                                    .addComponent(txtMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelBookID)
                                    .addComponent(bookSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBook, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelDate)
                                    .addComponent(issuedDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelReturnDate)
                                    .addGroup(panelIssueBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(panelIssueBookLayout.createSequentialGroup()
                                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(returnDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(14, 14, 14)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelIssueBookLayout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelIssueBookLayout.setVerticalGroup(
            panelIssueBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIssueBookLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelIssueBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIssueBookLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelIssueBookLayout.createSequentialGroup()
                        .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelIssueBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelIssueBookLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(375, 375, 375)
                                .addComponent(jLabel3))
                            .addGroup(panelIssueBookLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelMemberID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboMember, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelBookID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bookSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBook, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(issuedDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelReturnDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(returnDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelIssueBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEdit)
                                    .addComponent(btnDelete))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(btnClear)
                        .addGap(444, 444, 444))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelIssueBook, javax.swing.GroupLayout.DEFAULT_SIZE, 1840, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelIssueBook, javax.swing.GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtMemberIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemberIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMemberIDActionPerformed

    private void txtMemberIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMemberIDKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int m_id = Integer.parseInt(txtMemberID.getText().trim());

            for (int i = 0; i < comboMember.getItemCount(); i++) {
                IssuedBooksPanel.MemberItem item = (IssuedBooksPanel.MemberItem) comboMember.getItemAt(i);

                if (item.id == m_id) {
                    comboMember.setSelectedItem(item);
                    break;
                }
            }
        }
    }//GEN-LAST:event_txtMemberIDKeyPressed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)tableBooks.getModel();
        int selectIndex = tableBooks.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());

        MemberItem member = (MemberItem) comboMember.getSelectedItem();
        BookItem book = (BookItem) comboBook.getSelectedItem();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String issuedDate = dateFormat.format(issuedDateField.getDate());
        String returnDate = dateFormat.format(returnDateField.getDate());

        try {
            pst = conn.prepareStatement("update issued_book set member_id = ?, book_id = ?, issued_date = ?, return_date = ? where id = ?");
            pst.setInt(1, member.id);
            pst.setInt(2, book.id);
            pst.setString(3, issuedDate);
            pst.setString(4, returnDate);
            pst.setInt(5, id);
            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Issued Book updated");
                txtMemberID.setText("");
                comboMember.setSelectedIndex(-1);
                bookSearchField.setText("");
                comboBook.setSelectedIndex(-1);
                issuedDateField.setDate(null);
                returnDateField.setDate(null);
                txtMemberID.setEnabled(false);
                comboMember.setEnabled(false);
                comboBook.setEnabled(false);
                issuedDateField.setEnabled(false);
                returnDateField.setEnabled(false);
                bookSearchField.setEnabled(false);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
                loadIssuedBooksPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Error updating Issued Book entry");
            }

        } catch (SQLException ex) {
            Logger.getLogger(IssuedBooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)tableBooks.getModel();
        int selectIndex = tableBooks.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());

        try {
            pst = conn.prepareStatement("delete from issued_book where id = ?");
            pst.setInt(1, id);
            int executed = pst.executeUpdate();
            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Issued book entry deleted");
                txtMemberID.setText("");
                comboMember.setSelectedIndex(-1);
                bookSearchField.setText("");
                comboBook.setSelectedIndex(-1);
                issuedDateField.setDate(null);
                returnDateField.setDate(null);
                txtMemberID.setEnabled(false);
                comboMember.setEnabled(false);
                comboBook.setEnabled(false);
                issuedDateField.setEnabled(false);
                returnDateField.setEnabled(false);
                bookSearchField.setEnabled(false);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
                loadIssuedBooksPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting Issued Book Entry");
            }

        } catch (SQLException ex) {
            Logger.getLogger(IssuedBooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        txtMemberID.setText("");
        comboMember.setSelectedIndex(-1);
        bookSearchField.setText("");
        comboBook.setSelectedIndex(-1);
        issuedDateField.setDate(null);
        returnDateField.setDate(null);
        txtMemberID.setEnabled(false);
        comboMember.setEnabled(false);
        comboBook.setEnabled(false);
        issuedDateField.setEnabled(false);
        returnDateField.setEnabled(false);
        bookSearchField.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        tableBooks.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tableBooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBooksMouseClicked
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)tableBooks.getModel();

        int selectIndex = tableBooks.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());
        int m_id = Integer.parseInt(d.getValueAt(selectIndex, 1).toString());
        int b_id = Integer.parseInt(d.getValueAt(selectIndex, 3).toString());
        String m_name = d.getValueAt(selectIndex, 2).toString();
        String book_name = d.getValueAt(selectIndex, 4).toString();
        String issued_date = d.getValueAt(selectIndex, 5).toString();
        String return_date = d.getValueAt(selectIndex, 6).toString();

        for (int i = 0; i < comboMember.getItemCount(); i++) {
            IssuedBooksPanel.MemberItem memberItem = (IssuedBooksPanel.MemberItem) comboMember.getItemAt(i);
            if (memberItem.id == m_id) {
                comboMember.setSelectedItem(memberItem);
                break;
            }
        }

        for (int i = 0; i < comboBook.getItemCount(); i++) {
            BookItem bookItem = (BookItem) comboBook.getItemAt(i);
            if (bookItem.id == b_id) {
                comboBook.setSelectedItem(bookItem);
                break;
            }
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date issuedDate = dateFormat.parse(issued_date);
            Date returnDate = dateFormat.parse(return_date);

            issuedDateField.setDate(issuedDate);
            returnDateField.setDate(returnDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtMemberID.setEnabled(true);
        comboMember.setEnabled(true);
        comboBook.setEnabled(true);
        issuedDateField.setEnabled(true);
        returnDateField.setEnabled(true);
        bookSearchField.setEnabled(true);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
    }//GEN-LAST:event_tableBooksMouseClicked

    private void comboBookItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBookItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            IssuedBooksPanel.BookItem book = (IssuedBooksPanel.BookItem) comboBook.getSelectedItem();
            if (book != null) {
                bookSearchField.setText(String.valueOf(book.id));
            }
        }
    }//GEN-LAST:event_comboBookItemStateChanged

    private void comboBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBookActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBookActionPerformed

    private void bookSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookSearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookSearchFieldActionPerformed

    private void bookSearchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bookSearchFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int b_id = Integer.parseInt(bookSearchField.getText().trim());

            for (int i = 0; i < comboBook.getItemCount(); i++) {
                IssuedBooksPanel.BookItem item = (IssuedBooksPanel.BookItem) comboBook.getItemAt(i);

                if (item.id == b_id) {
                    comboBook.setSelectedItem(item);
                    break;
                }
            }
        }
    }//GEN-LAST:event_bookSearchFieldKeyPressed

    private void comboMemberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboMemberItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            IssuedBooksPanel.MemberItem member = (IssuedBooksPanel.MemberItem) comboMember.getSelectedItem();
            if (member != null) {
                txtMemberID.setText(String.valueOf(member.id));
            }
        }
    }//GEN-LAST:event_comboMemberItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bookSearchField;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JComboBox comboBook;
    private javax.swing.JComboBox comboMember;
    private javax.swing.Box.Filler filler1;
    private com.toedter.calendar.JDateChooser issuedDateField;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelBookID;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelMemberID;
    private javax.swing.JLabel labelReturnDate;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JPanel panelIssueBook;
    private com.toedter.calendar.JDateChooser returnDateField;
    private javax.swing.JTable tableBooks;
    private javax.swing.JTextField txtMemberID;
    // End of variables declaration//GEN-END:variables
}
