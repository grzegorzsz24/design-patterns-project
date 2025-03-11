package com.example.automotiveapp.repository;

import com.example.automotiveapp.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.roleName.name = :name")
    Optional<Role> findByName(@Param("name") String name);

}
