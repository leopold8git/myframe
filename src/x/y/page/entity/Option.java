package x.y.page.entity;

import x.y.util.StringUtils;

public class Option extends Element{
		private String optTpl = "<option {attr}>{attrVal}</option>";
		private String attr ; 
		private String attrVal ;
		private String namefld ;
		private String valuefld ;
		private String name ;
		private String value ;
		
		public Option(){}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}

		
		public String getNamefld() {
			return namefld;
		}
		public void setNamefld(String namefld) {
			this.namefld = namefld;
		}
		public String getValuefld() {
			return valuefld;
		}
		public void setValuefld(String valuefld) {
			this.valuefld = valuefld;
		}
		public String getAttr() {
			return attr;
		}
		public void setAttr(String attr) {
			this.attr = attr;
		}
		public String getAttrVal() {
			return attrVal;
		}
		public void setAttrVal(String attrVal) {
			this.attrVal = attrVal;
		} 
		
		public String toString(){

			if(StringUtils.isNotBlank(this.namefld) ){
				String attr = StringUtils.nullToString(this.getAttr())  ;
				String fldAttr = " namefld=\""+this.namefld+"\"";
				if(attr.equals("") || attr.indexOf(fldAttr)==-1  ){
					this.setAttr(attr+fldAttr);
				}
			}

			if(StringUtils.isNotBlank(this.valuefld) ){
				String attr = StringUtils.nullToString(this.getAttr())  ;
				String fldAttr = " valuefld=\""+this.valuefld+"\"";
				if(attr.equals("") || attr.indexOf(fldAttr)==-1  ){
					this.setAttr(attr+fldAttr);
				}
			}
			if(StringUtils.isNotBlank(this.name) ){
				String attr = StringUtils.nullToString(this.getAttr())  ;
				String fldAttr = " name=\""+this.name+"\"";
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
			return StringUtils.fillTpl(optTpl, this);
		}
}
