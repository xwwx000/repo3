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
			btnBar : false,maxDate:'#etime'
		});
		$('#etime').calendar({
			btnBar : false,minDate:'#stime'
		});

		/**
		 * 查询
		 */
		$("#search").click(function() {
			$("#loglist").submit();
		});
	});
//-->
</script>
</head>
<BODY>
	<FORM action="${ctx}/log/loglist" id="loglist" name="loglist"
		method="post">
		<INPUT type="hidden" name="pageno" value="${pageList.pages.cpage}">
		<table width="98%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td width="150" height="35" align="left" id="Title">系统维护--日志查询</td>
				<td bgcolor="#f7f7f7">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						height="24">
						<tr>
							<td align="left"></td>
							<td align="right"
								background="<app:iniPath />/images/htimages/titlebg_center.gif">
								 查询日期：
								<input type="text" id="stime" name="stime" class="runcode"
								maxlength="10" value="${stime}" readonly />-- <input
								type="text" id="etime" name="etime" class="runcode"
								maxlength="10" value="${etime}" readonly /> 
								日志类型：
								<select id="logtype" name="logtype">
								<c:choose>
									<c:when test="${logtype == 0}">
										<option value="">--请选择--</option>
										<option value="0" selected>后台操作</option>
										<option value="1">称接口操作</option>
										<option value="2">PDA接口操作</option>
									</c:when>
									<c:when test="${logtype == 1}">
										<option value="">--请选择--</option>
										<option value="0">后台操作</option>
										<option value="1" selected>称接口操作</option>
										<option value="2">PDA接口操作</option>
									</c:when>
									<c:when test="${logtype == 2}">
										<option value="">--请选择--</option>
										<option value="0">后台操作</option>
										<option value="1">称接口操作</option>
										<option value="2" selected>PDA接口操作</option>
									</c:when>									
									<c:otherwise>
										<option value="" selected>--请选择--</option>
										<option value="0">后台操作</option>
										<option value="1">称接口操作</option>
										<option value="2">PDA接口操作</option>
									</c:otherwise>
								</c:choose>
								</select>
								<a href="#"
								id="search" class="button icon search" style="margin-right: 0px">查询</a>
								<a href="#"
								id="clearlog" onclick="ajax_clear()" class="button icon search" style="margin-right: 0px">清空日志</a>								
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<!--页上开始-->
		<table width='98%' border='0' id='maintbl' cellspacing='1'
			cellpadding='3' class="contentTable" align='center'>
			<tr class="tableTitle" align="center">
				<td width="5%">用户编码</td>
				<td width="5%">用户名称</td>
				<td width="8%">操作时间</td>
				<td width="10%">用户IP地址</td>
				<td width="5%">用户类型</td>
				<td width="60%">日志描述</td>
			</tr>
			<!--内容开始-->
			<c:forEach items="${pageList.objectList}" var="list"
				varStatus="status">
				<TR class="defaultBGColor" align="center"
					onmouseout="this.style.backgroundColor='';"
					onmouseover="this.style.backgroundColor='#FDF9E3';">
					<td style="display: none"><span id="s_${status.index+1}">${list.id}</span></td>
					<td height="20">${list.userid}</td>
					<td>${list.username}</td>
					<td>${list.processtime}</td>
					<td>${list.useripaddress}</td>
					<td><c:choose>
							<c:when test="${list.logtype == 0}">
								后台操作
							</c:when>
							<c:when test="${list.logtype == 1}">
								称接口操作
							</c:when>
							<c:otherwise>
								PDA接口操作
							</c:otherwise>
						</c:choose>
					</td>
					<td>${list.logdesc}</td>
				</tr>
			</c:forEach>
		</TABLE>
		<!--页下开始-->
	</FORM>
	<DIV class=pagination>${pagination}</DIV>
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

	function ajax_clear(){
		var stime = $("#stime").val();
		var etime = $("#etime").val();
		$.ajax({
			url : "${ctx}/log/clearlog",
			async : true,
			type : "post",
			dataType : "json",
			data : {stime:stime,etime:etime},
			success : function(data) {
				var barray = eval(data);
				if(barray.code == 200){
					layer.msg('清除日志成功!', {
						shade : 0.2,
						time : 1000
					}, function() {
						$("#loglist").submit();
					});				
				}else{
					layer.msg('清除日志失败!', {
						shade : 0.2,
						time : 1000
					}, function() {
					});					
				}
			},
			error : function() {
				layer.msg('访问错误!', {
					shade : 0.2,
					time : 1000
				}, function() {
				});				
			}
		});		
	}	
</script>
</html>