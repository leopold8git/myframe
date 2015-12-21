var ajaxCoreBaseUrl;
	var scripts = document.getElementsByTagName("script");
	for(var i=0;i<scripts.length;i++){
	if(/.*\/ajaxcore\.js$/g.test(scripts[i].getAttribute("src")))
		ajaxCoreBaseUrl=scripts[i].getAttribute("src");
		
	}
	
	
	var agt =   window.navigator.userAgent;
	var isIE = agt.toLowerCase().indexOf("msie") != -1;
	var isGecko = agt.toLowerCase().indexOf("gecko") != -1;
	var isFirefox= agt.toLowerCase().indexOf("firefox") != -1;
	var ieVer = isIE ? parseInt(agt.split(";")[1].replace(/(^\s*)|(\s*$)/g,"").split(" ")[1]) : 0;
	var isIE8 = !!window.XDomainRequest && !!document.documentMode;
	var isIE7 = ieVer==7 && !isIE8;
	var ielt7 = isIE && ieVer<7;
	var pos=ajaxCoreBaseUrl.lastIndexOf("/");
	if(pos>=0) ajaxCoreBaseUrl = ajaxCoreBaseUrl.substring(0,pos)+"/"; else ajaxCoreBaseUrl="";	
	
	var topDoc = window.document;
	var getDom = function (id) {
	    return typeof id == "string" ? document.getElementById(id) : id;
	}	
/**
 * depends on jQuery
 * @returns
 */
 function AjaxCore(){
	 var self = this;
	 this.options = {};
	 this.setSelect = function(obj,cbFun){
		 var url=jQuery(obj).attr('dataUrl');
		 var completeCallback= jQuery(obj).attr('callback');
		
		 if(!url)return;
		 jQuery.get(url,function(data){
			 var result=[];
			 if( ! obj.oldLen){
		         obj.oldLen = 	obj.options.length;
		         if(obj.options.length == 0)return;         
		         var op = obj.options[obj.oldLen - 1];
		         if(!op)return;
		         obj.valuefld = op.value;
		         if(op.id)obj.id = op.id;
		         if(op.getAttribute("namefld"))obj.namefld = op.getAttribute("namefld");
		         if(op.getAttribute("valuefld"))obj.valuefld = op.getAttribute("valuefld");
		      }

		      obj.options.length = obj.oldLen - 1;
		      if( ! data)return;
		      if(!jQuery.isArray(data) && jQuery.isArray(data.res)){
		    	  result = data.res;
		      }else if(jQuery.isArray(data)){
		    	  result = data;
		      }
		      for(var i = 0; i < result.length; i ++ )
		      {
		        // var value = result[i][obj.valuefld.toLowerCase()];
		         //var name = result[i][obj.namefld.toLowerCase()];
		    	 var value = result[i][obj.valuefld];
			     var name = result[i][obj.namefld];
		         var opt = new Option(name, value);
		         if(obj.jvalue && obj.jvalue == value)opt.selected = true;
		         if(obj.id)opt.id = replacehref(obj.id, result[i], i);
		         obj.options[obj.oldLen - 1 + i] = opt;
		      }
		      //回调函数
		      if(cbFun){
		    	  cbFun(obj);
		      }else{
		    	  if(completeCallback){
				    	 var cb = eval(completeCallback);
				    	 cb(obj);
				      }
		      }
		     
		 });
	   }
	 
	 this.setSingle = function (oTable, value){
	      for(var i = 0; i < oTable.childNodes.length; i ++ ){
	         this.replaceCell(oTable.childNodes[i], value);
	         this.replacefld(oTable.childNodes[i], value);
	      }

    }
	 
	 
	 this.doSubmit = function(frm,  listDiv, pageDiv,callback,completeFun){   
		 if(listDiv.isQuery=="1")return;   
		 listDiv.isQuery="1";   
	   	 this.processStart(listDiv,"<nobr>&nbsp;&nbsp;查询中,请稍候...</nobr>");
	      var jsrc = this.buildQueryString(frm);   
	      var name="res";
	      var newJsrc=jsrc;
	      this.query(newJsrc, this.json_callback, name, listDiv, pageDiv, jsrc,callback,completeFun);
	   }
	 
	 this.getFrm = function(frm,data,callback,type){
		 var url = this.buildQueryString(frm);
		 jQuery.get(url,data,callback,type);
		 
	 }
	 
	 this.postFrm = function(frm,data,callback,type){
		 var url = this.buildQueryString(frm);
		 jQuery.post(url,data,callback,type);
		 
	 }
	 
	 /**
	  * url : the url visit to get data
	  * frm : the obj to fill with the data
	  * name : the resultName 
	  */
	 this.fillFrm=function (frm,url,data,callback,type){
		 var self = this;
		 if(!callback) callback = function(j){
			 if(j && j["res"]){
				 self.setSingle(frm, j["res"][0]);
			 }
		 } 
		 jQuery.get(url,data,callback,type);
	   }
	 
	 this.query = function(newJsrc, json_callback, name, listDiv, pageDiv, jsrc,callback,completeFun,options){
		 if(options){
			 if(!options.success){
				 options.success = function(j){
					 json_callback(j, name, listDiv, pageDiv, jsrc,callback,completeFun);
				 };
			 }
			 this.options = options;
			 jQuery.ajax(options); 
		 }else{
			 jQuery.ajax({
				 url : newJsrc,
				 success  : function(j){
					 json_callback(j, name, listDiv, pageDiv, jsrc,callback,completeFun);
				 }
			 }); 
		 }
		 
	 }
	 
	 this.exportExcel = function(url,frm){
		 var self = this;
		 if(frm){
			 var frmUrl = this.buildQueryString(frm); 
			  if(frmUrl != null && frmUrl.indexOf('?')>-1){
				  url += frmUrl.substring(frmUrl.indexOf('?'));
			  }
		 }
		  /**
		   * 不能用ajax去导出excel，因为ajax后台要开启PrintWrite，这样不能再获取outputStream了
		   */
		 self.process("正在导出，请稍等...");
		 var ifr = document.createElement("iframe");
		 ifr.style.display = 'none';
		 // location.href = url;
		 ifr.src=url;
		  if (ifr.attachEvent){//IE
			  /*
			  ifr.attachEvent("onload", function(){
				  self.processEnd();
		      });
		      */
			  ifr.onreadystatechange = function(){
				  //alert(ifr.readyState )
				  if(ifr.readyState == 'interactive'){
					  self.processEnd();
				  }
			  }
		  } else {//else
			  ifr.onload = function(){
				  self.processEnd();
		      }
		  }
		  
		 
		  document.body.appendChild(ifr);
		  //this.processEnd();
	 }
	 this.processStart = function(oTable, msg) {
			var that = this;
			this.timer = setTimeout(function() {
				that.process(msg);
			}, 1000);
	 }
	   
    this.process=function(msg){
			var div=getBgdiv();
			div.style.display="block";
			 var topWinBody = topDoc.getElementsByTagName(topDoc.compatMode == "BackCompat" ? "BODY" : "HTML")[0];
	       topWinBody.styleOverflow = topWinBody.style.overflow;
			if(window.navigator.userAgent.indexOf("Firefox/3.6") != -1){//在firefox下改变overflow属性会导致scrollTop=0;
				var topWinBodyScrollTop=topWinBody.scrollTop;
		        topWinBody.style.overflow = "hidden";
				topWinBody.scrollTop=topWinBodyScrollTop;
			}else{
	       	topWinBody.style.overflow = "hidden";
	       }		
	      this.showText(msg);	
	}
	this.processEnd=function(){
		clearTimeout(this.timer);
		var textDiv = window.getDom("_DialogBGDivText");
		if(!textDiv)return;
		textDiv.style.display="none";
		var div=getBgdiv();
			div.style.display="none";
	       var topWinBody = topDoc.getElementsByTagName(topDoc.compatMode == "BackCompat" ? "BODY" : "HTML")[0];
	       if(topWinBody.styleOverflow != undefined)
	   		if(window.navigator.userAgent.indexOf("Firefox/3.6") != -1){//在firefox下改变overflow属性会导致scrollTop=0;
	   			var topWinBodyScrollTop=topWinBody.scrollTop;
			        topWinBody.style.overflow = topWinBody.styleOverflow;
	   			topWinBody.scrollTop=topWinBodyScrollTop;
	   		}else{
		        	topWinBody.style.overflow = topWinBody.styleOverflow;
		        }			
	}  
	this.showHtml=function(text,height){
	    var bgdiv = window.getDom("_DialogBGDivText");
	    if (!bgdiv) {
	        bgdiv = topDoc.createElement("div");
	        bgdiv.id = "_DialogBGDivText";
	        var iWidth = document.documentElement.clientWidth; 
	 				var iHeight = document.documentElement.clientHeight;
	 		if(!height)height=0;		
	        bgdiv.style.cssText = "position:absolute;height:"+height+"px;left:"+(iWidth-200)/2+"px;top:"+(iHeight-height)/2+"px;z-index:9999;text-align:center;background:white";
	    		topDoc.getElementsByTagName("BODY")[0].appendChild(bgdiv);
	      }
			bgdiv.innerHTML=text;

			bgdiv.style.display="block";
					
		}
	this.showText=function(text){
		this.showHtml("<table><tr><td><img src='"+ajaxCoreBaseUrl+"img/loading.gif'></td><td nowrap>"+text+"</td></tr></table>",40);
	}


	this.buildQueryString = function(theForm)
	{
	   var url = "";
	   if(theForm.getAttribute("json")) url = theForm.getAttribute("json");
	   if(theForm.json) url = theForm.json;

	   for(var i = 0; i < theForm.elements.length; i++ )
	   {
	      if(theForm.elements[i].name == "" || theForm.elements[i].name == undefined)continue;

	      if(theForm.elements[i].type == "checkbox")
	      {
	         var value0 = theForm.elements[i].getAttribute("value0");
	         if(value0 == null)value0=0;
	         if( ! theForm.elements[i].checked && typeof(value0) == 'undefined')continue;
	         if(theForm.elements[i].value == "")continue;
	         if(url.indexOf("?") >= 0)url += "&";
	         else url += "?";
	         url +=  encodeURIComponent(theForm.elements[i].name);
	         url += "=";
	         if(theForm.elements[i].checked)url +=  encodeURIComponent(theForm.elements[i].value);
	         else url +=  encodeURIComponent(value0);

	      }
	      else if(theForm.elements[i].type == "radio")
	      {

	         if(theForm.elements[i].checked)
	         {
	            if(url.indexOf("?") >= 0)url += "&";
	            else url += "?";
	            url +=  encodeURIComponent(theForm.elements[i].name);
	            url += "=";
	            url +=  encodeURIComponent(theForm.elements[i].value);
	         }
	      }
	      else
	      {
	         if(url.indexOf("?") >= 0)url += "&";
	         else url += "?";
	         url +=  encodeURIComponent(theForm.elements[i].name);
	         url += "=";
	         var value = jQuery.trim(theForm.elements[i].value);
	         url +=  encodeURIComponent(value);
	      }

	   }
	   if(url.indexOf("?") >= 0){
		   url = url +"&ajax_core_time_random="+Math.random();
	   }else{
		   url = url+"?ajax_core_time_random="+Math.random();
	   }
	   
	   return url;
	}


	this.json_callback = function(j, name, listDiv, pageDiv, jsrc,callback,completeFun)
	{
	  listDiv.isQuery="0";	
	  //可以利用结果自行处理，首先执行，因为可能会对j 改变
	  if(callback){
		  cj = callback(j);
		  if(cj){//如果有返回值，则返回值为新的结果集
			  j = cj;
		  }
	  }
	  self.setList(listDiv, j, name);
	   new PageCore().set(j, name, listDiv, pageDiv, jsrc);
	  if(completeFun){
		  completeFun(j);
	  }
	 }
	 

	this.setList = function (oTable, j, name)
	{
		this.processEnd();
	     if(!name)name="res";
		   	if(oTable.isGrid){
		   		this.setGrid(oTable,j[name],oTable.defaultData);
		   		return;
		   	}
		  if(!oTable)return;
		  if(!oTable.childNodes)return;
	   if(oTable.childNodes.length == 0)return;
	 
	   this.node = oTable.childNodes[0];
	   if(this.node.style)this.node.style.display = "none";
	   for(var i = oTable.childNodes.length - 1; i >= 1;
	   i -- )
	   {
	      if(oTable.childNodes[i].ajaxCreate)
	      {
	         oTable.removeChild(oTable.childNodes[i]);
	      }
	   }
	   if( ! j)return;
	   var result = j[name];
	   if( ! result){    
	   message=j.message?j.message:"抱歉，查询结果无数据！";
	   this.appendNodeMessage(oTable,"<nobr>&nbsp;&nbsp;"+message+"</nobr>");
	   return;}
	   var nCount = oTable.childNodes.length;
	   for(var i = 0; i < result.length; i ++ )
	   {
	   	
	   	this.appendNode(oTable,result,i);
	     
	   }


	}
	
	var bgDiv;
	var getBgdiv = function () {
	    if (bgDiv) return bgDiv;
	    var bgdiv = window.getDom("_DialogBGDiv");
	    if (!bgdiv) {
	        bgdiv = topDoc.createElement("div");
	        bgdiv.id = "_DialogBGDiv";
	        bgdiv.style.cssText = "position:absolute;left:0px;top:0px;width:100%;height:100%;z-index:999999";
	        var bgIframeBox = '<div style="position:relative;width:100%;height:100%;">';
			var bgIframeMask = '<div id="_DialogBGMask" style="position:absolute;background-color:#333;opacity:0.4;filter:alpha(opacity=40);width:100%;height:100%;"></div>';
			var bgIframe = ielt7?'<iframe src="about:blank" style="filter:alpha(opacity=0);" width="100%" height="100%"></iframe>':'';
			bgdiv.innerHTML=bgIframeBox+bgIframeMask+bgIframe+'</div>';
	        topDoc.getElementsByTagName("BODY")[0].appendChild(bgdiv);
	        if (ielt7) {
	            var bgIframeDoc = bgdiv.getElementsByTagName("IFRAME")[0].contentWindow.document;
	            bgIframeDoc.open();
	            bgIframeDoc.write("<body style='background-color:#333' oncontextmenu='return false;'></body>");
	            bgIframeDoc.close();
				bgIframeDoc=null;
	        }
	    }
	    bgDiv = bgdiv;
		bgdiv=null;
	    return bgDiv;
	}

	this.appendNode=function(oTable,result,index){
        var obj=result[index];
   	 	if(oTable.childNodes.length == 0)return;
   	 	var nCount=0;
	   	for(var i = 0; i < oTable.childNodes.length; i ++ )
	      {
	         if(!oTable.childNodes[i].ajaxCreate)nCount++;else break;
	      }
        for(var ii = 0; ii < nCount; ii ++ )
         {
            var newTR = oTable.childNodes[ii].cloneNode(true);
            if(newTR.tagName=="TR"){
                    if(index % 2==1){
                    	newTR.className="list_two";
                    	/*jQuery(newTR).mouseout(function(){
                    		jQuery(newTR).removeClass("list_move");
                    		jQuery(newTR).addClass("list_two");
                    	});*/
                    	//jvc.attachEvent(newTR, "mouseout", jvc_data_setclass(newTR, 'list_two'));
                	}else{
                		newTR.className="list_one";
                		/*jQuery(newTR).mouseout(function(){
                    		jQuery(newTR).removeClass("list_move");
                    		jQuery(newTR).addClass("list_one");
                    	});*/
                		//jvc.attachEvent(newTR, "mouseout", jvc_data_setclass(newTR, 'list_one'));                		
                	}
                    
                    /*jQuery(newTR).mouseover(function(){
                		jQuery(newTR).removeClass("list_one").removeClass("list_two");
                		jQuery(newTR).addClass("list_move");
                	});*/
               		//jvc.attachEvent(newTR, "mouseover", jvc_data_setclass(newTR, 'list_move'));
          	}

            newTR.ajaxCreate = true;
            if(newTR.nodeType == 3)continue;
            if(newTR.style)	newTR.style.display = "";
            if(newTR.id){newTR.id = (replacehref(newTR.id, obj));}
            oTable.appendChild(newTR);
            this.replaceCell(newTR, obj, i + 1);
            this.replacefld(newTR, obj, i + 1);
         }
   	}
	
	this.replaceCell = function(cell, resultf)
	   {
	   	
	      if(cell.nodeType == 3)return;
	 	  //专门给setGrid用
	      var fld = cell.getAttribute('fld');
	      cell.jdata = resultf;
	      if(cell.type == "radio" || cell.type == "checkbox"){
	    	  if(cell.value  && cell.value.indexOf('@')==0)cell.value = (replacehref(cell.value, resultf));
	      }
	    	if(cell.tagName=="IMG"){
	    		var jsrc=cell.getAttribute('jsrc');
	      	 	if(jsrc){
	      	 		cell.src = replacehref(jsrc, resultf);
	      	 		return;
	      	 	}
	    	}
	    
	      if(fld)
	      {

	         var jdata = resultf;

	         var varParten = /^(\w){1,100}$/;
	         var intParten = /^[-\+]?\d+$/;
	         var dblParten = /^[-\+]?\d+(\.\d+)?$/;
	         var RegistEventObj = [];
	         //if( ! varParten.test(fld))return;
	         // fld = fld.toLowerCase();
	         var value = "";

	           if(fld.indexOf("(")>-1){
												var fields=[];
												var sEval="";
												for(var tmp_fld in resultf) fields[fields.length]=tmp_fld ; 
												for(var i=0;i<fields.length;i++){
													sEval+="var "+fields[i]+"=jdata."+fields[i]+";";
												}	
												try{
												value=eval(sEval+fld);
											}catch(err){
												}
											}else if(resultf)value = resultf[fld];
	         if(value == undefined)value = "";
	         if(cell.getAttribute("fmt"))value = format(value, cell.getAttribute("fmt"));
	         if(cell.type == "radio")
	         {
	            cell.checked = (cell.value == value);
	         }
	         else if(cell.type == "select-one" || cell.type == "select" )
	         {
	            cell.jvalue = value;
	            for(var i = 0; i < cell.options.length; i ++ )
	            {
	               if(cell.options[i].value == value)
	               {
	                  cell.options[i].selected = true;
	                  return;
	               }
	            }

	         }
	         else if(cell.type == "checkbox")
	         {
	            cell.checked = (cell.value == value);
	         }
	         else  if(cell.type == "text" || cell.type == "hidden"  || cell.type == "textarea")
	         {

	            cell.value = value;
	         }
	 		else  if(cell.type == "file")
	         {

	            cell.value = value;
	         }
	         else if(cell.tagName.toLowerCase()=="img"){
	           cell.src =value;
	         }
	         else
	         {
	         	try{
	            if(cell.innerText)cell.innerText = value;
	            else cell.innerHTML = value;
	          }catch(err){
	          	 alert("errorType"+cell.type );
	          	}
	         }

	      }
	      if(cell.jsrc)
	      {

	         cell.src = replacehref(cell.jsrc, resultf);
	      }
	      if(cell.jhref)cell.href = (replacehref(cell.jhref, resultf));
	      if(cell.id && cell.id.indexOf('@') != - 1)cell.id = (replacehref(cell.id, resultf));
	      if(cell.name && cell.name.indexOf('@') != - 1)cell.name = (replacehref(cell.name, resultf));
	      if(cell.title && cell.title.indexOf('@') != - 1)cell.title = (replacehref(cell.title, resultf));
	       if(cell.title && cell.title.indexOf('@') == 0)cell.title="";
	      if(cell.href && cell.href.indexOf('@') != - 1)cell.href = (replacehref(cell.href, resultf));

	   }
	
	 this.replacefld = function(newTR, resultf){
	      if(newTR.nodeType == 3)return;

	      for(var j = 0; j < newTR.childNodes.length;
	      j ++ )
	      {
	         var cell = newTR.childNodes[j];
	         this.replaceCell(cell, resultf);
	         this.replacefld(cell, resultf);
	      }
	   }
	 
	 
	 this.appendNodeMessage=function(oTable,message){
	        for(var i = oTable.childNodes.length - 1; i >= 1;i -- )
	      	{
	        	 if(oTable.childNodes[i].ajaxCreate && oTable.childNodes[i].ajaxMessage)oTable.removeChild(oTable.childNodes[i]);	
	        }
	   		if(oTable.tagName=="TBODY"){
			   	var newTR=document.createElement("tr");
			   	var newTD=document.createElement("td");
			   	newTR.appendChild(newTD);
			   	newTD.style.padding = "10px";
				var colsNum=0;
			   	if(oTable.children && oTable.children[0].children){
			   		var cnodes = oTable.children[0].children;
			   		for(var j=0;j<cnodes.length;j++){
			   			var cnode = cnodes[j];
			   			if("TD" == cnode.nodeName.toUpperCase() && cnode.style.display != 'none'){
			   				colsNum++;
			   			}
			   		}
			   	}
			   	//cols added by zenglp 2014/3/24
			   	newTD.setAttribute("colSpan",colsNum==0?1:colsNum);
			   	newTD.innerHTML=message;
			   	newTR.ajaxCreate=true;
			    newTR.ajaxMessage=true;	
				oTable.insertBefore(newTR, newTR.nextSibling);
			}else{
				var newTR=document.createElement("div");
			   	newTR.style.padding = "10px";
			   	newTR.innerHTML=message;
			   	newTR.ajaxCreate=true;
			   	newTR.ajaxMessage=true;
				oTable.insertBefore(newTR, newTR.nextSibling);
			}  
	   }
 }

function PageCore()
{
   this.set = function (result, name, listDiv, oDiv, jsSrc)
   {
      if( ! oDiv)return;
      this.pageDiv = oDiv;
      this.listDiv = listDiv;
      oDiv.innerHTML = "";
      if(!oDiv.style.fontSize)
      oDiv.style.fontSize = "12px";
      oDiv.style.Color = "#444";
      oDiv.me = this;
      this.name = name;
      this.page = oDiv;
      this.jsSrc = jsSrc;
      if(result[name + ".page"])
      this.pageinfo = result[name + ".page"][0];
      else if(result[name + "_page"])
      this.pageinfo = result[name + "_page"];
      else return;

      var strPage = "";
      // 首页
      if (this.pageinfo.CurPage > 2)
      {
         strPage += "<img style='cursor:pointer' src='"+ajaxCoreBaseUrl + "img/firs_button.gif' onclick='$(\"#"+oDiv.id+"\").get(0).me.loaddata(1)'> ";
      }
      // 上一页
      if (this.pageinfo.CurPage > 1)
      {
         strPage += "<img style='cursor:pointer' src='"+ajaxCoreBaseUrl + "img/prev_button.gif' onclick='$(\"#"+oDiv.id+"\").get(0).me.loaddata("+(this.pageinfo.CurPage-1)+")'> ";
      }
      // 下一页
      if (this.pageinfo.CurPage < this.pageinfo.TotalPageCount)
      {
         strPage += "<img style='cursor:pointer' src='"+ajaxCoreBaseUrl + "img/next_button.gif'  onclick='$(\"#"+oDiv.id+"\").get(0).me.loaddata("+(parseInt(this.pageinfo.CurPage)+1)+")'> ";
      }
      // 末页
      if (this.pageinfo.CurPage < this.pageinfo.TotalPageCount - 1)
      {
         strPage += "<img style='cursor:pointer' src='"+ajaxCoreBaseUrl + "img/last_button.gif'  onclick='$(\"#"+oDiv.id+"\").get(0).me.loaddata("+(this.pageinfo.TotalPageCount)+")'> ";
      }
      var arrPages="5,10,15,20,25,30,35,40,45,50,100,200,500,1000".split(",");
	
      strPage += "共" + this.pageinfo.RecordsCount + "条记录 分" + this.pageinfo.TotalPageCount + "页 每页";
      strPage +="<select onchange='$(\"#"+oDiv.id+"\").get(0).me.loadsize(this.value)'>";
      for(var i=0;i<arrPages.length;i++){
      	 var selected=(this.pageinfo.RecordsPerPage==arrPages[i])?"selected":"";
      	 strPage +="<option "+selected+" value='"+arrPages[i]+"'>"+arrPages[i]+"</option>";
      	 if(this.pageinfo.RecordsPerPage>arrPages[i]  && (i+1==arrPages.length || this.pageinfo.RecordsPerPage<arrPages[i+1]))
      		 strPage +="<option selected value='"+this.pageinfo.RecordsPerPage+"'>"+this.pageinfo.RecordsPerPage+"</option>";
      }
      strPage +="</select>";
      //strPage += "<input style='width:30px' value='" + this.pageinfo.RecordsPerPage + "' onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'');var e = event ? event :(window.event ? window.event : null);var currKey=e.keyCode||e.which||e.charCode; if(currKey==13){if(this.value>999)this.value=999;$$('"+oDiv.id+"').me.loadsize(this.value); }\" onblur='if(this.value>999)this.value=999;$$(\""+oDiv.id+"\").me.loadsize(this.value)'/>";
      strPage += "条 ";
      strPage += "当前第<select onchange='document.getElementById(\""+oDiv.id+"\").me.loaddata(this.value)'>";
      var showPageCount=10;
      var iStart=this.pageinfo.CurPage-showPageCount;
	  if(iStart<1)iStart=1;
	  var iEnd=iStart+2*showPageCount;
	  if(iEnd>this.pageinfo.TotalPageCount)iEnd=this.pageinfo.TotalPageCount;
      
      for (var i = iStart; i <= iEnd;
      i ++ )
      {
         strPage += "<option ";
         if(i == this.pageinfo.CurPage)strPage += "selected";
         strPage += " value=" + i + " >" + i + "</option>";
      }
      strPage += "</select>页";
      this.curpage = this.pageinfo.CurPage;
      oDiv.innerHTML = strPage;
   }
   this.reload=function(){
   		this.loaddata( this.curpage);
   }	
   this.loadsize=function(recordsperpage){
   
   	  if( this.pageinfo.RecordsPerPage==recordsperpage)return;
   	  if(isNaN(recordsperpage))return;
   	  if(recordsperpage==0)return;
   	  if(recordsperpage>1000)return;
      var src = this.jsSrc + "&" + this.name + ".recordsperpage=" + recordsperpage;  
      var ac = new AjaxCore();
      ac.query(src, ac.json_callback, this.name, this.listDiv, this.pageDiv, this.jsSrc);
   }
   this.loaddata = function(CurPage)
   {
   		if(this.listDiv.isQuery=="1")return;
   		this.listDiv.isQuery="1";
   		var ac = new AjaxCore();
   		ac.processStart(this.listDiv,"<nobr>&nbsp;&nbsp;查询中,请稍候...</nobr>");
      var src = this.jsSrc + "&" + this.name + ".CurPage=" + CurPage+"&"+ this.name + ".recordsperpage=" + this.pageinfo.RecordsPerPage;   		
      ac.query(src, ac.json_callback, this.name, this.listDiv, this.pageDiv, this.jsSrc);
   }

}



function replacehref(shref, resultf, row)
{
   var fields = [];
   for(var fld in resultf) fields[fields.length] = fld ;
   
   for(var i = 0; i < fields.length; i ++ )
   {
      shref = shref.replace(new RegExp("@" + fields[i], "gi"), resultf[fields[i]]);
      
   }
   if(row)shref = shref.replace(new RegExp("@numrow", "gi"), row);

   return shref;

}

function format(str, fmt)
{
   if( ! str || ! fmt)return "";
   if(str == "" || fmt == "")return "";
   if(fmt.indexOf("len:") == 0)return formatLen(str, fmt.replace(new RegExp("len:", "gi"), ""));
   if(fmt.indexOf("len2:") == 0)return formatLen2(str, fmt.replace(new RegExp("len2:", "gi"), ""));
   var value = str;
   if(fmt.indexOf("eval:") == 0)
   {
      return eval(fmt.replace(new RegExp("eval:", "gi"), ""));
   }

   return (formatDate(parseDate(str), fmt));
}
function formatLen(str, len)
{
   if(str.length <= len)return str;
   return str.substring(0, len);
}
function formatLen2(str, len)
{
   if(str.length <= len)return str;
   return str.substring(0, len)+"...";
}

function parseDate(str)
{
   if(typeof str == 'string')
   {
      results = str.match(/^ *(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);
      if(results && results.length > 3)
      return new Date(1900,0,1, parseInt2(results[1]), parseInt2(results[2]), parseInt2(results[3]));
      var results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) *$/);
      if(results && results.length > 3)
      return new Date(parseInt2(results[1]), parseInt2(results[2]) - 1, parseInt2(results[3]));
      results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);
      if(results && results.length > 6)
      return new Date(parseInt2(results[1]), parseInt2(results[2]) - 1, parseInt2(results[3]), parseInt2(results[4]), parseInt2(results[5]), parseInt2(results[6]));
      results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2})\.(\d{1,9}) *$/);
      if(results && results.length > 7)
      return new Date(parseInt2(results[1]), parseInt2(results[2]) - 1, parseInt2(results[3]), parseInt2(results[4]), parseInt2(results[5]), parseInt2(results[6]), parseInt2(results[7]));
   }
   return null;
}

function decimal(num, v)
{
   var vv = Math.pow(10, v);
   return Math.round(num * vv) / vv;
}

function parseInt2(str)
{
   var v = str;
   while(v.indexOf(0) == 0 && v.length > 0)v = v.substring(1, v.length);
   if(v == '')v = 0;
   return parseInt(v);
}
function formatDate(v)
{
   if(typeof v == 'string') v = parseDate(v);
   if(v instanceof Date)
   {
      var y = v.getFullYear();
      var m = v.getMonth() + 1;
      var d = v.getDate();
      var h = v.getHours();
      var i = v.getMinutes();
      var s = v.getSeconds();
      var ms = v.getMilliseconds();
      if(ms > 0) return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s + '.' + ms;
      if(h > 0 || i > 0 || s > 0) return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s;
      return y + '-' + m + '-' + d;
   }
   return '';
}


function formatDate(dt, cdti)
{
   if(cdti !== undefined)
   {
      return cdti.replace(/(yyyy|yy|MM|dd|HH|mm|ss|ms|wd)/ig, function($1)
      {
         switch($1)
         {
            case 'yyyy' :
               return dt.getFullYear();
            case 'yy' :
               {
                  var yy = dt.getFullYear() % 100;
                  if(yy < 10)yy = '0' + yy;
                  return yy
               }
               ;
            case 'MM' :
               return padNum(dt.getMonth() + 1, 0, 2);
            case 'dd' :
               return padNum(dt.getDate(), 0, 2);
            case 'HH' :
               return padNum(dt.getHours(), 0, 2);
            case 'mm' :
               return padNum(dt.getMinutes(), 0, 2);
            case 'ss' :
               return padNum(dt.getSeconds(), 0, 2);
            case 'ms' :
               return padNum(dt.getMilliseconds(), 0, 3);
            case 'wd' :
               return week(dt.getDay());
         }
      }
      )
   }
   else
   {
      return dt.getFullYear() + '-' + padNum(dt.getMonth() + 1, 0, 2) +
      '-' + padNum(dt.getDate(), 0, 2);
   }
   function week(day)
   {
      switch(day)
      {
         case 0 :
         day = '日';
         break;
         case 1 :
         day = '一';
         break;
         case 2 :
         day = '二';
         break;
         case 3 :
         day = '三';
         break;
         case 4 :
         day = '四';
         break;
         case 5 :
         day = '五';
         break;
         case 6 :
         day = '六';
         break;
      }
      return day;
   }
   function padNum(str, num, len)
   {
      var temp = ''
      for(var i = 0; i < len; temp += num, i ++ );
      return temp = (temp += str).substr(temp.length - len);
   }

}

function autoTypeValue(v)
{
   var intParten = /^[-\+]?\d+$/;
   var dblParten = /^[-\+]?\d+(\.\d+)?$/;

   if(  intParten.test(v))return parseInt(v);
   if(  dblParten.test(v))return parseFloat(v);

   return v;
}


$.ajaxcore =  new AjaxCore();

//fix setAttibute IE6、IE7
var dom = (function() { 
	var fixAttr = { 
		tabindex: 'tabIndex', 
		readonly: 'readOnly', 
		'for': 'htmlFor', 
		'class': 'className', 
		maxlength: 'maxLength', 
		cellspacing: 'cellSpacing', 
		cellpadding: 'cellPadding', 
		rowspan: 'rowSpan', 
		colspan: 'colSpan', 
		usemap: 'useMap', 
		frameborder: 'frameBorder', 
		contenteditable: 'contentEditable' 
	}, 
	div = document.createElement( 'div' ); 
	div.setAttribute('class', 't'); 
	var supportSetAttr = div.className === 't'; 
	return { 
		setAttr : function(el, name, val) { 
		el.setAttribute(supportSetAttr ? name : (fixAttr[name] || name), val); 
		}, 
		getAttr : function(el, name) { 
		return el.getAttribute(supportSetAttr ? name : (fixAttr[name] || name)); 
		} 
	  } 
	})(); 
