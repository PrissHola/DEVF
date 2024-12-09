package com.cfca.calidad.service;

public class Snippet {
	@Test
		void deleteTest() {
			// Crear un usuario viejo
			User usuarioViejo = new User("nombreEliminar", "email", "password");
			usuarioViejo.setId(1);
			baseDatos.put(usuarioViejo.getId(), usuarioViejo);
	
			// Simulamos que el usuario existe en la base de datos mockeada
			when(dao.findById(1)).thenReturn(usuarioViejo);
	
			// Simulamos el comportamiento de la eliminación en la base de datos
			when(dao.deleteUser(1)).thenAnswer(new Answer<Boolean>() {
				public Boolean answer(InvocationOnMock invocation) throws Throwable {
					Integer id = (Integer) invocation.getArguments()[0];
					baseDatos.remove(id);  // Eliminar el usuario de la base de datos mockeada
					return !baseDatos.containsKey(id);  // Verificamos que ya no existe
				}
			});
	
			// Ejecutar el servicio de eliminación
			boolean result = servicio.deleteUser(1);
	
			// Verificar que el resultado sea 'true' (se eliminó correctamente)
			assertThat(result, is(true));
			// Verificar que el usuario ya no exista en la base de datos simulada
			assertThat(baseDatos.containsKey(1), is(false));
		}
}

