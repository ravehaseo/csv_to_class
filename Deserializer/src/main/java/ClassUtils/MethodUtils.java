package ClassUtils;

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

public class MethodUtils {
	
	static String methodName = "parse";
	static CustomClassLoader ccl = new CustomClassLoader();
	
	public BufferedReader methodImplementation(String deObj, String processingFileName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException,
			IOException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		BufferedReader jediBf;
		Class cl = ccl.findClass(deObj);
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
		return jediBf;
	}

}
