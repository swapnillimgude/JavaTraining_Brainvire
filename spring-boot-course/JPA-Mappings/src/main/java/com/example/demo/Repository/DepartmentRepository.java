package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
