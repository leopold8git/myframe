package x.y.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

public class HtmlUtils {
	
	private final static Log log = LogFactory.getLog(HtmlUtils.class);

	public static String getPrice8ZGC(String url){//中关村
		String p = "";
		try {
			Parser parser = new Parser (url);
			HasAttributeFilter filter = new HasAttributeFilter("class","price-type price-retain");
			NodeList list = parser.parse (filter);
			
//			TextExtractingVisitor visitor  =   new  TextExtractingVisitor() ;
//			parser.visitAllNodesWith(visitor);
//			System.out.println(visitor.getExtractedText());
			
			for (int i = 0; i < list.size(); i++) {
				Node n = list.elementAt(i);
				String s = "";
				for (int j = 0; j < n.getParent().getChildren().size(); j++) {
					if(n.getParent().getChildren().elementAt(j) instanceof TextNode){
						s+=n.getParent().getChildren().elementAt(j).toHtml(false);
					}
				}
				s = s.trim().replaceAll("\\s", "");
				System.out.println(s.substring(1));
				p = s.substring(1);
			}  
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	public static String getPrice8YMS(String url){//亚马逊
//		url = "http://www.amazon.cn/gp/product/B00HSED7LK/";
		String p = "";
		try {
			Parser parser = new Parser (url);
			HasAttributeFilter filter = new HasAttributeFilter("id","priceblock_ourprice");
			HasParentFilter  hasParentFilter = new HasParentFilter(filter);
			NodeList list = parser.parse (hasParentFilter);
			
//			TextExtractingVisitor visitor  =   new  TextExtractingVisitor() ;
//			parser.visitAllNodesWith(visitor);
//			System.out.println(visitor.getExtractedText());
//			System.out.println(list.size());
			Node n  = list.elementAt(0);
			p = n.toHtml(false);
			p =  p.trim().replaceAll("\\s", "");
			p = p.substring(1);
			log.debug(p);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	
	public static String getPrice8JD(String url){//京东
	//	url = "http://p.3.cn/prices/get?skuid=J_981821&type=1&callback=cb";
		/*
		 * cb([{"id":"J_981821","p":"1999.00","m":"3288.00"}]);
		 */
		String p = "";
		try{
		String res = RemoteUtils.getHtmlSource(url, "UTF-8");
		log.debug(res);
		if(res != null){
			int start = res.indexOf("{");
			int end =  res.lastIndexOf("}");
			res = res.substring(start,end+1);
			JSONObject resJson = JSONObject.fromObject(res);
			if(resJson.has("p")){
				p = resJson.getString("p");
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return p;
	}
	
	
	public static String getPrice8GM(String url){//国美
		//动态获取
		
		/**http://www.gome.com.cn/product/A0004983638-pop8004658069.html
		 * http://g.gome.com.cn/ec/homeus/n/product/browse/priceInfo.jsp?callback=cb_804229580268532&productId=A0004983638&skuId=pop8004658069&areaId=31020100&programId=&threeD=n&_=1418382081015
		 * cb_804229580268532(
	{"price":5845,"originalPrice":9999,"proms":[{"site_enable":"0","type":"ZHIJIANG","iconText":"直降","desc":"已优惠4154元","titleList":[]}]}
	)
		 */
//		url = "http://g.gome.com.cn/ec/homeus/n/product/browse/priceInfo.jsp?callback=cb&productId=A0004983638&skuId=pop8004658069";
		String p = "";
		try{
		String res = RemoteUtils.getHtmlSource(url, "UTF-8");
		log.debug(res);
		if(res != null){
			int start = res.indexOf("{");
			int end =  res.lastIndexOf("}");
			res = res.substring(start,end+1);
			JSONObject resJson = JSONObject.fromObject(res);
			if(resJson.has("price")){
				p = resJson.getString("price");
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return p;
	}
	
	
	public static String getPrice8JXS(String url){//其它经销商
//		url = "http://www.zol.com/index.php?c=Ajax_GoodsSuit&callback=cb&goodsId=23411228";
		/*
		 * cb([{"id":"J_981821","p":"1999.00","m":"3288.00"}]);
		 */
		String p = "";
		try{
		String res = RemoteUtils.getHtmlSource(url, 3000 , "UTF-8" ,true);
		log.debug(res);
		if(res != null){
			int start = res.indexOf("{");
			int end =  res.lastIndexOf("}");
			res = res.substring(start,end+1);
			JSONObject resJson = JSONObject.fromObject(res);
			if(resJson.has("goodsPrice")){
				p = resJson.getString("goodsPrice");
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return p;
	}
	
	
	public static Map getPriceFromZGC(String url){//中关村
		Map map = new HashMap();
		map.put("url", url);
//		url = "http://detail.zol.com.cn/cell_phone/index273715.shtml";
		try {
			Parser parser = new Parser(url);
			//中关村参考价
			HasAttributeFilter zgcFilter = new HasAttributeFilter("id","J_PriceTrend");
			
			NodeList list = parser.parse (zgcFilter);
			
			String zgcPrice = "";
			if(list.size() == 1 ){
				Node n = list.elementAt(0);
				String s = "";
				for (int j = 0; j < n.getChildren().size(); j++) {
					if(n.getChildren().elementAt(j) instanceof TextNode){
						s+=n.getChildren().elementAt(j).toHtml(false);
					}
				}
				s = s.trim().replaceAll("\\s", "");
				zgcPrice = s.substring(1);
			}
			log.debug("zgcPrice"+ zgcPrice);
			map.put("zgcPrice", zgcPrice);
			
			//京东
			parser.reset();
			HasAttributeFilter jdFilter = new HasAttributeFilter("class","b2c-jd");
			HasParentFilter jdParentFilter = new HasParentFilter(jdFilter);
			NodeList jdList =  parser.parse(jdParentFilter);
			String jdPrice = "";
			if(jdList.size() == 3){
				Node n = jdList.elementAt(2);
				jdPrice = n.toHtml();
				jdPrice = jdPrice.trim();
				if(jdPrice.length()>1){
					jdPrice = jdPrice.substring(1);
				}
			}
		   
			log.debug("jdPrice"+ jdPrice);
			map.put("jdPrice", jdPrice);
			
			
			//亚马逊
			parser.reset();
			HasAttributeFilter amazonFilter = new HasAttributeFilter("class","b2c-amazon");
			HasParentFilter amazonParentFilter = new HasParentFilter(amazonFilter);
			NodeList amazonList =  parser.parse(amazonParentFilter);
			String amazonPrice = "";
			if(amazonList.size() == 3){
				Node n = amazonList.elementAt(2);
				amazonPrice = n.toHtml();
				amazonPrice = amazonPrice.trim();
				if(amazonPrice.length()>1){
					amazonPrice = amazonPrice.substring(1);
				}
			}
		   
			log.debug("amazonPrice"+ amazonPrice);
			map.put("amazonPrice", amazonPrice);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static Map getPriceFromJXS(String url){//其它经销商
		Map map = new HashMap();
		map.put("url", url);
//		url = "http://detail.sxdn.com.cn/?c=MemCache&type[]=detailMidMerchant&type[]=detailTopMerchant&proid=273715&province_id=11&city_id=0&num=10";
		
		try {
			String res = RemoteUtils.getHtmlSource(url, "GBK");
			
			int startIndex = res.indexOf("'");
			int endIndex = res.lastIndexOf("table");
			if(res.length() >6){
				res = res.substring(startIndex+1, endIndex+6);
				res = res.replaceAll("\\\\", "");
//				log.debug(res);
				Parser parser = new Parser(res);
				HasAttributeFilter jxsFilter = new HasAttributeFilter("class","ho12");
//				HasParentFilter jxsParentFilter = new HasParentFilter(jxsFilter);
				
				NodeList list = parser.parse (jxsFilter);
//				System.out.println(list.size());
				for (int i = 0; i < list.size(); i++) {
					Node n = list.elementAt(i);
					
					//获取价格
					String price = "";
					NodeList nc = n.getChildren();
					if(nc != null ){
						for(int j=0;j<nc.size();j++){
							Node tn = nc.elementAt(j);
							if(tn instanceof TextNode){
								price+= tn.toHtml();
							}
						}
						price = price.trim();
						if(price.length()>1){
							price = price.substring(1);
						}
//						System.out.println(price);
					}
					
					//获取店铺Id
				 String sellerId = "";
				  Node tr = n.getParent().getParent();
				  NodeList tds = tr.getChildren();
				  for(int k=0;k<tds.size();k++){
					  Node tempTd = tds.elementAt(k);
					  if(tempTd instanceof TableColumn){
						  //System.out.println(tempTd.toHtml());
						 TableColumn tc = (TableColumn) tempTd;
						String cl = tc.getAttribute("class");
						if(cl == null) continue;
						if("lh18".equals(cl)){
							NodeList tcc = tc.getChildren();
							for (int h = 0; h < tcc.size(); h++) {
								if(tcc.elementAt(h) instanceof LinkTag){
									LinkTag a = (LinkTag) tcc.elementAt(h);
									String href = a.getAttribute("href");
									sellerId = href.substring(href.indexOf("d_")+2,href.lastIndexOf("/"));
//									System.out.println(sellerId);
									break;
								}
							}
						}
					  }
				  }
				  //System.out.println(tr.toHtml());	
//					System.out.println(n.toHtml());
				  map.put("shop_"+sellerId,price);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(getPriceFromJXS(""));;
//		System.out.println(RemoteUtils.getHtmlSource("http://detail.sxdn.com.cn/detail/273715/index/", "GBK"));
	}
	
}
