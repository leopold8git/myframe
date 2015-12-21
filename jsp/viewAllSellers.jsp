<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示所有商家记录</title>
<script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="../js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="../js/ajaxcore.js"></script>
<script type="text/javascript" src="../resources/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="../resources/My97DatePicker/WdatePicker.js"></script>
<link href="../resources/main.css" rel="stylesheet" type="text/css"/>
<link href="../resources/artDialog/skins/blue.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
#editSelect select{
width:150px;
height : 200px;
}
#addSelect select{
width:150px;
height : 200px;
}
</style>
</head>
<body>
 <div id="title" style="background: url(../resources/images/BG_T_TD.gif)">
			<div style="font-weight:bold;color:white;height: 24px;line-height: 24px;">
				商家查询
				<font color="yellow"></font>
			</div>
		</div>
<div id="search" style="padding:  0.5em 0.5em 0em 0.5em;">
	<form id="frm" name="frm" json="../getSellers.htm">
	 <div class="inquire">
			<span>ID：</span>
            <input type="text" name="query.sellerId" class="txt"/>
            <span>商家名称：</span>
            <input type="text" class="txt"  name="query.sellerName.like"/>
             <span>是否有效：</span>
            <select name="query.isValid">
            	<option value="">全部</option>
            	<option value="1">有效</option>
            	<option value="0">无效</option>
            </select>
        <input type="button" value=" 查 询 " class="btn" onclick="query();">
       <!--  <input type="button" value=" 导 出" class="btn" onclick="excel();"> -->
        </div> 
        
        </form>
        <!-- <div style="clear:both;height:30px;width:100%;"></div> -->
        <div>
               <table width="100%"   class="tablebox" border="1">
				<thead>
			<tr>
			<th>商家ID</th> 
			<th>商家名称</th> 
			<th>是否有效</th> 
			<th>操作</th> 
		</tr>
		</thead>
			<tbody id="data">		
			<tr style="display:none;"> 
				<td fld="sellerId" nowrap="nowrap">&nbsp;</td>	
				<td fld="sellerName">&nbsp;</td>
				<td fld="vn(isValid)"></td>
				<td fld="op(sellerId,sellerName,isValid)"></td>
			 </tr>
		</tbody>
		
		<!-- <tr class="footer">
		<td height="36">&nbsp;&nbsp;&nbsp;&nbsp;
	   		<input style="cursor: pointer;" type="button" onclick="add()" value="添加" class="bt_add" />
		</td> 
	</tr>-->
		</table>
		</div>
		<div id="page" align="right"> </div>
		<input style="cursor: pointer;" type="button" onclick="add()" value="添加" class="bt_add" />
    </div>

<%-- 	</center> --%>
	
	
	
</body>
<script type="text/javascript">
$(function(){
	query();
	//$.get("test.htm",function(data){alert(data);})
	
	
});
function query(){
	//初始化
	$.ajaxcore.doSubmit($("#frm").get(0),$("#data").get(0),$("#page").get(0));
}

function vn(isValid){
	var r = "";
	if(isValid == '1'){
		r = "有效";
	}else if(isValid == '0'){
		r = "无效";
	}
	return r;
}

function op(sellerId,sellerName,isValid){
	var operation = "";
	var vn = isValid=="1"?"无效":"有效";
	operation = "<a href=\"javascript:openEdit("+sellerId+",'"+sellerName+"',"+isValid+")\">修改</a>";
	operation += "&nbsp;|&nbsp;";
	operation += "<a href=\"javascript:del("+sellerId+")\">删除</a>";
	/* operation += "&nbsp;|&nbsp;";
	operation += "<a href=\"javascript:validOrNotSeller('"+sellerId+"',"+isValid+")\">"+vn+"</a>"; */
	if(isValid == '1'){
		operation += "&nbsp;|&nbsp;";
		operation += "<a href=\"javascript:excel("+sellerId+",'"+sellerName+"')\">导出</a>";
	}
	return operation;
}

/* function validOrNotSeller(sellerId,isValid){
		 $.get("../updateSeller.htm",{"key.sellerId":sellerId,"module.isValid":isValid == '1' ? '0' : '1'},function(data){
	    	   query();
	       });
} */

function openEdit(sellerId,sellerName,isValid){
	
	$("#sellerName").val(sellerName);
	$("#sellerId").val(sellerId);
	$("#sellerId").attr("readonly",true);
	$("#isValid").val(isValid);

	$("#addSelect").hide();
	$("#editSelect").show();
	$.ajaxcore.setSelect($("#src0").get(0));
	var dataUrl="../getBrandsBySeller.htm?query.bs-sellerId="+sellerId; 
	$("#dest0").attr("dataUrl",dataUrl);
	$.ajaxcore.setSelect($("#dest0").get(0),function(){
		$("#dest0 option").dblclick(function(){
			$(this).remove();
		});
	});
	//var ns = $("#netSourceId").get(0);
	//ns.jvalue = netSourceId;
	//$.ajaxcore.setSelect(ns);
	$.artDialog({ content:$("#edit").get(0),
		    title:"修改信息",
		    width:400,
		    height:200,
			lock:true,
		    padding: 0,
		    okValue : '确定',
		    cancelValue : '取消',
		    ok: function(){
		    	 $.get("../updateSeller.htm",{"key.sellerId":sellerId,"module.sellerName":$("#sellerName").val(),"module.isValid":$("#isValid").val(),"brandIds":getVals4Select($("#dest0"))},function(data){
			    	   //alert(data.msg);
			    	   query();
			       });
		    },
		    cancel : function(){
		    	
		    }
		    });
}


function add(){
	$("#sellerName").val('');
	$("#sellerId").val('');
	$("#sellerId").removeAttr("readonly");
	
	$("#editSelect").hide();
	$("#addSelect").show();
	
	$.ajaxcore.setSelect($("#src1").get(0));
	$("#dest1 option").remove();
	//$.ajaxcore.setSelect($("#dest1").get(0));
	//$.ajaxcore.setSelect($("#netSourceId").get(0));
	$.artDialog({ content:$("#edit").get(0),
		    title:"新增信息",
		    width:400,
		    height:200,
			lock:true,
		    padding: 0,
		    okValue : '确定',
		    cancelValue : '取消',
		    ok: function(){
		    	if($("#sellerId").val() == ''){
		    		alert("商家名称不能为空！");
		    		return false;
		    	}
		    	
		    	 $.get("../addSeller.htm?"+$("#edit").children("input,select").serialize(),{"brandIds":getVals4Select($("#dest1"))},function(data){
			    	 //  alert(data.msg);
			    	   query();
			     });
		    	
		    	
		    },
		    cancel : function(){
		    	
		    }
		    });
}

function getVals4Select(obj){
	var vals = "";
	$(obj).find("option").each(function(i,ele){
		vals+=",";
		vals+=ele.value;
	});
	vals = vals.substring(1);
	return vals;
}

function del(sellerId){
	$.artDialog({ content:"你确定要删除么？",
	    title:"删除信息",
	    width:300,
	    height:100,
		lock:true,
	    padding: 0,
	    okValue : '确定',
	    cancelValue : '取消',
	    ok: function(){
	       $.get("../removeSeller.htm",{"key.sellerId":sellerId},function(data){
	    	   //alert(data.msg);
	    	   query();
	       });
	    },
	    cancel : function(){
	    	
	    }
	    });
}

//导出excel
function excel(sellerId,sellerName){
	//$.get("../excelBySeller.htm",{"query.p-sellerId":sellerId,"sellerName":sellerName});
	$.ajaxcore.exportExcel("../excelBySeller.htm?sellerId="+sellerId+"&sellerName="+sellerName);
}


function bindAddSelect(){
	$("#src1").dblclick(function(){
		 $("#src1 option:selected").each(function(index,ele){
			 if(!hasValue($("#dest1"),ele.value)){
				 var opt = ele.cloneNode(true);
				 $("#dest1").append(opt);
			 }
		 });
	});
	
	
	//点击图片
	  $("#img1").click(function(){
		copyLeftSelect2Right($("#src1"),$("#dest1"));
	}); 
	
	$("#dest1").dblclick(function(){
		$(this).find("option:selected").remove();
	}); 
}
function bindEditSelect(){
	//IE 不支持option绑定dblclick
	$("#src0").dblclick(function(){
		 $("#src0 option:selected").each(function(index,ele){
			 if(!hasValue($("#dest0"),ele.value)){
				 var opt = ele.cloneNode(true);
				 $("#dest0").append(opt);
			 }
		 });
	});
	
	//点击图片
	$("#img0").click(function(){
		copyLeftSelect2Right($("#src0"),$("#dest0"));
	});
	
	$("#dest0").dblclick(function(){
		$(this).find("option:selected").remove();
	});
	
}
/**
 * 将左侧下拉框的选中的option移到右侧下拉框
 */
function copyLeftSelect2Right(leftObj,rightObj,callback){
	$(leftObj).find("option:selected").each(function(index,ele){
		if(!hasValue(rightObj,ele.value)){
			var opt = $("<option></option>");
			opt.html($(ele).html());
			opt.attr("value",ele.value); 
			$(rightObj).append(opt);
			if(callback){
				callback(leftObj,rightObj,opt);
			}
			
		}
	});
}
/**
 * 判断下拉列表中是否有某个值的option
 */
function hasValue(selObj,val){
	var b = false;
	$(selObj).find("option").each(function(index,ele){
		if(ele.value == val){
			b = true;
			return false;
		}
	});
	return b;
}
</script>

<div id="edit" style="display:none;">
<span>商家ID：</span>
<input type="text" name="module.sellerId" id="sellerId"></input><br/>
<span>商家名称：</span>
<input type="text" name="module.sellerName" id="sellerName"></input><br/>
<span>是否有效：</span>
<select name="module.isValid" id="isValid">
	<option value="1">有效</option>
	<option value="0">无效</option>
</select>
<div id="editSelect" style="display:none;">
	<div>选择签约品牌(从左往右)：</div>
	<select id="src0" name="src" multiple="multiple" size="5"  dataUrl="../getPhoneBrands.htm?recordsperpage=-1" callback="bindEditSelect">
	 	<option namefld="brandName" valuefld="brandId"/>
	</select> 
	<img id="img0" src="../resources/images/right_arrow.png" style="width:50px;height:50px;vertical-align: top;margin-top:80px;cursor:pointer;"></img>
	<select id="dest0" name="dest" multiple="multiple" size="5">
	 	<option namefld="brandName" valuefld="brandId"/>
	</select> 
</div>
<div id="addSelect" style="display:none;">
	<div>选择签约品牌(从左往右)：</div>
	<select id="src1" name="src" multiple="multiple" size="5"  dataUrl="../getPhoneBrands.htm?recordsperpage=-1" callback="bindAddSelect">
	 	<option namefld="brandName" valuefld="brandId"/>
	</select> 
	<img id="img1" src="../resources/images/right_arrow.png" style="width:50px;height:50px;vertical-align: top;margin-top:80px;cursor:pointer;"></img>
	<select id="dest1" name="dest" multiple="multiple" size="5">
	</select> 
</div>
</div>
</html>