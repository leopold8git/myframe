<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>登录</title>
<link rel="stylesheet" type="text/css" href="../resources/css/login.css" />
<script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	function login(){
		$("#frm").submit();
	}
</script>
</head>
<body onload="document.getElementById('userId').focus()">
<div  class="loginBody">
 <!--Container-->
   <div class="container">
	   <div class="login_box">
	   <form id="frm" name="frm" action="../login.htm" method="get">
         <span class="login_form">
         	<ul>
               <li class="username">
                 <label>用户名：</label>
                 <input type="text" class="text" value="" id="userId" name="userId" /><span class="error_txt" id="errorUser" style="display: none"> 不存在的用户</span>
               </li>
               <li class="password">
                 <label>密　码：</label>
                 <input type="password" class="text"  id="userPassword" name="userPassword"  value=""/><span class="error_txt"  id="errorPwd" style="display: none">密码错误</span>
               </li>
               <li class="vcode">
                 <label>验证码：</label>
                  <input maxlength="4" type="text" class="ctext" name="checkCode" id="checkCode" value="" />
			 	  <img id="img" src="common/checkCode.jsp?t=<%=Math.random()%>" width="53" height="21"  onclick="changeImg()"/>
				  <span class="error_txt" style="display: none" id="errorCheck">验证码错误</span>
               </li>
               <li class="opert">
                  <input type="button" onclick="login()" value="登 录" class="submit" />
               </li>
             </ul>
         </span>
         </form>
	   </div>
    </div>
</div>

</body>

</html>
