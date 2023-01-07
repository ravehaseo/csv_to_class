package ClassUtils;

public class VelocityTemplate {
	
	private String fieldName;
	private String trim;
	private String fieldType;
	
	public VelocityTemplate(String fieldName, String fieldType, String trim) {
        super();
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.trim = trim;
    }
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getTrim() {
		return trim;
	}
	public void setTrim(String trim) {
		this.trim = trim;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
	

}
