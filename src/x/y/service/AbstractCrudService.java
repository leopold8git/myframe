package x.y.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import x.y.dao.BaseDao;
import x.y.service.CrudService;
import x.y.service.impl.InsertServiceImpl;
import x.y.service.impl.UpdateServiceImpl;

public abstract class AbstractCrudService implements CrudService{

	@Override
	public int getRecordsCount(String sql, Object[] objs) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Autowired
	protected BaseDao baseDao;

	protected final Log log = LogFactory.getLog(getClass());
	@Override
	public Object removeRecords(String tableName, Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object insertRecords(String tableName, Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getRecords(String sql, Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public List getAllRecords(String querySql, Map<String,String> params) {
		return null;
	}
	
	public List getRecords(String querySql, Object[] objs) {
		return null;
	}

	@Override
	public Object updateRecords(String tableName, Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map getRecordForMap(String sql, Object[] objs){
		return null;
	}
	
	public Object insertOrUpdateRecords(String querySql,String tableName, Map<String, String> params){
		return null;
	}

}
