<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="e" uri="http://x.y.easyweb/tags"%>
<e:queryPage formJson="query.htm?d=queryUsers" headers="用户编号|用户名称" template="query.ftl" title="账户查询1" qns="账户查询" >
    <e:input fid="elements" id="userId" name="query.userId"></e:input>
    <e:input fid="elements" id="username" name="query.username"></e:input>
    <e:input fid="elements" id="query" type="button" value="查询" onclick="query()" className="btn"></e:input>
    <e:td fid="tds" fld="userId"></e:td>
    <e:td fid="tds" fld="username"></e:td>
</e:queryPage>
<%--
<script type="text/javascript">
    $(function(){
        query();
    });
</script>--%>
