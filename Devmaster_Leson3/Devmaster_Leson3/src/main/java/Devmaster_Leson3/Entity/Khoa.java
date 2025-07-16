package Devmaster_Leson3.Entity;

public class Khoa {

    String maKh;
    String tenKh;
    public Khoa (){}

    public Khoa(String maKh, String tenKh) {
        this.maKh = maKh;
        this.tenKh = tenKh;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }
}
