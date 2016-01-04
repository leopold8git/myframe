<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="e" uri="http://x.y.easyweb/tags"%>
<e:queryPage formJson="query.htm?d=queryResources" headers="资源编号|资源名称|操作" template="query.ftl" title="资源查询" qns="资源查询" >
    <e:text content="资源编号：" fid="elements"></e:text>
    <e:input fid="elements" id="resourceId" name="query.resourceId"></e:input>
    <e:text content="资源名：" fid="elements"></e:text>
    <e:input fid="elements" id="resourceName" name="query.resourceName"></e:input>
    <e:input fid="elements" id="query" type="button" value="查询" onclick="query()" className="btn"></e:input>
    <e:td fid="tds" fld="resourceId"></e:td>
    <e:td fid="tds" fld="resourceName"></e:td>
    <e:td fid="tds" fld="opt(resourceId)"></e:td>
    <e:input fid="bottomElements" type="button" value="新增" className="bt_add" onclick="add()"></e:input>
</e:queryPage>
<script type="text/javascript">
    $(function(){
        query();
    });

    function opt(resourceId){
        return editstr(resourceId)+" | "+delstr(resourceId);
    }
    function editstr(resourceId){
        return createA('编辑','javascript:edit('+resourceId+');')
    }
    function delstr(resourceId){
        return createA('删除','javascript:del('+resourceId+');')
    }

    function del(resourceId){
        $.get('../delete.htm?d=d_delResource',{'key.resourceId':resourceId},function(data){
            if(data.row>0){
                $.dialog.tips(data.msg,'success',1);
                query();
            }else{
                $.dialog.tips(data.msg,'error',2);
            }
        });
    }
    function edit(resourceId){
        $.dialog.open('editUser.htm?resourceId='+resourceId,{
            width : 400,
            height : 300,
            padding: 10,
            ok : function(frame){frame.save(this,resourceId);return false;},
            cancel : function(){}
        });

    }

    function add(){
       $.dialog.open('editResource.htm',{
            width : 400,
            height : 300,
            padding: 10,
            ok : function(frame){frame.save(this);return false;},
            cancel : function(){}
        });
    }
</script>
