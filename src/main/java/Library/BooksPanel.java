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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abel
 */
public class BooksPanel extends javax.swing.JPanel {

    /**
     * Creates new form BooksPanel
     */
    public BooksPanel() {
        initComponents();
        dbConnect();
        loadAuthors();
        loadPublishers();
        loadCategories();
        loadComboFilter();
        loadBooks();
        btnIssueBook.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        comboAuthor.setSelectedIndex(-1);
        comboPublisher.setSelectedIndex(-1);
        comboCategory.setSelectedIndex(-1);
        authorSearchField.setText("");
        publisherSearchField.setText("");
        categorySearchField.setText("");
    }
    
    public class AuthorItem {
        int id;
        String name;
        
        public AuthorItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
    
    public class PublisherItem {
        int id;
        String name;
        
        public PublisherItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
    
    public class CategoryItem {
        int id;
        String name;
        
        public CategoryItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
    }
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    static int selectedBookID;
  
    
    public void dbConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadAuthors() {
        try {
            pst = conn.prepareStatement("select * from author");
            rs = pst.executeQuery();
            comboAuthor.removeAllItems();
            
            while (rs.next()) {
                comboAuthor.addItem(new AuthorItem(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadPublishers() {
        try {
            pst = conn.prepareStatement("select * from publisher");
            rs = pst.executeQuery();
            comboPublisher.removeAllItems();
            
            while (rs.next()) {
                comboPublisher.addItem(new PublisherItem(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadCategories() {
        try {
            pst = conn.prepareStatement("select * from category");
            rs = pst.executeQuery();
            comboCategory.removeAllItems();
            
            while (rs.next()) {
                comboCategory.addItem(new CategoryItem(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadComboFilter() {
        comboFilter.removeAllItems();
        comboFilter.addItem("Name");
        comboFilter.addItem("Author");
        comboFilter.addItem("Publisher");
        comboFilter.addItem("Category");
    }
    
    public void loadBooks() {
        try {
            String searchQuery = searchField.getText();
            if (searchQuery.isEmpty()) {
                pst = conn.prepareStatement("SELECT b.id, b.name, a.name, p.name, c.name, b.description FROM book b JOIN author a ON a.id = b.author JOIN publisher p ON p.id = b.publisher JOIN category c ON c.id = b.category;");
            } else {
                String filter = comboFilter.getSelectedItem().toString();
                String searchPattern = "%" + searchQuery + "%";
                    switch (filter) {
                        case "Name":
                            pst = conn.prepareStatement("SELECT b.id, b.name, a.name, p.name, c.name, b.description FROM book b JOIN author a ON a.id = b.author JOIN publisher p ON p.id = b.publisher JOIN category c ON c.id = b.category WHERE b.name LIKE ?");
                            pst.setString(1, searchPattern);
                            break;
                        case "Author":
                            pst = conn.prepareStatement("SELECT b.id, b.name, a.name, p.name, c.name, b.description FROM book b JOIN author a ON a.id = b.author JOIN publisher p ON p.id = b.publisher JOIN category c ON c.id = b.category WHERE a.name LIKE ?");
                            pst.setString(1, searchPattern);
                            break;
                        case "Publisher":
                            pst = conn.prepareStatement("SELECT b.id, b.name, a.name, p.name, c.name, b.description FROM book b JOIN author a ON a.id = b.author JOIN publisher p ON p.id = b.publisher JOIN category c ON c.id = b.category WHERE p.name LIKE ?");
                            pst.setString(1, searchPattern);
                            break;
                        case "Category":
                            pst = conn.prepareStatement("SELECT b.id, b.name, a.name, p.name, c.name, b.description FROM book b JOIN author a ON a.id = b.author JOIN publisher p ON p.id = b.publisher JOIN category c ON c.id = b.category WHERE c.name LIKE ?");
                            pst.setString(1, searchPattern);
                            break;
                    }
            }
            
            rs = pst.executeQuery();
            
            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            
            DefaultTableModel d = (DefaultTableModel)tableBooks.getModel();
            d.setRowCount(0);
            
            while (rs.next()) {
                Vector v = new Vector();
                
                for (int i = 1; i < c; i++) {
                    v.add(rs.getString("b.id"));
                    v.add(rs.getString("b.name"));
                    v.add(rs.getString("a.name"));
                    v.add(rs.getString("p.name"));
                    v.add(rs.getString("c.name"));
                    v.add(rs.getString("b.description"));
                }   
                
                d.addRow(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BooksPanel.class.getName()).log(Level.SEVERE, null, ex);
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

        panelBooks = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        txtBook = new javax.swing.JTextField();
        labelBook = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelDescription = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBooks = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        labelAuthor = new javax.swing.JLabel();
        comboAuthor = new javax.swing.JComboBox();
        labelPublisher = new javax.swing.JLabel();
        comboPublisher = new javax.swing.JComboBox();
        labelCategory = new javax.swing.JLabel();
        comboCategory = new javax.swing.JComboBox();
        authorSearchField = new javax.swing.JTextField();
        publisherSearchField = new javax.swing.JTextField();
        categorySearchField = new javax.swing.JTextField();
        btnIssueBook = new javax.swing.JButton();
        searchBookLabel = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        comboFilter = new javax.swing.JComboBox<>();

        panelBooks.setBackground(new java.awt.Color(51, 51, 51));
        panelBooks.setForeground(java.awt.Color.white);
        panelBooks.setPreferredSize(new java.awt.Dimension(1218, 850));

        labelTitle.setFont(new java.awt.Font("Liberation Serif", 1, 36)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(204, 204, 204));
        labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitle.setText("Books");
        labelTitle.setToolTipText("");

        txtBook.setBackground(new java.awt.Color(80, 80, 80));
        txtBook.setForeground(new java.awt.Color(255, 255, 255));

        labelBook.setForeground(new java.awt.Color(255, 255, 255));
        labelBook.setText("Name");

        labelDescription.setForeground(new java.awt.Color(255, 255, 255));
        labelDescription.setText("Description");

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

        tableBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Author", "Publisher", "Category", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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

        txtDescription.setBackground(new java.awt.Color(80, 80, 80));
        txtDescription.setColumns(20);
        txtDescription.setForeground(new java.awt.Color(255, 255, 255));
        txtDescription.setRows(5);
        jScrollPane2.setViewportView(txtDescription);

        labelAuthor.setForeground(java.awt.Color.white);
        labelAuthor.setText("Author");

        comboAuthor.setBackground(new java.awt.Color(80, 80, 80));
        comboAuthor.setForeground(new java.awt.Color(250, 250, 250));
        comboAuthor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboAuthor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboAuthorItemStateChanged(evt);
            }
        });
        comboAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAuthorActionPerformed(evt);
            }
        });

        labelPublisher.setForeground(java.awt.Color.white);
        labelPublisher.setText("Publisher");

        comboPublisher.setBackground(new java.awt.Color(80, 80, 80));
        comboPublisher.setForeground(new java.awt.Color(250, 250, 250));
        comboPublisher.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboPublisher.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPublisherItemStateChanged(evt);
            }
        });

        labelCategory.setForeground(java.awt.Color.white);
        labelCategory.setText("Category");

        comboCategory.setBackground(new java.awt.Color(80, 80, 80));
        comboCategory.setForeground(new java.awt.Color(250, 250, 250));
        comboCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboCategory.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCategoryItemStateChanged(evt);
            }
        });
        comboCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoryActionPerformed(evt);
            }
        });

        authorSearchField.setBackground(new java.awt.Color(80, 80, 80));
        authorSearchField.setForeground(new java.awt.Color(250, 250, 250));
        authorSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                authorSearchFieldKeyPressed(evt);
            }
        });

        publisherSearchField.setBackground(new java.awt.Color(80, 80, 80));
        publisherSearchField.setForeground(new java.awt.Color(250, 250, 250));
        publisherSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                publisherSearchFieldKeyPressed(evt);
            }
        });

        categorySearchField.setBackground(new java.awt.Color(80, 80, 80));
        categorySearchField.setForeground(new java.awt.Color(250, 250, 250));
        categorySearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categorySearchFieldActionPerformed(evt);
            }
        });
        categorySearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                categorySearchFieldKeyPressed(evt);
            }
        });

        btnIssueBook.setBackground(new java.awt.Color(80, 80, 80));
        btnIssueBook.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        btnIssueBook.setForeground(java.awt.Color.white);
        btnIssueBook.setText("Issue Book");
        btnIssueBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIssueBookActionPerformed(evt);
            }
        });

        searchBookLabel.setFont(new java.awt.Font("Cantarell", 0, 18)); // NOI18N
        searchBookLabel.setForeground(new java.awt.Color(255, 255, 255));
        searchBookLabel.setText("Search");

        searchField.setBackground(new java.awt.Color(80, 80, 80));
        searchField.setForeground(new java.awt.Color(255, 255, 255));
        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchFieldKeyPressed(evt);
            }
        });

        comboFilter.setBackground(new java.awt.Color(80, 80, 80));
        comboFilter.setForeground(new java.awt.Color(255, 255, 255));
        comboFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBooksLayout = new javax.swing.GroupLayout(panelBooks);
        panelBooks.setLayout(panelBooksLayout);
        panelBooksLayout.setHorizontalGroup(
            panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBooksLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(publisherSearchField)
                        .addComponent(labelCategory)
                        .addComponent(comboPublisher, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelPublisher)
                        .addComponent(comboAuthor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(categorySearchField)
                        .addComponent(comboCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelDescription)
                        .addComponent(authorSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBooksLayout.createSequentialGroup()
                        .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelBooksLayout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(btnIssueBook))
                    .addComponent(labelAuthor)
                    .addComponent(labelBook)
                    .addComponent(labelTitle)
                    .addComponent(txtBook, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBooksLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBooksLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1383, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel3))
                            .addGroup(panelBooksLayout.createSequentialGroup()
                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelBooksLayout.createSequentialGroup()
                        .addGap(273, 273, 273)
                        .addComponent(searchBookLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelBooksLayout.setVerticalGroup(
            panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBooksLayout.createSequentialGroup()
                .addGap(383, 383, 383)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelBooksLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBook)
                    .addComponent(searchBookLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtBook))
                .addGap(18, 18, 18)
                .addComponent(labelAuthor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBooksLayout.createSequentialGroup()
                        .addComponent(authorSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelPublisher)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(publisherSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(categorySearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelDescription)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEdit)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelBooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDelete)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnIssueBook, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBooksLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBooks, javax.swing.GroupLayout.DEFAULT_SIZE, 1845, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBooks, javax.swing.GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        String name = txtBook.getText();
        AuthorItem authorItem = (AuthorItem) comboAuthor.getSelectedItem();
        PublisherItem publisherItem = (PublisherItem) comboPublisher.getSelectedItem();
        CategoryItem categoryItem = (CategoryItem) comboCategory.getSelectedItem();
        String description = txtDescription.getText();

        try {
            pst = conn.prepareStatement("insert into book(name, author, publisher, category, description) values(?, ?, ?, ?, ?)");
            pst.setString(1, name);
            pst.setInt(2, authorItem.id);
            pst.setInt(3, publisherItem.id);
            pst.setInt(4, categoryItem.id);
            pst.setString(5, description);

            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Book created");
                txtBook.setText("");
                authorSearchField.setText("");
                publisherSearchField.setText("");
                categorySearchField.setText("");
                comboAuthor.setSelectedIndex(-1);
                comboPublisher.setSelectedIndex(-1);
                comboCategory.setSelectedIndex(-1);
                txtDescription.setText("");
                txtBook.requestFocus();
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Error creating book");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)tableBooks.getModel();
        int selectIndex = tableBooks.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());

        String book_name = txtBook.getText();
        AuthorItem author = (AuthorItem) comboAuthor.getSelectedItem();
        PublisherItem publisher = (PublisherItem) comboPublisher.getSelectedItem();
        CategoryItem category = (CategoryItem) comboCategory.getSelectedItem();
        String description = txtDescription.getText();

        try {
            pst = conn.prepareStatement("update book set name = ?, author = ?, publisher = ?, category = ?, description = ? where id = ?");
            pst.setString(1, book_name);
            pst.setInt(2, author.id);
            pst.setInt(3, publisher.id);
            pst.setInt(4, category.id);
            pst.setString(5, description);
            pst.setInt(6, id);
            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Book updated");
                txtBook.setText("");
                txtDescription.setText("");
                authorSearchField.setText("");
                publisherSearchField.setText("");
                categorySearchField.setText("");
                comboAuthor.setSelectedIndex(-1);
                comboPublisher.setSelectedIndex(-1);
                comboCategory.setSelectedIndex(-1);
                txtBook.requestFocus();
                loadBooks();
                btnAdd.setEnabled(true);
                btnIssueBook.setEnabled(false);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error updating book");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        DefaultTableModel d = (DefaultTableModel)tableBooks.getModel();
        int selectIndex = tableBooks.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());

        try {
            pst = conn.prepareStatement("delete from book where id = ?");
            pst.setInt(1, id);
            int executed = pst.executeUpdate();

            if (executed == 1) {
                JOptionPane.showMessageDialog(this, "Book deleted");
                txtBook.setText("");
                txtDescription.setText("");
                authorSearchField.setText("");
                publisherSearchField.setText("");
                categorySearchField.setText("");
                comboAuthor.setSelectedIndex(-1);
                comboPublisher.setSelectedIndex(-1);
                comboCategory.setSelectedIndex(-1);
                txtBook.requestFocus();
                loadBooks();
                btnAdd.setEnabled(true);
                btnIssueBook.setEnabled(false);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting book");
            }

        } catch (SQLException ex) {
            Logger.getLogger(BooksPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        btnAdd.setEnabled(true);
        btnIssueBook.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        txtBook.setText("");
        txtDescription.setText("");
        authorSearchField.setText("");
        publisherSearchField.setText("");
        categorySearchField.setText("");
        comboAuthor.setSelectedIndex(-1);
        comboPublisher.setSelectedIndex(-1);
        comboCategory.setSelectedIndex(-1);
        txtBook.requestFocus();
        searchField.setText("");
        tableBooks.clearSelection();
        loadBooks();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tableBooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBooksMouseClicked
        // TODO add your handling code here:
        btnIssueBook.setEnabled(true);
        DefaultTableModel d = (DefaultTableModel)tableBooks.getModel();

        int selectIndex = tableBooks.getSelectedRow();
        int id = Integer.parseInt(d.getValueAt(selectIndex, 0).toString());
        selectedBookID = id;
        txtBook.setText(d.getValueAt(selectIndex, 1).toString());
        txtDescription.setText(d.getValueAt(selectIndex, 5).toString());

        for (int i = 0; i < comboAuthor.getItemCount(); i++) {
            AuthorItem item = (AuthorItem) comboAuthor.getItemAt(i);
            if (item.toString().equals(d.getValueAt(selectIndex, 2))) {
                comboAuthor.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < comboPublisher.getItemCount(); i++) {
            PublisherItem item = (PublisherItem) comboPublisher.getItemAt(i);
            if (item.toString().equals(d.getValueAt(selectIndex, 3))) {
                comboPublisher.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < comboCategory.getItemCount(); i++) {
            CategoryItem item = (CategoryItem) comboCategory.getItemAt(i);
            if (item.toString().equals(d.getValueAt(selectIndex, 4))) {
                comboCategory.setSelectedIndex(i);
                break;
            }
        }

        btnAdd.setEnabled(false);
        btnIssueBook.setEnabled(true);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
    }//GEN-LAST:event_tableBooksMouseClicked

    private void comboAuthorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboAuthorItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            AuthorItem author = (AuthorItem) comboAuthor.getSelectedItem();
            if (author != null) {
                authorSearchField.setText(author.name);
            }
        }
    }//GEN-LAST:event_comboAuthorItemStateChanged

    private void comboAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAuthorActionPerformed
        // TODO add your handling code here:
        //        AuthorItem author = (AuthorItem) comboAuthor.getSelectedItem();
        //        authorSearchField.setText(author.name);
    }//GEN-LAST:event_comboAuthorActionPerformed

    private void comboPublisherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPublisherItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            PublisherItem publisher = (PublisherItem) comboPublisher.getSelectedItem();
            if (publisher != null) {
                publisherSearchField.setText(publisher.name);
            }
        }
    }//GEN-LAST:event_comboPublisherItemStateChanged

    private void comboCategoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCategoryItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            CategoryItem category = (CategoryItem) comboCategory.getSelectedItem();
            if (category != null) {
                categorySearchField.setText(category.name);
            }
        }
    }//GEN-LAST:event_comboCategoryItemStateChanged

    private void comboCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoryActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_comboCategoryActionPerformed

    private void authorSearchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_authorSearchFieldKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String searchText = authorSearchField.getText().trim();

            for (int i = 0; i < comboAuthor.getItemCount(); i++) {
                AuthorItem item = (AuthorItem) comboAuthor.getItemAt(i);

                if (item.name.equalsIgnoreCase(searchText)) {
                    comboAuthor.setSelectedItem(item);
                    break;
                }
            }
        }
    }//GEN-LAST:event_authorSearchFieldKeyPressed

    private void publisherSearchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_publisherSearchFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String searchText = publisherSearchField.getText().trim();

            for (int i = 0; i < comboPublisher.getItemCount(); i++) {
                PublisherItem item = (PublisherItem) comboPublisher.getItemAt(i);

                if (item.name.equalsIgnoreCase(searchText)) {
                    comboPublisher.setSelectedItem(item);
                    break;
                }
            }
        }
    }//GEN-LAST:event_publisherSearchFieldKeyPressed

    private void categorySearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categorySearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categorySearchFieldActionPerformed

    private void categorySearchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_categorySearchFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String searchText = categorySearchField.getText().trim();

            for (int i = 0; i < comboCategory.getItemCount(); i++) {
                CategoryItem item = (CategoryItem) comboCategory.getItemAt(i);

                if (item.name.equalsIgnoreCase(searchText)) {
                    comboCategory.setSelectedItem(item);
                    break;
                }
            }
        }
    }//GEN-LAST:event_categorySearchFieldKeyPressed

    private void btnIssueBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIssueBookActionPerformed
        // TODO add your handling code here:
        IssueBookDialog ibg = new IssueBookDialog(selectedBookID);
        ibg.setVisible(true);
    }//GEN-LAST:event_btnIssueBookActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed

    private void comboFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFilterActionPerformed

    private void searchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loadBooks();
        }
    }//GEN-LAST:event_searchFieldKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField authorSearchField;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnIssueBook;
    private javax.swing.JTextField categorySearchField;
    private javax.swing.JComboBox comboAuthor;
    private javax.swing.JComboBox comboCategory;
    private javax.swing.JComboBox<String> comboFilter;
    private javax.swing.JComboBox comboPublisher;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAuthor;
    private javax.swing.JLabel labelBook;
    private javax.swing.JLabel labelCategory;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelPublisher;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JPanel panelBooks;
    private javax.swing.JTextField publisherSearchField;
    private javax.swing.JLabel searchBookLabel;
    private javax.swing.JTextField searchField;
    private javax.swing.JTable tableBooks;
    private javax.swing.JTextField txtBook;
    private javax.swing.JTextArea txtDescription;
    // End of variables declaration//GEN-END:variables
}
