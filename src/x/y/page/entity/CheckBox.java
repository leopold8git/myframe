package x.y.page.entity;

import x.y.util.StringUtils;

public class CheckBox extends Element{

	private String tpl = "<input type=\"checkbox\" name=\"{name}\" id=\"{id}\" {attr}/>";
	
	@Override
	public String toString() {
		return StringUtils.fillTpl(tpl, this);
	}

}
