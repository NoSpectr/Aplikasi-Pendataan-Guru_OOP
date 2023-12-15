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

/**
 *
 * @author ASUS
 */
public class GUI_pendataanGuru extends javax.swing.JFrame {

    /**
     * Creates new form pendataanGuru_GUI
     */
    public GUI_pendataanGuru() {
        initComponents();
        koneksi();
        tampil();
    }
    //masukkan conection (public Connection conn;)
    public Connection conn;

    //masukkan method koneksi()
    public void koneksi() {
        try {
            conn = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/projectoop23?user=root&password=");
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(GUI_pendataanGuru.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Koneksi Database!");
        }
    }

    //atribut
    String nuptk1, kodeGuru1, nama1, jenisKelamin, agama1, alamat1, noTelp1, email1, statusMengajar1;

    //untuk batal()
    public void batal() {
        txtNuptk.setText("");
        txtkodeGuru.setText("");
        txtNama.setText("");
        txtAgama.setText("");
        txtAlamat.setText("");
        txtTelp.setText("");
        txtEmail.setText("");
        btnJk.clearSelection();
        txtStatus.setText("");
    }

    // Method itempilih
    public void itempilih() {
        txtNuptk.setText(String.valueOf(nuptk1));
        txtkodeGuru.setText(kodeGuru1);
        txtNama.setText(nama1);
        if (rdLaki.isSelected()) {
            jenisKelamin = rdLaki.getText();
        } else {
            jenisKelamin = rdPerempuan.getText();
        }
        txtAgama.setText(agama1);
        txtAlamat.setText(alamat1);
        txtTelp.setText(noTelp1);
        txtEmail.setText(email1);
        txtStatus.setText(statusMengajar1);
    }

    //method delete
    public void delete() {
        int ok = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin akan menghapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            try {
                int row = table_dataGuru.getSelectedRow();
                String kodeGuru = table_dataGuru.getValueAt(row, 1).toString(); // Ambil kode guru dari baris yang dipilih

                // Query untuk menghapus data berdasarkan kode guru tertentu
                String sql = "DELETE FROM tb_guru WHERE kodeGuru=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, kodeGuru);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
                batal();
                refresh();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data gagal dihapus");
                e.printStackTrace();
            }
        }
    }

    //method update
    public void update() {
        int nuptkLama = Integer.parseInt(nuptk1); // Variabel untuk menyimpan NUPTK sebelumnya
        String kodeGuru = txtkodeGuru.getText();
        String nama = txtNama.getText();
        String jenisKelamin;
        if (rdLaki.isSelected()) {
            jenisKelamin = "Laki-Laki";
        } else {
            jenisKelamin = "Perempuan";
        }
        String agama = txtAgama.getText();
        String alamat = txtAlamat.getText();
        String noTelp = txtTelp.getText();
        String email = txtEmail.getText();
        String statusMengajar = txtStatus.getText();

        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE tb_guru SET nuptk='" + nuptkLama + "', kodeGuru='" + kodeGuru + "', nama='" + nama + "', jenisKelamin='" + jenisKelamin + "', agama='" + agama + "', alamat='" + alamat + "', noTelp='" + noTelp + "', email='" + email + "', statusMengajar='" + statusMengajar + "' WHERE nuptk=" + nuptkLama;
            // Persiapan dan eksekusi query UPDATE
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Update Data Guru Berhasil!");
            batal(); // Bersihkan nilai setelah update
            refresh(); // Refresh tampilan tabel setelah update
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error : " + e);
        }
    }

    // method refresh()
    public void refresh() {
        GUI_pendataanGuru newGUI = new GUI_pendataanGuru();
        newGUI.setVisible(true);
        newGUI.tampil(); // Memanggil method tampil() untuk memperbarui data di tabel
        this.setVisible(false);
    }

    //method insert
    public void insert() {
        int nuptk = Integer.parseInt(txtNuptk.getText());
        String kodeGuru = txtkodeGuru.getText();
        String nama = txtNama.getText();
        String jenisKelamin;
        if (rdLaki.isSelected()) {
            jenisKelamin = "L";
        } else {
            jenisKelamin = "P";
        }
        String agama = txtAgama.getText();
        String alamat = txtAlamat.getText();
        String noTelp = txtTelp.getText();
        String email = txtEmail.getText();
        String statusMengajar = txtStatus.getText();

        try {
            koneksi(); // Memastikan koneksi ke database
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO tb_guru (nuptk, kodeGuru, nama, jenisKelamin, agama, alamat, noTelp, email, statusMengajar) "
                    + "VALUES ('" + nuptk + "','" + kodeGuru + "','" + nama + "','" + jenisKelamin + "','" + agama + "','" + alamat + "',"
                    + "'" + noTelp + "','" + email + "','" + statusMengajar + "')");
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Memasukkan Data Guru!");

            // Setelah operasi insert, panggil tampil() untuk memperbarui tampilan tabel
            tampil();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan Input: " + e.getMessage());
            e.printStackTrace(); // Tambahkan ini untuk menampilkan stack trace pada console untuk melacak masalah
        }
        batal();
    }

    //method tampil
    public void tampil() {
        DefaultTableModel tabelhead = new DefaultTableModel();
        tabelhead.addColumn("NUPTK");
        tabelhead.addColumn("Kode Guru");
        tabelhead.addColumn("Nama");
        tabelhead.addColumn("Jenis Kelamin");
        tabelhead.addColumn("Agama");
        tabelhead.addColumn("Alamat");
        tabelhead.addColumn("No Telp");
        tabelhead.addColumn("Email");
        tabelhead.addColumn("Status Mengajar");
        try {
            koneksi();
            String sql = "SELECT * FROM tb_guru"; // Mengambil data dari tabel guru
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                tabelhead.addRow(new Object[]{
                    res.getInt("nuptk"),
                    res.getString("kodeGuru"),
                    res.getString("nama"),
                    res.getString("jenisKelamin"),
                    res.getString("agama"),
                    res.getString("alamat"),
                    res.getString("noTelp"),
                    res.getString("email"),
                    res.getString("statusMengajar")
                });
            }
            table_dataGuru.setModel(tabelhead); // Mengatur model tabel dengan data yang telah diambil
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace(); // Tambahkan ini untuk melacak masalah ke konsol
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

        btnJk = new javax.swing.ButtonGroup();
        jOptionPane1 = new javax.swing.JOptionPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNuptk = new javax.swing.JTextField();
        rdLaki = new javax.swing.JRadioButton();
        rdPerempuan = new javax.swing.JRadioButton();
        txtNama = new javax.swing.JTextField();
        txtAgama = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        txtTelp = new javax.swing.JTextField();
        btnProses = new javax.swing.JButton();
        btnTutup = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtkodeGuru = new javax.swing.JTextField();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnFormMapel = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        txtStatus = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_dataGuru = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+6));
        jLabel1.setText("PENDATAAN DATA GURU");

        jLabel2.setText("NUPTK");

        jLabel3.setText("Nama");

        jLabel4.setText("Alamat");

        jLabel5.setText("No. Telp");

        jLabel6.setText("Agama");

        jLabel7.setText("Jenis Kelamin");

        txtNuptk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNuptkActionPerformed(evt);
            }
        });

        btnJk.add(rdLaki);
        rdLaki.setText("Laki - Laki");

        btnJk.add(rdPerempuan);
        rdPerempuan.setText("Perempuan");

        txtTelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelpActionPerformed(evt);
            }
        });

        btnProses.setText("Simpan");
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });

        btnTutup.setText("Tutup");
        btnTutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTutupActionPerformed(evt);
            }
        });

        jLabel8.setText("Satus");

        jLabel9.setText("Kode Guru");

        txtkodeGuru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodeGuruActionPerformed(evt);
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

        jLabel10.setText("Email");

        btnFormMapel.setText("Form Mata Pelajaran");
        btnFormMapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormMapelActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        table_dataGuru.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NUPTK", "Kode Guru", "Nama", "Jenis Kelamin", "Agama", "Alamat", "No Telp", "Email", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_dataGuru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_dataGuruMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_dataGuru);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnFormMapel)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel2))
                                    .addGap(21, 21, 21)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtNuptk, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtkodeGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(rdLaki, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(rdPerempuan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNama)
                                        .addComponent(txtAgama)
                                        .addComponent(txtTelp)
                                        .addComponent(txtAlamat)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                        .addComponent(txtStatus)))
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(24, 24, 24)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnProses)
                                    .addGap(55, 55, 55)
                                    .addComponent(btnUpdate)
                                    .addGap(58, 58, 58)
                                    .addComponent(btnHapus)
                                    .addGap(49, 49, 49)
                                    .addComponent(btnBatal)
                                    .addGap(121, 121, 121)
                                    .addComponent(btnTutup))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNuptk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtkodeGuru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(rdLaki))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdPerempuan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtAgama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal)
                    .addComponent(btnTutup)
                    .addComponent(btnProses)
                    .addComponent(btnUpdate))
                .addGap(18, 18, 18)
                .addComponent(btnFormMapel)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnProsesActionPerformed

    private void btnTutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnTutupActionPerformed

    private void txtNuptkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNuptkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNuptkActionPerformed

    private void txtTelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelpActionPerformed

    private void txtkodeGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodeGuruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodeGuruActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        batal();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnFormMapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormMapelActionPerformed
        // TODO add your handling code here:
        new GUI_mataPelajaran().setVisible(true);
    }//GEN-LAST:event_btnFormMapelActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void table_dataGuruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_dataGuruMouseClicked
        // TODO add your handling code here:
        int tabel = table_dataGuru.getSelectedRow();
        nuptk1 = table_dataGuru.getValueAt(tabel, 0).toString();
        kodeGuru1 = table_dataGuru.getValueAt(tabel, 1).toString();
        nama1 = table_dataGuru.getValueAt(tabel, 2).toString();
        String jenisKelaminFromTable = table_dataGuru.getValueAt(tabel, 3).toString();
        agama1 = table_dataGuru.getValueAt(tabel, 4).toString();
        alamat1 = table_dataGuru.getValueAt(tabel, 5).toString();
        noTelp1 = table_dataGuru.getValueAt(tabel, 6).toString();
        email1 = table_dataGuru.getValueAt(tabel, 7).toString();
        String cbStatusMengajar = table_dataGuru.getValueAt(tabel, 8).toString();

        // Konversi nilai dari tabel ke tipe yang sesuai, misalnya:
        if (jenisKelaminFromTable.equals("Laki-Laki")) {
            rdLaki.setSelected(true);
            rdPerempuan.setSelected(false);
        } else if (jenisKelaminFromTable.equals("Perempuan")) {
            rdLaki.setSelected(false);
            rdPerempuan.setSelected(true);
        }

        // Mengatur nilai pada field input dengan nilai yang dipilih dari tabel
        txtNuptk.setText(nuptk1);
        txtkodeGuru.setText(kodeGuru1);
        txtNama.setText(nama1);
        txtAgama.setText(agama1);
        txtAlamat.setText(alamat1);
        txtTelp.setText(noTelp1);
        txtEmail.setText(email1);
        txtStatus.setText(cbStatusMengajar);
        itempilih();
    }//GEN-LAST:event_table_dataGuruMouseClicked

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
            java.util.logging.Logger.getLogger(GUI_pendataanGuru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_pendataanGuru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_pendataanGuru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_pendataanGuru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_pendataanGuru().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnFormMapel;
    private javax.swing.JButton btnHapus;
    private javax.swing.ButtonGroup btnJk;
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnTutup;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdLaki;
    private javax.swing.JRadioButton rdPerempuan;
    private javax.swing.JTable table_dataGuru;
    private javax.swing.JTextField txtAgama;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNuptk;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTelp;
    private javax.swing.JTextField txtkodeGuru;
    // End of variables declaration//GEN-END:variables
}
