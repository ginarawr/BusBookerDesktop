package busbooker.com.model;

import java.sql.Timestamp;
public class Jadwal {
    private int id, idBus;
    private String keberangkatan, tujuan;
    private Timestamp waktu;
    public Jadwal() {}
    // getters/setters
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getIdBus(){return idBus;} public void setIdBus(int idBus){this.idBus=idBus;}
    public String getKeberangkatan(){return keberangkatan;} public void setKeberangkatan(String k){this.keberangkatan=k;}
    public String getTujuan(){return tujuan;} public void setTujuan(String t){this.tujuan=t;}
    public Timestamp getWaktu(){return waktu;} public void setWaktu(Timestamp w){this.waktu=w;}
}
