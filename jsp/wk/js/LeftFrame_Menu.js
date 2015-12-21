var selectedItem = null;

var targetWin;

document.onclick = handleClick;
document.onmouseover = handleOver;
document.onmouseout = handleOut;
document.onmousedown = handleDown;
document.onmouseup = handleUp;

document.write(writeSubPadding(20));  //write the stylesheet for the sub. Getting the indention right

function handleClick() {
	el = getReal(window.event.srcElement, "tagName", "DIV");
	var divName = el.id
	//var intImg = parseInt(divName.charAt(divName.length-1));
	var intImg = parseInt(divName.substring(4,divName.length));
	if ((el.className == "rootmenuItem") || (el.className == "childmenu02")) {
		el.sub = eval(el.id + "Sub");
		if (el.sub.style.display == null) el.sub.style.display = "none";
		if (el.sub.style.display != "block") { //hidden
			//any other sub open?
			if (el.parentElement.openedSub != null) {
				var opener = eval(el.parentElement.openedSub + ".opener");
				var divOpenerName = opener.id;
				//var intOpenerImg = parseInt(divOpenerName.charAt(divOpenerName.length-1));
				var intOpenerImg = parseInt(divOpenerName.substring(4,divOpenerName.length));
				//var divOpenerName = opener.id
				hide(el.parentElement.openedSub);
				document.all["imgDirect_"+intOpenerImg].src = "../images/Icon_Menu_Down.gif";
				if (opener.className == "rootmenuItem")
					outchildmenu01(opener);
			}
			document.all["imgDirect_"+intImg].src = "../images/Icon_Menu_Up.gif";
			el.sub.style.display = "block";
			el.sub.parentElement.openedSub = el.sub.id;
			el.sub.opener = el;
		}
		else {
			document.all["imgDirect_"+intImg].src = "../images/Icon_Menu_Down.gif";
			if (el.sub.openedSub != null) hide(el.sub.openedSub);
			else hide(el.sub.id);
		}
	}
	
	if (el.className == "childmenu03") {
		if (selectedItem != null)
		restorechildmenu03(selectedItem);
		highlightchildmenu03(el);
	}
	
	if (el.className == "childmenu01") {
		if (selectedItem != null)
		restorechildmenu01(selectedItem);
              highlightchildmenu01(el);
	}

	if ((el.className == "childmenu01") || (el.className == "childmenu03")) {
		if ((el.href != null) && (el.href != "")) {
			if ((el.target == null) || (el.target == "")) {
				if (window.opener == null) {
//					alert(document.all.tags("BASE").item(0));
					if (document.all.tags("BASE").item(0) != null)
//						eval(document.all.tags("BASE").item(0).target + ".location = el.href");
						window.open(el.href, document.all.tags("BASE").item(0).target);
					else 
						window.location = el.href;					// HERE IS THE LOADING!!!
				}
				else {
					window.opener.location =  el.href;
				}
			}
			else {
				window.open(el.href, el.target);
//				eval(el.target + ".location = el.href");
			}
		}
	}
	
	var tmp  = getReal(el, "className", "rootmenu");
	if (tmp.className == "rootmenu") fixScroll(tmp);

}

function handleOver() {
	var fromEl = getReal(window.event.fromElement, "tagName", "DIV");
	var toEl = getReal(window.event.toElement, "tagName", "DIV");
	if (fromEl == toEl) return;
	
	el = toEl;
	if (el.className == "childmenu01") overchildmenu01(el);
	if (el.className == "childmenu03") overchildmenu03(el);
	if ((el.className == "childmenu01") || (el.className == "childmenu03")) {
		if (el.href != null) {
			if (el.oldtitle == null) el.oldtitle = el.title;
			if (el.oldtitle != "")
				el.title = el.oldtitle + "\n" + el.href;
			else
				el.title = el.oldtitle + el.href;
		}
	}
	
	//if (el.className == "scrollButton") overscrollButton(el);
}

function handleOut() {
	var fromEl = getReal(window.event.fromElement, "tagName", "DIV");
	var toEl = getReal(window.event.toElement, "tagName", "DIV");
	if (fromEl == toEl) return;
	el = fromEl;
	//if (el.className == "childmenu01") outchildmenu01(el);
	//if (el.className == "childmenu03") outchildmenu03(el);
	//if (el.className == "scrollButton") outscrollButton(el);
}

function handleDown() {
	el = getReal(window.event.srcElement, "tagName", "DIV");
		
	//if (el.className == "scrollButton") {
		//downscrollButton(el);
		//var mark = Math.max(el.id.indexOf("Up"), el.id.indexOf("Down"));
		//var type = el.id.substr(mark);
		//var menuID = el.id.substring(0,mark);
		//eval("scroll" + type + "(" + menuID + ")");
	//}
}

function handleUp() {
	el = getReal(window.event.srcElement, "tagName", "DIV");
	if (el.className == "scrollButton") {
		upscrollButton(el);
		window.clearTimeout(scrolltimer);
	}
}

////////////////////// EVERYTHING IS HANDLED ////////////////////////////

function hide(elID) {
	var el = eval(elID);
	el.style.display = "none";
	el.parentElement.openedSub = null;
	if (el.openedSub != null) hide(el.openedSub);
}

function writeSubPadding(depth) {
	var str, str2, val;

	var str = "<style type='text/css'>\n";
	
	for (var i=0; i < depth; i++) {
		str2 ="";
		val  = 0;
		for (var j=0; j < i; j++) {
			str2 += ".sub "
			val += 0;
		}
		str += str2 + ".childmenu02 {padding-left: " + val + "px;}\n";
		str += str2 + ".childmenu03 {padding-left: " + val + "px;}\n";
              str += str2 + ".childmenu01 {padding-left: " + val + "px;}\n";

	}
	
	str += "</style>\n";
	return str;
}

//If you wan't to change colors do so below

function overchildmenu01(el) {
with(el.style) {
el.style.textDecoration = "none";
	}
}

function outchildmenu01(el) {
	if ((el.sub != null) && (el.parentElement.openedSub == el.sub.id)) { //opened
		with(el.style) {
		el.style.textDecoration = "none";
		}
	}
	else {
		 {
		}
	}
}
function highlightchildmenu01(el) {
	el.style.background = "url('../images/childmenu03.gif')";
	el.style.color = "black"; //"highlighttext";
	selectedItem = el;
}

function restorechildmenu01(el) {
	el.style.background = "url('../images/childmenu01.gif')";
	el.style.color      = "#c6d2dd";
	selectedItem = null;
}

function overchildmenu03(el) {
	el.style.textDecoration = "none";
}

function outchildmenu03(el) {
	el.style.textDecoration = "none";
}

function highlightchildmenu03(el) {
	el.style.background ="url('../images/childmenu04.gif')";
	el.style.color      = "black"; //"highlighttext";
	selectedItem = el;
}

function restorechildmenu03(el) {
	el.style.background = "url('../images/childmenu02.gif')";
	el.style.color      = "#96b7d5";
	selectedItem = null;
}

function overscrollButton(el) {
	el.style.padding = "0px";
}

function outscrollButton(el) {
	el.style.padding = "0px";
}

function downscrollButton(el) {
	with (el.style) {
		borderRight   = "1px solid buttonhighlight";
		borderLeft  = "1px solid buttonshadow";
		borderBottom    = "1px solid buttonhighlight";
		borderTop = "1px solid buttonshadow";
	}

}

function upscrollButton(el) {
	overchildmenu01(el);
	el.style.padding = "0px";
}

// ...till here

function getReal(el, type, value) {
	var temp = el;
	while ((temp != null) && (temp.tagName != "BODY")) {
		if (eval("temp." + type) == value) {
			el = temp;
			return el;
		}
		temp = temp.parentElement;
	}
	return el;
}


////////////////////////////////////////////////////////////////////////////////////////
// Fix the scrollbars

var globalScrollContainer;	// Needed because the object is called through windwow.setTimeout
var overflowTimeout = 1;

function fixScroll(el) {
	globalScrollContainer = el;
	window.setTimeout('changeOverflow(globalScrollContainer)', overflowTimeout);
}
function changeOverflow(el) {
	if (el.offsetHeight > el.parentElement.clientHeight)
		window.setTimeout('globalScrollContainer.parentElement.style.overflow = "auto";', overflowTimeout);
	else
		window.setTimeout('globalScrollContainer.parentElement.style.overflow = "hidden";', overflowTimeout);
}
