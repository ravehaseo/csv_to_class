import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ClassCreation {

	static String methodName = "parse";
	static String processingFileName = "jedi.txt";
	static ClassCreationUtil ccu = new ClassCreationUtil();

	static void classGenerator(String dir) throws Throwable {
		BufferedReader bf = null;
		BufferedReader jediBf = null;
		try {
			File csv = new File(dir);
			bf = new BufferedReader(new FileReader(csv));

			String line = "";
			List contents = new ArrayList<>();
			int track = 0;
			String objName = null;
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
			String deObj = ccu.deSerializationClassCreation(contents, objName, methodName);

			// To process the jedi text file into the newly created classes
			// I'm not sure how to arrange the files since the deserialization is using substring. Like yoda's last name supposed to be
			// empty so skip and put the jedi master into level instead of last name
			
			//I think i've been wasting a lot of time on this as java won't be able to refresh it's directory so this will not be able to detect 
			//If we would have another chance I would be honored to learn more from you guys. 
			//Thank you for the fun assignment, I've learnt a lot that I did not know before just from this.
			Class cl = Class.forName(deObj);
			Object obje = cl.newInstance();
			File jediCsv = new File(processingFileName);
			jediBf = new BufferedReader(new FileReader(jediCsv));
			String newLine = "";
			List jediLs = new ArrayList<>();
			while ((newLine = jediBf.readLine()) != null) {

				Method method = obje.getClass().getMethod(methodName, String.class);
				jediLs.add(method.invoke(obje, newLine));

			}
			for (int i = 0; i < jediLs.size(); i++) {
				Object object = (Object) jediLs.get(i);
				Field[] fields = object.getClass().getDeclaredFields();
				for (Field field : fields) {
					System.out.println(field.getName() + ": " + field.get(object));
				}
			}

			bf.close();
			jediBf.close();
		} catch (FileNotFoundException fileEx) {
			throw fileEx;

		} catch (IOException ioe) {
			throw ioe;
		} catch (InvocationTargetException invo) {
			try {
				throw invo.getCause();
			} catch (IllegalArgumentException ille) {
				throw ille;
			} catch (NullPointerException nullpoint) {
				throw nullpoint;
			}

		} catch (StringIndexOutOfBoundsException str) {
			throw str;
		} catch (Exception ex) {
			throw ex;
		} 

	}

	// public class StudentDeserialiser {
	// public Student parse(String lineFeed) {
	// Student record = new Student();
	// record.firstName = lineFeed.substring(0,20).trim();
	// record.lastName = lineFeed.substring(20,40).trim();
	// record.level = lineFeed.substring(40,50).trim();
	// record.studentClass = lineFeed.substring(50,60).trim();
	// return record;
	// }
	// }
	public static void main(String args[]) throws Throwable {
		String fileDir = "schema.txt";

		try {
			classGenerator(fileDir);
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found : ");
			ex.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("IOException");
			ioe.printStackTrace();
		} catch (InvocationTargetException invo) {
			System.out.println("Invocation Exception");
			invo.printStackTrace();
		} catch (IllegalArgumentException ille) {
			System.out.println("Illegal Argument Exception");
			ille.printStackTrace();
		} catch (NullPointerException nullpoint) {
			System.out.println("Null Pointer Exception");
			nullpoint.printStackTrace();
		} catch (StringIndexOutOfBoundsException str) {
			System.out.println("String index out of bounds Exception");
			str.printStackTrace();
		} catch (Exception ex) {
			System.out.println("Exception");
			ex.printStackTrace();
		}
	}

}
