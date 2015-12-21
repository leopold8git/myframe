var linkPage="",oDiv,xmlhttp,np;
if (window.attachEvent) window.attachEvent('onload',tree_init);
if (window.addEventListener) window.addEventListener('load',tree_init,false);
function trim(str){
	if(str==undefined){ return ''; }
	str=str.replace(/(^\s*|\s*$)/g,'');
	str=str.replace(/(\r*\n){3,}/g,'\n\n');
	str=str.replace(/\r/g,'');
	return str;
}
function onMouseOver(obj)
{
	if(obj.className=="clsLabel")
		obj.className="clsMouseOver";
	else if(obj.className=="clsLabel1")
		obj.className="clsMouseOver1";
}

function onMouseOut(obj)
{
   	if(obj.className=="clsMouseOver")
		obj.className="clsLabel";
	else if(obj.className=="clsMouseOver1")
		obj.className="clsLabel1";
}

function onMouseDown(obj,hh)
{
	if(hh=="" || linkPage=="") return;
	var nodes=document.getElementById('pid').getElementsByTagName("SPAN");
	for(i=0;i<nodes.length;i++)	
	{  
		if(nodes[i].className=="clsMouseDown") 
			nodes[i].className="clsLabel";	
		if(nodes[i].className=="clsMouseDown1") 
    		nodes[i].className="clsLabel1";	
	}
    if (obj.className=="clsMouseOver")
		obj.className="clsMouseDown";
	else
		obj.className="clsMouseDown1";
    
	if(linkPage.indexOf("?")<0)
		window.open(linkPage+"?"+hh,'content');
	else    
		window.open(linkPage+"&"+hh,'content');
}

function goDirect()
{
	var obj=document.getElementById("idDirect");
	if(obj.value=="")
	{
		alert("请输入门店名称!");
		obj.focus();
		return;
	}
	getXmlHttp();
	if(!xmlhttp) return false;
	xmlhttp.open("get","../tree/netbarid.jsp?netBarCaption="+obj.value,false);
	xmlhttp.send();
	var rc=xmlhttp.responseText;
	if(xmlhttp.status==200 && rc.indexOf("&netBarName=")>0)
	{
		rc=rc.replace(/\r/g,'');
		rc=rc.replace(/\n/g,'');
		/*
		if(linkPage.indexOf("?")<0)
			window.open(linkPage+"?"+rc,'content');
		else    
			window.open(linkPage+"&"+rc,'content');
		*/
		var params = rc.split("&");
		if(params.length>4){
			var netBarNameParams = params[2];
			var netBarIdParams = params[1];
			var netBarIdTempArr = netBarIdParams.split("=");
			var netBarId = "";
			if(netBarIdTempArr.length>1){
				netBarId = netBarIdTempArr[1];
			}
			var domainIdParams = params[4];
			var domainIdtempArr = domainIdParams.split("=");
			var domainId="";
			if(domainIdtempArr.length>1){
				domainId = domainIdtempArr[1];
			}else{
				domainId =  0;
			}
			var tempArr = netBarNameParams.split("=");
			if(tempArr.length>1){
				var netBarName = tempArr[1];
				//parent.child.grantchild
				var treeArr = netBarName.split(".");
				//展开树
				for(var i=0;i<treeArr.length-1;i++){
					var collapseSpanId="spid"+domainId+"_";
					
					for(var j=0;j<=i;j++){
						if(j == i){
							collapseSpanId+= treeArr[j];
						}else{
							collapseSpanId+= treeArr[j]+".";
						}
					}
					var collapseSpan = document.getElementById(collapseSpanId);
					justShow(collapseSpan,collapseSpanId.substring(1),domainId);
				}
				//点击
				var spanObj = document.getElementById(domainId+"_"+netBarName);
				if(spanObj != null){
					onMouseDown(spanObj,rc);
				}
			}else{
				alert("["+obj.value+"]未找到!");
				obj.focus();
			}
		}
		
	}
	else
	{
		alert("["+obj.value+"]未找到!");
		obj.focus();
	}
}

function isAgent()
{
	getXmlHttp();
	if(!xmlhttp) return false;
	xmlhttp.open("GET","../common/isagent.jsp",false);
	xmlhttp.send();
	var rc=xmlhttp.responseText;
	if(xmlhttp.status==200)
	{
		rc=rc.replace(/\r/g,'');
		rc=rc.replace(/\n/g,'');
		if(rc=="true")
		{ 
			return true;
		}
	}
	return false;
}

function tree_init() 
{
	readXML('',0);
}

function readXML(id,domainId) 
{	
	if(id=="")
	{
		oDiv = document.createElement("DIV");
		nP = parseInt(document.getElementById("pid"+id).style.paddingLeft);
		oDiv.innerHTML = "<div style='padding-left:"+nP+"px;'><nobr><input style='border:1px solid #529AE7; BACKGROUND: #ffffff; font-size:12px' type=text id='idDirect' size=14 onkeypress='if(event.keyCode==13) { goDirect();return false;}'><input type='button' style='border:1px solid #71A6DC;font-size:11px;background-color: #CBDCEF' value='GO' onclick='javascript:goDirect()'><span style=\"background-image:url(../images/tree/collapse_all.gif);background-repeat:no-repeat;background-position:top;width:20px;height:18px;\" onclick='collapseAll()'></span></nobr></div>";		
		document.getElementById("pid"+id).appendChild(oDiv);
	}
	oDiv = document.createElement("DIV");
	nP = parseInt(document.getElementById("pid"+id).style.paddingLeft);
	oDiv.innerHTML = "<div style='display:;padding-left:"+nP+"px'><span class='container'><span class='clsLeaf'>.</span></span> " + "<span class='clsNotReady' style='color:#000000;'>请稍侯.......</span></div>"
	oDiv.style.paddingLeft= nP + "px";
	document.getElementById("pid"+id).appendChild(oDiv);
	getXmlHttp();
	if(!xmlhttp) return;
	xmlhttp.onreadystatechange=getReady;
	xmlhttp.open("GET","tree2.jsp?parentid="+id+"&domainId="+domainId,false);
	xmlhttp.send();
}

function getReady() 
{
    if(xmlhttp.readyState==4 && xmlhttp.status==200)
	{
		var xmldom=null;
		if (window.ActiveXObject)
	  	{
	  		xmldom=new ActiveXObject("Microsoft.XMLDOM");
	  		xmldom.async=false;
	  		xmldom.loadXML(trim(xmlhttp.responseText));
	  	}
		else if (document.implementation &&	document.implementation.createDocument)
		{
			xmldom=new DOMParser().parseFromString(trim(xmlhttp.responseText), "text/xml");
		}
		if(xmldom==null || xmldom.documentElement.tagName!="root") 
		{
			oDiv.innerHTML="<div style='display:;padding-left:"+nP+"px'><span class='container'><span class='clsLeaf'>.</span></span> " + "<span class='clsNotReady'>1抱歉，装载数据失败。原因：返回的数据不是一个XML结构的文档1。</span></div>";
			return;
		}
		
		var nodes=xmldom.documentElement.childNodes;
		var strHtml = "";	
		var linkUrl="";
		var strcls="";
		var strimage="";
		var imagestyle="";
		var nodeId="";
		var nodeName="";
		var nodeCaption="";
		var nodePrivilege="";
		var codeNum="";
		var domainId="";
		var netBarName="";
		var nodeShow = "";
		var parentNetBar="";
		for(var i=0;i<nodes.length;i++) 
		{
			nodeName=getXmlNodeValue(nodes[i],"id");
			nodeCaption=getXmlNodeValue(nodes[i],"message");
			nodeId=getXmlNodeValue(nodes[i],"netBarId");
			netBarName=getXmlNodeValue(nodes[i],"netBarName");
			nodePrivilege=getXmlNodeValue(nodes[i],"Privilege");
			codeNum=getXmlNodeValue(nodes[i],"codeNum");
			domainId = getXmlNodeValue(nodes[i],"domainId");
			parentNetBar = getXmlNodeValue(nodes[i],"parentNetBar");
			nodeShow = domainId+"_"+netBarName;
			if(nodePrivilege=='0')
			{
				imagestyle="style='filter:Gray'";
 				linkUrl="";
			}
			else
			{
				imagestyle="";
 				linkUrl="pageName="+nodeName+"&netBarId="+nodeId+"&netBarName="+netBarName+"&nodeCaption="+nodeCaption+"&codeNum="+codeNum+"&domainId="+domainId+"&parentNetBar="+parentNetBar;
			}
 			if (netBarName.indexOf(".")<0)
				strimage="<img src='../css/subjectmain.gif' width='13' height='17' "+ imagestyle +">";
 			else if (getXmlNodeValue(nodes[i],"issubject")!='1')
 				strimage="<img src='../css/subject.gif' width='13' height='17' "+ imagestyle +">";
 			else 
 				strimage="<img src='../css/page.gif' width='13' height='18'  "+ imagestyle +">";
			if(nodePrivilege=='0') 
				strcls="clsLabel3";
			else if(getXmlNodeValue(nodes[i],"issubject")=='1')
 				strcls="clsLabel";
 			else
				strcls="clsLabel1";
			if(getXmlNodeValue(nodes[i],"childcount")!='0') 
				strHtml+="<div style='display:;padding-left:"+nP+"px' id='pid"+nodeShow+"'><nobr><span class='container'><span class='clsCollapse' status='' id='spid"+nodeShow+"' onclick='hideshow(this,\"pid"+nodeShow+"\","+domainId+")'>+</span></span> ";
			else 
				strHtml+="<div style='display:;padding-left:"+nP+"px' id='pid"+nodeShow+"'><nobr><span class='container'><span class='clsLeaf' onclick='hideshow(this,\"pid"+nodeShow+"\","+domainId+")'>.</span></span> "
            strHtml+=strimage;    
			strHtml+="<span onclick=\"onMouseDown(this,'"+linkUrl+"')\" onmouseover=\"onMouseOver(this)\" onmouseout=\"onMouseOut(this)\" class='"+strcls+"' id='"+nodeShow+"'>"+nodeCaption+"</span></nobr></div>";
		}
		oDiv.innerHTML = strHtml;
		/*
		if(nodes.length>0)
		{   
			var id0="pid"+getXmlNodeValue(nodes[0],"id");
			var domainId0 = getXmlNodeValue(nodes[0],"domainId");
			var o=document.getElementById(id0).firstChild.firstChild.firstChild;
			if(id0.indexOf(".")<0 || getXmlNodeValue(nodes[0],"Privilege")=="0") hideshow(o,id0,domainId0);
		}
		*/
	} 
}

function hideshow(o,oId,domainId)
{
	var subjectid = oId.substr(3,oId.length)
	if(o.innerText=='.') return;
	if (!o.status)
	{
		readXML(subjectid,domainId)
		o.innerText="-";
		o.status="old";
		return;
	}
	var childNodes=document.getElementById(oId).getElementsByTagName("DIV");
	if(childNodes.length==0) return;
	var oChild=childNodes[0];
	if(oChild.style.display=="")
	{
		o.innerText="+";
		oChild.style.display="none";
	}
	else
	{
		o.innerText="-";
		oChild.style.display="";
	}
	event.returnValue=false;
	return false;
}

function justShow(o,oId,domainId)
{
	var subjectid = oId.substr(3,oId.length)
	if(o.innerText=='.') return;
	if (!o.status)
	{
		readXML(subjectid,domainId)
		o.innerText="-";
		o.status="old";
		return;
	}
	var childNodes=document.getElementById(oId).getElementsByTagName("DIV");
	if(childNodes.length==0) return;
	var oChild=childNodes[0];
	o.innerText="-";
	oChild.style.display="";
	event.returnValue=false;
	return false;
}
//折叠所有depends on jquery.js
function collapseAll(){
	jQuery(".container").each(function(index,element){
		var t = $.trim($(this).text());
		if(t == '-'){
			$(this).children().click();
		}
	});
}

function getXmlHttp()
{
  try { xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); }
  catch (e) { try { xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); }
  catch (e) { try { xmlhttp = new XMLHttpRequest(); }
  catch (e) { xmlhttp = null; }}}
}

function getXmlNodeValue(node,tagName)
{
		var retValue="";
	try{
		retValue=node.getElementsByTagName(tagName)[0].firstChild.nodeValue;
	}catch(e){}
	if(retValue==null){
		retValue="";
	}
	return retValue;
}