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
import x.y.util.NumberUtils;
import x.y.util.StringUtils;

@Service("batchUpdateService")
public class BatchUpdateServiceImpl extends AbstractCrudService {


	@Override
	public Object updateRecords(String tableName, Map<String, String> params) {
		
		String prekey = StringUtils.blankToString(params.get("prekey"), "key")+".";
		
		String prestr = StringUtils.blankToString(params.get("prestr"), "module")+".";
		
		String where = params.get("where");
		if(where == null){
			where = "";
		}
		 String sql = "update " + tableName +" set ";
		 
		  boolean first = true;
		  boolean firstWhere = "".equals(where)?true:false;
		
		  List paramsList = new ArrayList();
		  
		  List keyList = new ArrayList();
		  
		  for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ){
				Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				String[] p = key.split("[.]");
				
				if(key != null && key.startsWith(prestr)){
					 if(!first) { 
						 sql = sql + "," + p[1] + "=?"; 
					 } else {
			            sql = sql + p[1] + "=?";
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
					    	 where = where + " and " + p[1] + " " + operate + "?"; 
				    	 } else {
					       where = where +" where "+ p[1] + " " + operate + "?";
					       firstWhere = false;
				       }
				}
				
		  }
		  paramsList.addAll(keyList);
		  sql = sql + where;
		  log.info("sql:"+sql);
			
		//对参数值进行处理
		  /**
		   * module.name=aa,bb,cc&module.age=11,11,11     aa,11  bb,11 cc,11
		   */
		  List<Object[]> batchParams = new ArrayList<Object[]>();
		  
		  List copyParamsList = new ArrayList(paramsList.size());
		  //批量执行的sql条数
		  int batchNum=0;
		  for (int j = 0; j < paramsList.size(); j++) {
			  String p = (String) paramsList.get(j);
			  String[] pa = p.split(",");
			  if(batchNum<pa.length)
			  batchNum = pa.length;
			  List valueList =  Arrays.asList(pa);
			  copyParamsList.add(valueList);
		  }
		  
		  for(int k=0;k<batchNum;k++){
			  Object[] objs = new Object[copyParamsList.size()];
			  for (int j = 0; j < copyParamsList.size(); j++) {//aa,bb,cc
				  List temp = (List)copyParamsList.get(j);
				  if(temp.size()<k+1){
					  objs[j] =   temp.get(temp.size()-1);
				  }else{
					  objs[j] =   temp.get(k); 
				  }
			  }
			  log.info("sql-param"+k+":"+Arrays.toString(objs));
			  batchParams.add(objs) ;
		  }
		  
		 int[] i = baseDao.batchUpdateRecords(sql, batchParams);
		
		return getResult(i);
		
	}
	
	private Object getResult(int[] result){
		JSONObject jObject = new JSONObject();
		int rows = NumberUtils.sum(result);
		jObject.element("row", rows);
		if(rows>0){
			jObject.element("msg", "修改成功!");
		}else{
			jObject.element("msg", "修改失败!");
		}
		
		return jObject;
	}

}
