<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<script src="${ctx}/js/tool/jquery-1.8.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/tool/layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
		//alert("未登陆或登陆超时!");
		layer.msg('未登录或登陆超时!');
		setTimeout(function(){
			parent.window.location.href = "${ctx}";
			},1000);
		//parent.window.location.href="${ctx}/login.jsp?unlogin=1";
		//parent.window.location.href = "${ctx}";
	});
</script>
</head>
</html>
