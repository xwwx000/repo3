<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${ctx}/js/tool/jquery-1.8.2.min.js"
	type="text/javascript"></script>
<link href="${ctx}/css/frame/style.css" rel="stylesheet">
<link href="${ctx}/css/frame/reset.css" rel="stylesheet">
<title></title>
<script type="text/javascript">
	var timeString;
	function tick() {
		var hours, minutes, seconds, ap;
		var intHours, intMinutes, intSeconds;
		var today;
		today = new Date();
		intHours = today.getHours();
		intMinutes = today.getMinutes();
		intSeconds = today.getSeconds();
		if (intHours == 0) {
			hours = "12:";
			ap = "午夜";
		} else if (intHours < 12) {
			hours = intHours + ":";
			ap = "上午";
		} else if (intHours == 12) {
			hours = "12:";
			ap = "中午";
		} else {
			hours = intHours + ":";
			ap = "下午";
		}
		if (intMinutes < 10) {
			minutes = "0" + intMinutes + ":";
		} else {
			minutes = intMinutes + ":";
		}
		if (intSeconds < 10) {
			seconds = "0" + intSeconds + " ";
		} else {
			seconds = intSeconds + " ";
		}
		str = today.getFullYear() + "年" + (today.getMonth() + 1) + "月"
				+ today.getDate() + "日";
		Clock1.innerHTML = str;
		timeString = ap + hours + minutes + seconds;
		Clock.innerHTML = timeString;
		//更新在线状态
		window.setTimeout("tick();", 1000);
	}
	window.onload = tick;

	function showWelcome() {
		self.parent.document.getElementById("mainFrame").src = "${ctx}/jsp/frame/menu!welcome.do";
	}

	function exit() {
		if (confirm("<s:text name='admin.whether.logout'></s:text>")) {
			parent.window.location.href = "<app:iniPath />/login!exit.do?username=";
		} else {
			return;
		}
	}
	function removeline() {
		if (event.clientX < 0 || event.clientY < 0) {
			document
					.write('<iframe width=100 height=100 src=<app:iniPath />/jsp/removeSession.jsp></iframe>');
			document.all.WebBrowser.ExecWB(45, 1);
		}
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<div class="bot_bg">
		&nbsp;&nbsp;&nbsp;&nbsp;用户编码:${sessionScope.user_session_sewage.userid}&nbsp;&nbsp;&nbsp;&nbsp; 
		用户名称:${sessionScope.user_session_sewage.username}&nbsp;&nbsp;&nbsp;&nbsp;
		用户组:${sessionScope.user_session_sewage.group.groupdesc}&nbsp;&nbsp;&nbsp;&nbsp;
		部门:${sessionScope.user_session_sewage.dept.deptname}&nbsp;&nbsp;&nbsp;&nbsp;
		当前时间 <span id="Clock1"></span> <span id="Clock"></span>&nbsp;&nbsp; <span id="status"></span>
	</div>
</body>
<script type="text/javascript">
$(function (){
	//防止超时 5分钟执行一次
	setInterval("flush();",1000*60*5);
});
function flush(){
	$.ajax({
		url : "${ctx}/user/heartbeat?timeString="+timeString,
		async : true,
		type : "get",
		dataType : "json",
		data : {},
		success : function(data) {
		},
		error : function() {
		}
	});
}
</script>
</html>