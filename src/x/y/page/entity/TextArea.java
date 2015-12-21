package x.y.page.entity;

import x.y.util.StringUtils;

public class TextArea extends Element {
	
	private String tpl = "<textarea  name=\"{name}\" id=\"{id}\" {attr}></textarea>";
	
	@Override
	public String toString() {
		if(StringUtils.isNotBlank(this.fld)){
			this.setAttr(StringUtils.nullToString(this.getAttr())+" fld=\""+this.fld+"\"");
		}
		return StringUtils.fillTpl(tpl, this);
	}
	
}
