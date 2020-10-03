<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="${ctx}/css/frame/style.css" rel="stylesheet">
<link href="${ctx}/css/frame/reset.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/js/tool/css3-accordion-menu/css/normalize.css">
<link rel="stylesheet" href="${ctx}/js/tool/css3-accordion-menu/css/style.css?a=123" media="screen"
	type="text/css" />
<script type="text/javascript"
	src="${ctx}/js/tool/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/tool/ztree/jquery.ztree.all.min.js"></script>	
</head>
<body>
	<input type="hidden" id="moduleid" name="moduleid"
		value="${requestScope.moduleid}" />
	<div class="container">
	<!-- 
		<ul>
			<li class="dropdown"><a href="#" data-toggle="dropdown">运输记录查询<i class="icon-arrow"></i></a>
				<ul class="dropdown-menu">
				<li><a href="#">记录查询</a></li>
				<li><a href="#">遗漏查询</a></li>
				</ul>
			</li>												
		</ul>
		 -->
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	$.ajax({
		url : "${ctx}/user/left",
		async : true,
		type : "post",
		dataType : "json",
		data : {
			moduleid : $("#moduleid").val()
		},
		success : function(data) {
			var barray = eval(data);
			var hstr="";
			var levelid,id,pid;
			if (barray != null) {
				//alert(JSON.stringify(barray));
				hstr = "<ul>";
				for(var i in barray){
					//菜单头部
					if(barray[i].levelid == "2"){
						if(levelid == "3"){
							hstr = hstr + "</ul></li>";
							hstr = hstr + "<li class=\"dropdown\"><a href=\"#\" class=\"abc\" data-toggle=\"dropdown\">"+barray[i].name+"<i class=\"icon-arrow\"></i></a>";
						}else{
							hstr = hstr + "<li class=\"dropdown\"><a href=\"#\" class=\"abc\"  data-toggle=\"dropdown\">"+barray[i].name+"<i class=\"icon-arrow\"></i></a>";
						}
					}else{
						if(levelid == "2"){
							hstr = hstr + "<ul class=\"dropdown-menu\">";
							hstr = hstr + "<li><a href=\"#\" onclick=menu(\""+barray[i].target+"\") >"+barray[i].name+"</a></li>";
						}else{
							hstr = hstr + "<li><a href=\"#\" onclick=menu(\""+barray[i].target+"\") >"+barray[i].name+"</a></li>";
						}
					}
					//最后一个
					if(i == barray.length-1){
						hstr = hstr + "</ul></li>";
					}
					levelid = barray[i].levelid;
					id = barray[i].id;
					pid = barray[i].pid;
				}
				hstr = hstr + "</ul>";
				$(".container").append(hstr);
				//alert(hstr);
				initMenu();
				$('.abc').click();
			}
		},
		error : function() {
		}
	});
});
function initMenu(){
	var dropdown = document.querySelectorAll('.dropdown');
	var dropdownArray = Array.prototype.slice.call(dropdown,0);
	dropdownArray.forEach(function(el){
		var button = el.querySelector('a[data-toggle="dropdown"]'),
				menu = el.querySelector('.dropdown-menu'),
				arrow = button.querySelector('i.icon-arrow');

		button.onclick = function(event) {
			if(!menu.hasClass('show')) {
				menu.classList.add('show');
				menu.classList.remove('hide');
				arrow.classList.add('open');
				arrow.classList.remove('close');
				event.preventDefault();
			}
			else {
				menu.classList.remove('show');
				menu.classList.add('hide');
				arrow.classList.remove('open');
				arrow.classList.add('close');
				event.preventDefault();
			}
		};
	})

	Element.prototype.hasClass = function(className) {
	    return this.className && new RegExp("(^|\\s)" + className + "(\\s|$)").test(this.className);
	};
}
function menu(url){
	self.parent.document.getElementById("mainFrame").src="${ctx}/"+url+"?random="+randomn(16);
}
function randomn(n) {
	  if (n > 21) return null
	  return parseInt((Math.random() + 1) * Math.pow(10,n-1))
}
</script>
</html>