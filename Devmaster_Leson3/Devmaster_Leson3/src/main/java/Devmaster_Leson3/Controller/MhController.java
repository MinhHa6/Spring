package Devmaster_Leson3.Controller;

import Devmaster_Leson3.Entity.MonHoc;
import Devmaster_Leson3.Service.ServiceMH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MhController {
    @Autowired
    private ServiceMH servicesMh;
    @GetMapping("/Mh-list")
    public List<MonHoc> getAllStudents()
    {
        return servicesMh.getMonHocs();
    }
    @GetMapping("/mh/{mamh}")
    public MonHoc getAllMHs(@PathVariable String mamh)
    {
        String param=mamh;
        return servicesMh.getMhmaMh(param);
    }
    @PostMapping("/mh-add")
    public MonHoc addMh(@RequestBody MonHoc mh)
    {
        return servicesMh.addMh(mh);
    }
    @PutMapping("/mh/{mamh}")
    public MonHoc updateMh(@PathVariable String mamh,@RequestBody MonHoc mh)
    {
        String param=mamh;
        return servicesMh.updateMH(param,mh);
    }
    @DeleteMapping("/mh/{mamh}")
    public boolean deleteMH(@PathVariable String mamh)
    {
        String param=mamh;
        return servicesMh.deleteMH(param);
    }
}
