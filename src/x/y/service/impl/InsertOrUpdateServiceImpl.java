package x.y.service.impl;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.service.AbstractCrudService;
import x.y.service.CrudService;
/**
 * @author ASUS
 * 还未测。。。
 *
 */
@Service("insertOrUpdateService")
public class InsertOrUpdateServiceImpl extends AbstractCrudService {
	
	@Autowired
	private CrudService queryService;
	@Autowired
	private CrudService updateService;
	@Autowired
	private CrudService insertService;

	@Override
	public Object insertOrUpdateRecords(String querySql,String tableName, Map<String, String> params) {
		JSONObject jObject = (JSONObject) queryService.getRecords(querySql, params);
		if(jObject.has("res")){
			JSONArray jsonArray = (JSONArray) jObject.get("res");
			if(jsonArray.size()>0){//update
				return updateService.updateRecords(tableName,params);
			}else{//insert
				return insertService.insertRecords(tableName,params);
			}
			
		}else{
			JSONObject jObj = new JSONObject();
			jObj.element("msg", "查询失败!");
			return jObj;
		}
	}
}
