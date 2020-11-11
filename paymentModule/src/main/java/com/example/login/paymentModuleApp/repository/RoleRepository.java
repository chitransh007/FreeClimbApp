package com.example.login.paymentModuleApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.login.paymentModuleApp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByRole(String role);

}
