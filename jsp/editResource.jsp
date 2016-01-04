<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="e" uri="http://x.y.easyweb/tags"%>
<e:editPage formJson="update.htm?d=pc_editResource"  template="edit.ftl" title="编辑资源" >
    <e:text content="资源名称：" fid="elements"></e:text>
    <e:input fid="elements" id="resourceName" name="module.resourceName" fld="resourceName"></e:input>
</e:editPage>
<script type="text/javascript">
    $(function(){
        var resourceId = '${param.resourceId}';
        if(resourceId){
            $.ajaxcore.fillFrm($("#frm").get(0),"query.htm?d=queryResources",{"query.resourceId":resourceId});
        }

    });
    //called by parent frame
    function save(dia,resourceId){
        var param = {} ;
        if(!!resourceId){
            param["key.resourceId"] = resourceId ;
            $("#frm").attr("json","update.htm?d=pc_editResource");
        }else{
            $("#frm").attr("json","insert.htm?d=pc_addResource");
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
