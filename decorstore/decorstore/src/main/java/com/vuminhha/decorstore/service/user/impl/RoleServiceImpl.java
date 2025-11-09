package com.vuminhha.decorstore.service.user.impl;

import com.vuminhha.decorstore.entity.Permission;
import com.vuminhha.decorstore.entity.Role;
import com.vuminhha.decorstore.repository.PermissionRepository;
import com.vuminhha.decorstore.repository.RoleRepository;
import com.vuminhha.decorstore.service.user.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    public RoleServiceImpl(RoleRepository roleRepository,PermissionRepository permissionRepository)
    {
        this.roleRepository=roleRepository;
        this.permissionRepository=permissionRepository;
    }
    // LAy tat ca Role
    @Override
    public List<Role> getAll ()
    {
        return roleRepository.findAll();
    }
    // tim role theo ten
    @Override
    public Optional<Role> getRoleByName(String roleName)
    {
        return roleRepository.findByName(roleName);
    }
    // tao role moi
    @Override
    public Role createRole(String roleName, Set<String> permissionNames)
    {
        Set<Permission> permissions=permissionRepository.findByNameIn(permissionNames);
        Role role= new Role();
        role.setName(roleName);
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }
    // Gan them quyen vao role
    @Override
    public Role addPermissionToRole(String roleName,String permissionName)
    {
        Role role=roleRepository.findByName(roleName).orElseThrow(()-> new RuntimeException("Role not found"));
        Permission permission=permissionRepository.findByName(permissionName).orElseThrow(()->new RuntimeException("Permission not found"));
        role.getPermissions().add(permission);
        return roleRepository.save(role);
    }
}
