package x.y.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao {

	List queryRecords(String sql,Object[] params);
	
	List queryRecords(String sql);
	
	int queryCounts(String sql,Object[] params);
	
	int queryCounts(String sql);
	
	Map queryById(String sql,Object id);
	
	Map queryForMap(String sql, Object[] ids);
	
	int deleteRecords(String sql );

	int deleteRecords(String sql, Object[] params);
	
	int updateRecords(String sql );

	int updateRecords(String sql, Object[] params);
	
	int[] batchUpdateRecords(String sql, List<Object[]> params);
	
}
