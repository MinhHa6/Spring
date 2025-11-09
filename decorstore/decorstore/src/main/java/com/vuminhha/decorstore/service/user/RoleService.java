package com.vuminhha.decorstore.service.user;

import com.vuminhha.decorstore.entity.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    List<Role> getAll ();
    Optional<Role> getRoleByName(String roleName);
    Role createRole(String roleName, Set<String> permissionNames);
    Role addPermissionToRole(String roleName,String permissionName);
}
