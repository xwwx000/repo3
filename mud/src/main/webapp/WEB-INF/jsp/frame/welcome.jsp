<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${ctx}/css/frame/style.css" rel="stylesheet">
<link href="${ctx}/css/frame/reset.css" rel="stylesheet">
<script src="${ctx}/js/tool/jquery-1.8.2.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/js/tool/layer/layer.js"></script>	
<script type="text/javascript">

function showdiv(){
	layer.confirm('确定要退出？', {
		  btn: ['确认','取消'] //按钮
		}, function(){
			parent.window.location.href="${ctx}";
		}, function(){
			window.location.href="${ctx}/order/orderList";
		});
	/*
	layer.open({
	    content: '确定要退出？',
	    btn: ['确认', '取消111'],
	    shadeClose: false,
	    yes: function(){
	        layer.open({content: '你点了确认', time: 1});
	        parent.window.location.href="${ctx}";
	    }, no: function(){
	    	alert(111);
	        //layer.open({content: '你选择了取消', time: 1});
	        parent.window.location.href="${ctx}/order/orderList";
	    }
	});		
	*/
}
function showtimeoutdiv(){

	layer.confirm('网页超时,请重新登陆', {
		  btn: ['确定'] //按钮
		}, function(){
			layer.open({content: '你点了确认', time: 1});
			parent.window.location.href="${ctx}/login.jsp";
		})	
}
</script>	
<style>
html,body{
	height: 100%;
}
.body{
	height: 96%;
}
</style>
</head>
<body>
	<div class="body" >
		<div class="desktop" >
			<ul>
			<c:forEach items="${list}" var="list" varStatus="status">
				<c:choose>
				<c:when test="${status.index == 0}">
				<li onmouseover="document.getElementById('id${status.index}').className='out'" onmouseout="document.getElementById('id${status.index}').className='hover'"
                id="id${status.index}" class="hover">
				<a href="<app:iniPath />/${list.target}" >
					<p class="text1">${list.moduledesc}</p>
					<p class="text2">${list.filename}</p>
				</a>
				</li>					
				</c:when>
				<c:otherwise>
				<li onmouseover="document.getElementById('id${status.index}').className='out${status.index}'" onmouseout="document.getElementById('id${status.index}').className='hover${status.index}'"
                id="id${status.index}" class="hover${status.index}">
				<a href="<app:iniPath />/${list.target}" >
					<p class="text1">${list.moduledesc}</p>
					<p class="text2">${list.filename}</p>
				</a>
				</li>				
				</c:otherwise>
				</c:choose>		
			</c:forEach>
			</ul>
		</div>
	</div>
</body>   
</html>