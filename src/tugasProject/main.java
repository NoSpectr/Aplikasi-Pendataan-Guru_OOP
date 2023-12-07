/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasProject;

/**
 *
 * @author ASUS
 */
public class main {

    public static void main(String[] args) {
        Guru data = new Guru("G3245", "Irfan", "Laki - laki", "Islam", "Sidoarjo", "085456123789", 5642314, "Irfan@gmail.com");

        // Mencetak data guru
        System.out.println("DATA GURU SEKOLAH");
        System.out.println("================================");
        System.out.println("NUPTK: " + data.getKodeGuru());
        System.out.println("Nama: " + data.getNama());
        System.out.println("Jenis Kelamin: " + data.getJenisKelamin());
        System.out.println("Agama: " + data.getAgama());
        System.out.println("Alamat: " + data.getAlamat());
        System.out.println("No Telp: " + data.getNoTelp());
        System.out.println("--------------------------------------------------------");
        System.out.println("Status Mengajar: " + (data.isStatusMengajar() ? "Mengajar" : "Tidak Mengajar"));
    }
}
