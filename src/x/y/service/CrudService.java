package x.y.service;

import java.util.List;
import java.util.Map;

public interface CrudService {

	Object removeRecords(String tableName,Map<String,String> params);
	
	Object insertRecords(String tableName,Map<String,String> params);
	
	Object getRecords(String sql,Map<String,String> params);
	
	
	int getRecordsCount(String sql,Object[] objs);
	
	Map getRecordForMap(String sql, Object[] objs);
	
	List getAllRecords(String querySql, Map<String,String> params);
	
	List getRecords(String querySql,Object[] objs);
	
	Object updateRecords(String tableName,Map<String,String> params);
	
    Object insertOrUpdateRecords(String querySql,String tableName, Map<String, String> params);
}
