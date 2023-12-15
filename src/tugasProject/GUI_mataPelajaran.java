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
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import tugasProject.GUI_mataPelajaran;

/**
 *
 * @author ASUS
 */
public class GUI_mataPelajaran extends javax.swing.JFrame {

    /**
     * Creates new form GUI_mataPelajaran
     */
    public GUI_mataPelajaran() {
        initComponents();
        koneksi(); // Panggil metode koneksi saat GUI dimulai
        tampil(); // Tampilkan data awal pada tabel saat GUI dimulai
        tampil_guru();
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
    String kodeGuru1, namaMapel1, kodeMapel1, kelas1;

    //method itempilih()
    public void itempilih() {
        int selectedRow = table_dataMataPelajaran.getSelectedRow();

        // Menyimpan data yang dipilih dari tabel pada variabel
        kodeGuru1 = table_dataMataPelajaran.getValueAt(selectedRow, 0).toString();
        namaMapel1 = table_dataMataPelajaran.getValueAt(selectedRow, 1).toString();
        kodeMapel1 = table_dataMataPelajaran.getValueAt(selectedRow, 2).toString();
        kelas1 = table_dataMataPelajaran.getValueAt(selectedRow, 3).toString();

        // Menampilkan data pada komponen-komponen GUI
        cmkodeGuru.setSelectedItem(kodeGuru1);
        txtMapel.setText(namaMapel1);
        txtkodeMapel.setText(kodeMapel1);
        txtkelasMapel.setText(kelas1);
    }

    //method tampil_guru()
    public void tampil_guru() {
        try {
            // Pastikan koneksi terbentuk dengan benar
            koneksi();

            // Ambil data kode guru dari tabel guru di GUI Pendataan Guru
            String sql = "SELECT kodeGuru FROM tb_guru ORDER BY kodeGuru ASC";
            Statement stt = conn.createStatement();
            ResultSet res = stt.executeQuery(sql);

            // Bersihkan ComboBox sebelum menambahkan data baru
            cmkodeGuru.removeAllItems();

            // Tambahkan data kode guru ke dalam ComboBox
            while (res.next()) {
                String kodeGuru = res.getString("kodeGuru");
                cmkodeGuru.addItem(kodeGuru);
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
        String kodeGuru = cmkodeGuru.getSelectedItem().toString();
        String namaMapel = txtMapel.getText();
        String kodeMapel = txtkodeMapel.getText();
        String kelas = txtkelasMapel.getText();

        try {
            koneksi();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO tb_mapel (kodeGuru, namaMapel, kodeMapel, kelas) VALUES (?, ?, ?, ?)");
            stmt.setString(1, kodeGuru);
            stmt.setString(2, namaMapel);
            stmt.setString(3, kodeMapel);
            stmt.setString(4, kelas);

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
        } finally {
            // Tutup koneksi di sini jika perlu
            // Tutup statement di sini jika perlu
        }
        batal();
    }

    //method update()
    public void update() {
        String kodeGuru = cmkodeGuru.getSelectedItem().toString();
        String namaMapel = txtMapel.getText();
        String kodeMapel = txtkodeMapel.getText();
        String kelas = txtkelasMapel.getText();

        try {
            koneksi();
            PreparedStatement stmt = conn.prepareStatement("UPDATE tb_mapel SET kodeGuru=?, namaMapel=?, kodeMapel=?, kelas=? WHERE kodeMapel=?");
            stmt.setString(1, kodeGuru);
            stmt.setString(2, namaMapel);
            stmt.setString(3, kodeMapel);
            stmt.setString(4, kelas);
            stmt.setString(5, kodeMapel);
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
            String kodeMapel = txtkodeMapel.getText();

            try {
                koneksi();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM tb_mapel WHERE kodeMapel=?");
                stmt.setString(1, kodeMapel);
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
        refresh(); // Memanggil refresh di sini untuk memastikan data diperbarui setelah penghapusan
    }

    //method tampil()
    public void tampil() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("Kode Guru");
        tabelhead.addColumn("Nama Mapel");
        tabelhead.addColumn("Kode Mapel");
        tabelhead.addColumn("Kelas");

        try {
            koneksi();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM tb_mapel");
            while (res.next()) {
                tabelhead.addRow(new Object[]{
                    res.getString("kodeGuru"),
                    res.getString("namaMapel"),
                    res.getString("kodeMapel"),
                    res.getString("kelas")
                });
            }
            table_dataMataPelajaran.setModel(tabelhead);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //method refresh()
    public void refresh() {
        GUI_mataPelajaran newGUI = new GUI_mataPelajaran();
        newGUI.setVisible(true);
        newGUI.tampil(); // Memanggil method tampil() untuk memperbarui data di tabel
        this.setVisible(false);
    }

    //method batal()
    public void batal() {
        cmkodeGuru.setSelectedIndex(0);
        txtMapel.setText("");
        txtkodeMapel.setText("");
        txtkelasMapel.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMapel = new javax.swing.JTextField();
        txtkelasMapel = new javax.swing.JTextField();
        txtkodeMapel = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnTutup = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_dataMataPelajaran = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnkodeGuru = new javax.swing.JButton();
        cmkodeGuru = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+6));
        jLabel1.setText("MATA PELAJARAN");

        jLabel2.setText("Nama Mata Pelajaran");

        jLabel3.setText("Kode Mata Pelajaran");

        jLabel4.setText("Kelas");

        txtMapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMapelActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

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

        btnTutup.setText("Tutup");
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });

        table_dataMataPelajaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Guru", "Nama Mapel", "Kode Mapel", "Kelas"
            }
        ));
        table_dataMataPelajaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_dataMataPelajaranMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_dataMataPelajaran);

        jButton1.setText("Form Jadwal Mengajar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnkodeGuru.setText("Kode Guru");
        btnkodeGuru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkodeGuruActionPerformed(evt);
            }
        });

        cmkodeGuru.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "~Kode Guru~" }));
        cmkodeGuru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmkodeGuruActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(300, 300, 300)
                            .addComponent(jLabel1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(32, 32, 32)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnkodeGuru)))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtMapel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                        .addComponent(txtkelasMapel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                        .addComponent(txtkodeMapel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                        .addComponent(cmkodeGuru, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnSimpan)
                                    .addGap(26, 26, 26)
                                    .addComponent(btnHapus)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnUpdate)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnBatal)
                                    .addGap(36, 36, 36)
                                    .addComponent(btnTutup))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnkodeGuru)
                            .addComponent(cmkodeGuru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtkodeMapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtkelasMapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal)
                    .addComponent(btnTutup)
                    .addComponent(btnUpdate))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtMapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMapelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMapelActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        // Memanggil fungsi 'clear' untuk membersihkan nilai dari komponen
        batal();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnTutupActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new GUI_jadwalMengajar().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmkodeGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmkodeGuruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmkodeGuruActionPerformed

    private void btnkodeGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkodeGuruActionPerformed
        // TODO add your handling code here:
        new GUI_pendataanGuru().setVisible(true);
    }//GEN-LAST:event_btnkodeGuruActionPerformed

    private void table_dataMataPelajaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_dataMataPelajaranMouseClicked
        // TODO add your handling code here:
        int selectedRow = table_dataMataPelajaran.getSelectedRow();
        if (selectedRow >= 0) {
            kodeGuru1 = table_dataMataPelajaran.getValueAt(selectedRow, 0).toString(); // Ambil kode guru dari tabel Guru
            namaMapel1 = table_dataMataPelajaran.getValueAt(selectedRow, 1).toString(); // Ambil nama mapel dari tabel Guru
            kodeMapel1 = table_dataMataPelajaran.getValueAt(selectedRow, 2).toString(); // Ambil kode mapel dari tabel Guru
            kelas1 = table_dataMataPelajaran.getValueAt(selectedRow, 3).toString(); // Ambil kelas dari tabel Guru

            // Atur nilai yang diambil ke komponen GUI Mata Pelajaran
            cmkodeGuru.setSelectedItem(kodeGuru1);
            txtMapel.setText(namaMapel1);
            txtkodeMapel.setText(kodeMapel1);
            txtkelasMapel.setText(kelas1);
        }
    }//GEN-LAST:event_table_dataMataPelajaranMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

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
            java.util.logging.Logger.getLogger(GUI_mataPelajaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_mataPelajaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_mataPelajaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_mataPelajaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_mataPelajaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTutup;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnkodeGuru;
    private javax.swing.JComboBox<String> cmkodeGuru;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_dataMataPelajaran;
    private javax.swing.JTextField txtMapel;
    private javax.swing.JTextField txtkelasMapel;
    private javax.swing.JTextField txtkodeMapel;
    // End of variables declaration//GEN-END:variables
}
