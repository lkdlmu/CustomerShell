package shell.command.io;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shell.command.io.CdCommand;
import shell.exception.InvalidPathException;


public class CdCommandTest {

	private static Class<?> clazz = null;
	private static CdCommand cdCommand = new CdCommand("");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		clazz = CdCommand.class;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {}
	
	@Test
	public void testTransPathFromLinuxToWin() {
		String args1 = "/c/windows";
		String expectedResult1 = "c:/windows";
		String actualResult1 = "";
		
		String args2 = "/d";
		String expectedResult2 = "d:/";
		String actualResult2 = "";
		
		String args3 = "/d/";
		String expectedResult3 = "d:/";
		String actualResult3 = "";
		
		try {
			Method method = clazz.getDeclaredMethod("transPathFromLinuxToWin", String.class);
			method.setAccessible(true);
			actualResult1 = (String) method.invoke(cdCommand, args1);
			actualResult2 = (String) method.invoke(cdCommand, args2);
			actualResult3 = (String) method.invoke(cdCommand, args3);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		assertEquals(expectedResult1, actualResult1);
		assertEquals(expectedResult2, actualResult2);
		assertEquals(expectedResult3, actualResult3);
	}
	
	@Test
	public void testTransPathFromLinuxToWinWithException() {
		String args = "/dd";
		String expectedException = "InvalidPathException";
		Method method = null;
		try {
			method = clazz.getDeclaredMethod("transPathFromLinuxToWin", String.class);
			method.setAccessible(true);
			method.invoke(cdCommand, args);
			fail("Expected throw InvalidPathException");
		} catch(Exception e) {
			assertEquals(expectedException, e.getCause().getClass().getSimpleName());
		}
	}
}
