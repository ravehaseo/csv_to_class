import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ClassUtils.CustomClassLoader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

public class ClassCreationUtilTest {
	
	static ClassCreationUtil ccu = new ClassCreationUtil();
	static CustomClassLoader cl = new  CustomClassLoader();
	
	static String csvFile = "src\\main\\resources\\schema.txt";
	static BufferedReader bf = null;
	static File csv = null;
	static List contents = null;
	static String objName = "";

	@BeforeAll
	public static void setUp() throws FileNotFoundException, IOException {
		String a = "" ;
		csv = new File(csvFile);
		bf = new BufferedReader(new FileReader(csv));
		String line = "";
		int track = 0;
		contents = new ArrayList<>();
		while ((line = bf.readLine()) != null) {
			String[] lineArr = line.split(",");
			if (track == 0) {
				objName = lineArr[0];
			} else {
				int i = 0;
				Contents c = new Contents();
				c.setColumnName(lineArr[i++]);
				c.setFirstIndex(lineArr[i++]);
				c.setLastIndex(lineArr[i++]);
				c.setDataType(lineArr[i++]);
				contents.add(c);
			}

			track++;
		}
		ccu.objectCreation(contents, objName);
		bf.close();
	}

	@Test
	@DisplayName("Test deSerializationClassCreation")
	public void testDeSerializationClassCreation() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		String className = ccu.deSerializationClassCreation(contents, objName, "parse");		
		Class cla = cl.findClass(className);
		assertEquals(cla != null, Boolean.TRUE);
	}
	
	@Test
	@DisplayName("Test objectCreation")
	public void testObjectCreation() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		String className = ccu.objectCreation(contents, objName);		
		Class cla = cl.findClass(className);
		assertEquals(cla != null, Boolean.TRUE);
		
	}

}
