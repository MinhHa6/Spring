package Devmaster_Leson3.Service;

import Devmaster_Leson3.Entity.MonHoc;
import Devmaster_Leson3.Entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ServiceMH {
    List<MonHoc>monHocs= new ArrayList<MonHoc>();
    public ServiceMH ()
    {
        monHocs.addAll(Arrays.asList(new MonHoc("Mh1","Toan",45), new MonHoc("Mh2","Van",30)));
    }
    // Lay toan bo ds mon hoc ra
    public List<MonHoc>getMonHocs()
    {
        return monHocs;
    }
    //lay ds theo mamh
    public MonHoc getMhmaMh(String maMh)
    {
        return monHocs.stream().filter(monHoc -> monHoc.getMaMH().equals(maMh)).findFirst().orElse(null);
    }
    // them mon hoc moi
    public MonHoc addMh (MonHoc mh)
    {
        monHocs.add(mh);
        return mh;
    }
    //Cap nhat thong tin Mon hoc
    public MonHoc updateMH(String mamh,MonHoc mh)
    {
        MonHoc check=getMhmaMh(mamh);
        if (check==null)
        {
            return null;
        }
        monHocs.forEach(item->{
            if(item.getMaMH()==mamh)
            {
                item.setTenMH(mh.getTenMH());
                item.setSoTiet(mh.getSoTiet());
            }
        });
        return mh;
    }
    // xoa thong tin them mamh
    public boolean deleteMH(String mh)
    {
        MonHoc check=getMhmaMh(mh);
        return monHocs.remove(check);
    }
}
