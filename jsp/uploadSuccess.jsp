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
<title>显示所有的记录</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/ajaxcore.js"></script>
<script type="text/javascript" src="js/string.prototype.js"></script>
<script type="text/javascript" src="resources/artDialog/jquery.artDialog.js"></script>
<link href="resources/main.css" rel="stylesheet" type="text/css"/>

</head>
<body>
 上传成功${file0}
<script type="text/javascript">
var filePath ;
var imgSpan;
var div;
var sizeDiv;
var nameDiv;
var type;
var rightDiv;
<c:forEach var="file" items="${fileList}" varStatus="i">
	 type = "${file.fileType}";
	 div = $("<div></div>");
	 div.css({"float":"left","margin":"5px"});
	 rightDiv = $("<div></div>").css("float","left");
	 imgSpan = $("<span></span>");
	 nameDiv = $("<div></div>");
	 sizeDiv = $("<div></div>");
	 sizeDiv.html("${file.size}"/1000+"K");
	 nameDiv.html("${file.fileName}");
	 imgSpan.css({"width":"32px","height":"32px","background-image":"url(<%=basePath%>/resources/images/fileico_v5.png)","background-repeat":"no-repeat","display":"block","float":"left"});
	 if(type == 'html' || type == 'htm'){
		 imgSpan.css({"background-position":"-288px -64px"});
	 }else if(type == 'rar' || type == 'zip'){
		 imgSpan.css({"background-position":"-160px -64px"});
	 }else if(type == 'jpg'){
		 imgSpan.css({"background-position":"0px -96px"});
	 }else if(type == 'gif'){
		 imgSpan.css({"background-position":"-32px -96px"});
	 }else if(type == 'png'){
		 imgSpan.css({"background-position":"-64px -96px"});
	 }else{
		 imgSpan.css({"background-position":"-384px -64px"});
	 }
	 div.append(imgSpan);
	 rightDiv.append(sizeDiv);
	 rightDiv.append(nameDiv);
	 div.append(rightDiv);
	 parent.$("#showFileArea").append(div);
</c:forEach>

</script>
</body>
</html>