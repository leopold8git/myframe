<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title!''}</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/ajaxcore.js"></script>
<script type="text/javascript" src="js/string.prototype.js"></script>
<script type="text/javascript" src="resources/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="resources/artDialog/iframeTools.source.js"></script>
<script type="text/javascript" src="resources/artDialog/jquery.artDialog.extend.js"></script>
<#if jsLink??>
	<#list jsLink as js>
        <script type="text/javascript" src="${js}"></script>
	</#list>
</#if>
<link href="resources/main.css" rel="stylesheet" type="text/css"/>
<link href="resources/artDialog/skins/blue.css" rel="stylesheet" type="text/css"/>
<#if styleLink??>
	<#list styleLink as link>
        <link href="${link}" rel="stylesheet" type="text/css"/>
	</#list>
</#if>
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
<#if style??>
${style}
</#if>
</style>
</head>
<body>
 <div id="bodyDiv" style="display: block;width:100%;height:auto;">
		<form method="post" id="frm" name="frm" class="edit-form" json="${formJson}">
				<div id="tc">
				<table border="0" cellpadding="2" cellspacing="3" width="100%">
				<#if elements??>
					<#list elements as elem>
                        <#if elem_index % 2 == 0>
                            <tr>
                                <td class="right">${elem}</td>
                        </#if>
                        <#if elem_index % 2 != 0>
                            <td class="left">${elem}</td>
                            </tr>
                        </#if>
					</#list>
				</#if>

				</table>
				</div>
					</form>
</div>
</body>
<script type="text/javascript">
<#if js??>
${js}
</#if>
</script>
</html>