<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<title><fmt:message key="admin.title"
		bundle="${applicationBundle}" /></title>
  <link rel="stylesheet" href="${ctx}/css/login.css">		
<script type="text/javascript"
	src="${ctx}/js/tool/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/html5.js"></script>
<script type="text/javascript" src="${ctx}/js/tool/layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
		//$("#userid").focus();
		$('body').bind('keypress',function(event){
            if(event.keyCode == "13"){
            	$("#btn_login").click();
            }
        });
		$("#btn_login").click(function() {
			var userid = $("#userid").val();
			var password = $("#password").val();
			if(userid.length < 1){
				layer.msg('用户名不能为空!');
				//$("#userid").focus();
				return;
			}
			if(password.length < 1){
				layer.msg('密码不能为空!');
				$("#password").focus();
				return;
			}
			$.ajax({
				url : "${ctx}/user/login",
				async : true,
				type : "post",
				dataType : "json",
				data : {
					userid : userid,
					password : password
				},
				success : function(data) {
					var barray = eval(data);
					//alert(JSON.stringify(barray));
					if (barray.message != "ok") {
						//alert("登陆失败!");
						$(".errorText").text("用户名密码错误");
					} else {
						//跳转到系统主界面
						$("#loginform").submit();
					}
				},
				error : function(xmr,errorText,errorType) {
					$(".errorText").text("访问错误!");
					//alert(errorText + ':' + errorType);
			        //alert(xmr.status + ':' + xmr.statusText);
				}
			});
		});
	});
</script>
</head>
<body>

<div class="loginBox">
    <p class="title"><img src="${ctx}/images/new/login_title2.png" /></p>
    <!--[if lte IE 9]>
    <span>用户编码：</span>
    <![endif]-->
    <form name="loginform" id="loginform" action="${ctx}/user/main" method="post">
    <input type="hidden" name="token" id="token" >
    <input type="hidden" name="unlogin" id="unlogin" value="${param.unlogin}">
    <input type="text" name="userid" id="userid" size="10" placeholder="用户编码">

    <!--[if lte IE 9]>
    <div style="clear: both;"></div>
    <span>密　　码：</span>
    <![endif]-->
    <input type="password" name="password" id="password" size="10" placeholder="密码">

    <!--[if lte IE 9]>
    <div style="clear: both;"></div>
    <![endif]-->
    <input type="button" name="btn_login" id="btn_login" value="登&nbsp;录">
    <p class="errorText"></p>
    </form>
  </div>
  <!-- <div class="foot">Copyright © 2017 浑南水务.All rights reserved</div> -->
</body>
</html>