package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {

        List<Role> findAll();
        Optional<Role> findRoleById(Long rentId);
}
