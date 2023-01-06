import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ClassUtils.CustomClassLoader;

public class ClassCreationTest {
	
	static String csvFile = "src\\main\\resources\\schema.txt";
	static BufferedReader bf = null;
	static File csv = null;
	static ClassCreationUtil ccu = new ClassCreationUtil();
	static List contents = null;
	static String objName = "";
	static CustomClassLoader ccl = new CustomClassLoader();

	@BeforeAll
	public static void setUp() throws FileNotFoundException, IOException {

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
	@DisplayName("Test FileNotExistException")
	public void fileNotExistException() throws FileNotFoundException {
		
		FileNotFoundException fileNotFound = assertThrows(FileNotFoundException.class, () -> {				
			BufferedReader test = new BufferedReader(new FileReader("abc.txt"));
		});
	}

	@Test
	@DisplayName("Test FileExist")
	public void fileExist() throws FileNotFoundException {
		File anotherCsv = new File(csvFile);
		assertEquals(anotherCsv.exists(), Boolean.TRUE);
	}

	@Test
	@DisplayName("Test ClassNotExist")
	public void classNotExists() throws Exception {
		//		 String objName = "TestDisable";
		ccu.objectCreation(contents, "OtherName");
//		expectedEx.expect(ClassNotFoundException.class);
		ClassNotFoundException classNotFound = assertThrows(ClassNotFoundException.class, () -> {				
			Class cl = Class.forName("TestDisable");
			cl.newInstance();
		});
		
	}

}
