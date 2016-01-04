<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="e" uri="http://x.y.easyweb/tags"%>
<e:editPage formJson="update.htm?d=pc_editRole"  template="edit.ftl" title="编辑角色" >
    <e:text content="角色名称：" fid="elements"></e:text>
    <e:input fid="elements" id="roleName" name="module.roleName" fld="roleName"></e:input>
</e:editPage>
<script type="text/javascript">
    $(function(){
        var roleId = '${param.roleId}';
        if(roleId){
            $.ajaxcore.fillFrm($("#frm").get(0),"query.htm?d=queryRoles",{"query.roleId":roleId});
        }

    });
    //called by parent frame
    function save(dia,roleId){
        var param = {} ;
        if(!!roleId){
            param["key.roleId"] = roleId ;
            $("#frm").attr("json","update.htm?d=pc_editRole");
        }else{
            $("#frm").attr("json","insert.htm?d=pc_addRole");
        }
        $.ajaxcore.getFrm($("#frm").get(0),param,function(data){
            if(data.row>0){
                $.dialog.tips(data.msg,'success',200);
                parent.query();
                dia.close();
            }else{
                $.dialog.tips(data.msg,'error',2);
            }
        });
    }
</script>
