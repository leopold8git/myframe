package x.y.util;

import java.text.DecimalFormat;

public class NumberUtils {

	private static final DecimalFormat df2digits = new DecimalFormat("###,##0.00");
	
	 public static String formatTo2digits(Object obj){
		 double d = Double.valueOf(obj+"");
		 return df2digits.format(Math.round(d * 100.0D) / 100.0D);
   }
	 
	 public static double sum(double[] d){
			double sum = 0;
			for (int i = 0; i < d.length; i++) {
				sum += d[i];
			}
			return sum;
	}
	 
	public static double sum(double[] d,int index){
		double sum = 0;
		for (int i = 0; i < d.length && i<=index; i++) {
			sum += d[i];
		}
		return sum;
	}
	
	 public static int sum(int[] d){
			int sum = 0;
			for (int i = 0; i < d.length; i++) {
				sum += d[i];
			}
			return sum;
	}
}
