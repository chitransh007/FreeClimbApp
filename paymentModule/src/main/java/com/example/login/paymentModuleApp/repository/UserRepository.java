package com.example.login.paymentModuleApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.login.paymentModuleApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
    public User findByConfirmationToken(String confirmationToken);

}
