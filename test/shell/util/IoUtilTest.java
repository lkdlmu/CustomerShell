package shell.util;

import static org.junit.Assert.*;
import static shell.util.IoUtil.*;

import org.junit.Test;

import shell.exception.InvalidPathException;

public class IoUtilTest {

	@Test
	public void testIsAbsolutePath() {
		assertTrue(isAbsolutePath("/"));
		assertTrue(isAbsolutePath("/c"));
		assertFalse(isAbsolutePath("c:/"));
	}

	@Test
	public void testGetParentPath() {
		assertNull(getParentPath("c:\\"));
		assertEquals("c:\\", getParentPath("c:\\windows"));
	}
	
	@Test
	public void testTransPathFromLinuxToWin() throws InvalidPathException {
		String args = "/c/windows";
		String expectedResult = "c:/windows";
		String actualResult = transPathFromLinuxToWin(args);
		assertEquals(expectedResult, actualResult);
		
		args = "/d";
		expectedResult = "d:/";
		actualResult = transPathFromLinuxToWin(args);
		assertEquals(expectedResult, actualResult);
		
		args = "/d/";
		expectedResult = "d:/";
		actualResult = transPathFromLinuxToWin(args);
		assertEquals(expectedResult, actualResult);
	}

	@Test(expected = InvalidPathException.class)
	public void testTransPathFromLinuxToWinWithException() throws InvalidPathException {
		String args = "/ab";
		transPathFromLinuxToWin(args);
	}
	
	@Test
	public void testTransPathFromWinToLinux() {
		assertEquals("/c/", transPathFromWinToLinux("c:\\"));
		assertEquals("/c/windows", transPathFromWinToLinux("c:/windows"));
	}
	
	
	
	
	
	
	
	
	
}
