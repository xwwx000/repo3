function getObj(objName, index) {
	var obj = eval("document.all." + objName);
	if (!(typeof (obj) == "object"))
		return '';
	var len = obj.length;
	if (!(typeof (len) == "undefined")) {
		obj = eval("document.all." + objName + "[" + index + "]");
	}
	return obj;
}
function getElementLen(elementName) {
	var obj = eval("document.all." + elementName);
	if (!(typeof (obj) == "object"))
		return 0;
	var len = obj.length;
	if (typeof (len) == "undefined") {
		len = 1;
	}
	return len;
}
function hasOneRecord(objName) {
	var objChk;
	var returnval = 0;
	var j = 0;
	for (var i = 0; i < getElementLen(objName); i++) {
		objChk = getObj(objName, i);
		if (objChk.checked && (!objChk.disabled)) {
			j++;
			returnval = objChk.value;
		}
	}
	if (j == 1)
		return returnval;
	else
		return 0;
}
function hasManyRecord(objName) {
	var objChk;
	var returnval = '';
	var j = 0;
	for (var i = 0; i < getElementLen(objName); i++) {
		objChk = getObj(objName, i);
		if (objChk.checked && (!objChk.disabled)) {
			if (j == 0) {
				returnval = objChk.value;
			} else {
				returnval = returnval + ',' + objChk.value;
			}
			j++;
		}
	}
	return returnval;
}
/**
 * 全选
 */
function selAllChk() {

	var allobj = document.getElementsByName("chk");
	var allsobj = document.getElementsByName("chkall")[0];
	var selectcar = "";
	if (allsobj.checked == true) {
		for (var i = 0; i < allobj.length; i++) {
			if (allobj[i].checked == false) {
				allobj[i].checked = true;
			}
			if (selectcar == "") {
				selectcar = allobj[i].value;
			} else {
				selectcar = selectcar + "," + allobj[i].value;
			}
		}
	} else {
		for (var i = 0; i < allobj.length; i++) {
			if (allobj[i].checked == true) {
				allobj[i].checked = false;
			}
		}
	}
}
function isNumber(name) {
	if (name.length == 0)
		return false;
	for (i = 0; i < name.length; i++) {
		if (name.charAt(i) < "0" || name.charAt(i) > "9")
			return false;
	}
	return true;
}

function setpage(pagenum) {

	document.forms[0].pageno.value = pagenum;
	document.forms[0].submit();
}