package x.y.page.entity;

public abstract class Element {

	protected String id;
	
	protected String name;
	
	protected String attr;
	
	protected String fld;
	
	public abstract String toString();
	
	public String getFld() {
		return fld;
	}

	public void setFld(String fld) {
		this.fld = fld;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

}
