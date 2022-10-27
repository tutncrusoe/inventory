package com.group.inventory.department.repository;

import com.group.inventory.department.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByName(String name);

    @Query("select distinct d from Department as d left join d.users where d.id = ?1")
    Department findDepartmentWithUsersById(UUID id);

    @Query("select distinct d from Department as d left join fetch d.users")
    List<Department> findAllDepartmentWithUsers();
}
