package com.cfca.calidad.service;

import java.util.ArrayList;
import java.util.List;

import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.model.User;

public class UserService {
private IDAOUser dao;
	
	public UserService(IDAOUser dao) {
		this.dao = dao;
	}
	
	public User createUser(String name, String email, String password) {
		User user = null;
		if (password.length() >= 8 && password.length() <=16) {
			user = dao.findUserByEmail(email);
			
			if (user == null) {
				user = new User(name,email,password);
				int id = dao.save(user);
				user.setId(id);
			}
		}
		return user;
	}
	
	public List<User> findAllUsers(){
		List<User> users = new ArrayList<User>();
		users = dao.findAll();
	
		return users;
	}

	public User findUserByEmail(String email) {
		
		return dao.findUserByEmail(email);
	}

public User findUserById(int id) {
		
		return dao.findById(id);
	}
    
    public User updateUser(User user) {
    	User userOld = dao.findById(user.getId());
    	userOld.setName(user.getName());
    	userOld.setPassword(user.getPassword());
    	return dao.updateUser(userOld);
    }

    

    public boolean deleteUser(int id) {//eliminar
    	return dao.deleteById(id);
    }


    
    
    
}
