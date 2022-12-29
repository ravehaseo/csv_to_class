import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnitTest {

	static String csvFile = "schema.txt";
	static BufferedReader bf = null;
	static File csv = null;
	static ClassCreationUtil ccu = new ClassCreationUtil();
	static List contents = null;
	static String objName = "";

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws FileNotFoundException, IOException {

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
	public void fileNotExistException() throws FileNotFoundException {
		File anotherCsv = new File("schemas.txt");

		expectedEx.expect(FileNotFoundException.class);
		BufferedReader bf = new BufferedReader(new FileReader(anotherCsv));
	}

	@Test
	public void fileExist() throws FileNotFoundException {
		File anotherCsv = new File(csvFile);
		assertEquals(anotherCsv.exists(), Boolean.TRUE);
	}

	@Test
	public void classNotExists() throws Exception {
//		 String objName = "TestDisable";
		 ccu.objectCreation(contents, "OtherName");
		expectedEx.expect(ClassNotFoundException.class);
		Class cl = Class.forName("TestDisable");
		assertEquals(cl.newInstance() != null, true);
	}


}
