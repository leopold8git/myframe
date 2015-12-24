package x.y.page.entity;

import x.y.util.StringUtils;

public class Label extends Element{
	
	private String tpl = "<label id=\"{id}\" for=\"{relId}\">{content}</label>";
	
	private String relId;
	
	@Override
	public String toString() {
		return StringUtils.fillTpl(tpl, this);
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}
}
