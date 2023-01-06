package ClassUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader{
	
	@Override
    public Class findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassFromFile(name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassFromFile(String fileName)  {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                fileName.replace('.', File.separatorChar) + ".class");
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = 0;
        try {
            while ( (nextValue = inputStream.read()) != -1 ) {
                byteStream.write(nextValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer = byteStream.toByteArray();
        return buffer;
    }

//    private static final CompilingClassLoader __instance
//    = new CompilingClassLoader();
// 
//    private static final Logger logger =
//        Logger.getLogger(CompilingClassLoader.class.getName());
// 
//    private Pattern namePattern;
//    private Pattern packagePattern;
// 
//    private CompilingClassLoader() {
//        this.namePattern =
//    Pattern.compile(".*class[ ]+([a-zA-Z0-9$_]+).*");
//        this.packagePattern =
//    Pattern.compile(".*package[ ]+([a-zA-Z0-9$_.]+).*");
//    }
// 
//    public static CompilingClassLoader getInstance() {
//        return __instance;
//    }
//	
//	 public Class<?> loadClassFromString(final String program) throws ClassNotFoundException {
//	        final String className = getClassName(program);
//	        final String packagePath = getPackagePath(program);
//	 
//	        final String fullClassName;
//	        if (packagePath != null) {
//	            fullClassName = packagePath + '.' + className;
//	        } else {
//	            fullClassName = className;
//	        }
//	 
//	        logger.info("Loading " + fullClassName);
//	 
//	        // compile it!
//	        boolean result =
//	        JavaStringCompiler.INSTANCE
//	        .compileStringCode(fullClassName, program);
//	 
//	        if (result) {
//	            byte[] classBytes = getClassBytes(className);
//	            if (classBytes != null) {
//	                logger.info("Loaded " + fullClassName);
//	                return defineClass(fullClassName, classBytes, 0, classBytes.length);
//	            } else
//	                throw new ClassNotFoundException("Unable to load: " + fullClassName +
//	                                                 ". Reason = failed to load class bytes.");
//	        } else
//	            throw new ClassNotFoundException("Unable to load: " + fullClassName +
//	                                             ". Reason = compilation failed.");
//	    }
//	 
//	    private String getClassName(final String program) {
//	        Matcher m = namePattern.matcher(program);
//	 
//	        if (m.matches() && (m.groupCount() == 1)) {
//	            return m.group(1);
//	        }
//	        throw new RuntimeException("Could not find main class to load!");
//	    }
//	 
//	    private String getPackagePath(final String program) {
//	        Matcher m = packagePattern.matcher(program);
//	 
//	        if (m.matches() && (m.groupCount() == 1)) {
//	            return m.group(1);
//	        }
//	        return null;
//	    }        
//	 
//	    private byte[] getClassBytes(final String className) {
//	        final String classFilePath =
//	            className.replace('.', File.separatorChar) + ".class";
//	 
//	        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(classFilePath));
//	             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//	            byte[] buffer = new byte[4 * 1024];
//	            int bytesRead = -1;
//	 
//	            while ((bytesRead = bin.read(buffer)) != -1) {
//	                baos.write(buffer, 0, bytesRead);
//	            }
//	 
//	            // delete the class file before returning
//	            try {
//	                Files.deleteIfExists(Paths.get(classFilePath));
//	            } catch (IOException ex) {
//	                //
//	            }
//	 
//	            return baos.toByteArray();
//	        } catch (IOException ex) {
//	            return null;
//	        }
//	    }
//	}
}
