<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="e" uri="http://x.y.easyweb/tags"%>
<e:queryPage formJson="query.htm?d=queryRoles" headers="角色编号|角色名称|分配资源|操作" template="query.ftl" title="角色查询" qns="角色查询" >
    <e:text content="角色编号：" fid="elements"></e:text>
    <e:input fid="elements" id="userId" name="query.roleId"></e:input>
    <e:text content="角色名称：" fid="elements"></e:text>
    <e:input fid="elements" id="roleName" name="query.roleName"></e:input>
    <e:input fid="elements" id="query" type="button" value="查询" onclick="query()" className="btn"></e:input>
    <e:td fid="tds" fld="roleId"></e:td>
    <e:td fid="tds" fld="roleName"></e:td>
    <e:td fid="tds" fld="setResource(roleId)"></e:td>
    <e:td fid="tds" fld="opt(roleId)"></e:td>
    <e:input fid="bottomElements" type="button" value="新增" className="bt_add" onclick="add()"></e:input>
</e:queryPage>
<script type="text/javascript">
    $(function(){
        query();
    });

    function opt(roleId){
        return editstr(roleId)+" | "+delstr(roleId);
    }
    function editstr(roleId){
        return createA('编辑','javascript:edit('+roleId+');')
    }
    function delstr(roleId){
        return createA('删除','javascript:del('+roleId+');')
    }

    function setResource(resourceId){
        return createA('分配资源','javascript:allocateResource('+resourceId+');')
    }

</script>
