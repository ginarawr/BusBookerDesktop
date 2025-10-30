package busbooker.com.model;


import java.sql.Timestamp;
public class Pemesanan {
    private int id, idUser, idJadwal;
    private Timestamp tanggalPesan;
    private String status;
    public Pemesanan() {}
    // getters/setters
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getIdUser(){return idUser;} public void setIdUser(int u){this.idUser=u;}
    public int getIdJadwal(){return idJadwal;} public void setIdJadwal(int j){this.idJadwal=j;}
    public Timestamp getTanggalPesan(){return tanggalPesan;} public void setTanggalPesan(Timestamp t){this.tanggalPesan=t;}
    public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
}
