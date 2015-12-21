<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>后台首页</title>
<style type="text/css">
#header {
background : url("../resources/images/headBg_02.gif") repeat scroll 0 0 rgba(0, 0, 0, 0);
width : 100%;
height : 67px;
color : white;
}
#header div{
position :absolute;
left : 30px;
top : 20px;
}
#menu{
		background: url("../resources/images/rootMenuBackground.gif") repeat scroll 0 0 rgba(0, 0, 0, 0);
		border: 0 solid blue;
		font-size: 9pt;
		height: 29px;
		line-height: 22px;
		list-style: outside none none;
		margin: 0;
		padding: 0;
		width: 100%;
	}
#menu li{
	float : left;
}

#menu a {
  border-right: 0 solid #ccc;
  color: white;
  display: block;
  line-height: 29px;
  text-align: center;
  text-decoration: none;
  text-overflow: ellipsis;
  width: 100px;
}
.rootmenuA {
  background: url("../resources/images/rootSpacer.gif") no-repeat scroll right 50% rgba(0, 0, 0, 0);
  border-right: 0 solid #ccc;
  color: white;
  display: block;
  height: 29px;
  text-align: center;
  text-decoration: none;
  text-overflow: ellipsis;
  vertical-align: middle;
  width: 100px;
}

#menu li ul {
/*
  background: url("../resources/images/menuBackground.gif") repeat scroll center bottom rgba(0, 0, 0, 0);
*/
  border-color: #93bede #5292c1 #5292c1 #93bede;
  border-style: solid;
  border-width: 0 2px 2px 1px;
  font-weight: normal;
  left: -9999em;
  margin: 0 0 0 -1px;
  padding: 0 0 12px;
  position: absolute;
  width: 120px;
}
#menu li li {
/*
  background: none repeat scroll 0 0 #edf8ff;
 */
  width: 100px;
 
}

#menu >li.rootmenuitemhover {
  background: url("../resources/images/rootMenuBackground1.gif") no-repeat scroll center center rgba(0, 0, 0, 0);
}
#workArea{
margin-top : 10px;
	
}
</style>
<link href="../resources/jqueryui/jquery-ui.css" rel="stylesheet">
<script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="../js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="../resources/jqueryui/jquery-ui.js"></script>
</head>
<body>
<div id="header">
	<div >Easy Web ,你的管理专家！</div>
</div>
<div>
	<ul id="menu">
		<li  class="">
		<a href="javascript:void(0);" class="rootmenuA">我的账户</a>
		</li>
		<li  class="">
		<a href="javascript:void(0);" class="rootmenuA">系统设置</a>
			<ul class="submenu">
				<li url="../viewAllUsers.htm">账户管理</li>
				<li url="../viewAllRoles.htm">角色管理</li>
				<li url="../viewAllResources.htm">资源管理</li>
				<li url="../viewAllOperations.htm">操作管理</li>
			</ul>
		</li>	
		<li  class="">
		<a href="javascript:void(0);" class="rootmenuA">自助转账</a>
		</li>		
	</ul>
</div>
<div id="workArea">
  <iframe src="../viewAllUsers.htm?debug=1"  frameborder="0" width="100%" onload="window.frames['mainWorkArea'].document.onclick = function(){$(document).click()}" marginheight="0" marginwidth="0" name="mainWorkArea" id="mainWorkArea"  style="height:400px;">
  </iframe>
</div>
</body>
<script type="text/javascript">

$("#menu> li").hover(function(){
	$(this).addClass("rootmenuitemhover");
},function(){
	$(this).removeClass("rootmenuitemhover");
}).click(function(e){
	$(this).children("ul").css("left",100*$(this).index());
	$(".submenu").show();
	//this is very important
	e.stopPropagation();
});

//点击其它隐藏

$(document).bind("click",function(eventObj){
	 $("#menu li").children("ul").css("left","-9999em");
});


var menuWidget = $(".submenu").menu({
	select: function( event, ui ) {
		//ui.item represent the active li 
		toPage($(ui.item).attr("url"));
		$(".submenu").hide();
		event.stopPropagation();
	}
});

function toPage(url){
	if(url)
	$("#mainWorkArea").attr("src",url);
}

</script>

</html>
