package x.y.util;

import java.util.Arrays;

public class RandomUtils {

	//根据比率生成随机数
	public static int getRandomIndex(double[] rateArr){
		int index = 0;
		double sum = NumberUtils.sum(rateArr);
		//转化百分比
		double[] newRateArr = new double[rateArr.length];
		for (int i = 0; i < newRateArr.length; i++) {
			newRateArr[i] = rateArr[i]/sum;
		}
		double r = Math.random();
		for (int i = 0; i < newRateArr.length; i++) {
			if(i == 0 && r>=0 && r<newRateArr[i]){
				index = 0 ;
				break;
			}else if(r<NumberUtils.sum(newRateArr,i) && r>=NumberUtils.sum(newRateArr,i-1)){
				index = i;
				break;
			}
		}
		return index;
	}
	
	//根据比率生成随机数组
	public static int[] getRandomIndexArr(double[] rateArr,int num){
		int ia[] = new int[num]; 
		for (int i = 0; i < num; i++) {
			ia[i] = getRandomIndex(rateArr);
			//if chosen then set rate=0
			rateArr[ia[i]] = 0;
		}
		return ia;
	}
	
	
	
	public static void main(String[] args) {
		double d[] = {0.1,0.4,0.15,0.05,0.3};
		System.out.println(Arrays.toString(getRandomIndexArr(d,3)));
	}
}
