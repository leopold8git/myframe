<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="e" uri="http://x.y.easyweb/tags"%>
<e:editPage formJson="update.htm?d=pc_editUser"  template="edit.ftl" title="编辑用户" >
    <e:text content="用户名称：" fid="elements"></e:text>
    <e:input fid="elements" id="username" name="module.username" fld="username"></e:input>
</e:editPage>
<script type="text/javascript">
    $(function(){
        var userId = '${param.userId}';
        if(userId){
            $.ajaxcore.fillFrm($("#frm").get(0),"query.htm?d=queryUsers",{"query.userId":userId});
        }

    });
    //called by parent frame
    function save(dia,userId){
        var param = {} ;
        if(!!userId){
            param["key.userId"] = userId ;
            $("#frm").attr("json","update.htm?d=pc_editUser");
        }else{
            $("#frm").attr("json","insert.htm?d=pc_addUser");
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
