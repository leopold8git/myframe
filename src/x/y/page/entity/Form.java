package x.y.page.entity;

import x.y.util.StringUtils;

public class Form extends Element {

	private String tpl = "<input name=\"{name}\" id=\"{id}\" {attr}>{content}</form>";
	
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
