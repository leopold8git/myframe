package x.y.page.entity;

import x.y.util.StringUtils;

public class TD extends Element{

	private String tpl = "<td {attr}>{content}</td>";

	private String content;
	
	@Override
	public String toString() {
		if(StringUtils.isNotBlank(this.fld) ){
			String attr = StringUtils.nullToString(this.getAttr())  ;
			if(attr.equals("") || attr.indexOf("fld=")==-1  ){
				this.setAttr(StringUtils.nullToString(this.getAttr())+" fld=\""+this.fld+"\"");
			}
		}
		return StringUtils.fillTpl(tpl, this);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
