package x.y.entity;

public enum CrubEnum {

	query("QUERY"),update("UPDATE"),batchUpdate("BATCHUPDATE"),insert("INSERT"),batchInsert("BATCHINSERT"),delete("DELETE"),excel("EXCEL"),insertOrUpdate("INSERTORUPDATE");
	
	private String crub; 
	
	CrubEnum(String crub){
		this.crub = crub;
	}
	
	public String getCrub(){
		return this.crub;
	}
	
}
