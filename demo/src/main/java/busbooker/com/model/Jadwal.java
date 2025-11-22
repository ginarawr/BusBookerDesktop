package busbooker.com.model;

import java.sql.Timestamp;

public class Jadwal {
    private int id;
    private int idBus;
    private String keberangkatan;
    private String tujuan;
    private Timestamp waktu;
    private double harga;
    private String namaBus;  // Nama bus dari tabel bus

    // Constructor tanpa parameter
    public Jadwal() {}

    // Constructor lengkap (dengan namaBus)
    public Jadwal(int id, int idBus, String keberangkatan, String tujuan, Timestamp waktu, double harga, String namaBus) {
        this.id = id;
        this.idBus = idBus;
        this.keberangkatan = keberangkatan;
        this.tujuan = tujuan;
        this.waktu = waktu;
        this.harga = harga;
        this.namaBus = namaBus;
    }

    // Getter and Setter
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

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getNamaBus() {
        return namaBus;
    }

    public void setNamaBus(String namaBus) {
        this.namaBus = namaBus;
    }

    @Override
    public String toString() {
        return "Jadwal{" +
                "id=" + id +
                ", idBus=" + idBus +
                ", namaBus='" + namaBus + '\'' +
                ", keberangkatan='" + keberangkatan + '\'' +
                ", tujuan='" + tujuan + '\'' +
                ", waktu=" + waktu +
                ", harga=" + harga +
                '}';
    }
}
