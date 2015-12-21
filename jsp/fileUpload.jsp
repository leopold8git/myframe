<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传图片</title>
<script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="../js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="../js/ajaxcore.js"></script>
<script type="text/javascript" src="../resources/artDialog/jquery.artDialog.js"></script>
<link href="../resources/main.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/fileupload.css" rel="stylesheet" type="text/css"/>

</head>
<body>
 <div id="title" style="background: url(../resources/images/BG_T_TD.gif)">
			<div style="font-weight:bold;color:white;height: 24px;line-height: 24px;">
				文件上传
				<font color="yellow"></font>
			</div>
</div>
<iframe name="ifr" id="ifr" style="display: none">
</iframe>
<div id="search" style="padding:  0.5em 0.5em 0em 0.5em;">
		<form method="post" id="frm" action="../multiUpload.htm" enctype="multipart/form-data" target="ifr">
		 <span>请选择文件:</span>
			 <div>
                  <a href="javascript:void(0);" class="a2">选择上传</a>
                  <input type="file"  name="file" class="file" onchange="doUpload()"  />
             </div>
            <!-- <input type="text" name="name"/> -->
           <!--  <input type="file" name="file"/><br/>
            <input type="file" name="file"/> -->
            <!-- <input type="submit" value="上传"/> -->
        </form>
        <div id="showFileArea"></div>
</div>

</body>
<script type="text/javascript">
	function doUpload(){
		$("#frm").get(0).submit();
	} 
</script>
</html>