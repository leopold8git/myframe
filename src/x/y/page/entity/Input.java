package x.y.page.entity;

import x.y.util.StringUtils;

public class Input extends Element {
	
	private String tpl = "<input type=\"{type}\" name=\"{name}\" id=\"{id}\" {attr}/>";

	private String type = "text";
	
	private String className;
	
	private String onclick;
	
	private String value;
	
	@Override
	public String toString() {
		if(StringUtils.isNotBlank(this.fld)){
			this.setAttr(StringUtils.nullToString(this.getAttr())+" fld=\""+this.fld+"\"");
		}
		if(StringUtils.isNotBlank(this.className)){
			this.setAttr(StringUtils.nullToString(this.getAttr())+" class=\""+this.className+"\"");
		}
		if(StringUtils.isNotBlank(this.onclick)){
			this.setAttr(StringUtils.nullToString(this.getAttr())+" onclick=\""+this.onclick+"\"");
		}
		if(StringUtils.isNotBlank(this.value)){
			this.setAttr(StringUtils.nullToString(this.getAttr())+" value=\""+this.value+"\"");
		}
		return StringUtils.fillTpl(tpl, this);
	}
	
	public static void main(String[] args) {
		Input input = new Input();
		input.setName("myname");
		input.setId("lp");
		input.setAttr("class=\"txt\"");
		input.setFld("");
		System.out.println(input.toString());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
