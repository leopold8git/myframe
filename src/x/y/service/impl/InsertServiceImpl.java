package x.y.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.dao.BaseDao;
import x.y.service.AbstractCrudService;
import x.y.util.StringUtils;

@Service("insertService")
public class InsertServiceImpl extends AbstractCrudService {

	@Override
	public Object insertRecords(String tableName, Map<String, String> params) {
		int i = 0;
		
		String prestr = StringUtils.blankToString(params.get("prestr"), "module")+".";
		
		 StringBuffer sqlBuffer = new StringBuffer("insert into ").append(tableName).append("(");
		 
		 StringBuffer valueBuffer = new StringBuffer("values(");
		 
		  boolean first = true;
		
		  List paramsList = new ArrayList();
		  
		  for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ){
				Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				String[] p = key.split("[.]");
				
				
				if(key != null && key.startsWith(prestr)&& !"".equals(value)){
					    
					     paramsList.add(value);
					
					     if (!first) { 
					    	 sqlBuffer.append(",").append(p[1]);
					    	 valueBuffer.append(",?");
				    	 } else {
				    		 sqlBuffer.append(p[1]);
				    		 valueBuffer.append("?");
					      first = false;
				       }
				}
				
		  }
		  
		  sqlBuffer.append(")");
		  valueBuffer.append(")");
		  
		  Object[] obj = new Object[paramsList.size()];
			obj = paramsList.toArray(obj);
			sqlBuffer.append(valueBuffer.toString());
			
			if(log.isDebugEnabled()){
				log.debug("sql:"+sqlBuffer.toString());
				log.debug("params:"+Arrays.toString(obj));
			}
			
			i = baseDao.updateRecords(sqlBuffer.toString(), obj);
		
		return getResult(i);
	}
	
	private Object getResult(int result){
		JSONObject jObject = new JSONObject();
		jObject.element("row", result);
		if(result>0){
			jObject.element("msg", "新增成功!");
		}else{
			jObject.element("msg", "新增失败!");
		}
		
		return jObject;
	}

}
