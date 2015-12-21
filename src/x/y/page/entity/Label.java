package x.y.page.entity;

import x.y.util.StringUtils;

public class Label extends Element{
	
	private String tpl = "<label id=\"{id}\">{content}</label>";
	
	private String content;
	
	@Override
	public String toString() {
		return StringUtils.fillTpl(tpl, this);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
