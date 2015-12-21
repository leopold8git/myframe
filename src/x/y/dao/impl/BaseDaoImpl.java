package x.y.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import x.y.dao.BaseDao;

@Repository("baseDao")
public class BaseDaoImpl implements BaseDao{

	@Autowired
	 private JdbcTemplate jdbcTemplate;

	@Override
	public List queryRecords(String sql, Object[] params) {
		List list  = jdbcTemplate.queryForList(sql, params);
		return list;
	}

	@Override
	public List queryRecords(String sql) {
		List list  = jdbcTemplate.queryForList(sql);
		return list;
	}

	@Override
	public int queryCounts(String sql, Object[] params) {
		int count = jdbcTemplate.queryForInt(sql, params);
		return count;
	}

	@Override
	public int queryCounts(String sql) {
		int count = jdbcTemplate.queryForInt(sql);
		return count;
	}

	
	public int deleteRecords(String sql ,Object[] params){
		int i = jdbcTemplate.update(sql, params);
		return i;
	}
	
	public int deleteRecords(String sql ){
		int i = jdbcTemplate.update(sql);
		return i;
	}

	@Override
	public int updateRecords(String sql) {
		int i = jdbcTemplate.update(sql);
		return i;
	}

	@Override
	public int updateRecords(String sql, Object[] params) {
		int i = jdbcTemplate.update(sql, params);
		return i;
	}
	
	@Override
	public int[] batchUpdateRecords(String sql, List<Object[]> params) {
		int[] i = jdbcTemplate.batchUpdate(sql, params);
		return i;
	}

	@Override
	public Map queryById(String sql, Object id) {
		return jdbcTemplate.queryForMap(sql, new Object[]{id});
	}
	
	@Override
	public Map queryForMap(String sql, Object[] ids) {
		return jdbcTemplate.queryForMap(sql, ids);
	}
	
}
