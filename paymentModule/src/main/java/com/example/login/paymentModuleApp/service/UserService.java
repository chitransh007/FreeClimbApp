package com.example.login.paymentModuleApp.service;

import com.example.login.paymentModuleApp.model.User;

public interface UserService {

	public User findUserByEmail(String email);

	public void saveUser(User user);
}
