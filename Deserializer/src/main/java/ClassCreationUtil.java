import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import ClassUtils.CustomClassLoader;

public class ClassCreationUtil {
	
	static final String mainDir = "src\\main\\java\\";
	static CustomClassLoader ccl = new CustomClassLoader();

	public String deSerializationClassCreation(List contents, String objName, String methodName) throws FileNotFoundException, IOException, ClassNotFoundException {
		StringBuffer sbDe = new StringBuffer();
		String deObj = objName + "Deserializer";
		sbDe.append("public class " + deObj + "{");
		sbDe.append("\n public " + objName + " " + methodName + "(String lineFeed) {");
		sbDe.append("\n " + objName + " record = new " + objName + "();");
		for (int i = 0; i < contents.size(); i++) {
			Contents con = (Contents) contents.get(i);
			if (con.getDataType().equals("int")) {
				sbDe.append("\n record." + con.getColumnName() + " = Integer.parseInt(lineFeed.substring(" + con.getFirstIndex() + ","
						+ con.getLastIndex() + ").trim());");
			} else {
				sbDe.append("\n record." + con.getColumnName() + " = lineFeed.substring(" + con.getFirstIndex() + "," + con.getLastIndex()
						+ ").trim();");
			}
		}
		sbDe.append("\n return record; \n } \n}");
		System.out.println(sbDe);
		FileOutputStream fos2 = new FileOutputStream(mainDir + deObj + ".java");
		fos2.write(sbDe.toString().getBytes());
		
		fos2.flush();
		fos2.close();
		return deObj;
	}

	public String objectCreation(List contents, String objName) throws FileNotFoundException, IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("public class " + objName + "{");
		for (int i = 0; i < contents.size(); i++) {
			Contents con = (Contents) contents.get(i);
			sb.append("\n public " + con.getDataType() + " " + con.getColumnName() + ";");
		}
		sb.append("\n }");
		System.out.println(sb);
		FileOutputStream fos = new FileOutputStream(mainDir + objName + ".java");
		fos.write(sb.toString().getBytes());
		fos.flush();
		fos.close();
		
		return objName;
	}

}
