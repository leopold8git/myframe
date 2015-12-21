package x.y.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import x.y.service.AbstractCrudService;
import x.y.util.JsonUtils;
import x.y.util.StringUtils;

@Service("queryService")
public class QueryServiceImpl extends AbstractCrudService {

	@Override
	public Object getRecords(String querySql, Map<String,String> params) {
		
		List paramList = new ArrayList();
		String sql = getConditionSql(querySql,params,paramList);
		Object[] obj = new Object[paramList.size()];
		obj = paramList.toArray(obj);
		
		String resultType = params.get("resultType");
		if("single".equals(resultType)){//查询单条记录，主要是统计用
			Map map = getRecordForMap(sql,obj);
			JSONObject resObj = new JSONObject();
			resObj.putAll(map);
			return resObj;
		}
		
		//分页信息
		String recordsPerPageStr = params.get("recordsperpage");
		if(org.apache.commons.lang.StringUtils.isBlank(recordsPerPageStr)){
			recordsPerPageStr = params.get("res.recordsperpage");
			if(org.apache.commons.lang.StringUtils.isBlank(recordsPerPageStr)){
				recordsPerPageStr = "10";
			}
		}
		int recordsPerPage = Integer.valueOf(recordsPerPageStr);
		int curPage = 1;
		String curPageStr = params.get("res.CurPage");
		if(curPageStr!= null && !"".equals(curPageStr)){
			curPage = Integer.valueOf(curPageStr);
		}
		
		String queryCountSql = "select count(*) from ("+sql+")t";
		
		if(recordsPerPage != -1){
			sql = sql + " limit " + (curPage - 1) * recordsPerPage + "," + recordsPerPage;
		}
		
//		System.out.println("sql:"+sql);
//		System.out.println("queryCountSql:"+queryCountSql);
		if(log.isDebugEnabled()){
			log.debug("sql:"+sql);
			log.debug("queryCountSql:"+queryCountSql);
			log.debug("params:"+Arrays.toString(obj));
		}
		
//		System.out.println("params:"+Arrays.toString(obj));
		
		List list = baseDao.queryRecords(sql, obj);
		
		//子查询
		String subSql = params.get("subSql");
		if(subSql != null && !"".equals(subSql)){
			String[] subSqlArr = subSql.split("[|]");
			for (int i = 0; i < list.size(); i++) {
				Map m = (Map)list.get(i);
				for (int j = 0; j < subSqlArr.length; j++) {
					String tempSubSql = x.y.util.StringUtils.fillTpl(subSqlArr[j], m);
					
					List subParamList = new ArrayList();
					tempSubSql = getSubConditionSql(tempSubSql,params,subParamList);
					Object[] subObj = new Object[subParamList.size()];
					subObj = subParamList.toArray(subObj);
					
					if(log.isDebugEnabled()){
						log.debug(tempSubSql);
					}
					List subList = baseDao.queryRecords(tempSubSql,subObj);
					if(subList.size()>1){
						log.warn("子查询"+tempSubSql+"返回记录大于1");
					}
					//默认选择第一条记录
					m.putAll((Map)subList.get(0));
				}
			}
		}
		
		int recordsCount = baseDao.queryCounts(queryCountSql, obj);
		
		Object res = getResult(list,recordsCount,recordsPerPage,curPage);
		return res;
	}
	
	@Override
	public List getAllRecords(String querySql, Map<String,String> params) {
		List paramList = new ArrayList();
		String sql = getConditionSql(querySql,params,paramList);
		Object[] obj = new Object[paramList.size()];
		obj = paramList.toArray(obj);
		List list = baseDao.queryRecords(sql, obj);
		return list;
	}

		
	private Object getResult(List list,int recordsCount,int recordsPerPage,int curPage){
		JSONObject jObject = new JSONObject();
		//List list = baseDao.queryRecords(sql, params);
		JSONArray json = JSONArray.fromObject(list);
//		jObject.element("res", JsonUtils.toKeyLowerCase(json));
		jObject.element("res", json);
			JSONArray pageArray = new JSONArray();
			JSONObject pageObject = new JSONObject();
			int totalPageCount = 1 ;
			if(recordsPerPage != -1){
				if(recordsCount%recordsPerPage == 0){
					totalPageCount = recordsCount/recordsPerPage;
				}else{
					totalPageCount = recordsCount/recordsPerPage + 1;
				}
			}
			pageObject.element("RecordsCount", recordsCount);
			pageObject.element("CurPage", curPage);
			pageObject.element("RecordsPerPage", recordsPerPage);
			pageObject.element("TotalPageCount", totalPageCount);
			pageArray.add(pageObject);
			jObject.element("res.page", pageArray);
		return jObject;
	}	
	
	
	protected String getConditionSql(String querySql, Map<String,String> params,List paramList) {
		
		String preStr = StringUtils.blankToString(params.get("prestr"), "query")+".";
		
		String group = params.get("group");
		
		String where  = StringUtils.blankToString(params.get("where")," where 1=1 ");
		
		String order = params.get("order");
		
		String sql = querySql;
		
		where = getWherePortion(preStr,where,params,paramList);
		
		sql = sql + "  " + where + " " + (group==null?"":group) + " " + (order==null?"":order);
		
		return sql;
	}
	
	protected String getWherePortion(String preStr,String where,Map<String,String> params,List outParamList/*输出参数*/){
		for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ){
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			if(key != null && key.startsWith(preStr)&& !"".equals(value)){
				String[] p = key.split("[.]");
				String operate = "=";
				if (p.length >= 3) {
					if (p[2].equals("more")) {
						operate = ">";
					} else if (p[2].equals("less")) {
						operate = "<";
					} else if (p[2].equals("morethan")) {
						operate = ">=";
					} else if (p[2].equals("lessthan")) {
						operate = "<=";
					} else if (p[2].equals("unequ")) {
						operate = "<>";
					} else if (p[2].equals("uequ")) {
						operate = "uequ";
					} else if (p[2].equals("ulike")) {
						operate = "ulike";
						value = "%" + value + "%";
					} else if (p[2].equals("like")) {
						operate = "like";
						value = "%" + value + "%";
					} else if (p[2].equals("leftlike")) {
						operate = "like";
						value = value + "%";
					} else if (p[2].equals("rightlike")) {
						operate = "like";
						value = "%" + value;
					} else if (p[2].equals("in")) {
						operate = "in";
					} else if (p[2].equals("notin")) {
						operate = "not in";
					} else if (p[2].equals("between")) {
						operate = "between";
					}
				}	
					
					//表别名
					String tempField = p[1].replaceAll("-",".");
					
					where += " and ";
					if (operate.equalsIgnoreCase("between")) {
		                  where += (tempField + ">=" + " ? and " + tempField + " <= ? ");
		                  String[] tempValue = value.split("[|]"); 
		                  for (int i = 0; i < tempValue.length; i++) {
		                	  outParamList.add(tempValue[i]);
		                  }
	                }else if ((operate.equalsIgnoreCase("in")) || (operate.equalsIgnoreCase("not in"))) {
	                	 String[] tempValue = value.split(","); 
	                	 where += (tempField +" " +operate + " (");
		                  for (int iValues = 0; iValues < tempValue.length; iValues++) {
		                    if (iValues != 0)  where += ",";
		                    where = where + "?";
		                    outParamList.add(tempValue[iValues]);
		                  }
		                  where = where + ")";
		                }
		                else{
		                	where = where + tempField +" "+operate + " ? ";
		                	outParamList.add(value);
		                }
					
				}
				
			}
		
		return where;
	}
	
	
	protected String getSubConditionSql(String querySql, Map<String,String> params,List paramList) {
		String preStr = params.get("subprestr");
		String sql = querySql;
		if (preStr == null || "".equals(preStr)) preStr = "subquery";
		preStr+=".";
		String group = params.get("subgroup");
		String where = params.get("subwhere");
		if((sql != null && sql.indexOf("where") == -1) &&  (where == null || "".equals(where)) ){
			where = " where 1=1 ";
		}else{
			where ="";
		}
		
		String order = params.get("suborder");
		
		where = getWherePortion(preStr,where,params,paramList);
		
		
		sql = sql + "  " + where + " " + (group==null?"":group) + " " + (order==null?"":order);
		
		return sql;
	}
	

	@Override
	public Map getRecordForMap(String sql, Object[] objs) {
		return baseDao.queryForMap(sql, objs);
	}

	@Override
	public int getRecordsCount(String sql, Object[] objs) {
		return baseDao.queryCounts(sql, objs);
	}

	@Override
	public List getRecords(String querySql, Object[] objs) {
		return baseDao.queryRecords(querySql, objs);
	}
	
}
