package x.y.page.entity;

import java.util.ArrayList;
import java.util.List;

import x.y.util.StringUtils;

/**
 * 查询界面
 * @author ASUS
 *
 */
public class QueryPage extends Page{
	
	private String formJson ;
	
	private List<Element> elements = new ArrayList<Element>();

	private List<String> headers = new ArrayList<String>();//split by |
	
	private List<TD> tds = new ArrayList<TD>();//split by |
	
	private List<Element> bottomElements = new ArrayList<Element>();
	//查询名称
	private String qns;
	
	public String getFormJson() {
		return formJson;
	}

	public void setFormJson(String formJson) {
		this.formJson = formJson;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	
	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	public String getQns() {
		return qns;
	}

	public void setQns(String qns) {
		this.qns = qns;
	}

	public List<TD> getTds() {
		return tds;
	}

	public void setTds(List<TD> tds) {
		this.tds = tds;
	}

	public List<Element> getBottomElements() {
		return bottomElements;
	}

	public void setBottomElements(List<Element> bottomElements) {
		this.bottomElements = bottomElements;
	}

	
}
