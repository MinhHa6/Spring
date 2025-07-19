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
}
