/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tugasProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import tugasProject.jadwalMengajar;

/**
 *
 * @author ASUS
 */
public class GUI_jadwalMengajar extends javax.swing.JFrame {

    /**
     * Creates new form GUI_jadwalMengajar
     */
    public GUI_jadwalMengajar() {
        initComponents();
        koneksi(); // Panggil metode koneksi saat GUI dimulai
        tampil(); // Tampilkan data awal pada tabel saat GUI dimulai
        tampil_namaguru();
        tampil_mapel();
        //nonaktifkan cbKelas
        cbKelas.setEnabled(false);
    }

    //conection
    public Connection conn;

    //method koneksi()
    public void koneksi() {
        try {
            conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/projectoop23?user=root&password=");
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(GUI_mataPelajaran.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Koneksi Database!");
        }
    }

    //attribut
    String namaPengajar1, hari1, jam1, kelas1, mapel1;

    //method itempilih()
    public void itempilih() {
        int selectedRow = table_jadwalMengajar.getSelectedRow();

        // Menyimpan data yang dipilih dari tabel pada variabel
        namaPengajar1 = table_jadwalMengajar.getValueAt(selectedRow, 0).toString();
        hari1 = table_jadwalMengajar.getValueAt(selectedRow, 1).toString();
        jam1 = table_jadwalMengajar.getValueAt(selectedRow, 2).toString();
        kelas1 = table_jadwalMengajar.getValueAt(selectedRow, 3).toString();
        mapel1 = table_jadwalMengajar.getValueAt(selectedRow, 4).toString();

        cbnamaPengajar.setSelectedItem(namaPengajar1);
        cbHari.setSelectedItem(hari1);
        cbJam.setSelectedItem(jam1);
        cbKelas.setSelectedItem(kelas1);
        cbMapel.setSelectedItem(mapel1);
    }

    //method tampil_namaguru()
    public void tampil_namaguru() {
        try {
            koneksi(); // Memastikan koneksi terbentuk dengan benar

            // Query untuk mengambil data nama guru dari tabel tb_guru
            String sql = "SELECT nama FROM tb_guru ORDER BY nama ASC";
            PreparedStatement stt = conn.prepareStatement(sql);
            ResultSet res = stt.executeQuery();

            // Bersihkan ComboBox sebelum menambahkan data baru
            cbnamaPengajar.removeAllItems();

            // Tambahkan data nama guru ke dalam ComboBox
            while (res.next()) {
                String nama = res.getString("nama");
                cbnamaPengajar.addItem(nama);
            }

            res.close();
            stt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //method insert()
    public void insert() {
        String namaPengajar = cbnamaPengajar.getSelectedItem().toString();
        String hari = cbHari.getSelectedItem().toString();
        String jam = cbJam.getSelectedItem().toString();
        String mapel = cbMapel.getSelectedItem().toString();

        // Mengambil data kelas dari GUI Mata Pelajaran
        String kelas = getKelasFromMapel(mapel);

        try {
            koneksi();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO tb_jadwal (namaPengajar, hari, jam, kelas, mapel) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, namaPengajar);
            stmt.setString(2, hari);
            stmt.setString(3, jam);
            stmt.setString(4, kelas); // Menggunakan kelas yang diambil dari GUI Mata Pelajaran
            stmt.setString(5, mapel);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
                batal();
                tampil();
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menambahkan data");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //method mengamil kelas dari GUI_mataPelajaran
    private String getKelasFromMapel(String selectedMapel) {
        String kelas = "";

        try {
            koneksi();
            PreparedStatement stmt = conn.prepareStatement("SELECT kelas FROM tb_mapel WHERE namaMapel = ?");
            stmt.setString(1, selectedMapel);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                kelas = res.getString("kelas");
            }

            res.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }

        return kelas;
    }

    //method update()
    public void update() {
        String namaPengajar = cbnamaPengajar.getSelectedItem().toString();
        String hari = cbHari.getSelectedItem().toString();
        String jam = cbJam.getSelectedItem().toString();
        String kelas = cbKelas.getSelectedItem().toString();
        String mapel = cbMapel.getSelectedItem().toString();

        try {
            koneksi();
            PreparedStatement stmt = conn.prepareStatement("UPDATE tb_jadwal SET namaPengajar=?, hari=?, jam=?, kelas=? WHERE mapel=?");
            stmt.setString(1, namaPengajar);
            stmt.setString(2, hari);
            stmt.setString(3, jam);
            stmt.setString(4, kelas);
            stmt.setString(5, mapel);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
            batal();
            tampil();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //method delete()
    public void delete() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin akan menghapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            String mapel = cbMapel.getSelectedItem().toString();

            try {
                koneksi();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM tb_jadwal WHERE mapel=?");
                stmt.setString(1, mapel);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                    batal();
                    tampil();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau gagal dihapus");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
                e.printStackTrace();
            }
        }
        refresh(); // Memanggil refresh untuk memastikan data diperbarui setelah penghapusan
    }

    //method tampil()
    public void tampil() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("Nama Pengajar");
        tabelhead.addColumn("Hari");
        tabelhead.addColumn("Jam");
        tabelhead.addColumn("Kelas");
        tabelhead.addColumn("Mata Pelajaran");

        try {
            koneksi(); // Memastikan koneksi terbentuk dengan benar

            // Query untuk mengambil data yang diinginkan dari tabel yang sesuai (misalnya: tb_jadwal)
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("SELECT namaPengajar, hari, jam, kelas, mapel FROM tb_jadwal");

            // Mengisi data dari hasil query ke dalam tabel GUI
            while (res.next()) {
                tabelhead.addRow(new Object[]{
                    res.getString("namaPengajar"),
                    res.getString("hari"),
                    res.getString("jam"),
                    res.getString("kelas"),
                    res.getString("mapel")
                });
            }

            // Mengatur model tabel dengan data yang telah diambil
            table_jadwalMengajar.setModel(tabelhead);

            res.close();
            stat.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //method cari
    public void cari() {
        try {
            String searchText = txtCari.getText();
            DefaultTableModel model = (DefaultTableModel) table_jadwalMengajar.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table_jadwalMengajar.setRowSorter(sorter);

            if (searchText.trim().length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Menggunakan filter regex case-insensitive
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    //method refresh()
    public void refresh() {
        GUI_jadwalMengajar newGUI = new GUI_jadwalMengajar();
        newGUI.setVisible(true);
        newGUI.tampil(); // Memanggil method tampil() untuk memperbarui data di tabel
        this.setVisible(false);
    }

    // Method batal untuk membersihkan nilai komponen input
    public void batal() {
        cbnamaPengajar.setSelectedIndex(0);
        cbHari.setSelectedIndex(0);
        cbJam.setSelectedIndex(0);
        cbKelas.setSelectedIndex(0);
        cbMapel.setSelectedIndex(0);
    }

    //method tampil_mapel()
    public void tampil_mapel() {
        try {
            koneksi();
            String sql = "SELECT namaMapel FROM tb_mapel order by namaMapel asc";
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(sql);
            while (res.next()) {
                Object[] ob = new Object[3];
                ob[0] = res.getString(1);
                cbMapel.addItem((String) ob[0]);
            }
            res.close();
            stt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

        PopUpError = new javax.swing.JOptionPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbMapel = new javax.swing.JComboBox<>();
        cbHari = new javax.swing.JComboBox<>();
        cbJam = new javax.swing.JComboBox<>();
        cbKelas = new javax.swing.JComboBox<>();
        btnSimpan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_jadwalMengajar = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        cbnamaPengajar = new javax.swing.JComboBox<>();
        btnCari = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("JADWAL MENGAJAR");

        jLabel2.setText("Hari");

        jLabel3.setText("Jam");

        jLabel5.setText("Mata Pelajaran");

        cbMapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMapelActionPerformed(evt);
            }
        });

        cbHari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Senin", "Selasa", "Rabu", "Kamis", "Jumat" }));

        cbJam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ke - 1", "Ke - 2", "Ke - 3", "Ke - 4", "Ke - 5" }));

        cbKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10", "11", "12" }));

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        table_jadwalMengajar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Pengajar", "Hari", "Jam", "Kelas", "Mapel"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_jadwalMengajar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_jadwalMengajarMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_jadwalMengajar);

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jButton1.setText("Nama Pengajar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cbnamaPengajar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbnamaPengajarActionPerformed(evt);
            }
        });

        btnCari.setText("Cari Nama 🔍");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Kelas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbJam, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbKelas, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbMapel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbHari, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbnamaPengajar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                                .addComponent(btnHapus)
                                .addGap(32, 32, 32)
                                .addComponent(jButton3)
                                .addGap(27, 27, 27)
                                .addComponent(btnBatal)
                                .addGap(33, 33, 33)
                                .addComponent(btnClose))
                            .addComponent(jScrollPane2))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(305, 305, 305)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCari)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbnamaPengajar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbHari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbMapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal)
                    .addComponent(btnClose)
                    .addComponent(btnHapus)
                    .addComponent(jButton3))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        batal();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new GUI_pendataanGuru().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void table_jadwalMengajarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_jadwalMengajarMouseClicked
        // TODO add your handling code here:
        itempilih();
    }//GEN-LAST:event_table_jadwalMengajarMouseClicked

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_btnCariActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new GUI_mataPelajaran().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbMapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMapelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMapelActionPerformed

    private void cbnamaPengajarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbnamaPengajarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbnamaPengajarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_jadwalMengajar.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_jadwalMengajar.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_jadwalMengajar.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_jadwalMengajar.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_jadwalMengajar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JOptionPane PopUpError;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cbHari;
    private javax.swing.JComboBox<String> cbJam;
    private javax.swing.JComboBox<String> cbKelas;
    private javax.swing.JComboBox<String> cbMapel;
    private javax.swing.JComboBox<String> cbnamaPengajar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_jadwalMengajar;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
