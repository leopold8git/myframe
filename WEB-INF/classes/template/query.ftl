<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="js/ajaxcore.js"></script>
<script type="text/javascript" src="js/string.prototype.js"></script>
<script type="text/javascript" src="resources/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="resources/artDialog/iframeTools.source.js"></script>
<script type="text/javascript" src="resources/artDialog/jquery.artDialog.extend.js"></script>
<script type="text/javascript" src="resources/My97DatePicker/WdatePicker.js"></script>
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
<#if style??>
${style}
</#if>
</style>
</head>
<body>

<div id="title" style="background: url(resources/images/BG_T_TD.gif)">
			<div style="font-weight:bold;color:white;height: 24px;line-height: 24px;">
				${qns}
				<font color="yellow"></font>
			</div>
		</div>
<div id="search" style="padding:  0.5em 0.5em 0em 0.5em;">
	<form id="frm" name="frm" json="${formJson}">
	 <div class="inquire">
		 <#if elements??>
			 <#list elements as ele>
                 <th>${ele }</th>
			 </#list>
		 </#if>

        </div> 
        
	</form>
        <div>
               <table width="100%"   class="tablebox" border="1">
				<thead>
			<tr>
				<#if headers??>
					<#list headers as th>
                        <th>${th }</th>
					</#list>
				</#if>
		</tr>
		</thead>
			<tbody id="data">		
			<tr style="display:none;">
				<#if tds??>
					<#list tds as td>
						${td }
					</#list>
				</#if>
			 </tr>
		</tbody>
		</table>
		</div>
		<div id="page" align="right"> </div>
	<#if bottomElements??>
		 <#list bottomElements as belem>
				${belem } 
		 </#list>
	</#if>
		<!-- <input style="cursor: pointer;" type="button" onclick="add()" value="Ìí¼Ó" class="bt_add" /> -->
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
<#if js??>
	${js}
</#if>
</script>
</html>