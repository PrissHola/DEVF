package com.fca.calidad.unitaria;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.cfca.calidad.service.UserService;
import com.fca.calidad.dao.IDAOUser;
import com.fca.calidad.model.User;


class UserServiceTest {
	
	private UserService servicio;
	private IDAOUser dao;
	private HashMap<Integer,User>baseDatos;
	private User usuario;
	
	@BeforeEach
	void setup () {
		dao = mock(IDAOUser.class);
		servicio = new UserService(dao);
		baseDatos = new HashMap<Integer, User>();
	}
	
	@Test
	void updateTest() {
		User usuarioViejo = new User ("nombre1","email","password");  
		usuarioViejo.setId(1);  
		baseDatos.put(usuarioViejo.getId(), usuarioViejo);  
		
		User usuarioNuevo = new User("nuevoNombre","email","nuevoPassword");
		usuarioNuevo.setId(1);
		
		when(dao.findById(1)).thenReturn(usuarioViejo);
		
		when(dao.updateUser(any(User.class))).thenAnswer(new Answer<User>() {
			public User answer(InvocationOnMock invocation) throws Throwable{
			User arg = (User) invocation.getArguments()[0];
			baseDatos.replace(arg.getId(), arg);
			return baseDatos.get(arg.getId());
			}
		}
	);
		
		User result = servicio.updateUser(usuarioNuevo);
		
		assertThat("nuevoPassword",is(result.getPassword()));
	    assertThat("nuevoNombre",is(result.getName()));
	}
	
	
	@Test
	void deleteTest() {
	    
	    User usuarioViejo = new User("nombreEliminar", "email", "password");
	    usuarioViejo.setId(1);
	    baseDatos.put(usuarioViejo.getId(), usuarioViejo);

	   
	    when(dao.findById(1)).thenReturn(usuarioViejo);  //simular que existe el usuario

	    
	    when(dao.deleteById(1)).thenAnswer(new Answer<Boolean>() {  
	        public Boolean answer(InvocationOnMock invocation) throws Throwable {
	            Integer id = (Integer) invocation.getArguments()[0];
	            baseDatos.remove(id); 
	            return !baseDatos.containsKey(id);  // Verificar que ya no existe
	        }
	    });

	    // Ejecutar  eliminación
	    boolean result = servicio.deleteUser(1);

	    // Verificar v
	    assertThat(result, is(true));
	    // Verificar que el usuario ya no exista
	    assertThat(baseDatos.containsKey(1), is(false));
	}
	

	 @Test
	    void createUserTest() {
	        String nombre = "nuevoNombre";
	        String email = "nuevoEmail@gmail.com";
	        String password = "nuevaPassword";


	        when(dao.findUserByEmail(email)).thenReturn(null);//simular que no existe
	        User usuarioNuevo = new User(nombre, email, password); 
	        when(dao.save(any(User.class))).thenReturn(1);  
	       
	        User result = servicio.createUser(nombre, email, password);//llamar método

	        // Verificación
	        assertThat(result, is(notNullValue())); // El usuario no debe ser nulo
	        assertThat(result.getName(), is("nuevoNombre"));
	        assertThat(result.getEmail(), is("nuevoEmail@gmail.com"));
	        assertThat(result.getPassword(), is("nuevaPassword"));
	        assertThat(result.getId(), is(1));  // id 1 que se simulo antes 

	       
	    }

	 
	 @Test
	 void findUserByIdTest() {
	     User usuario = new User("Juan", "pris@example.com", "contra123");
	     usuario.setId(1);
	     baseDatos.put(usuario.getId(), usuario);

	     when(dao.findById(1)).thenReturn(usuario);

	     User result = servicio.findUserById(1);

	     assertThat(result, is(notNullValue()));
	     assertThat(result.getId(), is(1));
	     assertThat(result.getName(), is("Juan"));
	     
	     // Caso en el que no existe el usuario
	     when(dao.findById(2)).thenReturn(null);
	     User notFoundUser = servicio.findUserById(2);
	     assertThat(notFoundUser, is(nullValue()));
	 }
	   
	 
	 @Test
	 void findUserByEmailTest() {
	     // Preparar datos 
	     User usuarioExistente = new User("nombreExistente", "emailExistente@gmail.com", "contra123");
	     usuarioExistente.setId(1);
	     baseDatos.put(usuarioExistente.getId(), usuarioExistente);

	     // DAO
	     when(dao.findUserByEmail("emailExistente@gmail.com")).thenReturn(usuarioExistente);
	     when(dao.findUserByEmail("emailNoExistente@gmail.com")).thenReturn(null);  // Simular un email no registrado

	     // Llamar método de UserService
	     User result = servicio.findUserByEmail("emailExistente@gmail.com");

	     // Verificar 
	     assertThat(result, is(notNullValue()));
	     assertThat(result.getEmail(), is("emailExistente@gmail.com"));
	     assertThat(result.getName(), is("nombreExistente"));
	     User resultNoExistente = servicio.findUserByEmail("emailNoExistente@gmail.com");  // Verificar que no se encuentra un usuario
	     assertThat(resultNoExistente, is(nullValue()));  // Debe devolver null
	 }
	 
	 
	 @Test
	 void findAllUsersTest() {
	     User usuario1 = new User("Maria", "pris@gmail.com", "contra123");
	     usuario1.setId(1);
	     baseDatos.put(usuario1.getId(), usuario1);

	     User usuario2 = new User("Carlos", "betty@gmail.com", "contra456");
	     usuario2.setId(2);
	     baseDatos.put(usuario2.getId(), usuario2);

	     when(dao.findAll()).thenReturn(baseDatos.values().stream().toList());

	     List<User> result = servicio.findAllUsers();

	     assertThat(result, hasSize(2));
	     assertThat(result, containsInAnyOrder(usuario1, usuario2));

	     // Caso sin usuarios
	     when(dao.findAll()).thenReturn(List.of());
	     List<User> emptyResult = servicio.findAllUsers();
	     assertThat(emptyResult, is(empty()));
	 }


	}
	
