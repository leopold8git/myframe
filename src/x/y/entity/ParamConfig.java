package x.y.entity;

import java.util.Map;

import x.y.service.ResultHandler;

/**
 * 参数配置，传给service
 * @author ASUS
 *
 */
public class ParamConfig {

	private Map configMap ;
	/**
	 * 结果处理
	 */
	private ResultHandler resultHandler;
	
	/**
	 * 关联处理方法
	 * @return
	 */
	private Result result; 
	

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public ResultHandler getResultHandler() {
		return resultHandler;
	}

	public void setResultHandler(ResultHandler resultHandler) {
		this.resultHandler = resultHandler;
	}

	public Map getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map configMap) {
		this.configMap = configMap;
	}
}
