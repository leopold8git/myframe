<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*"%>
<% 
String funcTitle[];
String funcVerb[];

funcTitle=new String[]{"品牌详情","商家详情","产品详情"};
funcVerb=new String[]{"../../../index.htm","../../viewAllSellers.jsp","../../viewAllProducts.jsp"};	
%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="-1">
		<link href="../css/currency.css" rel="stylesheet" type="text/css">
		<link type="text/css" rel="stylesheet" href="../css/tree.css">
		<script language="JavaScript">
	linkPage="<%=funcVerb[0]%>";
	
	function doAction(curr,URL)
	{
		var objAll=document.getElementsByName("node");
		if(objAll!=null)
		{
			for(var i=0;i<objAll.length;i++)
				objAll[i].className="NormalNode";
			objAll[curr].className="SelectedNode";
		}
		parent.content.location=URL;
	}
</script>
	</head>

	<body bgcolor="#529AE7" leftmargin="5" topmargin="5">

		<form name="tt">
			<div>
				<table width="165" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="27" valign="bottom" background="../images/BG_Menu.gif"
							style="background-repeat: no-repeat; padding-left: 9px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="21%">
										<a href="../index.jsp" target="_parent"><img
												src="../images/home.gif" width="24" border=0>
											</a>
									</td>
									<td width="80%">查询中心&nbsp;</td>
									
								</tr>
							
							</table>
						</td>
					</tr>
				
				</table>
			</div>
			<DIV class=rootmenu>
				<table width="100" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td style="border: 0px solid #79A6D4;">
							<%
								for (int i = 0; i < funcTitle.length; i++) {
							%>
							<DIV id="Menu<%=i + 1%>">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									style="border-top: 1px solid #529AE7;">
									<tr>
										<td height="27" background="../images/BG_Menu.gif"
											style="background-repeat: no-repeat; padding-left: 10px; padding-right: 2px;">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr style="cursor:pointer;">
													<td width="23%">
														<img src="../images/Icon_TM_01.gif" width="25" height="25">
													</td>
													<td name="node" id="node"
														onclick="linkPage='<%=funcVerb[i]%>';doAction(<%=i%>,'<%=funcVerb[i]%>')"
														width="62%" valign="bottom"
														class='<%=i > 0 ? "NormalNode" : "SelectedNode"%>'><%=funcTitle[i]%></td>
													<td width="15%" align="right" valign="middle"
														style="color: #2159C6; padding-bottom: 2px;"></td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</DIV>
							<%
								}
							%>
							
							<DIV id=MenuTree>
								<table width="100%" border="0">
									<tr>
										<td valign="top" style="padding-right: 8px;" bgcolor="#ECF5FF">
											<div id="pid" style="padding-left: 4px; width: 0; height: 0"></div>
										</td>
									</tr>
								</table>
							</DIV>
				
					
				</table>
			</DIV>
		</form>

	</body>
</html>