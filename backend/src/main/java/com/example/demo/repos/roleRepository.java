package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.role;



public interface roleRepository extends JpaRepository<role, Long> {
role findByRole(String role);
}