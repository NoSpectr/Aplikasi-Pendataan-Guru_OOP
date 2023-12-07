/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasProject;

/**
 *
 * @author ASUS
 */
public class Guru extends ClassAbstract {

    // Atribut-atribut data pribadi guru
    public String kodeGuru, nama, jenisKelamin, agama, alamat, noTelp, guruEmail;
    public int nuptk;
    private boolean statusMengajar;

    //Konstruktor untuk membuat objek Guru
    public Guru(String kodeGuru, String nama, String jenisKelamin, String agama, String alamat, String noTelp, int nuptk, String guruEmail) {
        this.kodeGuru = kodeGuru;
        this.nuptk = nuptk;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.agama = agama;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.guruEmail = guruEmail;
    }

    //Metode getter dan setter untuk atribut-atribut Guru
    public void setKodeGuru(String kodeGuru) {
        this.kodeGuru = kodeGuru;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    // Overloading setter untuk jenis kelamin
    public void setJenisKelamin(char jenisKelamin) {
        this.jenisKelamin = String.valueOf(jenisKelamin);
    }

    // Overloading setter untuk jenis kelamin dengan format string
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public void setNuptk(int nuptk) {
        this.nuptk = nuptk;
    }

    public String getKodeGuru() {
        return kodeGuru;
    }

    public String getNama() {
        return nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getAgama() {
        return agama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public int getNuptk() {
        return nuptk;
    }

    //Metode untuk mendapatkan status mengajar guru.
    public boolean isStatusMengajar() {
        return statusMengajar;
    }

    //Metode untuk mengatur status mengajar guru.
    public void setStatusMengajar(boolean statusMengajar) {
        this.statusMengajar = statusMengajar;
    }

    // Implementasi dari method abstrak di kelas Guru
    @Override
    public String getEmail() {
        return guruEmail;
    }

    // Metode setter untuk email
    public void setEmail(String email) {
        this.guruEmail = email;
    }

    // Metode getter untuk email
    public String getGuruEmail() {
        return guruEmail;
    }
}
