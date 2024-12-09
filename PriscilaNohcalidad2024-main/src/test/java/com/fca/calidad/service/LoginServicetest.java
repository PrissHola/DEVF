package com.fca.calidad.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.cfca.calidad.service.LoginService;
import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.model.User;

class LoginServicetest {
	

	LoginService service;
	IDAOUser dao;
	@Test
	void test() {
		//inicialización
		dao = mock(IDAOUser.class);
		//esta es la instancia 
		service = new LoginService(dao);
		User usuario = new User("nombre1","email@email.com","123456");
		//este es el comportamiento, esto es lo que le mandas a la base de datos 
		when(dao.findByUserName("nombre")).thenReturn(null);
		
		//Ejercicio- como si se estuviera escribiendo el login
		boolean result = service.login("nombre", "12345");
		
		//verificación
		assertThat(result,is(false));
	
	}

}
