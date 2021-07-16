package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.sql.Connection;

//import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

import com.revature.connection.ConnectionUtil;
import com.revature.dao.RealtorRepository;
import com.revature.dto.PostRealtorDTO;
import com.revature.exception.AddRealtorException;
import com.revature.exception.BadParameterException;
import com.revature.exception.DatabaseException;
//import com.revature.exception.RealtorNotFoundException;
import com.revature.model.Realtor;

public class RealtorServiceTest {

	private static RealtorRepository mockRealtorRepository;
	private static Connection mockConnection;
	
	private RealtorService realtorService;
	
	@BeforeClass
	public static void setUp() throws DatabaseException, AddRealtorException {
		mockRealtorRepository = mock(RealtorRepository.class);
		mockConnection = mock(Connection.class);
		
		when(mockRealtorRepository.addRealtor(eq(new PostRealtorDTO("Joe", "Shimamura"))))
		.thenReturn(new Realtor(1, "Joe", "Shimamura"));
		
	}
	
	@Before
	public void beforeTest() {
		realtorService = new RealtorService(mockRealtorRepository);
	}
	
	@Test
	public void test_happyPath() throws BadParameterException, DatabaseException, AddRealtorException {

		try(MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			Realtor actual = realtorService.addRealtor(new PostRealtorDTO("Joe", "Shimamura"));
			
			Realtor expected = new Realtor(1, "Joe", "Shimamura");
			
			assertEquals(expected, actual);
		}
		
	}
	
	@Test
	public void test_blankName() throws BadParameterException, DatabaseException {
		
		try(MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			try {
				realtorService.addRealtor(new PostRealtorDTO("", ""));
				fail("AddRealtorException was not thrown");
			} catch (AddRealtorException e) {
				assertEquals(e.getMessage(), "User tried to add a realtor with a blank name");
			}
			
		}
		
	}
	
	@Test
	public void test_blankSpaceName() throws BadParameterException, DatabaseException {
		
		try(MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			try {
				realtorService.addRealtor(new PostRealtorDTO("    ", "    "));
				fail("AddRealtorException was not thrown");
			} catch (AddRealtorException e) {
				assertEquals(e.getMessage(), "User tried to add a realtor with a blank name");
			}
			
		}
		
	}
	
	@Test
	public void test_blankLastSpace() throws BadParameterException, DatabaseException {
		
		try(MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			try {
				realtorService.addRealtor(new PostRealtorDTO("", "    "));
				fail("AddRealtorException was not thrown");
			} catch (AddRealtorException e) {
				assertEquals(e.getMessage(), "User tried to add a realtor with a blank name");
			}
			
		}
		
	}
	
	@Test
	public void test_blankFirstSpace() throws BadParameterException, DatabaseException {
		
		try(MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			try {
				realtorService.addRealtor(new PostRealtorDTO("    ", ""));
				fail("AddRealtorException was not thrown");
			} catch (AddRealtorException e) {
				assertEquals(e.getMessage(), "User tried to add a realtor with a blank name");
			}
			
		}
		
	}
	
	@Test
	public void test_nonBlankFirst() throws BadParameterException, DatabaseException {

		try(MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			try {
				realtorService.addRealtor(new PostRealtorDTO("Joe", ""));
				fail("AddRealtorException was not thrown");
			} catch (AddRealtorException e) {
				assertEquals(e.getMessage(), "User tried to add a realtor with a blank name");
			}
			
		}
		
	}
	
	@Test
	public void test_nonBlankFirstSpace() throws BadParameterException, DatabaseException {
		
		try(MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			try {
				realtorService.addRealtor(new PostRealtorDTO("Joe", "    "));
				fail("AddRealtorException was not thrown");
			} catch (AddRealtorException e) {
				assertEquals(e.getMessage(), "User tried to add a realtor with a blank name");
			}
			
		}
		
	}
	
	@Test
	public void test_nonBlankLast() throws BadParameterException, DatabaseException {
		
		try(MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			try {
				realtorService.addRealtor(new PostRealtorDTO("", "Shimamura"));
				fail("AddRealtorException was not thrown");
			} catch (AddRealtorException e) {
				assertEquals(e.getMessage(), "User tried to add a realtor with a blank name");
			}
			
		}
		
	}
	
	@Test
	public void test_nonBlankLastSpace() throws BadParameterException, DatabaseException {
		
		try(MockedStatic<ConnectionUtil> mockedConnectionUtil = mockStatic(ConnectionUtil.class)) {
			mockedConnectionUtil.when(ConnectionUtil::getConnection).thenReturn(mockConnection);
			
			try {
				realtorService.addRealtor(new PostRealtorDTO("    ", "Shimamura"));
				fail("AddRealtorException was not thrown");
			} catch (AddRealtorException e) {
				assertEquals(e.getMessage(), "User tried to add a realtor with a blank name");
			}
			
		}
		
	}
	
}