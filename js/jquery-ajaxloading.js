/**
 * Created by zenglp 20160120
 */
/**
 * jquery ajax 加载时 通用 的  显示 正在加载等待 提示
 *
 */
(function ($) {
    var ajaxCoreBaseUrl;
    var scripts = document.getElementsByTagName("script");
    for(var i=0;i<scripts.length;i++){
        if(/.*\/jquery-ajaxloading\.js$/g.test(scripts[i].getAttribute("src")))
            ajaxCoreBaseUrl=scripts[i].getAttribute("src");
    }
    var pos=ajaxCoreBaseUrl.lastIndexOf("/");
    if(pos>=0) ajaxCoreBaseUrl = ajaxCoreBaseUrl.substring(0,pos)+"/"; else ajaxCoreBaseUrl="";

    var  processObj  = {} ;

    processObj.processStart = function(msg) {
        var that = this;
        this.timer = setTimeout(function() {
            that.process(msg);
        }, 1000);
    }
    processObj.process=function(msg){
        var topWinBody = document.getElementsByTagName(document.compatMode == "BackCompat" ? "BODY" : "HTML")[0];
        topWinBody.styleOverflow = topWinBody.style.overflow;
        if(window.navigator.userAgent.indexOf("Firefox/3.6") != -1){//在firefox下改变overflow属性会导致scrollTop=0;
            var topWinBodyScrollTop=topWinBody.scrollTop;
            topWinBody.style.overflow = "hidden";
            topWinBody.scrollTop=topWinBodyScrollTop;
        }else{
            topWinBody.style.overflow = "hidden";
        }
        this.showText(msg);
    }
    processObj.processEnd=function(){
        clearTimeout(this.timer);
        var textDiv = $("#_DialogBGDivText").get(0);
        if(!textDiv)return;
        textDiv.style.display="none";
        var topWinBody = document.getElementsByTagName(document.compatMode == "BackCompat" ? "BODY" : "HTML")[0];
        if(topWinBody.styleOverflow != undefined)
            if(window.navigator.userAgent.indexOf("Firefox/3.6") != -1){//在firefox下改变overflow属性会导致scrollTop=0;
                var topWinBodyScrollTop=topWinBody.scrollTop;
                topWinBody.style.overflow = topWinBody.styleOverflow;
                topWinBody.scrollTop=topWinBodyScrollTop;
            }else{
                topWinBody.style.overflow = topWinBody.styleOverflow;
            }
    }

    processObj.showText=function(text){
        this.showHtml("<table><tr><td><img src='"+ajaxCoreBaseUrl+"img/loading.gif'></td><td nowrap>"+text+"</td></tr></table>",40);
    }

    processObj.showHtml=function(text,height){
        var bgText = $("#_DialogBGDivText").get(0);
        if (!bgText) {
            bgText = document.createElement("div");
            bgText.id = "_DialogBGDivText";
        }
        var iWidth = $(window).width();
        var iHeight = $(window).height();
        var sWidth = $(window).scrollLeft();
        var sHeight = $(window).scrollTop();
        var top = (iHeight-height)/2 + sHeight ;
        var left = (iWidth-200)/2 + sWidth ;
        //console.info("top:"+top+"---"+"left:"+left);
        if(!height)height=0;
        bgText.style.cssText = "position:absolute;height:"+height+"px;left:"+left+"px;top:"+top+"px;z-index:9999;text-align:center;background:white";
        document.getElementsByTagName("BODY")[0].appendChild(bgText);
        $(bgText).html(text).css("display","block");
    }

    $( document ).ajaxSend(function() {
        //console.info("ajaxSend !");
        processObj.processStart("<nobr>&nbsp;&nbsp;查询中,请稍候...</nobr>");
    });
    $(document).ajaxComplete(function(){
        //console.info("processEnd !");
        processObj.processEnd();
    });

})(jQuery);
