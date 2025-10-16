package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Permission;
import com.vuminhha.decorstore.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    public PermissionService(PermissionRepository permissionRepository)
    {
        this.permissionRepository=permissionRepository;
    }
    // Lay tat ca quyen
    public List<Permission> getAll()
    {
        return permissionRepository.findAll();
    }
    // Tao quyen moi
    public Permission createPermission (String name)
    {
        if(permissionRepository.findByName(name).isPresent())
        {
            throw  new RuntimeException("Permission already exists:"+name);
        }
        Permission permission=new Permission();
        permission.setName(name);
        return permissionRepository.save(permission);
    }
    // tim quyen theo ten
    public Optional<Permission> getPermissionByName(String name)
    {
        return permissionRepository.findByName(name);
    }
    // xoa quyen
    public void deletePermission(Long id)
    {
        if(!permissionRepository.existsById(id))
        {
            throw  new RuntimeException("Permission not found with by id:"+id);
        }
        permissionRepository.deleteById(id);
    }

}
