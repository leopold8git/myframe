package x.y.page.entity;

import java.util.ArrayList;
import java.util.List;

import x.y.util.StringUtils;

public class Select extends Element {

	private String tpl = "<select name=\"{name}\" id=\"{id}\" {attr}>{options}</select>";
	
	private String dataUrl;
	
	private String options ; 
	
	private List<Option> optionList = new ArrayList<Option>();
	/*<select id="brand" dataUrl="getPhoneBrands.htm">
 	<option value="">全部</option>
 	<option namefld="brandName" valuefld="brandId"/>
 </select> */

	@Override
	public String toString() {
		if(StringUtils.isNotBlank(this.fld) ){
			String attr = StringUtils.nullToString(this.getAttr())  ;
			String fldAttr = " fld=\""+this.fld+"\"";
			if(attr.equals("") || attr.indexOf(fldAttr)==-1  ){
				this.setAttr(attr+fldAttr);
			}
		}
		if(StringUtils.isNotBlank(this.dataUrl) ){
			String attr = StringUtils.nullToString(this.getAttr())  ;
			String fldAttr = " dataUrl=\""+this.dataUrl+"\"";
			if(attr.equals("") || attr.indexOf(fldAttr)==-1  ){
				this.setAttr(attr+fldAttr);
			}
		}
		return StringUtils.fillTpl(tpl, this);
	}
	
	public String getOptions() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < optionList.size(); i++) {
			Option option = optionList.get(i);
			sb.append(option.toString());
		}
		return sb.toString();
	}

	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}
	
	public String getDataUrl() {
		return dataUrl;
	}


	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public List<Option> getOptionList() {
		return optionList;
	}

}
