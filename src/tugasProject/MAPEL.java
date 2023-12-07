/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugasProject;

/**
 *
 * @author ASUS
 */
public class MAPEL extends mataPelajaran {

    public MAPEL(String kodeGuru, String nama, String jenisKelamin, String agama, String alamat, String noTelp, int nuptk, String guruEmail,
            String kodeMapel, String kelasMapel) {
        super(kodeGuru, nama, jenisKelamin, agama, alamat, noTelp, nuptk, guruEmail, kodeMapel, kelasMapel);
    }
    // Ini merupakan penerapan polimorfisme dengan metode Mapel()
    @Override
    public String Mapel() {
        return "Merupakan mata pelajaran " + getNamaMapel();
    }
}
