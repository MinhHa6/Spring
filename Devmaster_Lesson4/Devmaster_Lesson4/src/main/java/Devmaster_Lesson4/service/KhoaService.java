package Devmaster_Lesson4.service;

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
    }
}
