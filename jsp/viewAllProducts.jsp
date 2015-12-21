<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示所有产品记录</title>
<script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="../js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="../js/ajaxcore.js"></script>
<script type="text/javascript" src="../resources/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="../resources/My97DatePicker/WdatePicker.js"></script>
<link href="../resources/main.css" rel="stylesheet" type="text/css"/>
<link href="../resources/artDialog/skins/blue.css" rel="stylesheet" type="text/css"/>

</head>
<body>
 <div id="title" style="background: url(../resources/images/BG_T_TD.gif)">
			<div style="font-weight:bold;color:white;height: 24px;line-height: 24px;">
				产品查询
				<font color="yellow"></font>
			</div>
		</div>
<div id="search" style="padding:  0.5em 0.5em 0em 0.5em;">
	<form id="frm" name="frm" json="../getProducts.htm">
	 <div class="inquire">
			<span>ID：</span>
            <input type="text" name="query.p-productId" class="txt"/>
            <span>产品名称：</span>
            <input type="text" class="txt"  name="query.p-productName.like"/>
            <span >手机品牌：</span>
             <select id="brand" name="query.pb-brandId" dataUrl="../getPhoneBrands.htm?recordsperpage=-1">
             	<option value="">全部</option>
             	<option namefld="brandName" valuefld="brandId"/>
             </select> 
              <span>是否有效：</span>
            <select name="query.isValid">
            	<option value="">全部</option>
            	<option value="1">有效</option>
            	<option value="0">无效</option>
            </select>
           <!--   <span >商家：</span>
             <select id="seller" name="query.ps-sellerId" dataUrl="../getSellers.htm">
             	<option value="">全部</option>
             	<option namefld="sellerName" valuefld="sellerId"/>
             </select>  -->
        <input type="button" value=" 查 询 " class="btn" onclick="query();">
        </div> 
        
        </form>
        <!-- <div style="clear:both;height:30px;width:100%;"></div> -->
        <div>
               <table width="100%"   class="tablebox" border="1">
				<thead>
			<tr>
			<th>产品ID</th> 
			<th>品牌名称</th> 
			<th>产品名称</th> 
			<th>是否有效</th> 
			<th>操作</th> 
		</tr>
		</thead>
			<tbody id="data">		
			<tr style="display:none;"> 
				<td fld="productId" nowrap="nowrap">&nbsp;</td>	
				<td fld="brandName">&nbsp;</td>
				<td fld="productName">&nbsp;</td>
				<td fld="vn(isValid)"></td>
				<td fld="op(brandId,productId,productName)"></td>
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
	$.ajaxcore.setSelect($("#brand").get(0));
	//$.ajaxcore.setSelect($("#seller").get(0));
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

function op(brandId,productId,productName){
	var operation = "";
	operation = "<a href=\"javascript:openEdit("+brandId+","+productId+",'"+productName+"')\">修改</a>";
	operation += "&nbsp;|&nbsp;";
	operation += "<a href=\"javascript:del("+productId+")\">删除</a>";
	return operation;
}

function openEdit(brandId,productId,productName){
	$("#productName").val(productName);
	$("#productId").val(productId== 'null'?'':productId);
	$("#i_edit").hide();
	$("#e_brand").get(0).jvalue = brandId;
	$.ajaxcore.setSelect($("#e_brand").get(0));
	$.artDialog({ content:$("#edit").get(0),
		    title:"修改信息",
		    width:300,
		    height:100,
			lock:true,
		    padding: 0,
		    okValue : '确定',
		    cancelValue : '取消',
		    ok: function(){
		    	 $.get("../updateProduct.htm?"+$("#edit").children("input,select").serialize(),{"key.productId":productId},function(data){
			    	   //alert(data.msg);
			    	   query();
			       });
		    },
		    cancel : function(){
		    	
		    }
		    });
}


function add(){
	$("#productName,#productId").val('');
	$.ajaxcore.setSelect($("#e_brand").get(0));
	//$.ajaxcore.setSelect($("#e_seller").get(0));
	$("#i_edit").show();
	$.artDialog({ content:$("#edit").get(0),
		    title:"新增信息",
		    width:300,
		    height:100,
			lock:true,
		    padding: 0,
		    okValue : '确定',
		    cancelValue : '取消',
		    ok: function(){
		    	 if($("#productId").val() == ''){
		    		 alert("产品id不能为空！");
		    		 return;
		    	 }
		    	 $.get("../addProduct.htm?"+$("#edit").find("input,select").serialize(),function(data){
			    	 //  alert(data.msg);
			    	   query();
			       });
		    },
		    cancel : function(){
		    	
		    }
		    });
}

function del(productId){
	$.artDialog({ content:"你确定要删除么？",
	    title:"删除信息",
	    width:300,
	    height:100,
		lock:true,
	    padding: 0,
	    okValue : '确定',
	    cancelValue : '取消',
	    ok: function(){
	       $.get("../removeProduct.htm",{"key.productId":productId},function(data){
	    	   //alert(data.msg);
	    	   query();
	       });
	    },
	    cancel : function(){
	    	
	    }
	    });
}
</script>

<div id="edit" style="display:none;">

 <div id="i_edit">
 	<span>产品id：</span>
	<input type="text" name="module.productId" id="productId"></input><br/>
 </div>
 <span>产品名称：</span>
<input type="text" name="module.productName" id="productName"></input><br/>
<span >手机品牌：</span>
	 <select id="e_brand" name="module.brandId" dataUrl="../getPhoneBrands.htm?recordsperpage=-1">
	 	<option namefld="brandName" valuefld="brandId"/>
	 </select> 
	 <br/>
	 <span>是否有效：</span>
<select name="module.isValid" id="isValid">
	<option value="1">有效</option>
	<option value="0">无效</option>
</select>
</div>
</html>