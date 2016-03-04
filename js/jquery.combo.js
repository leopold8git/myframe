/**
 * Extending jQuery with combo
 * Version: 1.0
 * Author: 曾炼平
 * 自定义下拉内容
 * Time : 2014/7/25
 * example:$("#a_yearAmount").combo({url:"",page:,autowidth:false,width:"200px",height:"60px",relativeLeft:-5,relativeTop:5});
 * 将一个现有的div（id 为 a_yearAmount）拼装在 input下面
 *
 *  changed at 20151207 增加分页，返回的json格式改成{"data":[],"page":{"curPage": ,"totalCount": ,"pageSize": "pageNum":}}
 *  changed at 20160201 将ul li 换成div ，解决 overflow auto 时 ， ul li 会一直显示滚动边框
 */
(function($) {

// some key codes
	var RETURN = 13;
	var TAB = 9;
	var ESC = 27;
	var ARRLEFT = 37;
	var ARRUP = 38;
	var ARRRIGHT = 39;
	var ARRDOWN = 40;
	var BACKSPACE = 8;
	var DELETE = 46;

	//弹出层对象
	function Combo(elem,conf){
		var self = this;
		//用于填充的数据
		self.data = [];
		self.page = {"curPage":1,"pageNum":0};
		elem = jQuery(elem);
		elem.data("combo",self);
		elemDom = elem.get(0);
		jQuery.extend(self,
			{
				_create : function(){
					var p = this._createPop();
					var self = this;
					if(conf.content){
						if(typeof conf.content == 'string'){
							p.append(conf.content);
						}else if(typeof conf.content == 'object'){
							conf.content.show();
							p.append(conf.content);
						}
					}else if(conf.url || conf.data){
						var arrow = this.addDownArrow();
						arrow.click(function(e){
							e.stopPropagation();
							//need reload
							if(conf.url){
								self.reloadUrl();
							} else{
								self.reloadData(conf.data);
							}
							if(p.is(":hidden")){
								p.show();
							}else{
								p.hide();
							}
						});
						//主要便于提交
						if(conf.valueInput){
							var valueInput = $("<input type='hidden'></input>");
							valueInput.attr("name",elem.attr("name"));
							var eid = elem.attr("id");
							var vid = (eid==null||eid=='')?(elem.attr("name")+'_hid'):(eid+'_hid');
							valueInput.attr("id",vid);
							//change elem name
							elem.attr("name","_combo_"+new Date().getTime());
							valueInput.insertBefore(elem);
							this.valueInput = valueInput;
						}
						if(conf.data){
							this.data = conf.data;
							this.loadData(conf.data);
						}else{
							this.ajaxLoad(conf.url);
						}
					}
				},
				_createPop : function(){
					var popDiv = $("<div></div>");
					popDiv.id = "pop_div_"+new Date().getTime();
					popDiv.css("position","absolute");
					popDiv.css({
						"padding" : "0",
						"border" : "1px solid black",
						"backgroundColor":"white",
						"overflow" : "hidden",
						"zIndex" : 99999
					});
					popDiv.css("display","none");

					if(!conf.input){
						elem.attr("readonly",true);
					}else{
						elem.removeAttr("readonly");
					}

					popDiv.css("min-width",elem.outerWidth());
					if(conf.relativeLeft){
						popDiv.css("left",(getElementLeft(elemDom)+conf.relativeLeft)+"px");
					}else{
						popDiv.css("left",getElementLeft(elemDom)+"px");
					}
					if(conf.relativeTop){
						popDiv.css("top",(getElementTop(elemDom)+elem.outerHeight()+conf.relativeTop)+"px");
					}else{
						popDiv.css("top",getElementTop(elemDom)+elem.outerHeight()+"px");
					}
					popDiv.css("height",'auto');
					if($.fn.bgiframe)
						$(popDiv).bgiframe({height: popDiv.get(0).scrollHeight});

					if(!conf.autowidth){
						popDiv.css("width",conf.width||elem.outerWidth());
					}

					$("body").append(popDiv);

					elem.data("pop_div",popDiv);
					return popDiv;
				},
				getPopDiv : function(){
					return elem.data("pop_div");
				},
				ajaxLoad : function(url){
					if(url){
						var param = conf.page || {} ;
						$.get(url,param,function(res){
							self.data = res.data;
							conf.page = res.page ;
							self.loadData(self.data);
						});
					}
				},
				loadData : function(data){
					var self = this;
					if(!data || data.length<1){
						return;
					}
					var droplist = $("<div></div>");
					droplist.css({background: 'none repeat scroll 0 0 #f3f9fc',
						border: '1px solid #57a8e4',
						left: '0',
						overflow : 'auto',
						//    position: 'absolute',
						top: '0px',
						margin:0,
						padding:0,
						width: '100%'});

					var dropItemCss = {color: '#333',
						listStyle: 'outside none none',
						margin: 0,
						padding: 0,
						cursor: 'default',
						height : '20px',
						lineHeight: '20px',
						paddingLeft: '2px',
						whiteSpace : 'nowrap'
					}
					var actualH = 0;
					//添加默认值
					if(conf.headKey && conf.headValue != undefined){
						var dropItem = $("<div></div>");
						dropItem.css(dropItemCss);
						actualH += dropItem.height();

						dropItem.html(conf.headKey);

						dropItem.attr("val",conf.headValue);
						dropItem.mouseover(function(){
							$(this).css("background","none repeat scroll 0 0 #57a8e4");
						});
						dropItem.mouseout(function(){
							$(this).css("background","none repeat scroll 0 0 #f3f9fc");
						});
						dropItem.click(function(){
							elem.val($(this).html());
							elem.attr("val",$(this).attr("val"));
							if(self.valueInput){
								self.valueInput.val($(this).attr("val"));
							}
						});
						droplist.append(dropItem);
					}

					if(data){
						for(var i=0;i<data.length;i++){
							var dropItem = $("<div></div>");
							dropItem.css(dropItemCss);
							actualH += dropItem.height();
							var j = data[i];
							dropItem.html(j[conf.optionField||'text']);
							dropItem.attr("val",j[conf.valueField||'value']); // 这里不要使用li.attr("value",xxx) , value 是元素固有的属性，当xxx为空时，如果是value，则li.attr("value") 会等于0
							//选中 指定selectedValue 项
							var sv = elem.attr("selectedValue") ;
							if( sv && sv == j[conf.valueField||'value']){
								elem.val(j[conf.optionField||'text']);
								if(self.valueInput){
									self.valueInput.val(sv);
								}
							}else{//到headValue中找
								if(sv && conf.headKey && conf.headValue != undefined && sv == conf.headValue){
									elem.val(conf.headKey);
									if(self.valueInput){
										self.valueInput.val(sv);
									}
								}
							}
							dropItem.mouseover(function(){
								$(this).css("background","none repeat scroll 0 0 #57a8e4");
							});
							dropItem.mouseout(function(){
								$(this).css("background","none repeat scroll 0 0 #f3f9fc");
							});
							dropItem.click(function(){
								elem.val($(this).html());
								elem.attr("val",$(this).attr("val"));
								if(self.valueInput){
									self.valueInput.val($(this).attr("val"));
								}
							});
							droplist.append(dropItem);
						}
						//分页信息 page : {curPage: '1', pageSize:'10'}
						if (conf.page) {
							var pageItem = $("<div id='combo_page' style='font-size:13px;'></div>");
							pageItem.css(dropItemCss);
							pageItem.find("a").css({"text-decoration":"none"});


							var lastPageSpan = "<a href='javascript:void(0)' id='combo_last' style='color:blue;'>上一页 </a> ";
							var nextPageSpan = "<a href='javascript:void(0)' id='combo_next' style='color:blue;'>下一页</a>";
							if (conf.page.curPage>1) {
								pageItem.append(lastPageSpan);
								pageItem.find("#combo_last").click(function(e){
									e.stopPropagation();
									conf.page.curPage = parseInt(conf.page.curPage) -1 ;
									self.reloadUrl();
								});
							}
							if (conf.page.curPage<conf.page.pageNum) {
								pageItem.append(nextPageSpan);
								pageItem.find("#combo_next").click(function(e){
									e.stopPropagation();
									conf.page.curPage = parseInt(conf.page.curPage) +1 ;
									self.reloadUrl();
								});
							}
							if(conf.page.curPage >1 ||  conf.page.curPage<conf.page.pageNum){
								droplist.append(pageItem);
								actualH += pageItem.height();
							}
						}

					}
					var boxId = "_combobox_"+new Date().getTime();
					droplist.attr("id",boxId);
					this.getPopDiv().append(droplist);
					var maxH = conf.maxHeight || 150;
					/*if(actualH<=maxH){
						ul.height(actualH);
						ul.css("overflow","scroll");
					}else{
						ul.height(maxH);
					}*/
					if(actualH>maxH){
						droplist.height(maxH);
						droplist.css("overflow","scroll");
					}
				},
				getData : function(){
					return this.data;
				},
				reloadUrl : function(url){
					var _url = url || conf.url
					if( _url ){
						var param = conf.page || {} ;
						$.get(_url,param,function(res){
							self.data = res.data;
							conf.page = res.page ;
							self.reloadData(self.data);
						});
					}
				},
				reloadData : function(data){
					this.clearPopDiv();
					this.loadData(data);
				},
				clearPopDiv : function(){
					this.getPopDiv().html('');
				},
				addDownArrow : function(){
					var meLeft = getElementLeft(elemDom);
					var meTop = getElementTop(elemDom);
					//给input右侧添加下拉箭头
					var img = $("<img></img>");
					img.attr("src",getBasePath("jquery.combo")+"combo/down_arrow_163.png");
					var d = $("<span></span>");
					d.css({cursor:'pointer',lineHeight:elem.outerHeight()+'px',textAlign:'center',position:'absolute',left :(meLeft+elem.width()-elem.height())+'px',top:meTop+'px',width:elem.outerHeight()+'px',height:elem.outerHeight()+'px'});

					img.css({width:'10px',height:'10px',marginLeft:(d.width()-20)/2 +'px',marginTop:(d.height()-10)/2 +'px'});
					d.append(img);
					elem.data("downArrow",d);
					$("body").append(d);
					return d;
				},
				getValue : function(){
					return elem.attr("val");
				},
				setValue : function(value){
					if(this.valueInput){
						this.valueInput.val(value);
					}
					elem.val(value)
					elem.attr("val",value);
				},
				getUrlParam : function(){
					var name = elem.attr("name");
					var value = elem.attr("val");
					return "&"+name+"="+value;
				},
				getConf : function(){
					return conf ;
				},
				clear : function(){
					elem.data("pop_div").html('');
					elem.data("downArrow").remove();
				},
					resetPosition : function(){//由于文本框 的位置可能变化，所以下拉框与下拉箭头位置都会变
					var meLeft = getElementLeft(elem.get(0));
					var meTop = getElementTop(elem.get(0));
					var d = elem.data("downArrow") ;
					if(d){
						d.css({lineHeight:elem.outerHeight()+'px',left :(meLeft+elem.width()-elem.height())+'px',top:meTop+'px',width:elem.outerHeight()+'px',height:elem.outerHeight()+'px'});
						d.find("img").css({width:'10px',height:'10px',marginLeft:(d.width()-20)/2 +'px',marginTop:(d.height()-10)/2 +'px'});
					}
					var popDiv = elem.data("pop_div");
					if(popDiv){
						popDiv.css("min-width",elem.outerWidth());
						if(conf.relativeLeft){
							popDiv.css("left",(meLeft+conf.relativeLeft)+"px");
						}else{
							popDiv.css("left",meLeft+"px");
						}
						if(conf.relativeTop){
							popDiv.css("top",(meLeft+elem.outerHeight()+conf.relativeTop)+"px");
						}else{
							popDiv.css("top",meTop+elem.outerHeight()+"px");
						}
					}
				}
			});
	}

	$.fn.combo = function(opt){
		//input settings
		var me = $(this);
		var me_dom = $(this).get(0);
		if(!me.is('input:text'))
			return;
		// default options
		var options = $.extend({
				height    : 150 ,
				autowidth : true ,
				//当focusin时，执行
				focusin : true,
				focusin_fun : function(inputObj,popDivObj){
				},
				//input 是否可编辑,默认不可编辑
				input : false,
				relativeLeft : 0,
				relativeTop : 0,
				//search conf
				cache : true,
				delay     : 500 ,
				timeout   : 5000 ,
				multi     : false ,
				noresults : 'No results'
			},
			opt);
		var pop = new Combo(me_dom,options);
		pop._create();
		var popDiv = pop.getPopDiv();

		//点击其它隐藏
		$(document).bind("click",function(eventObj){
			if(eventObj.target != me_dom &&eventObj.target != popDiv.get(0)){
				popDiv.css("display","none");
			}
		});

		//点击弹出
		if(options.focusin){
			me.bind("focusin",function(eventObj){
				popDiv.css("display","block");
				if(options.focusin_fun){
					options.focusin_fun(me_dom,popDiv.get(0));
				}
			});
		}



		// handle normal characters here
		me.bind('input',function(ev) {
			switch(ev.which) {
				case RETURN: case ESC: case ARRLEFT: case ARRRIGHT: case ARRUP: case ARRDOWN:
				return false;
				default:
					getSuggestions(getUserInput());
			}
			return true;
		});

		var getSuggestionsTimer;

		// get suggestions
		function getSuggestions(val){

			if (options.cache){//如果是缓存，则直接从data中取
				var d = pop.getData();
				var newarr = [];
				if(d){

					for(var i=0;i<d.length && val !='';i++){
						if(d[i][options.optionField||'text'].indexOf(val) != -1){
							newarr.push(d[i]);
						}
					}
				}

				pop.reloadData(val==""?d:newarr);
			}
			else{// do new request
				if(getSuggestionsTimer)
					clearTimeout(getSuggestionsTimer);

				getSuggestionsTimer = setTimeout(
					function(){
						suggestions = [];
						// call pre callback, if exists
						if($.isFunction(options.pre_callback))
							options.pre_callback();
						// call get
						if($.isFunction(options.get)){
							if( $.isArray(options.get(val))){
								pop.reloadData(options.get(val));
							}
						}
						// call AJAX get
						else if($.isFunction(options.ajax_get)){
							//showLoadingTimer = setTimeout(show_loading,options.delay);
							options.ajax_get(val,function(data){
								pop.reloadData(data);
							});
						}
					},
					options.delay );
			}
			return false;
		};


		function getUserInput(){
			var val = me.val();
			return ltrim(val);
		}

		function ltrim(s){
			if(s == undefined || !s) return '';
			return s.replace(/^\s+/g,'');
		}



		return pop;//返回弹出层对象

	}




//获取popdiv的left
	function getElementLeft(element){
		var actualLeft = element.offsetLeft;
		var current = element.offsetParent;
		while (current !== null){
			actualLeft += current.offsetLeft;
			current = current.offsetParent;
		}
		return actualLeft;
	}
//获取popdiv的top
	function getElementTop(element){
		var actualTop = element.offsetTop;
		var current = element.offsetParent;
		while (current !== null){
			actualTop += current.offsetTop;
			current = current.offsetParent;
		}
		return actualTop;
	}

	/**
	 * 获取引用界面的上下文路径
	 */
	function getBasePath(jsName){
		var basePath = "";
		var scripts = document.getElementsByTagName("script");
		for(var i=0;i<scripts.length;i++){
			var src = scripts[i].getAttribute("src");
			var end = src.lastIndexOf(".");
			var start = src.lastIndexOf("/");
			if(end > start){
				var name = src.substring(start+1,end);
				if(jsName && jsName == name){
					basePath = start==-1?"":src.substring(0,start+1);
					return basePath;
				}
			}
		}
	}

})(jQuery);
