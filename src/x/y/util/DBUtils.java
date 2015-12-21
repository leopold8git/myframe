package x.y.util;

import org.springframework.jdbc.core.JdbcTemplate;

public class DBUtils {

	public static JdbcTemplate getJdbcTemplate(){
		return (JdbcTemplate)SpringBeanUtils.getBean("jdbcTemplate");
	}
	
	public static int getSerialNo(String tableName){
		return getSerialNo(tableName,1);
	}
	
	public static int getSerialNo(String tableName , int startNo){
		JdbcTemplate jdbc = getJdbcTemplate();
		int count = jdbc.queryForInt("select count(*) from SerialNum where tableName=?", new Object[]{tableName});
		if(count > 0 ){
			jdbc.update("update SerialNum set serialNumber = serialNumber+1 where tableName=? ", new Object[]{tableName});
		}else{
			jdbc.update("insert into SerialNum values(?,?) ", new Object[]{tableName,startNo});
		}
		return jdbc.queryForInt("select serialNumber from SerialNum where tableName=?", new Object[]{tableName});
	}
	
}
 