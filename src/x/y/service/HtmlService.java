package x.y.service;

import java.util.Map;

public interface HtmlService {

	String getPrice(int netId , String productId);
	
	Map getZGCPrice(String productId);
	
	Map getJXSPrice(String productId);
}
