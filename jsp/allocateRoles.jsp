<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="e" uri="http://x.y.easyweb/tags"%>
<e:page  template="page.ftl" title="分配角色" jsLink="resources/editor/one2many0/one2many0.js" styleLink="resources/editor/one2many0/one2many0.css">

</e:page>
<script type="text/javascript">
    var one2many  ;
    $(function(){
        var userId = '${param.userId}';
        one2many = $("body").one2many0({url:"query.htm?d=queryRoles&subquery.userId="+userId,"ths":"角色名称|选择","flds":"roleName|_checkbox"});
    });

    function save(dia){
        var userId = '${param.userId}';
        var selectedRoleIds = one2many.getQueryParam("roleId");
        //alert(selectedRoleIds)

        $.get('delete.htm?d=pc_setUserRole',{'key.userId':userId,'module1.userId':userId,'module1.roleId':selectedRoleIds},function(data){
            if(data.row>0){
                $.dialog.tips(data.msg,'success',1);
                // dia.close();
            }else{
                $.dialog.tips(data.msg,'error',2);
            }
        });

    }
</script>
