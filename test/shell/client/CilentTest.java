package shell.client;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

public class CilentTest {

	@Test
	public void testGetClassName() {
		Class<Client> clientClass = Client.class;
		String args = "ls";
		String exepectResult = "shell.command.io.LsCommand";
		String actualResult = "";
		try {
			Method getClassNameMethod = clientClass.getDeclaredMethod("getClassName", String.class);
			getClassNameMethod.setAccessible(true);
			actualResult = (String) getClassNameMethod.invoke(new Client(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals(exepectResult, actualResult);
	}

}
