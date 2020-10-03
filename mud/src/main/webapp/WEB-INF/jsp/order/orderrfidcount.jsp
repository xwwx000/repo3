<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>封签统计列表</title>
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
<style type="text/css">
.aggregate {
	color: red;
	font-size: 10pt;
}

.runcode {
	border: 1px solid #ddd;
	background: url(${ctx}/js/date/lhgcalendar/_doc/images/iconDate.gif)
		center right no-repeat #f7f7f7;
	cursor: pointer;
	font: 12px tahoma, arial;
	height: 21px;
	width: 100px;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#stime').calendar({
			//btnBar : false,maxDate:'#etime'
			btnBar : false
		});
		$('#etime').calendar({
			//btnBar : false,minDate:'#stime'
			btnBar : false
		});

		/**
		 * 查询
		 */
		$("#search").click(function() {
			$("#orderrfidcount").submit();
		});
		/**
		 * 导出excel
		 */
		$("#export").click(function() {
			
			var sgoodsdept = $("#sgoodsdept").val();
			var goodstype = $("#goodstype").val();
			var rgoodsstatus = $("#rgoodsstatus").val();
			var stime = $("#stime").val();
			var etime = $("#etime").val();
			var etime = $("#etime").val();
			var url = "${ctx}/export/orderlist?stime="+stime+"&etime="+etime+"&sgoodsdept="+sgoodsdept+"&goodstype="+goodstype+"&rgoodsstatus="+rgoodsstatus+"&status=0";
			//alert(url);
			location.href = url;
		});
	});
//-->
</script>
</head>
<body>
	<FORM action="${ctx}/order/orderrfidcount" id="orderrfidcount"
		name="pdauserlist" method="post">
		<table width="98%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td width="150" height="35" align="left" id="Title">运输数据--封签统计</td>
				<td bgcolor="#f7f7f7">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						height="24">
						<tr>
							<td align="left"></td>
							<td align="right"
								background="<app:iniPath />/images/htimages/titlebg_center.gif">
								&nbsp;&nbsp;查询日期：
								<input type="text" id="stime" name="stime" class="runcode"
								maxlength="10" value="${stime}" readonly />-- <input
								type="text" id="etime" name="etime" class="runcode"
								maxlength="10" value="${etime}" readonly /> <a href="#"
								id="search" class="button icon search" style="margin-right: 0px">查询</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="50%" border="0" cellspacing="0" cellpadding="0" 
			align="center">
			<tr align="center">
				<td align="center" height="50"></td>
			</tr>
			<tr align="center">
				<td align="center" height="100">
				<div>
				<table width="98%" border="0" cellspacing="5" cellpadding="5" class="contentTable" align="center">
				<tr class="tableTitle" align="center" height="50px">
					<td width="50%"><h1><b>收货单位</b></h1></td>
					<td><h1><b>封签数量</b></h1></td>
				</tr>
				<c:forEach items="${orderrfidrlist}" var="list"
				varStatus="status">
				<tr class="defaultBGColor" align="center" height="40px">
				<td><h2><b>${list.rgoodsdept}</b></h2></td>
				<td><h2><b>${list.num}</b></h2></td>
				</tr>
				</c:forEach>
				</table>
				</div>
				</td>
			</tr>
			<tr><td align="center" height="30"></tr>
			<tr align="center">
				<td align="center" height="100">
				<div>
				<table width="98%" border="0" cellspacing="5" cellpadding="5" class="contentTable" align="center">
				<tr class="tableTitle" align="center" height="50px">
					<td width="50%"><h1><b>发货单位</b></h1></td>
					<td><h1><b>封签数量</b></h1></td>
				</tr>
				<c:forEach items="${orderrfidslist}" var="list"
				varStatus="status">
				<tr class="defaultBGColor" align="center" height="40px">
				<td><h2><b>${list.sgoodsdept}</b></h2></td>
				<td><h2><b>${list.num}</b></h2></td>
				</tr>
				</c:forEach>
				</table>
				</div>
				</td>
			</tr>			
		</table>
	</FORM>
</BODY>
<script type="text/javascript">
	$(function() {
		initDate();
	});
	function initDate() {
		var d = new Date();
		var vYear = d.getFullYear();
		var vMon = d.getMonth() + 1;
		var vDay = d.getDate();
		vMon = vMon > 9 ? vMon : "0" + vMon;
		vDay = vDay > 9 ? vDay : "0" + vDay;
		datatmp = vYear + "-" + vMon + "-" + vDay;
		var stimetmp = $("#stime").attr("value");
		var etimetmp = $("#etime").attr("value");
		if (stimetmp == "") {
			$("#stime").val(datatmp);
			$("#etime").val(datatmp);
		}
	}
</script>
</html>