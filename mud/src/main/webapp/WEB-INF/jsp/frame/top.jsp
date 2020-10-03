<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${ctx}/css/frame/style.css" rel="stylesheet">
<link href="${ctx}/css/frame/reset.css" rel="stylesheet">
<style type="text/css">
.top_cent ul li a {
	font-size: 15px;
	font-family: 微软雅黑;
	/*color: #E8EC75;*/
	color: white;
}
</style>
<script type="text/javascript"
	src="<app:iniPath />/js/tool/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript">
	function showWelcome() {
		self.parent.document.getElementById("mainFrame").src = "${ctx}/user/welcome";
	}
	function exit() {
		self.parent.document.getElementById("mainFrame").src = "${ctx}/user/welcome";
		setTimeout("top.window.frames['mainFrame'].showdiv()", 500);
	}
	function ActionHandle(m) {
		var str = "${ctx}/user/left?moduleid=" + m;
		self.parent.document.getElementById("leftFrame").src = str;
		self.parent.document.getElementById("mainFrame").src = "${ctx}/user/welcome";
	}
</script>
</head>
<body>
	<div class="top_bg">
		<div class="top">
			<div class="top_left">
				<img height="70" src="${ctx}/images/new/logonew2.png" />
			</div>
			<div class="top_cent">
			<!-- 
				<ul>
					<li><a href="#" onmouseover="this.style.cursor='pointer';"
						onclick="javascript:ActionHandle(11);"><img
							src="${ctx}/images/new/t3.png" /><br>受理平台</a></li>
					<li><a href="#" onmouseover="this.style.cursor='pointer';"
						onclick="javascript:ActionHandle(12);"><img
							src="${ctx}/images/new/t2.png" /><br>公告平台</a></li>
				</ul>
			 -->
			</div>
			<div class="top_right">
				<div class="r10">
					<p class="home2">
						<a href="#" onclick="javascript:exit();">退出</a>
					</p>
					<p class="home1">
						<img src="${ctx}/images/new/home2.gif"
							onmouseover="this.style.cursor='pointer';"
							onclick="javascript:exit();" />
					</p>
					<!-- 
					<p class="home2">
						<a href="#" onclick="javascript:showWelcome();">首页</a>
					</p>
					<p class="home1">
						<img src="${ctx}/images/new/home1.gif"
							onmouseover="this.style.cursor='pointer';"
							onclick="javascript:showWelcome();" />
					</p>
					 -->
				</div>
			</div>
		</div>
	</div>
</body>
</html>