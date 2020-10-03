<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务数据列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/default.css">
<link href="${ctx}/css/page.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/default.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/button/gh-buttons.css"
	type="text/css">
<script type="text/javascript" src="${ctx}/js/tool/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript"
	src="${ctx}/js/date/lhgcalendar/lhgcalendar.min.js"></script>
<script src="${ctx}/js/tool/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="${ctx}/js/tool/jquery-ui-1.12.1.custom/jquery-ui.min.css">
<link rel="stylesheet" href="${ctx}/js/tool/layui/css/layui.css">
<script type="text/javascript" src="${ctx}/js/tool/layer/layer.js"></script>
<script src="${ctx}/js/page.js" type="text/javascript"></script>
</head>
<body>
	<FORM action="${ctx}/pda/pdaUserList" id="pdauserlist"
		name="pdauserlist" method="post">
		<INPUT type="hidden" name="pageno" value="${pageList.pages.cpage}">
		<table width="98%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td width="150" height="35" align="left" id="Title">系统维护--软件下载</td>
				<td bgcolor="#f7f7f7">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						height="24">
						<tr>
							<td align="left"></td>
							<td align="right"
								background="<app:iniPath />/images/htimages/titlebg_center.gif">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="98%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr align="center">
				<td align="center" height="100"></td>
			</tr>
			<tr align="center">
				<td align="center" height="100"><button type="button"
						class="layui-btn layui-btn-lg layui-btn-normal" style="height:80px" onclick="toPdas1()">发货封签PDA软件(大)</button></td>
			</tr>
			<tr align="center">
				<td align="center" height="100"><button type="button"
						class="layui-btn layui-btn-lg layui-btn-normal" style="height:80px" onclick="toPdar1()">收货扫码PDA软件(大)</button></td>
			</tr>
			<tr align="center">
				<td align="center" height="100">
			</td>
			</tr>
			<tr align="center">
				<td align="center" height="100"><button type="button"
						class="layui-btn layui-btn-lg layui-btn-normal" style="height:80px" onclick="toPdas2()">发货封签PDA软件(小)</button></td>
			</tr>
			<tr align="center">
				<td align="center" height="100"><button type="button"
						class="layui-btn layui-btn-lg layui-btn-normal" style="height:80px" onclick="toPdar2()">收货扫码PDA软件(小)</button></td>
			</tr>			
		</table>
		<!--页下开始-->
	</FORM>
	<script type="text/javascript">
	function toPdas1(){
		window.location.href = "${ctx}/pda/pda_fahuo_d";
	}
	function toPdar1(){
		window.location.href = "${ctx}/pda/pda_shouhuo_d";
	}
	function toPdas2(){
		window.location.href = "${ctx}/pda/pda_fahuo_x";
	}
	function toPdar2(){
		window.location.href = "${ctx}/pda/pda_shouhuo_x";
	}	
	</script>
</BODY>
</html>