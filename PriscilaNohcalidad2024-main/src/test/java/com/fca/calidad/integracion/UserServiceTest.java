package com.fca.calidad.integracion;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cfca.calidad.service.UserService;
import com.fca.calidad.dao.DAOUserSQLite;
import com.fca.calidad.model.User;



class UserServiceTest extends DBTestCase {
	private DAOUserSQLite dao;
	private UserService userService;

	public UserServiceTest(){
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.sqlite.JDBC");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:sqlite:C:\\\\Users\\\\Lenovo\\\\Desktop\\\\Users.db");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
	}

	@BeforeEach
	public void setUp() {
		//crear instancia
		dao= new DAOUserSQLite();
		userService =new UserService(dao);
		
		//inicializar la base
		IDatabaseConnection connection;
		try {
			connection = getConnection();
			DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
			IDataSet databaseDataSet =connection.createDataSet();
			ITable actualTable = databaseDataSet.getTable("users");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			fail("fallo setup");
		}
	}
		protected IDataSet getDataSet() throws Exception {
			return new FlatXmlDataSetBuilder().build(new FileInputStream("C:\\Users\\Lenovo\\eclipse-workspace\\maven\\src\\resourses\\Initdb.xml"));
		}
		@Test
		void createUserTest() {
			
			//ejercicio de codigo
			User usuario = userService.createUser("nombre", "email", "password");
			
			//Assertion
			int resultadoEsperado = 1;
			
			IDatabaseConnection connection;
			try {
				connection = getConnection();
				IDataSet databaseDataSet =connection.createDataSet();
				ITable tablaReal = databaseDataSet.getTable("users");
				tablaReal = databaseDataSet.getTable("users");
				String nombreReal = (String) tablaReal.getValue(0, "name");
				String nombreEsperado = "nombre";
				assertEquals(nombreReal, nombreEsperado);
				
				//CREAR 
				String emailReal = (String) tablaReal.getValue(0, "email");
				String emailEsperado = "email";
				String passwordReal = (String) tablaReal.getValue(0, "password");
				String passwordEsperado = "password";
				
				
				assertEquals(emailReal, emailEsperado);
				assertEquals(passwordReal, passwordEsperado);
				
				
				int resultadoActual = tablaReal.getRowCount();
				assertEquals(resultadoEsperado, resultadoActual );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				fail("fallo");
			}
			
		
	}
		@Test
		
		void createUser3Test() {
			User usuario = userService.createUser("nombre", "email", "password");
			IDatabaseConnection connection;
			try {
				connection = getConnection();
				IDataSet databaseDataSet = connection.createDataSet();
				ITable tablaReal = databaseDataSet.getTable("users");
				IDataSet exceptedDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("C:\\Users\\Lenovo\\eclipse-workspace\\maven\\src\\resourses\\createUser.xml"));
				ITable exceptedTable = exceptedDataSet.getTable("users");
				
				ITable filteredTable = DefaultColumnFilter.includedColumnsTable(tablaReal, 
						exceptedTable.getTableMetaData().getColumns());
				
				Assertion.assertEquals(filteredTable, exceptedTable);
			}catch (Exception e) {
				
				e.printStackTrace();
				fail("Fallo");
			}
}}