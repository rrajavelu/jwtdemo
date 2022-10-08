package com.eshanit.jwtdemo.service;

import com.eshanit.jwtdemo.entity.RoleEntity;
import com.eshanit.jwtdemo.model.RoleModel;

import java.util.List;

public interface RoleService {
    public RoleModel createRole(RoleModel roleModel);
    public List<RoleModel> getAllRoles();
    public RoleModel getRoleById(Long roleId);
    public void deleteRoleById(Long roleId);
}
