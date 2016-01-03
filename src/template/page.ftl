<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title!'' }</title>
    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="js/jquery.json-2.4.js"></script>
    <script type="text/javascript" src="js/ajaxcore.js"></script>
    <script type="text/javascript" src="js/string.prototype.js"></script>
    <script type="text/javascript" src="resources/artDialog/jquery.artDialog.js"></script>
    <script type="text/javascript" src="resources/artDialog/iframeTools.source.js"></script>
    <link href="resources/artDialog/skins/blue.css" rel="stylesheet" type="text/css"/>
	<#if jsLink??>
		<#list jsLink as js>
			<script type="text/javascript" src="${js}"></script>
		</#list>
	</#if>

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
        }


		<#if style??>
			${style}
		</#if>
    </style>
</head>
<body>

</body>
<script type="text/javascript">

    <#if js??>
		${js}
	</#if>
</script>
</html>