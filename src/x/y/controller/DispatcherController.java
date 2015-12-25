/**
 * 
 */
package x.y.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import x.y.entity.CrubEnum;
import x.y.entity.ParamConfig;
import x.y.entity.Result;
import x.y.page.entity.EditPage;
import x.y.page.entity.Page;
import x.y.page.entity.QueryPage;
import x.y.service.CrudService;
import x.y.service.impl.ExcelServiceImpl;
import x.y.service.impl.InsertOrUpdateServiceImpl;
import x.y.util.SpringBeanUtils;
import x.y.util.SpringUtils;
import x.y.util.StringUtils;

/**
 * @author zenglp
 *
 */
@Controller
@Scope("request")
public class DispatcherController {
	
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private CrudService queryService;
	@Autowired
	private CrudService deleteService;
	@Autowired
	private CrudService updateService;
	@Autowired
	private CrudService insertService;
	
	@Autowired
	private CrudService batchUpdateService;
	@Autowired
	private CrudService batchInsertService;
	
	@Autowired
	private ExcelServiceImpl excelService;
	
	@Autowired
	@Qualifier("insertOrUpdateService")
	private CrudService insertOrUpdateService ; 
	
	public DispatcherController(){
		
	}
	//TODO 事务、异步
	@Transactional
	@RequestMapping(value = "/{reqPath:query|update|delete|insert|excel|batchUpdate|batchInsert|insertOrUpdate}.htm", method = RequestMethod.GET)
	public Object crub(@PathVariable String reqPath,HttpServletRequest req,HttpServletResponse resp){
		Object resObj = null;
	try {
			Map reqMap = SpringUtils.getRequestPramaMap(req);
			Map params = SpringUtils.getParams(reqMap,req);
			
			resObj = doService(reqPath,params,resp);
			
			if(resObj == null) return null;
			
			//以下要递归
			ParamConfig pc = SpringUtils.getParamConfig(req);
			if(pc != null && pc.getResultHandler() != null){
				resObj =  pc.getResultHandler().handlerResult(params, resObj, req, resp);
			}
			next( pc, reqMap, resObj,req,resp);
			
			if(resObj != null){
				resp.setContentType("text/json;charset=utf-8");
				log.debug("最终结果："+resObj.toString());
				resp.getWriter().print(resObj.toString());
				resp.getWriter().close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	 	return null;
	}
	
	private void next(ParamConfig pc,Map reqMap,Object resObj,HttpServletRequest req,HttpServletResponse resp){
		Object newResObj = null;
		Result pcRes = pc.getResult();
		if(pcRes != null && !"".equals(pcRes)){
			String m = pcRes.getMethod();
			String spelRes = pcRes.getRes();
			boolean merge = pcRes.isMerge();
			//将查询出的结果作为上下文
			EvaluationContext context = new StandardEvaluationContext();
			context.setVariable("resObj",resObj);
			ExpressionParser parser = new SpelExpressionParser();
			//am . #resObj.
			Expression exp = parser.parseExpression(spelRes);
			String pcId = (String) exp.getValue(context);
			if(pcId != null && !"".equals(pcId)){
				ParamConfig pc2  = (ParamConfig) SpringBeanUtils.getBean(pcId);
				Map newParamMap = new HashMap();
				newParamMap.putAll(reqMap);
				newParamMap.putAll(pc2.getConfigMap());
				//执行m
				doNextService(m,merge,newParamMap,resObj);
				
				if(pc2 != null && pc2.getResultHandler() != null){
					resObj =  pc2.getResultHandler().handlerResult(newParamMap, resObj, req, resp);
				}
				//递归
				 next( pc2,reqMap, resObj,req, resp);
			}
			
			
		}
	}
	
	
	private Object doService(String reqPath,Map params,HttpServletResponse resp){
		
		Object resObj = null;
		
		CrubEnum ce = CrubEnum.valueOf(reqPath);
		
		String tableName ,sql;
		
		switch(ce){
			case query : 
				sql = params.get("sql")+"";
				resObj = queryService.getRecords(sql, params);
				break;
			case update :
				tableName = params.get("tableName")+"";
				resObj = updateService.updateRecords(tableName, params);
				break;
			case batchUpdate : 
				tableName = params.get("tableName")+"";
				resObj = batchUpdateService.updateRecords(tableName, params);
				break;
			case insert : 
				tableName = params.get("tableName")+"";
			    resObj = insertService.insertRecords(tableName, params);
			    break;
			case batchInsert : 
				tableName = params.get("tableName")+"";
				resObj = batchInsertService.insertRecords(tableName, params);
				break;
			case delete :
				tableName = params.get("tableName")+"";
			    resObj = deleteService.removeRecords(tableName, params);
			    break;
			case excel :
				sql = params.get("sql")+"";
				List list  = queryService.getAllRecords(sql, params);
				excelService.createExcel(resp, params, list);
				break;
			case insertOrUpdate :
				tableName = params.get("tableName")+"";
				sql = params.get("sql")+"";
				resObj = insertOrUpdateService.insertOrUpdateRecords(sql, tableName, params);
				break;
			default : 
				//ajax return 404没用
				log.warn("uri:"+reqPath+" not found!");
		}
		return resObj;
	}
	
	private  void doNextService(String m,boolean merge,Map newParamMap,Object resObj){
		
			CrubEnum ce = CrubEnum.valueOf(m);
			
			String tableName = null;
			Object o = null;
			boolean excuted = false;
			switch(ce){
				case update : 
					tableName = newParamMap.get("tableName")+"";
					o = updateService.updateRecords(tableName, newParamMap);
					excuted = true;
				case  batchUpdate : 
					if(!excuted){
						tableName = newParamMap.get("tableName")+"";
						o = batchUpdateService.updateRecords(tableName, newParamMap);
						excuted = true;
					}
				case  insert : 
					if(!excuted){
						tableName = newParamMap.get("tableName")+"";
						o = insertService.insertRecords(tableName, newParamMap);
						excuted = true;
					}
				case  batchInsert : 
					if(!excuted){
						tableName = newParamMap.get("tableName")+"";
						o = batchInsertService.insertRecords(tableName, newParamMap);
						excuted = true;
					}
				case  delete : 
					if(!excuted){
						tableName = newParamMap.get("tableName")+"";
						o  = deleteService.removeRecords(tableName, newParamMap);
						excuted = true;
					}
					
				default : 
					
					if(excuted && resObj instanceof JSONObject && o instanceof JSONObject){
						if(merge){
							 ((JSONObject)resObj).putAll((JSONObject)o);
						}else{
							JSONObject jsonO =  (JSONObject)resObj;
							jsonO.clear();
							jsonO.putAll((JSONObject)o);
						}
					}
			}
	
	}
	
	
	
	/**
	 * 除crub外的其它操作
	 * @TODO 以下这种写法，思路是 请求 edit/edit.htm 则界面为edit/edit.jsp
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/**.htm")
	public ModelAndView goToPage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String reqUri = request.getRequestURI() ;
//		if(SpringBeanUtils.hasBean(reqPath)){
//			Object bean = SpringBeanUtils.getBean(reqPath+"");
//			 if(bean instanceof QueryPage){//查询界面
//				QueryPage qp = (QueryPage)bean;
//				mav.addObject("headers", StringUtils.stringToList(qp.getHeaders(), "|"));
//				mav.addObject("page",qp);
//				mav.setViewName(qp.getViewName()==null?"query":qp.getViewName());
//			}else if(bean instanceof EditPage){
//				EditPage qp = (EditPage)bean;
//				mav.addObject("page",qp);
//				mav.setViewName(qp.getViewName()==null?"edit/edit":qp.getViewName());
//			}else if(bean instanceof Page){
//				Page p = (Page)bean;
//				mav.addObject("page",p);
//				mav.setViewName(p.getViewName());
//			}
//		}
		if(reqUri.startsWith("/") || reqUri.startsWith("\\")){
			reqUri = reqUri.substring(1,reqUri.lastIndexOf("."));
		}
		mav.setViewName(reqUri);
		return mav;
	}
}
