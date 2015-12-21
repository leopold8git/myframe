package x.y.page.entity;

import java.util.List;

import x.y.util.StringUtils;

/**
 * 查询界面
 * @author ASUS
 *
 */
public class EditPage extends Page{
	
	private String formJson ;
	
	private List<Element> elements ;
	
	public String getFormJson() {
		return formJson;
	}

	public void setFormJson(String formJson) {
		this.formJson = formJson;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
	
}
