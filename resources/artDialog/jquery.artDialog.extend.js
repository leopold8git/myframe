/**
 * @param sUrl (页面的url)
 * @param config
 * 在打开的窗口中嵌入一个页面
 */
$.dialog.open2 = function (sUrl,config) {
	var t = new Date().getTime();
	if(!config.content){
		config.content = "<iframe name='ifr_"+t+"' id='ifr_"+t+"' src='"+sUrl+"'  noresize='noresize'  border=0 frameborder=0 style='padding:0;margin:0;width:"+config.width+"px;height:"+config.height+"px;'></iframe>";
	}
	var adia = $.artDialog(config);
	adia.frame = $("#ifr_"+t).get(0).contentWindow;
}
/**
 * 
 * @returns
 */
//类似与wordpress登录失败后登录框可爱的左右晃动效果
$.dialog.fn.shake = function (){
    var style = this.DOM.wrap[0].style,
        p = [4, 8, 4, 0, -4, -8, -4, 0],
        fx = function () {
            style.marginLeft = p.shift() + 'px';
            if (p.length <= 0) {
                style.marginLeft = 0;
                clearInterval(timerId);
            };
        };
    p = p.concat(p.concat(p));
    timerId = setInterval(fx, 13);
    return this;
}
/**
 * art.dialog.notice({
    title: '万象网管',
    width: 220,// 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
    content: '尊敬的顾客朋友，您IQ卡余额不足10元，请及时充值',
    icon: 'face-sad',
    time: 5
});
 * @param options
 * @returns
 */
$.dialog.notice = function (options) {
    var opt = options || {},
        api, aConfig, hide, wrap, top,
        duration = 800;
        
    var config = {
        id: 'Notice',
        left: '100%',
        top: '100%',
        fixed: true,
        drag: false,
        resize: false,
        follow: null,
        lock: false,
        init: function(here){
            api = this;
            aConfig = api.config;
            wrap = api.DOM.wrap;
            top = parseInt(wrap[0].style.top);
            hide = top + wrap[0].offsetHeight;
            
            wrap.css('top', hide + 'px')
                .animate({top: top + 'px'}, duration, function () {
                    opt.init && opt.init.call(api, here);
                });
        },
        close: function(here){
            wrap.animate({top: hide + 'px'}, duration, function () {
                opt.close && opt.close.call(this, here);
                aConfig.close = $.noop;
                api.close();
            });
            
            return false;
        }
    };	
    
    for (var i in opt) {
        if (config[i] === undefined) config[i] = opt[i];
    };
    
    return artDialog(config);
};

