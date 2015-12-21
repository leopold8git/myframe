/**
 *  扩展js中的string功能 
 **/
//check if a CharSequence is whitespace, empty ("") or null. 
String.prototype.isBlank = function(){
	return (this == null || $.trim(this) == "");
}
//Checks if a CharSequence is empty ("") or null.
String.prototype.isEmpty = function(){
	return this == null || this == "";
}
//寻找searchStr在字符串中第ordinal出现的索引，lastIndex：是否从后往前搜
//var s = "abcdabcd";alert(s.ordinalIndexOf("c",2))
String.prototype.ordinalIndexOf = function(searchStr, ordinal,lastIndex){
	if (searchStr == null || ordinal <= 0) {
		return INDEX_NOT_FOUND;
	}
	if (searchStr.length == 0) {
		return lastIndex ? s.length : 0;
	}
	var found = 0;
	var	index = lastIndex ? s.length : INDEX_NOT_FOUND;
	do {
		if (lastIndex) {
			index = s.lastIndexOf(searchStr, index - 1);
		} else {
			index = s.indexOf(searchStr, index + 1);
		}
		if (index < 0) {
			return index;
		}
		found++;
	} while (found < ordinal);
	return index;
}
//寻找searchStr在字符串中第ordinal出现的索引，从后往前搜
//var s = "abcdabcd";alert(s.lastOrdinalIndexOf("c",2))
String.prototype.lastOrdinalIndexOf = function(searchStr, ordinal){
	return this.ordinalIndexOf(searchStr, ordinal,true);
}

String.prototype.replaceAll = function (regexp,replacement) { 
    return this.replace(new RegExp(regexp,"gm"),replacement);
}

String.prototype.toDate = function(formatStr){
	 	var reg = /\b/;
	    var re = new Date();
	    re.setHours(0, 0, 0, 0);
	    var arys = this.split(reg);
	    var farys = formatStr.split(reg);
	    for (var i = 0; i < farys.length; i++) {
	        var n = farys[i];
	        switch (n) {
	            case 'yyyy':
	            case 'YYYY':
	                re.setFullYear(arys[i] * 1);
	                break;
	            case 'MM':
	                re.setMonth(arys[i] * 1 - 1);
	                break;
	            case 'dd':
	            case 'DD':
	                re.setDate(arys[i] * 1);
	                break;
	            case 'hh':
	            case 'HH':
	                re.setHours(arys[i] * 1);
	                break;
	            case 'mm':
	                re.setMinutes(arys[i] * 1);
	                break;
	            case 'ss':
	            case 'SS':
	                re.setSeconds(arys[i] * 1);
	                break;
	        }
	    }
	    return re;
}

//js string template 
/**
 *  tpl : 模板  如 abcd{hello}dsff{world}
 *  replacement : 替换词 类型 object {name:value,name2:value2}
 *  使用方法： "abcd{hello}dsff{world}".fillTpl({hello:"Hi",world:"John"});
 */
String.prototype.fillTpl = function(replacement){
	var res = "";
	var exp = /\{\w+\}/g;
	res = this.replace(exp,function(word){
				if(word && word.length>1){
					var p = word.substring(1,word.length-1);
					return replacement[p]||"";
				}
			});
	return res;
}

/**
 * 填充数组
 * 
 */
String.prototype.fillTpls = function(replaceArray){
	var res = "";
	if(replaceArray && replaceArray.constructor == Array){
		for(var i=0;i<replaceArray.length;i++){
			var replacement = replaceArray[i];
			res += this.fillTpl(replacement);
		}
	}
	return res;
}

/**
 * 按byte数截串,中文算两个字符,b取小范围还是大范围
 * "我们abc".substringByte(1,5,true) == "们a" 
 * "我们abc".substringByte(1,5,false) == "我们a" 
 */
String.prototype.substringByte = function(start,end,b){
	var index = -1;
	var subStartIndex=-1;
	var subEndIndex=0;
	var len = this.length;
	for(var i=0;i<len;i++){
		var c = this.charAt(i);
		if(isCh(c)){
			index+=2;
		}else{
			index++;
		}
		if(index <= start){
			subStartIndex++;
		}
		if(b && start-index ==1){
			subStartIndex++;
		}
		
		if(b && end-index == 1){
			break;
		}
		if(index <= end){
			subEndIndex++;
		}
	}
	return this.substring(subStartIndex,subEndIndex);
}