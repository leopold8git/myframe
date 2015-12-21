<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${page.title }</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/ajaxcore.js"></script>
<script type="text/javascript" src="js/string.prototype.js"></script>
<script type="text/javascript" src="resources/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="resources/artDialog/iframeTools.source.js"></script>
<script type="text/javascript" src="resources/artDialog/jquery.artDialog.extend.js"></script>
<c:forEach var="js" items="${page.jsLink }">
	<script type="text/javascript" src="${js}"></script>
</c:forEach>
<link href="resources/main.css" rel="stylesheet" type="text/css"/>
<link href="resources/artDialog/skins/blue.css" rel="stylesheet" type="text/css"/>
<c:forEach var="link" items="${page.styleLink }">
	<script type="text/javascript" src="${link}"></script>
</c:forEach>
<style type="text/css">
body{
	padding : 0;
	margin : 0;
	font-size :16px;
	background : none;
}
table{
	border-collapse:separate;
	border-spacing:10px;
}

td.right { 
	text-align: right;
	width : 30%;
}
td.left{
text-align: left;
width : 70%;
}

<c:if test="${not empty page.style}"> 
${page.style}
</c:if>
</style>
</head>
<body>
 <div id="bodyDiv" style="display: block;width:100%;height:auto;">
		<form method="post" id="frm" name="frm" class="edit-form" json="${page.formJson}"> 
				<div id="tc">
				<table border="0" cellpadding="2" cellspacing="3" width="100%">
					<c:forEach var="elem" items="${page.elements}"  varStatus="status">
					 	 	<c:if test="${status.index % 2 == 0 }">
					 	 	<tr>
					  			<td class="right">${elem}</td>
					  		</c:if>
							<c:if test="${status.index % 2 != 0 }">
					  			<td class="left">${elem}</td>
					  		</tr>
			  				</c:if>
					 </c:forEach>
	
				</table>
				</div>
					</form>
</div>
</body>
<script type="text/javascript">
<c:if test="${not empty page.js}"> 
${page.js}
</c:if>
</script>
</html>