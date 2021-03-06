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
		$("#search").click(function(){
			$("#pageno").val("1");
			$("#orderlist").submit();
		});
		/**
		 * 导出excel
		 */
		$("#export").click(function() {
			
			var sgoodsdept = $("#sgoodsdept").val();
			var rgoodsdept = $("#rgoodsdept").val();
			var goodstype = $("#goodstype").val();
			var rgoodsstatus = $("#rgoodsstatus").val();
			var stime = $("#stime").val();
			var etime = $("#etime").val();
			var etime = $("#etime").val();
			var url = "${ctx}/export/orderlist?stime="+stime+"&etime="+etime+"&sgoodsdept="+sgoodsdept+"&rgoodsdept="+rgoodsdept+"&goodstype="+goodstype+"&rgoodsstatus="+rgoodsstatus+"&status=0";
			//alert(url);
			location.href = url;
		});
	});
//-->
</script>
</head>
<BODY>
	<FORM action="${ctx}/order/orderList" id="orderlist" name="orderlist"
		method="post">
		<INPUT type="hidden" id="pageno" name="pageno" value="${pageList.pages.cpage}">
		<table width="98%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td width="150" height="35" align="left" id="Title">运输数据--记录查询</td>
				<td bgcolor="#f7f7f7">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						height="24">
						<tr>
							<td align="left"></td>
							<td align="right"
								background="<app:iniPath />/images/htimages/titlebg_center.gif">
								发货单位：<select id="sgoodsdept" name="sgoodsdept">
									<option value="">--请选择--</option>
									<c:forEach items="${sgoodsdeptlist}" var="list"
										varStatus="status">
										<c:choose>
											<c:when test="${list.deptname == sgoodsdept}">
												<option value="${list.deptname}" selected>${list.deptname}</option>
											</c:when>
											<c:otherwise>
												<option value="${list.deptname}">${list.deptname}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
							</select>&nbsp;&nbsp; 货物类型：<select id="goodstype" name="goodstype">
									<option value="">--请选择--</option>
									<c:forEach items="${goodstypelist}" var="list"
										varStatus="status">
										<c:choose>
											<c:when test="${list.goodstypename == goodstype}">
												<option value="${list.goodstypename}" selected>${list.goodstypename}</option>
											</c:when>
											<c:otherwise>
												<option value="${list.goodstypename}">${list.goodstypename}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
							</select>&nbsp;&nbsp; 收货状态： <select id="rgoodsstatus" name="rgoodsstatus">
									<option value="">--请选择--</option>
									<c:forEach items="${shztlist}" var="list">
										<c:choose>
											<c:when test="${list.itemno == rgoodsstatus}">
												<option value="${list.itemno}" selected>${list.itemstr}</option>
											</c:when>
											<c:otherwise>
												<option value="${list.itemno}">${list.itemstr}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
							</select> &nbsp;&nbsp;车牌号：<input id="carnum" name="carnum"
								value="${carnum}" size=10 maxlength="20" />&nbsp;&nbsp;收货单位：<input id="rgoodsdept" name="rgoodsdept"
								value="${rgoodsdept}" size=10 maxlength="60" />  &nbsp;&nbsp; 查询日期：
								<input type="text" id="stime" name="stime" class="runcode"
								maxlength="10" value="${stime}" readonly />-- <input
								type="text" id="etime" name="etime" class="runcode"
								maxlength="10" value="${etime}" readonly /> <a href="#"
								id="search" class="button icon search" style="margin-right: 0px">查询</a><a
								href="#" id="export" class="button icon arrowdown"
								style="margin-right: 2px">导出Excel</a>
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
				<td width="8%">流水号</td>
				<td width="5%">车牌号</td>
				<td width="5%">货物类型</td>
				<td width="10%">实重(kg)</td>
				<td width="8%">发货单位</td>
				<td width="8%">发货人</td>
				<td width="8%">发货时间</td>
				<td width="5%">封签状态</td>
				<td width="8%">收货单位</td>
				<td width="8%">收货人</td>
				<td width="8%">收货时间</td>
				<td width="5%">收货状态</td>
				<td width="5%">操作</td>
			</tr>
			<!--内容开始-->
			<c:forEach items="${pageList.objectList}" var="list"
				varStatus="status">
				<TR class="defaultBGColor" align="center"
					onmouseout="this.style.backgroundColor='';"
					onmouseover="this.style.backgroundColor='#FDF9E3';">
					<td style="display: none"><span id="s_${status.index+1}">${list.id}</span></td>
					<td height="20">${list.snum}</td>
					<td>${list.carnum}</td>
					<td>${list.goodstype}</td>
					<td>${list.goodsweight}</td>
					<td>${list.sgoodsdept}</td>
					<td>${list.consignor}</td>
					<td>${list.sgoodstime}</td>
					<td>
					<c:choose>
							<c:when test="${list.rfidstatus == 0}">
								未封签
							</c:when>
							<c:otherwise>
								已封签
							</c:otherwise>
						</c:choose>
					</td>
					<td>${list.rgoodsdept}</td>
					<td>${list.consignee}</td>
					<td>${list.rgoodstime}</td>
					<td><c:choose>
							<c:when test="${list.rgoodsstatus == 0}">
								未收货
							</c:when>
							<c:otherwise>
								已收货
							</c:otherwise>
						</c:choose></td>
					<td><a href="#"
						onclick="to_map(
					<c:choose>
							<c:when test="${empty list.lo}">
								0
							</c:when>
							<c:otherwise>
								${list.lo}
							</c:otherwise>
					</c:choose>,
					<c:choose>
							<c:when test="${empty list.la}">
								0
							</c:when>
							<c:otherwise>
								${list.la}
							</c:otherwise>
					</c:choose>,
					<c:choose>
							<c:when test="${empty list.rgoodsdept}">
								'0'
							</c:when>
							<c:otherwise>
								'${list.rgoodsdept}'
							</c:otherwise>
					</c:choose>)"><span>位置</span></a>|<a
						href="#" onclick="to_remark('${list.id}')"><span>备注</span></a></td>
					<!-- <td>${fn:substring(list.carnum,0,30)}</td> -->
				</tr>
			</c:forEach>
			<!--内容结束-->
			<tr class="tableTitle" align="center">
				<td></td>
				<td></td>
				<td>总重量：</td>
				<td>${allweight}吨</td>
				<td colspan="9"></td>
			</tr>
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

	function to_remark(id) {
		layer.closeAll();
		layer
				.open({
					type : 2,
					title : '备注',
					shade : 0.1,
					//shadeClose : true,
					area : [ '40%', '35%' ],
					content : '${ctx}/order/remark?id='
							+ id// iframe的url
				});
	}
	
	function to_map(lo,la,rgoodsdept) {

		if(lo==0 || la==0 || rgoodsdept=='0'){
			layer.msg('收货坐标错误!', {
				shade : 0.2
			});
			return false;
		}
		layer.closeAll();
		layer.open({
					type : 2,
					title : '收货点位置',
					shade : 0.1,
					//shadeClose : true,
					area : [ '60%', '50%' ],
					content : '${ctx}/order/map?lo='+ lo+'&la='+la+'&rgoodsdept='+rgoodsdept//iframe的url
				});
	}
	
</script>
</html>