<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${page.title}</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/ajaxcore.js"></script>
<script type="text/javascript" src="js/string.prototype.js"></script>
<script type="text/javascript" src="resources/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="resources/artDialog/iframeTools.source.js"></script>
<script type="text/javascript" src="resources/artDialog/jquery.artDialog.extend.js"></script>
<script type="text/javascript" src="resources/My97DatePicker/WdatePicker.js"></script>
<c:forEach var="js" items="${page.jsLink }">
	<script type="text/javascript" src="${js}"></script>
</c:forEach>
<link href="resources/main.css" rel="stylesheet" type="text/css"/>
<link href="resources/artDialog/skins/blue.css" rel="stylesheet" type="text/css"/>
<c:forEach var="link" items="${page.styleLink }">
<link href="${link}" rel="stylesheet" type="text/css"/>
</c:forEach>
<style type="text/css">
<c:if test="${not empty page.style}"> 
${page.style}
</c:if>
</style>
</head>
<body>

<div id="title" style="background: url(resources/images/BG_T_TD.gif)">
			<div style="font-weight:bold;color:white;height: 24px;line-height: 24px;">
				${page.qns}
				<font color="yellow"></font>
			</div>
		</div>
<div id="search" style="padding:  0.5em 0.5em 0em 0.5em;">
	<form id="frm" name="frm" json="${page.formJson}">
	 <div class="inquire">
	      <c:forEach var="ele" items="${page.elements}">
				<th>${ele }</th> 
		 </c:forEach>
        </div> 
        
        </form>
        <div>
               <table width="100%"   class="tablebox" border="1">
				<thead>
			<tr>
			<c:forEach var="th" items="${headers}">
				<th>${th }</th> 
			</c:forEach>
		</tr>
		</thead>
			<tbody id="data">		
			<tr style="display:none;"> 
				<c:forEach var="td" items="${page.tds}">
					${td }	
				</c:forEach>
			 </tr>
		</tbody>
		</table>
		</div>
		<div id="page" align="right"> </div>
		 <c:forEach var="belem" items="${page.bottomElements}">
				${belem } 
		 </c:forEach>
		<!-- <input style="cursor: pointer;" type="button" onclick="add()" value="添加" class="bt_add" /> -->
    </div>
	
</body>
<script type="text/javascript">
function query(){
	$.ajaxcore.doSubmit($("#frm").get(0),$("#data").get(0),$("#page").get(0));
}
function createA(content,href,click,attr){
	var tpl,json ;
		tpl = "<a href=\"{href}\" {attr}>{content}</a>";
		json = {"content":content,"href":href,"attr":attr||""};
	//alert(tpl.fillTpl(json))
	return tpl.fillTpl(json);
}
<c:if test="${not empty page.js}"> 
${page.js}
</c:if>
</script>
</html>