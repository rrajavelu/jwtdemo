package com.eshanit.jwtdemo.repository;

import com.eshanit.jwtdemo.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
