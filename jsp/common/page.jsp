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
<link href="resources/artDialog/skins/blue.css" rel="stylesheet" type="text/css"/>
<c:forEach var="js" items="${page.jsLink }">
	<script type="text/javascript" src="${js}"></script>
</c:forEach>

<c:forEach var="link" items="${page.styleLink }">
	<link href="${link}" rel="stylesheet" type="text/css"/>
</c:forEach>
<style type="text/css">
body{
	padding : 0;
	margin : 0;
	font-size :16px;
}


<c:if test="${not empty page.style}"> 
${page.style}
</c:if>
</style>
</head>
<body>
	
</body>
<script type="text/javascript">

<c:if test="${not empty page.js}"> 
${page.js}
</c:if>
</script>
</html>