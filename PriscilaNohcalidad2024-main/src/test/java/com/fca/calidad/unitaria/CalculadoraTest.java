package com.fca.calidad.unitaria;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Calculadoratest {

	private double num1 = 0;
	private double num2 = 0;
	private Calculadora calculadora= null;
	@BeforeEach
	void setup() {
		num1 = 2;
		num2 = 5;
		calculadora = new Calculadora();
	}
	@Test
	void suma2numerosPositivosTest() {
		
		//Inicialización
	
		double resEsperado = 7;
		Calculadora calculadora = new Calculadora();
		
		//Ejercicio, llamar al metodo que queremos provar 
		double resEjecucion = calculadora.suma(num1, num2);
		
		//verificar
		assertThat(resEsperado, is(resEjecucion));
		
	}
	@Test
	void restar2numerosPositivosTest() {
		//Inicializacion
		double num1=2;
		double num2=5;
		double resEsperado = -3;
		
		Calculadora calculadora = new Calculadora();
		
		//Ejercici, llamar al metodo que queremos probar
		double resEjecucion = calculadora.resta(num1, num2);
		
		//Verificar
		assertThat(resEsperado, is(resEjecucion));
		
	}
	@Test
	void multiplicar2numerosPositivosTest () {
		//inicializacion
		double num1=2;
		double num2=5;
		double resEsperado = 10;
		Calculadora calculadora = new Calculadora();
		
		//Ejercicio, llamar al metodo que queremos probar
		double resEjecucion = calculadora.multiplica(num1, num2);
		
		//Verificar
		assertThat(resEsperado, is (resEjecucion));
	}
	
	@Test
	void dividir2numerosPositivosTest() {
		//inicializacion
		double num1 = 2;
		double num2 = 5;
		double resEsperado = 0.4;
		Calculadora calculadora = new Calculadora();
		
		//Ejercicio, llamar al metodo que queremos probar
		double resEjecucion = calculadora.divide(num1, num2);
		
		//verfificar
		assertThat(resEsperado, is(resEjecucion));	
	}
	
	@AfterEach
	void print() {
		System.out.println("Esto se imprime despues de cada prueba");
	}
	
}