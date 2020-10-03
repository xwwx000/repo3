<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${ctx}/css/frame/style.css" rel="stylesheet">
<link href="${ctx}/css/frame/reset.css" rel="stylesheet">
<style>
html{min-height:100%;height:100%;}
</style>
<script language="javascript">
function isShowNav(){
    if(parent.document.getElementById("centerFrame").cols == "200,11,*"){  
       parent.document.getElementById("centerFrame").cols  = "0,11,*";  
    }else{  
      parent.document.getElementById("centerFrame").cols  = "200,11,*";  
    }  
}
</script>  
</head>
<body>
<div class="click_bg2" >
	<img src="${ctx}/images/new/click_2.gif" onmouseover="this.style.cursor='pointer';" onclick="isShowNav()" class="hand"/>
</div>
</body>   
</html>