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

		if(StringUtils.isNotBlank(this.fld) ){
			String attr = StringUtils.nullToString(this.getAttr())  ;
			String fldAttr = " fld=\""+this.fld+"\"";
			if(attr.equals("") || attr.indexOf(fldAttr)==-1  ){
				this.setAttr(attr+fldAttr);
			}
		}
		if(StringUtils.isNotBlank(this.className) ){
			String attr = StringUtils.nullToString(this.getAttr())  ;
			String fldAttr = " class=\""+this.className+"\"";
			if(attr.equals("") || attr.indexOf(fldAttr)==-1  ){
				this.setAttr(attr+fldAttr);
			}
		}

		if(StringUtils.isNotBlank(this.onclick) ){
			String attr = StringUtils.nullToString(this.getAttr())  ;
			String fldAttr = " onclick=\""+this.onclick+"\"";
			if(attr.equals("") || attr.indexOf(fldAttr)==-1  ){
				this.setAttr(attr+fldAttr);
			}
		}
		if(StringUtils.isNotBlank(this.value) ){
			String attr = StringUtils.nullToString(this.getAttr())  ;
			String fldAttr = " value=\""+this.value+"\"";
			if(attr.equals("") || attr.indexOf(fldAttr)==-1  ){
				this.setAttr(attr+fldAttr);
			}
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
