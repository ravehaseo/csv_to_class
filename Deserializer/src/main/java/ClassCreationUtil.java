import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import ClassUtils.CustomClassLoader;
import ClassUtils.VelocityTemplate;

public class ClassCreationUtil {
	
	static final String mainDir = "src\\main\\java\\";
	static CustomClassLoader ccl = new CustomClassLoader();

//	public String deSerializationClassCreation(List contents, String objName, String methodName) throws FileNotFoundException, IOException, ClassNotFoundException {
//		StringBuffer sbDe = new StringBuffer();
//		String deObj = objName + "Deserializer";
//		sbDe.append("public class " + deObj + "{");
//		sbDe.append("\n public " + objName + " " + methodName + "(String lineFeed) {");
//		sbDe.append("\n " + objName + " record = new " + objName + "();");
//		for (int i = 0; i < contents.size(); i++) {
//			Contents con = (Contents) contents.get(i);
//			if (con.getDataType().equals("int")) {
//				sbDe.append("\n record." + con.getColumnName() + " = Integer.parseInt(lineFeed.substring(" + con.getFirstIndex() + ","
//						+ con.getLastIndex() + ").trim());");
//			} else {
//				sbDe.append("\n record." + con.getColumnName() + " = lineFeed.substring(" + con.getFirstIndex() + "," + con.getLastIndex()
//						+ ").trim();");
//			}
//		}
//		sbDe.append("\n return record; \n } \n}");
//		System.out.println(sbDe);
//		FileOutputStream fos2 = new FileOutputStream(mainDir + deObj + ".java");
//		fos2.write(sbDe.toString().getBytes());
//		
//		fos2.flush();
//		fos2.close();
//		return deObj;
//	}
//
//	public String objectCreation(List contents, String objName) throws FileNotFoundException, IOException {
//		StringBuffer sb = new StringBuffer();
//		sb.append("public class " + objName + "{");
//		for (int i = 0; i < contents.size(); i++) {
//			Contents con = (Contents) contents.get(i);
//			sb.append("\n public " + con.getDataType() + " " + con.getColumnName() + ";");
//		}
//		sb.append("\n }");
//		System.out.println(sb);
//		FileOutputStream fos = new FileOutputStream(mainDir + objName + ".java");
//		fos.write(sb.toString().getBytes());
//		fos.flush();
//		fos.close();
//		
//		return objName;
//	}
	
	public String velocityObjectCreation(List contents, String objName) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		VelocityEngine veloEngine = new VelocityEngine();
		veloEngine.init();
		
		Template t = veloEngine.getTemplate("src\\main\\resources\\vtemplates\\object.vm");
		VelocityContext context = new VelocityContext();
		List properties = new ArrayList<>();
		for (int i = 0; i < contents.size(); i++) {
			Contents con = (Contents) contents.get(i);
			VelocityTemplate vt = null;
			
			vt = new VelocityTemplate(con.getColumnName(), con.getDataType(), null);
			properties.add(vt);			
		}
		context.put("className", objName);
		context.put("properties", properties);
		
		StringWriter writer = new StringWriter();
		t.merge( context, writer );
		System.out.println( writer.toString());
		
		FileOutputStream fos = new FileOutputStream(mainDir + objName + ".java");
		fos.write(writer.toString().getBytes());
		fos.flush();
		fos.close();
		return objName;
	}
	
	public String velocityDeserialClassCreation(List contents, String objName, String methodName) throws FileNotFoundException, IOException, ClassNotFoundException {
		String deObj = objName + "Deserializer";
		VelocityEngine veloEngine = new VelocityEngine();
		veloEngine.init();
		
		Template t = veloEngine.getTemplate("src\\main\\resources\\vtemplates\\deserializer.vm");
		VelocityContext context = new VelocityContext();
		List properties = new ArrayList<>();
		for (int i = 0; i < contents.size(); i++) {
			Contents con = (Contents) contents.get(i);
			VelocityTemplate vt = null;
			String trim = "";
			if (con.getDataType().equals("int")) {
				trim = "Integer.parseInt(lineFeed.substring(" + con.getFirstIndex() +","+ con.getLastIndex() +").trim())";				
			}else {
				trim = "lineFeed.substring(" + con.getFirstIndex() + "," + con.getLastIndex()+ ").trim()";			
			}
			vt = new VelocityTemplate(con.getColumnName(), null, trim);
			properties.add(vt);			
		}
		context.put("className", deObj);
		context.put("objName", objName);
		context.put("methodName", methodName);
		context.put("properties", properties);
		
		StringWriter writer = new StringWriter();
		t.merge( context, writer );
		System.out.println( writer.toString());
		
		FileOutputStream fos = new FileOutputStream(mainDir + deObj + ".java");
		fos.write(writer.toString().getBytes());
		fos.flush();
		fos.close();
		return deObj;
	}

}
