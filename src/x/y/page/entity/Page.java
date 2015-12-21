package x.y.page.entity;

import java.util.List;

import x.y.entity.ParamConfig;

public class Page extends Element{
	
	private String js;
	
	private String style;
	
	private List<String> jsLink;
	
	private List<String> styleLink;
	
	private String title;
	//结果界面名称
	private String viewName;
	
	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<String> getJsLink() {
		return jsLink;
	}

	public void setJsLink(List<String> jsLink) {
		this.jsLink = jsLink;
	}

	public List<String> getStyleLink() {
		return styleLink;
	}

	public void setStyleLink(List<String> styleLink) {
		this.styleLink = styleLink;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public String toString() {
		return null;
	}
}
