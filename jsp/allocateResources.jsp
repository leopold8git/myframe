<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="e" uri="http://x.y.easyweb/tags"%>
<e:page  template="page.ftl" title="分配资源" jsLink="resources/ztree/jquery.ztree.all-3.5.js" styleLink="resources/ztree/zTreeStyle/zTreeStyle.css">
</e:page>
<script type="text/javascript">
    var zTreeObj,
            setting = {
                view: {
                    selectedMulti: false
                },
                check: {
                    enable: true,
                    chkboxType : { "Y":"", "N":""}
                }
            },
            zTreeNodes = [
                {"name":"网站导航", open:true, children: [
                    { "name":"google", "url":"http://g.cn", "target":"_blank"},
                    { "name":"baidu", "url":"http://baidu.com", "target":"_blank"},
                    { "name":"sina", "url":"http://www.sina.com.cn", "target":"_blank"}
                    ]
                }
            ];
    $(function(){
        var div = $("<div/>").css({"width":"100%","text-align":"center"});
        div.append($("<ul/>").attr("id","ztree").addClass("ztree").css({width:"230px", overflow:"auto",margin:"auto"}));
        $("body").append(div);
        $.get("query.htm?d=queryResources",function(j){
            if(j && j.res){
                zTreeNodes = [{"name":"资源选择", open:true,children:[]}];
                for(var i=0;i<j.res.length;i++){
                    var t = {};
                    t.name = j.res[i]['resourceName'];
                    t.id = j.res[i]['resourceId'];
                    zTreeNodes[0].children.push(t);
                }
            }
            zTreeObj = $.fn.zTree.init($("#ztree"), setting,zTreeNodes);
        });

    });

    function save(){
        var nodes = zTreeObj.getCheckedNodes(true);
        alert($.toJSON(nodes))
    }
</script>
