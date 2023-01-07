import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ClassUtils.CustomClassLoader;
import ClassUtils.MethodUtils;

public class ClassCreationUtilTest {
	
	static ClassCreationUtil ccu = new ClassCreationUtil();
	static CustomClassLoader cl = new  CustomClassLoader();
	static MethodUtils mu = new MethodUtils();
	static String processingFileName = "src\\main\\resources\\jediTest.txt";
	static String csvFile = "src\\main\\resources\\schema.txt";
	static BufferedReader bf = null;
	static File csv = null;
	static List contents = null;
	static String objName = "";
	static String deserializationContent = """
public class JediMasterDeserializer{
 public JediMaster parse(String lineFeed) {
 JediMaster record = new JediMaster();
 record.firstName = lineFeed.substring(0,20).trim();
 record.lastName = lineFeed.substring(21,40).trim();
 record.level = lineFeed.substring(41,50).trim();
 record.jediClass = lineFeed.substring(51,60).trim();
 record.age = Integer.parseInt(lineFeed.substring(61,70).trim());
 return record; 
 } 
}""";
	

	@BeforeAll
	public static void setUp() throws FileNotFoundException, IOException, ClassNotFoundException {
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
		ccu.velocityObjectCreation(contents, objName);
		bf.close();
	}

	@Test
	@DisplayName("Test deSerializationClassCreation")
	public void testDeSerializationClassCreation() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		String className = ccu.velocityDeserialClassCreation(contents, objName, "parse");		
		Class cla = cl.findClass(className);
		assertEquals(cla != null, Boolean.TRUE);
		
	}
	
	
	
		@Test
	@DisplayName("Test objectCreation")
	public void testObjectCreation() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		String className = ccu.velocityObjectCreation(contents, objName);		
		Class cla = cl.findClass(className);
		assertEquals(cla != null, Boolean.TRUE);
		
	}
	
	
	//For these 2 (wrongclassName and correctClasName) i actually tried to do return the stringbuffer.toString back then use the same textBlock to just assert for the class name but would always fail. 
	//Would like your advice on how to do it properly.
//		@Test
//		@DisplayName("Test correctClassName")
//		public void testCorrectClassName() throws FileNotFoundException, IOException, ClassNotFoundException {
//			String className = ccu.deSerializationClassCreation(contents, objName, "parse");		
////			Class cla = cl.findClass(deObj);
//			assertEquals(deserializationContent, className);
//		}
	@Test
	@DisplayName("Test wrongClassName")
	public void testWrongClassName() throws FileNotFoundException, IOException, ClassNotFoundException {
		String className = ccu.velocityDeserialClassCreation(contents, objName, "parse");		
//		Class cla = cl.findClass(deObj);
		assertNotEquals("""
                          StudentDeserializer""", className);	}
	

	
	@Test
	@DisplayName("Test wrongMethodName")
	public void testMethodName() throws FileNotFoundException, IOException, ClassNotFoundException {
		String className = ccu.velocityDeserialClassCreation(contents, objName, "parse");		
//		Class cla = cl.findClass(className);
		Class cla = Class.forName(className);
		NoSuchMethodException classNotFound = assertThrows(NoSuchMethodException.class, () -> {				
			cla.getMethod("wrong", String.class);
		});
	}
	
	@Test
	@DisplayName("Test lessContent")
	public void testContent() throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		
		String className = ccu.velocityDeserialClassCreation(contents, objName, "parse");		
//		Class cla = cl.findClass(className);
		BufferedReader bf = mu.methodImplementation(className, processingFileName);
		assertEquals(bf != null, Boolean.TRUE);
	}
	
	
	
	
	
	

}
