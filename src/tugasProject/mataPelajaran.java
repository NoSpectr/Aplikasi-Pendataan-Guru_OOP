/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasProject;

/**
 *
 * @author ASUS
 */
public class mataPelajaran extends Guru {

    private String namaMapel, kodeMapel, kelasMapel;

    public mataPelajaran(String kodeGuru, String nama, String jenisKelamin, String agama, String alamat, String noTelp, int nuptk, String guruEmail,
        String kodeMapel, String kelasMapel) {
        super(kodeGuru, nama, jenisKelamin, agama, alamat, noTelp, nuptk, guruEmail);
        this.namaMapel = namaMapel;
        this.kodeMapel = kodeMapel;
        this.kelasMapel = kelasMapel;
    }

    public void setNamaMapel(String namaMapel) {
        this.namaMapel = namaMapel;
    }

    public void setKodeMapel(String kodeMapel) {
        this.kodeMapel = kodeMapel;
    }

    public void setKelasMapel(String kelasMapel) {
        this.kelasMapel = kelasMapel;
    }

    public String getNamaMapel() {
        return namaMapel;
    }

    public String getKodeMapel() {
        return kodeMapel;
    }

    public String getKelasMapel() {
        return kelasMapel;
    }

    // Polimorfisme diterapkan di kelas Jaringan dengan override metode ini
    public String Mapel() {
        return "Merupakan mata pelajaran " + getNamaMapel();
    }
}
