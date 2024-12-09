package com.fca.calidad.doubles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import  org.mockito.invocation.InvocationOnMock;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

class DependencyTest {
	private Dependency dependency;
	private SubDependency sub;
		
	@BeforeEach
	void setup() {
		sub = mock(SubDependency.class);
		dependency = mock(Dependency.class);
	}
	
@Test
void addTwoAnswerTest(){
	//inicialización
	when(dependency.addTwo(anyInt())).thenAnswer(new Answer<Integer>() {
		public Integer answer (InvocationOnMock invocation) throws Throwable{
			int arg = (Integer)invocation.getArguments()[0];
			return 20 * 2 + 10 + arg;
		}
	});
	int Resultadoesperado = 55;
	assertThat(Resultadoesperado, is(dependency.addTwo(5)));
}
	@Test
	void test() {
		System.out.println(sub.getClassName());
	}

	@Test
	public void testDependency() {
		//inicialización
		when(sub.getClassName()).thenReturn("hi there");
		String ResultadoEsperado = "hi there";
		//ejercicio
		String resultadoReal = sub.getClassName();
		
		//verificación
		assertThat(resultadoReal,is(ResultadoEsperado));
	}
}
