package x.y.page.entity;

import x.y.util.StringUtils;

public class Option {
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
			if(StringUtils.isNotBlank(this.namefld)){
				this.setAttr(StringUtils.nullToString(this.getAttr())+" namefld=\""+this.namefld+"\"");
			}
			if(StringUtils.isNotBlank(this.valuefld)){
				this.setAttr(StringUtils.nullToString(this.getAttr())+" valuefld=\""+this.valuefld+"\"");
			}
			if(StringUtils.isNotBlank(this.name)){
				this.setAttrVal(StringUtils.nullToString(this.getAttrVal())+this.name);
			}
			if(StringUtils.isNotBlank(this.value)){
				this.setAttr(StringUtils.nullToString(this.getAttr())+"value=\""+this.value+"\"");
			}
			return StringUtils.fillTpl(optTpl, this);
		}
}
