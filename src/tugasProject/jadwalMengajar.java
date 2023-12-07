/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasProject;

/**
 *
 * @author ASUS
 */
public class jadwalMengajar extends Guru implements displayInterface {

    // Atribut-atribut jadwal mengajar guru
    private String hari;
    private int jam;
    private String kelas;
    private String mataPelajaran;

    //Konstruktor untuk membuat objek jadwalMengajar
    public jadwalMengajar(String kodeGuru, String nama, String jenisKelamin, String agama, String alamat, String noTelp, int nuptk, String guruEmail,
            String hari, int jam, String kelas, String mataPelajaran) {
        super(kodeGuru, nama, jenisKelamin, agama, alamat, noTelp, nuptk, guruEmail); // atribut-atribut dari kelas Guru
        this.hari = hari;
        this.jam = jam;
        this.kelas = kelas;
        this.mataPelajaran = mataPelajaran;
    }

    // Overriding metode dari kelas induk (Guru)
    @Override
    public String getNama() {
        // Implementasi sesuai kebutuhan
        return super.nama; // Menggunakan metode getNama dari kelas Guru
    }

    //Metode getter dan setter untuk atribut-atribut jadwal mengajar
    public void setHari(String hari) {
        this.hari = hari;
    }

    public void setJam(int jam) {
        this.jam = jam;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public void setMataPelajaran(String mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }

    public String getHari() {
        return hari;
    }

    public int getJam() {
        return jam;
    }

    public String getKelas() {
        return kelas;
    }

    public String getMataPelajaran() {
        return mataPelajaran;
    }

    // Implementasi metode baru dari interface
    @Override
    public void displayJadwal() {
        System.out.println("Nama Mengajar: " + getNama());
        System.out.println("Hari: " + getHari());
        System.out.println("Jam: " + getJam());
        System.out.println("Kelas: " + getKelas());
        System.out.println("Mata Pelajaran: " + getMataPelajaran());
    }
}
