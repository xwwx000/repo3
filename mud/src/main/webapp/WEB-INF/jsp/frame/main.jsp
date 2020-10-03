<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="admin.title"
		bundle="${applicationBundle}" /></title>
<script type="text/javascript"
	src="${ctx}/js/tool/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/tool/layer/layer.js"></script>			
</head>
<frameset id="upFrame" name="upFrame" rows="96,10,*,26" cols="*"
	frameborder="NO" border="0" framespacing="0">
	<frame id="topFrame" name="topFrame" src="${ctx}/user/top" scrolling="no"
		noresize target="contents">
	<frame id="topFrame1" name="topFrame1" src="${ctx}/user/click1" scrolling="no"
		noresize="noresize" id="topFrame1" />
	<frameset id="centerFrame" name="centerFrame" cols="200,11,*"
		framespacing="1">
		<frame id="leftFrame" name="leftFrame" src="${ctx}/user/leftframe" scrolling="yes"
			noresize>
		<frame id="pushRLFrame" name="pushRLFrame" src="${ctx}/user/click2"
			scrolling="no" noresize>
		<frame id="mainFrame" name="mainFrame" src="${ctx}/order/orderList"
			scrolling="auto">
	</frameset>
	<frame id="bottomFrame" name="bottomFrame" src="${ctx}/user/bottom"
		scrolling="no" noresize target="contents">
</frameset>
</html>