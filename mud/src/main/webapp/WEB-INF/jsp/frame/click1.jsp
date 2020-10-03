<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${ctx}/css/frame/style.css" rel="stylesheet">
<link href="${ctx}/css/frame/reset.css" rel="stylesheet">
<script type="text/javascript">
function isShowNav(){  
    if(window.parent.document.getElementById("upFrame").rows == "96,10,*,26"){  
       window.parent.document.getElementById("upFrame").rows  = "0,10,*,26";  
    }else{  
      window.parent.document.getElementById("upFrame").rows  = "96,10,*,26";  
    }
}
</script>
</head>
<body>
<div class="click_bg" >
	<img src="${ctx}/images/new/click_1.gif" onmouseover="this.style.cursor='pointer';" onclick="isShowNav()"/>
</div>
</body>   
</html>