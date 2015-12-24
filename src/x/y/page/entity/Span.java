package x.y.page.entity;

import x.y.util.StringUtils;

public class Span extends Element{
	
	private String tpl = "<input id=\"{id}\" {attr}/>";
	
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
