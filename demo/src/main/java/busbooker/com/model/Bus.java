package busbooker.com.model;

public class Bus {
    private int id;
    private String nama, kelas;
    private double harga;
    public Bus() {}
    // getters/setters
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getNama(){return nama;} public void setNama(String n){this.nama=n;}
    public String getKelas(){return kelas;} public void setKelas(String k){this.kelas=k;}
    public double getHarga(){return harga;} public void setHarga(double h){this.harga=h;}
}
