package x.y.page.entity;

import x.y.util.StringUtils;

public class TD extends Element{

	private String tpl = "<td {attr}>{content}</td>";

	private String content;
	
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
