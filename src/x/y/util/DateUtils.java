package x.y.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    
	public static String now(){                                                              
		return now("yyyy-MM-dd HH:mm:ss");                           
	}                                                              
	public static String now(String format) {                      
		java.util.Date date = new java.util.Date();                  
		SimpleDateFormat dateFm = new SimpleDateFormat(format);      
		return dateFm.format(date);                                  
	}    
	
	public static String getFormatStr(Date d,String format){
		SimpleDateFormat dateFm = new SimpleDateFormat(format);      
		return dateFm.format(d);                    
	}
	
	 public static Date addDay(Date date, int num) {
		  Calendar startDT = Calendar.getInstance();
		  startDT.setTime(date);
		  startDT.add(Calendar.DAY_OF_MONTH, num);
		  return startDT.getTime();
	 }
	 
	 public static Date addMinute(Date date, int num) {
		  Calendar startDT = Calendar.getInstance();
		  startDT.setTime(date);
		  startDT.add(Calendar.MINUTE, num);
		  return startDT.getTime();
	 }
	 
	 public static Date addSecond(Date date, int num) {
		  Calendar startDT = Calendar.getInstance();
		  startDT.setTime(date);
		  startDT.add(Calendar.SECOND, num);
		  return startDT.getTime();
	 }
	 
	 public static Date getDate(String s , String format){
		 SimpleDateFormat dateFm = new SimpleDateFormat(format); 
		 Date d = null;
		 try {
			d = dateFm.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	 }
	 
	 public static Calendar getCalendar(Date d){
		 Calendar startDT = Calendar.getInstance();
		 startDT.setTime(d);
		 return startDT;
	 }
	 
	 public static int getDiff(Date d1,Date d2,int field){
		 long time1 = d1.getTime();
		 long time2 = d2.getTime();
		 long d = Math.abs(time1-time2);
		 int di = 0;
		 if(field == Calendar.HOUR_OF_DAY){
			di = (int) d/1000/60/60;
		 }else if(field == Calendar.MINUTE){
			 di = (int) d/1000/60;
		 }else if(field == Calendar.SECOND){
			 di = (int) d/1000;
		 }
		 return di;
	 }
	 
	 public static int getDiff(Date d1,Date d2){
		return getDiff(d1,d2,Calendar.HOUR_OF_DAY);
	 }
	 
	 
	 public static String[] getNearTime(int num,String type){
		 String[] resArr = new String[2];
		 Calendar startDT = Calendar.getInstance();
		 
		 if("day".equalsIgnoreCase(type)){
			 String endStr = getFormatStr(startDT.getTime(),"yyyy-MM-dd")+" 23:59:59";
			 startDT.add(Calendar.DAY_OF_MONTH, (-1)*num);
			 String startStr = getFormatStr(startDT.getTime(),"yyyy-MM-dd")+" 00:00:00";
			 resArr[0] = startStr;
			 resArr[1] = endStr;
		 }else if("hour".equalsIgnoreCase(type)){
			 String endStr = getFormatStr(startDT.getTime(),"yyyy-MM-dd HH:")+"59:59";
			 startDT.add(Calendar.HOUR_OF_DAY, (-1)*num);
			 String startStr = getFormatStr(startDT.getTime(),"yyyy-MM-dd HH:")+"00:00";
			 resArr[0] = startStr;
			 resArr[1] = endStr;
		 }else if("month".equalsIgnoreCase(type)){
			 String endStr = getFormatStr(startDT.getTime(),"yyyy-MM-dd")+" 23:59:59";
			 startDT.add(Calendar.MONTH, (-1)*num);
			 String startStr = getFormatStr(startDT.getTime(),"yyyy-MM-dd")+" 00:00:00";
			 resArr[0] = startStr;
			 resArr[1] = endStr;
		 }
		 return resArr;
	 }
	 
	 
	 public static void main(String[] args) {
		 Date d = new Date();
		 Date d1 = addMinute(d, 5);
		System.out.println(getDiff(d,d1,Calendar.HOUR_OF_DAY)); 
	}
}
