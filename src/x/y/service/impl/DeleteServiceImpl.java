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

@Service("deleteService")
public class DeleteServiceImpl extends AbstractCrudService {

	@Override
	public Object removeRecords(String tableName, Map<String, String> params) {
		int i = 0;
		String prekey = StringUtils.blankToString(params.get("prekey"), "key")+".";
		
		String where = StringUtils.blankToString(params.get("where"));
		
		StringBuffer sqlBuffer = new StringBuffer("delete from   ").append(tableName).append(where);
		
		  boolean first = true;
		
		  List paramsList = new ArrayList();
		  
		  for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ){
				Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				if(key != null && key.startsWith(prekey)&& !"".equals(value)){
					String[] p = key.split("[.]");
					
					     String operate = "=";
					
					     if (p.length >= 3)
					       if (p[2].equals("more")) { operate = ">";
					       } else if (p[2].equals("less")) { operate = "<";
					       } else if (p[2].equals("morethan")) { operate = ">=";
					       } else if (p[2].equals("lessthan")) { operate = "<=";
					       } else if (p[2].equals("unequ")) { operate = "<>";
					       } else if (p[2].equals("like")) { operate = "like"; value = "%" + value + "%";
					       } else if (p[2].equals("leftlike")) { operate = "like"; value = value + "%";
					       } else if (p[2].equals("rightlike")) { operate = "like"; value = "%" + value;
					       }
					     String fieldtype = "str";
					     if (p.length >= 4) fieldtype = p[3];
					     paramsList.add(value);
					
					     if (!first) { 
					    	 sqlBuffer.append(" and ").append(p[1]).append(" ").append(operate).append("?");
				    	 } else {
					       sqlBuffer.append(" where ").append(p[1]).append(" ").append(operate).append("?");
					       first = false;
				       }
				}
				
				
			}
		  Object[] obj = new Object[paramsList.size()];
			obj = paramsList.toArray(obj);
			if(log.isDebugEnabled()){
				log.debug("sql:"+sqlBuffer.toString());
				log.debug("params:"+Arrays.toString(obj));
			}
			i = baseDao.deleteRecords(sqlBuffer.toString(), obj);
		
		return getResult(i);
	}
	
	
	private Object getResult(int result){
		JSONObject jObject = new JSONObject();
		jObject.element("row", result);
		if(result>0){
			jObject.element("msg", "删除成功!");
		}else{
			jObject.element("msg", "删除失败!");
		}
		
		return jObject;
	}

}
