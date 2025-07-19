package Devmaster_Leson3.Service;

import Devmaster_Leson3.Entity.Khoa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceKhoa {
    List<Khoa>khoas= new ArrayList<>();
    public ServiceKhoa ()
    {
        khoas.add(new Khoa("Cn1","Cntt"));
        khoas.add(new Khoa("cn2","Truyen thong da phuong tien"));
        khoas.add(new Khoa("cn3","Lap trinh game"));
        khoas.add(new Khoa("cn4","Do hoa"));
    }

    public List<Khoa> getKhoas() {
        return khoas;
    }
    // thay thong tin theo ma khoa
    public Khoa getMaKh(String mk)
    {
        return khoas.stream().filter(kh -> kh.getMaKh().equals(mk)).findFirst().orElse(null);
    }
    //them khoa moi
    public Khoa addKh(Khoa kh)
    {
        khoas.add(kh);
        return kh;
    }
    // cap nhat thong tin ma khoa
    public Khoa updateKh(String makh,Khoa kh)
    {
        Khoa check=getMaKh(makh);
        if (check==null)
        {
            return null;
        }
        khoas.forEach(item->{
            if (item.getMaKh()==makh){
                item.setMaKh(kh.getMaKh());
                item.setTenKh(kh.getTenKh());
            }
        });
        return kh;
    }
    // xoa thong tin khoa
    public Boolean deleteKh(String maKh)
    {
        Khoa par=getMaKh(maKh);
        return khoas.remove(par);
    }
}
