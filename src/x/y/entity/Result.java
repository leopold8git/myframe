package x.y.entity;

public class Result {

	private String method;
	
	/**
	 * 结果处理(应用SPEL)
	 * @return
	 */
	private String res ;
	
	private boolean merge;

	public boolean isMerge() {
		return merge;
	}

	public void setMerge(boolean merge) {
		this.merge = merge;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	
}
