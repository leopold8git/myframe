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

@Service("updateService")
public class UpdateServiceImpl extends AbstractCrudService {


	@Override
	public Object updateRecords(String tableName, Map<String, String> params) {
		int i = 0;
		
		String prekey = StringUtils.blankToString(params.get("prekey"), "key")+".";
		
		String prestr = StringUtils.blankToString(params.get("prestr"), "module")+".";
		
		String whereStr = StringUtils.blankToString(params.get("where"));
		
		StringBuffer sqlBuffer = new StringBuffer("update ").append(tableName).append(" set ");
		
		StringBuffer whereBuffer = new StringBuffer(whereStr);
		
		  boolean first = true;
		  boolean firstWhere = "".equals(whereBuffer.toString())?true:false;
		
		  List paramsList = new ArrayList();
		  
		  List keyList = new ArrayList();
		  
		  for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ){
				Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				String[] p = key.split("[.]");
				
				if(key != null && key.startsWith(prestr)){
					 if(!first) { 
						 sqlBuffer.append(",").append(p[1]).append("=?");
					 } else {
			            sqlBuffer.append(p[1]).append("=?");
			            first = false;
			          }
					 
					 paramsList.add(value);
				}
				
				
				if(key != null && key.startsWith(prekey)&& !"".equals(value)){
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
					     keyList.add(value);
					
					     if (!firstWhere) { 
					    	 whereBuffer.append(" and ").append(p[1]).append(" ").append(operate).append("?");
				    	 } else {
					       whereBuffer.append(" where ").append(p[1]).append(" ").append(operate).append("?");
					       firstWhere = false;
				       }
				}
				
		  }
		  paramsList.addAll(keyList);
		  Object[] obj = new Object[paramsList.size()];
			obj = paramsList.toArray(obj);
			sqlBuffer.append(whereBuffer.toString());
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
			jObject.element("msg", "修改成功!");
		}else{
			jObject.element("msg", "修改失败!");
		}
		
		return jObject;
	}

}
