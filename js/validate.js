var focusObj = null;
var validate_tipsClass_failed="validatTips_failure";//ʧ����ʾ����ʽ��
var validate_tipsClass_success="validatTips_success";//�ɹ���ʾ����ʽ��
var Validate_inputClass_failed="input_validation_failed";//��֤ʧ�ܵ��������ʽ��
var Validate_inputClass_failed="input_validation_failed";//��֤ʧ�ܵ��������ʽ��
$(function(){
	var xOffset = -5; // x distance from mouse
    var yOffset = 5; // y distance from mouse  

	$("[reg],[ajaxurl]:not([reg]),[tip]").live("blur",function(){
		focusObj = null;
	    validate(this);
	    showTipsExplain(this,"hide");
	}).live("focus",function(){
	    validateBefore($(this));
		$(this).removeClass(Validate_inputClass_failed);
		focusObj = this;
		showTipsExplain(this,"show");
	}).change(function(){
	    validate(this);
	}).live("mouseover",function(){
		if($(this).attr("error"))
		    ChangeTips($(this),"false");
	}).live("mouseout",function(){
		if($(this).attr("error"))
		    ChangeTips($(this),"remove",null);
	});
	
	/*$("form").submit(function(){
	    return validateForm(this);
	});*/
});

function validateBefore(thisObj){
    var fun=thisObj.data('validateBefore');
    if(typeof(fun)=='function'){
       fun(thisObj);
    }
}

function initValidateForm(){
	var xOffset = -5; // x distance from mouse
    var yOffset = 5; // y distance from mouse  

	$("[reg],[ajaxurl]:not([reg]),[tip]").live("blur",function(){
		focusObj = null;
	    validate(this);
	    showTipsExplain(this,"hide");
	}).live("focus",function(){
		$(this).removeClass(Validate_inputClass_failed);
		focusObj = this;
		 showTipsExplain(this,"show");

	}).change(function(){
	    validate(this);
	});
	
	$("form").submit(function(){
	    return validateForm(this);
	});
}


function validate(target){
    if($(target).attr("skipValidate"))return;
    if($(target).attr("check") != undefined){
        diyValidate($(target).attr("check"),$(target));
    }else if($(target).attr("reg") == "CheckboxMenu"){
        checkboxMenuValidate($(target));
    }else if($(target).attr("reg") == "MultipleEnter"){
        multipleEnterValidate($(target));
    }else if($(target).attr("reg") != undefined){
        commonValidate($(target));
    }
}

function checkboxMenuValidate(target){
    var isValid = false;
    var menu = Qfang.MenuManager.getMenu(target.attr("id"));
    if (menu) {
        var result = menu.getValue();
        if (result && result.length >= 1) {
            isValid = true;
        }
    }
    return setStyle(isValid,target);
}

function multipleEnterValidate(target){
    var isValid = false;
    var multipleEnter = qfang.multiple.Manager.getMultipleEnter(target.find("input").attr("id"));
    if (multipleEnter) {
        var result = multipleEnter.getValue();
        if (result && result.length >= 1) {
            isValid = true;
        }
    }
    return setStyle(isValid,target);
}

function diyValidate(cb,target){
    return setStyle(eval(cb+"(target)"),target);
}

function setStyle(isValid,target){
    if(isValid){
        change_error_style(target,"remove");
        change_tip(target,null,"remove");
        return isValid;
    }else{
        change_error_style(target,"add");
        change_tip(target,null,"remove");
        return isValid;
    }
}

function validateForm(obj){
		var isSubmit = true;
		$(obj).find("[reg],[ajaxurl]:not([reg])").each(function(){
		     if($(this).attr("skipValidate"))return;
			if($(this).attr("check") != undefined){
                if(!diyValidate($(this).attr("check"),$(this))){
                    isSubmit = false;
                }
            }else if($(this).attr("reg") == "CheckboxMenu"){
                if(!checkboxMenuValidate($(this))){
                    isSubmit = false;
                }
            }else if($(this).attr("reg") == "MultipleEnter"){
                if(!multipleEnterValidate($(this))){
                    isSubmit = false;
                }
            }else{ 
				if(!commonValidate($(this))){
					isSubmit = false;
				}
			 }
		});

		return isSubmit; 
}

function commonValidate(obj){
	var objValue = $.trim(obj.attr("value"));
	regText=obj.attr("reg")
	var maxLength=0; //����ַ���
	var minLength=0; //��С�ַ���
	var allowNull = false;	//���������Ϊ��ֵ
	var minNumber = false;	//�����������Сֵ ��������С
	if(obj.attr("maxValLength")){
		maxLength = parseInt(obj.attr("maxValLength"));
	}
	if(obj.attr("minValLength")){
		minLength = parseInt(obj.attr("minValLength"));
	}
	if(obj.attr("minNumber")){
		minNumber = parseFloat(obj.attr("minNumber"));
	}
	if(obj.attr("allowNull")){
		allowNull = obj.attr("allowNull");
	}
	
	var mapValue="0,0";
 	switch(regText){
		case "number" :
			regChar=/^-?[0-9]*$/;
		break;
		case "positiveNumber" :
			regChar=/^[0-9]*$/;
		break;
		case "Float" :
			regChar=/^[0-9]+\.?[0-9]*$/;
		break;
		case "Email" :
			regChar=/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
		break;
		case "Chinese":
			regChar=/^[\u4e00-\u9fa5]+$/;	
		break;
		case "Phone":
			regChar=/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
		break;
		case "Required":
			regChar=/.+/;	
		break;
		case "IdCard":
			regChar=/\d{15}|\d{18}/;
		break;
		case "Password":
			regChar=/\S{6,}/;
		break;
		case "Mobile":
			regChar=/^[1][3458]\d{9}$/;
		break;
		case "GlobalMobile":
			regChar=/^(([1][3458]\d{9})||([569]\d{7}))$/;
		break;
		case "ContactPhone" :
			  regChar=/^[0-9]{11,14}$/;
		break;
		case "JobNumber" :
			regChar=/^[0-9]{4,10}$/;
		break;	
		case "isPhone":
			  regChar=/^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
		break;
		case "MapCheck":  //��ʾ�жϵ�ͼ
			  mapValue=objValue;
			 regChar='';
		break;
		case "OnlyLength":
			 regChar='';
		break;
		case "NoSign":
			 regChar='^[^<>]*$';
		break;
		case "ChEnNumber":
			 regChar=/^(\w|[\u4E00-\u9FA5])*$/;
			 break;
		default:
		;
	}
	// alert(regText)
	var reg = new RegExp(regChar);
	var isMobile=/^(?:13\d|15\d)\d{5}(\d{3}|\*{3})$/;   
	if((objValue==null || objValue=="") && allowNull == false){
		change_error_style(obj,"add");
		change_tip(obj,null,"remove");
		return false;
	}else{
		//�ж��ֻ��������������
		if(regText == "isPhone" && objValue != ""){
			if(!reg.test(objValue) && !isMobile.test(objValue)){
				change_error_style(obj,"add");
				change_tip(obj,null,"remove");
				return false;	
			}
		}else if(regText=="IdCard"){
			if(!IdCardValidate(objValue)){
				change_error_style(obj,"add");
				change_tip(obj,null,"remove");
				return false;
			}
		}else{
			if(regChar && objValue){
				if(!reg.test(objValue)){
					change_error_style(obj,"add");
					change_tip(obj,null,"remove");
					return false;
				}
			}else{
				if(regText =="MapCheck" &&  mapValue == "0,0"){
					change_error_style(obj,"add");
					change_tip(obj,null,"remove");
					return false;
				}
			}
			if(maxLength > 0 && objValue.length > maxLength){
				change_error_style(obj,"add");
				change_tip(obj,null,"remove");
				return false;
			}
			if(minLength > 0 && objValue.length < minLength){
				change_error_style(obj,"add");
				change_tip(obj,null,"remove");
				return false;
			}
			if(minNumber!==false&&minNumber>=parseFloat(objValue)){
			    change_error_style(obj,"add");
				change_tip(obj,null,"remove");
				return false;
			}
		}
		if(obj.attr("ajaxurl") == undefined){
				change_error_style(obj,"true");
				change_tip(obj,null,"remove");
				return true;
		}else{
			return ajax_validate(obj);
		}
	}
}
/**  
 * ���֤15λ�������dddddd yymmdd xx p   
 * dddddd��������   
 * yymmdd: ����������   
 * xx: ˳������룬�޷�ȷ��   
 * p: �Ա�����Ϊ�У�ż��ΪŮ  
 * <p />  
 * ���֤18λ�������dddddd yyyymmdd xxx y   
 * dddddd��������   
 * yyyymmdd: ����������   
 * xxx:˳������룬�޷�ȷ��������Ϊ�У�ż��ΪŮ   
 * y: У���룬��λ��ֵ��ͨ��ǰ17λ������  
 * <p />  
 * 18λ�����Ȩ����Ϊ(���ҵ���) Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,1 ]  
 * ��֤λ Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]   
 * У��λ���㹫ʽ��Y_P = mod( ��(Ai��Wi),11 )   
 * iΪ���֤��������������� 2...18 λ; Y_PΪ��ѾУ��������У��������λ��  
 *   
 */  
  
var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// ��Ȩ����   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// ���֤��֤λֵ.10����X   
function IdCardValidate(idCard) {   
    idCard = trim(idCard.replace(/ /g, ""));   
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);   
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");// �õ����֤����   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
/**  
 * �ж����֤����Ϊ18λʱ������֤λ�Ƿ���ȷ  
 * @param a_idCard ���֤��������  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0; // ������Ȩ��ͱ���   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;// �����λΪx����֤���滻Ϊ10�����������   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];// ��Ȩ���   
    }   
    valCodePosition = sum % 11;// �õ���֤����λ��   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}   
/**  
 * ͨ�����֤�ж�������Ů  
 * @param idCard 15/18λ���֤����   
 * @return 'female'-Ů��'male'-��  
 */  
function maleOrFemalByIdCard(idCard){   
    idCard = trim(idCard.replace(/ /g, ""));// �����֤���������������ַ����пո�   
    if(idCard.length==15){   
        if(idCard.substring(14,15)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else if(idCard.length ==18){   
        if(idCard.substring(14,17)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else{   
        return null;   
    }   

}   
 /**  
  * ��֤18λ�����֤�����е������Ƿ�����Ч����  
  * @param idCard 18λ�����֤�ַ���  
  * @return  
  */  
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // ������getFullYear()��ȡ��ݣ�����ǧ�������   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}   
  /**  
   * ��֤15λ�����֤�����е������Ƿ�����Ч����  
   * @param idCard15 15λ�����֤�ַ���  
   * @return  
   */  
  function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // ���������֤�е����������迼��ǧ��������ʹ��getYear()����   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }   
//ȥ���ַ���ͷβ�ո�   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
} 

function ajax_validate(obj){
	var pass = true;
	var url_str = obj.attr("ajaxurl");
	var dataName_str =obj.attr("name");
	var dataVal_str = obj.val();
	if(dataVal_str != null && dataVal_str != ''){
		$.ajax({
			"url" : url_str ,
			"cache" : false,
			"async" : false,
			"data" : {"val":dataVal_str},
			"success" : function(d){
				if(d && d.code == "1"){
					change_error_style(obj,"remove");
					change_tip(obj,null,"remove");
					return true;
				}else{
					change_error_style(obj,"add");
					change_tip(obj, d?d.msg:null,"add");
					pass = false;
					return false;
				}
			}
		});
	}
  return pass ;
}



function change_tip(obj,msg,action_type){
	 //�����ǰԪ�������ص�����ʾ������Ϣ
    if($(obj).is(":hidden")){
		return;
    }
    
	if(obj.attr("tip") == undefined){//��ʼ���ж�TIP�Ƿ�Ϊ��
		obj.attr("is_tip_null","yes");
	}
	if(action_type == "add"){
		if(obj.attr("is_tip_null") == "yes"){
			obj.attr("tip",msg);
		}else{
			if(msg != null){
				if(obj.attr("tip_bak") == undefined){
					obj.attr("tip_bak",obj.attr("tip"));
				}
				obj.attr("tip",msg);
			}
		}
	}else{
		if(obj.attr("is_tip_null") == "yes"){
			obj.removeAttr("tip");
			obj.removeAttr("tip_bak");
		}else{
			obj.attr("tip",obj.attr("tip_bak"));
			obj.removeAttr("tip_bak");
		}
	}
}

function ChangeTips(obj,type,msg){
     //�����ǰԪ�������ص�����ʾ������Ϣ
    if($(obj).is(":hidden")){
		return;
    }
    
	var parent=obj.parent()
	var oWidth=$(obj).outerWidth();
	var oHeight=$(obj).outerHeight();
	var oX=$(obj).offset().left;
	var oY=$(obj).offset().top;
	var posY=oY+oHeight+4;
	var posX=oX;
	var thisTureId=obj.attr("id")+"_trueTips";
	
	
	var tureTipsId=obj.attr("tureTipsId");
	var falseTips=obj.attr("falseTips");
	
	if(!tureTipsId){
		tureTipsId="vTip_true_"+new Date().getTime();
	    obj.attr("tureTipsId",tureTipsId);	
    }
	
	if(!falseTips){
		falseTips="Tip_false_"+new Date().getTime()
	    obj.attr("falseTips",falseTips);	
    }
	
	//var tureTipsId="vTip_true_"+tId;//��ʾ��ȷ�Ķ���ID
	//var falseTips="vTip_false_"+tId;//��ʾ����Ķ���ID
	
	var tipText ;
	if(msg){
		tipText = msg;
	}else{
		tipText = obj.attr("tip") ;
	}
	if(type=='false'){ 
		if($(obj).attr('tip') != undefined){
			if($("#"+falseTips).length <1 ){
				$("body").append('<div id="'+falseTips+'" class="'+validate_tipsClass_failed+'"><span class="t_arrow"></span><div class="t_cont" style=" ">' + tipText + '</div></div>' );
				$("#"+falseTips).css({"left":oX+"px","top":oHeight+oY-1+"px","min-width": oWidth+"px"});
			}else{
				$("#"+falseTips).show()
			}
		}
		$("#"+tureTipsId).remove();
		
	}else if(type=='remove'){
		$("#"+falseTips).remove();
		$("#"+tureTipsId).remove();
	}else{			  
		if($(obj).attr('tip') != undefined){
			$("#"+falseTips).remove()
			if($("#"+tureTipsId).length < 1 ){

				$("body").append( '<div id="'+tureTipsId+'" class="'+validate_tipsClass_success+'"></div>' );
				$("#"+tureTipsId).css({"left":oWidth+oX-10+"px","top":oY-10+"px","display":"block","height":oHeight+"px"});

			}else{
				$("#"+tureTipsId).show()
			}
		}	
	}

}
 
function showTipsExplain(obj,type){
    //�����ǰԪ�������ص�����ʾ������Ϣ
    if($(obj).is(":hidden")){
		return;
    }

	var oWidth=$(obj).outerWidth();
	var oHeight=$(obj).outerHeight();
	var oX=$(obj).offset().left;
	var oY=$(obj).offset().top;
	var posY=oY+oHeight+4;
	var posX=oX;
	var tid=$(obj).attr("id");
	var expTid="validatTips_exp_"+tid;
	var expContent=$(obj).attr("explain")
	var tureTipsId="vTip_true_"+tid;//��ʾ��ȷ�Ķ���ID
	var falseTips="vTip_false_"+tid;//��ʾ����Ķ���ID
	if($(obj).attr("explain") != undefined){
		if(type=="show"){
			if($("#"+expTid).length < 1 ){
				$("body").append('<div id="'+expTid+'" class="'+validate_tipsClass_failed+' validatTips_focusInfo "><span class="t_arrow"></span><div class="t_cont" style=" ">' +expContent + '</div></div>' );
				$("#"+expTid).css({"left":oX+"px","top":oHeight+oY-1+"px","min-width": oWidth+"px"});
				
			} 
			$("#"+falseTips).remove()
			$("#"+tureTipsId).remove()
		}else{
			$("#"+expTid).remove()
	
		}
	}
}

function resetValidate(formObj){
	/*$(formObj).find('.'+Validate_inputClass_failed).each(function(){
		$(this).removeClass(Validate_inputClass_failed);
 	}) 
 	$(formObj).find('.'+validate_tipsClass_success).each(function(){
 		$(this).remove();
 	})
 	$(formObj).find('.'+validate_tipsClass_failed).each(function(){
 		$(this).remove();
 	})*/
 	$(formObj).find('[reg],[ajaxurl]:not([reg]),[tip]').each(function(){
		var tureTipsId=$(this).attr("tureTipsId");
		var falseTipsId=$(this).attr("falseTips");
		
		$("#"+tureTipsId).remove();
		$("#"+falseTipsId).remove();
 	})
	
}

/*���ڶ�������֤��ʾ��Ϣ,
obj����֤����������
action_type: "add" ������"remove" �Ƴ�
msg:��ʾ��Ϣ
*/
function change_error_style(obj,action_type,msg){
	if(action_type == "add"){
		obj.addClass(Validate_inputClass_failed);
		obj.attr("error",true);
		//ChangeTips(obj,'false',msg);
	}else if(action_type == "remove"){
		obj.removeClass(Validate_inputClass_failed);
		ChangeTips(obj,'remove',msg);
		obj.removeAttr("error");
	}else{
		obj.removeClass(Validate_inputClass_failed);
		ChangeTips(obj,'true',msg);
		obj.removeAttr("error");
	}
}

function hideTips(obj){
	var tId=obj.attr("id");
	var tureTipsId="vTip_true_"+tId;//��ʾ��ȷ�Ķ���ID
	var falseTips="vTip_false_"+tId;//��ʾ����Ķ���ID
	$('#'+tureTipsId).hide();
	$('#'+falseTips).hide();
}

$.fn.validate_callback = function(msg,action_type,options){
	this.each(function(){
		if(action_type == "failed"){
			change_error_style($(this),"add");
			change_tip($(this),msg,"add");
		}else{
			change_error_style($(this),"remove");
			change_tip($(this),null,"remove");
		}
	});
};