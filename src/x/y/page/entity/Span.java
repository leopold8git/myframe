package x.y.page.entity;

import x.y.util.StringUtils;

public class Span extends Element{
	
	private String tpl = "<input id=\"{id}\" {attr}/>";
	
	@Override
	public String toString() {
		if(StringUtils.isNotBlank(this.fld)){
			this.setAttr(StringUtils.nullToString(this.getAttr())+" fld=\""+this.fld+"\"");
		}
		return StringUtils.fillTpl(tpl, this);
	}

}
