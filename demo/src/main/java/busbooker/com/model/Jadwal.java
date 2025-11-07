package busbooker.com.model;

import java.sql.Timestamp;

public class Jadwal {
    private int id;
    private int idBus;
    private String keberangkatan;
    private String tujuan;
    private Timestamp waktu;

    // ✅ Constructor tanpa parameter (dibutuhkan oleh beberapa library)
    public Jadwal() {}

    // ✅ Constructor lengkap (ini yang dibutuhkan oleh MainMenu.java)
    public Jadwal(int id, int idBus, String keberangkatan, String tujuan, Timestamp waktu) {
        this.id = id;
        this.idBus = idBus;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
        this.waktu = waktu;
    }

    // ✅ Getters dan Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    public String getKeberangkatan() {
        return keberangkatan;
    }

    public void setKeberangkatan(String keberangkatan) {
        this.keberangkatan = keberangkatan;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public Timestamp getWaktu() {
        return waktu;
    }

    public void setWaktu(Timestamp waktu) {
        this.waktu = waktu;
    }

    // ✅ (Opsional) Tambahkan toString agar mudah debug
    @Override
    public String toString() {
        return "Jadwal{" +
                "id=" + id +
                ", idBus=" + idBus +
                ", keberangkatan='" + keberangkatan + '\'' +
                ", tujuan='" + tujuan + '\'' +
                ", waktu=" + waktu +
                '}';
    }
}
