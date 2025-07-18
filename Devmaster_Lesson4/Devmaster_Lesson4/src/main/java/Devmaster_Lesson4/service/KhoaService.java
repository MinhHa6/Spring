package Devmaster_Lesson4.service;

import Devmaster_Lesson4.dto.KhoaDTO;
import Devmaster_Lesson4.entity.Khoa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KhoaService {
    List<Khoa>khoas= new ArrayList<Khoa>();
    public KhoaService ()
    {
        khoas.add(new Khoa("CNTT1","Cong nghe thong tin"));
        khoas.add(new Khoa("CNTT2","Truyen thong da phuong tien"));
        khoas.add(new Khoa("CNTT3","Lap trinh game"));
        khoas.add(new Khoa("CNTT4","He thong"));
    }
    public List<Khoa>findAll()
    {
        return khoas;
    }
    public boolean create(KhoaDTO khoaDTO)
    {
        try {
            Khoa khoa= new Khoa();
            khoa.setMaKh(khoaDTO.getMaKh());
            khoa.setTenKh(khoaDTO.getTenKh());
            return true;
        }
        catch (Exception  e)
        {
            return false;
        }

    }
}
