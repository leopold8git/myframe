<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="e" uri="http://x.y.easyweb/tags"%>
<e:queryPage formJson="query.htm?d=queryUsers" headers="用户编号|用户名称|操作" template="query.ftl" title="账户查询" qns="账户查询" >
    <e:text content="用户编号：" fid="elements"></e:text>
    <e:input fid="elements" id="userId" name="query.userId"></e:input>
    <e:text content="用户名：" fid="elements"></e:text>
    <e:input fid="elements" id="username" name="query.username"></e:input>
    <e:input fid="elements" id="query" type="button" value="查询" onclick="query()" className="btn"></e:input>
    <e:td fid="tds" fld="userId"></e:td>
    <e:td fid="tds" fld="username"></e:td>
    <e:td fid="tds" fld="opt(userId)"></e:td>
    <e:input fid="bottomElements" type="button" value="新增" className="bt_add" onclick="add()"></e:input>
</e:queryPage>
<script type="text/javascript">
    $(function(){
        query();
    });

    function opt(userId){
        return editstr(userId)+" | "+delstr(userId);
    }
    function editstr(userId){
        return createA('编辑','javascript:edit('+userId+');')
    }
    function delstr(userId){
        return createA('删除','javascript:del('+userId+');')
    }

    function del(userId){
        $.get('../delete.htm?d=pc_delUser',{'key.userId':userId},function(data){
            if(data.row>0){
                $.dialog.tips(data.msg,'success',1);
                query();
            }else{
                $.dialog.tips(data.msg,'error',2);
            }
        });
    }
    function edit(userId){
        $.dialog.open('editUser.htm?userId='+userId,{
            width : 400,
            height : 300,
            padding: 10,
            ok : function(frame){frame.save(this,userId);return false;},
            cancel : function(){}
        });

    }

    function add(){
       $.dialog.open('editUser.htm',{
            width : 400,
            height : 300,
            padding: 10,
            ok : function(frame){frame.save(this);return false;},
            cancel : function(){}
        });
    }
</script>
