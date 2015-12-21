/**
 * depends on string.prototype.js
 */
(function($){
	var tableTpl = "<table class=\"table_wrapper\"><thead><tr>{ths}</tr></thead><tbody>{trs}</tbody></table>";
	var thTpl = "<th>{th}</th>";
	var tdTpl = "<td>{td}</td>";
	var trTpl = "<tr>{tr}</tr>";
	var checkboxTpl = "<input type='checkbox' name='cb' id='{id}' {attr}/>";
    /**
     * ths:角色|权限
     * flds : roleName|<input type='checkbox'/>
     */
	function One2Many0(config){
	//url,headers,tds
		this.container = $("<div/>").addClass("wrapper_div");
		this.ths = config.ths;
		this.flds = config.flds;
		this.url = config.url;
		this.data = config.data;
		this.urlData = [];
		//初始化
		this.init();
	}
    
	One2Many0.prototype.init = function(){
		var self = this;
		var tableObj = {};
		var ths="";
		var thObjArr = this.ths.split("|");
		var fldObjArr = this.flds.split("|");
		 for(var i=0;i<thObjArr.length;i++){
			ths+=thTpl.fillTpl({"th":thObjArr[i]});
		 }
		 tableObj.ths = ths;
		 $.get(this.url,this.data,function(j){
			 if(j && j.res){
				 self.urlData = j.res;
				 var trs = "";
				 for(var k=0;k<j.res.length;k++){
					 var jr = j.res[k];
					 var tr = "";
					 for(var m=0;m<fldObjArr.length;m++){
						 if("_checkbox" == fldObjArr[m]){//多选项
							 tr += tdTpl.fillTpl({"td":checkboxTpl.fillTpl({"id":"cb_"+k,"attr":jr.selected==1?"checked='true'":""})});
						 }else{
							 tr += tdTpl.fillTpl({"td":jr[fldObjArr[m]]});
						 }
						
					 }
					trs += trTpl.fillTpl({"tr":tr});
				 }
				 tableObj.trs = trs; 
				 self.container.append(tableTpl.fillTpl(tableObj));
			 }
		 });
	}
	
	One2Many0.prototype.getSelectedData = function(){
		var self = this;
		var resData = [];
		self.container.find(":checked").each(function(index,ele){
		    var cbId =$(ele).attr("id");
		    var i = cbId.substring(3);
		    resData.push(self.urlData[i]);
		});
		return resData;
	}
	
	One2Many0.prototype.getQueryParam = function(projection){
		var r = [];
		var d = this.getSelectedData();
		if(d){
			for(var i=0;i<d.length;i++){
				r.push(d[i][projection]);
			}
		}
		return r.join(",");
	}

	$.fn.one2many0 = function(config){
		var o = new One2Many0(config);
		this.append(o.container);
		return o;
	}

})(jQuery);

