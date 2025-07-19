package Devmaster_Leson3.Controller;

import Devmaster_Leson3.Entity.Khoa;
import Devmaster_Leson3.Entity.Student;
import Devmaster_Leson3.Service.ServiceKhoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KhController {
    @Autowired
    private ServiceKhoa serviceKhoa;
    @GetMapping("/khoa-list")
    public List<Khoa> getAll()
    {
        return serviceKhoa.getKhoas();
    }
    @GetMapping("/khoa/{makh}")
    public Khoa getAllKhoa(@PathVariable String makh)
    {
        String param=makh;
        return serviceKhoa.getMaKh(makh);
    }
    @PostMapping("/khoa-add")
    public Khoa addKhoa(@RequestBody Khoa khoa)
    {
       return serviceKhoa.addKh(khoa);
   }
    @PutMapping("/khoa/{makh}")
    public Khoa updateKhoa(@PathVariable String makh,@RequestBody Khoa khoa)
    {
        String param=makh;
        return serviceKhoa.updateKh(param,khoa);
    }
    @DeleteMapping("/Khoa/{makh})")
    public Boolean deleteKh(@PathVariable String maKh)
    {
        return serviceKhoa.deleteKh(maKh);
    }
}
