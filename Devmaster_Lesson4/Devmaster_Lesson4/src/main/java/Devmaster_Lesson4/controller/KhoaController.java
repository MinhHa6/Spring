package Devmaster_Lesson4.controller;

import Devmaster_Lesson4.dto.KhoaDTO;
import Devmaster_Lesson4.dto.UsersDTO;
import Devmaster_Lesson4.entity.Khoa;
import Devmaster_Lesson4.entity.User;
import Devmaster_Lesson4.service.KhoaService;
import Devmaster_Lesson4.service.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class KhoaController {
    @Autowired
    KhoaService khoaService;
    @GetMapping("/khoa-list")
    public List<Khoa> getAllKhoa()
    {
        return khoaService.findAll();
    }
    @PostMapping("/khoa-add")
    public ResponseEntity<String> addUser(@Valid @RequestBody KhoaDTO kh)
    {
        khoaService.create(kh);
        return ResponseEntity.badRequest().body("Users created successfully");
    }
    @GetMapping("/khoa/{makh}")
    public Khoa getKhoa(@PathVariable String makh)
    {
        String par=makh;
        return khoaService.getKhoa(makh);
    }
    @DeleteMapping("/khoa/{makh}")
    public Boolean deleteKhoa(@PathVariable String makh)
    {
        String param=makh;
        return khoaService.deleteKhoa(makh);
    }
}
