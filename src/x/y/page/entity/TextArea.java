package x.y.page.entity;

import x.y.util.StringUtils;

public class TextArea extends Element {
	
	private String tpl = "<textarea  name=\"{name}\" id=\"{id}\" {attr}></textarea>";
	
	@Override
	public String toString() {
		if(StringUtils.isNotBlank(this.fld) ){
			String attr = StringUtils.nullToString(this.getAttr())  ;
			String fldAttr = " fld=\""+this.fld+"\"";
			if(attr.equals("") || attr.indexOf(fldAttr)==-1  ){
				this.setAttr(attr+fldAttr);
			}
		}
		return StringUtils.fillTpl(tpl, this);
	}
	
}
