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
			btnBar : false
		});
		$('#etime').calendar({
			btnBar : false
		});

		/**
		 * 查询
		 */
		$("#search").click(function() {
			$("#orderOmitSetList").submit();
		});
	});
//-->
</script>
</head>
<BODY>
	<FORM action="${ctx}/order/orderManualList" id="orderManualList" name="orderManualList"
		method="post">
		<INPUT type="hidden" name="pageno" value="${pageList.pages.cpage}">
		<table width="98%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td width="150" height="35" align="left" id="Title">运输数据--数据补录</td>
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
							</select> &nbsp;&nbsp;车牌号：<input id="carnum" name="carnum"
								value="${carnum}" size=10 maxlength="20" /> &nbsp;&nbsp; 查询日期： <input
								type="text" id="stime" name="stime" class="runcode"
								maxlength="10" value="${stime}" readonly />-- <input
								type="text" id="etime" name="etime" class="runcode"
								maxlength="10" value="${etime}" readonly /> 
								<a href="#"
								id="search" class="button icon search" onclick="searchRecord();" style="margin-right: 0px">查询</a>
								<a href="#"
								id="search" class="button icon add" onclick="createRecord();" style="margin-right: 2px">新建</a>
								<c:choose>
									<c:when test="${sessionScope.user_session_sewage.group.groupdesc=='系统管理员'}">
									<a href="#"
										id="search" class="button icon add" onclick="createSimulate();" style="margin-right: 2px">模拟订单</a>
									</c:when>
								</c:choose>

								<!-- 
								<div style="float: right;">
									<ul class="button-group">
										<li><a href="#" class="button primary pill icon search"
											onclick="searchRecord();">搜索</a></li>
										<li><a href="#" class="button pill icon add"
											onclick="createRecord();">新建</a></li>
									</ul>
								</div>	
								 -->							
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
				<td width="10%">发货单位</td>
				<td width="10%">发货人</td>
				<td width="10%">发货时间</td>
				<td width="10%">收货单位</td>
				<td width="10%">收货人</td>
				<td width="10%">收货时间</td>
				<td width="10%">操作</td>
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
					<td>${list.rgoodsdept}</td>
					<td>${list.consignee}</td>
					<td>${list.rgoodstime}</td>
					<td>
					<a href="#" onclick="to_remark('${list.id}')"><span>备注</span></a>|
					<a href="#" onclick="to_edit('${list.id}')"><span>修改</span></a>|
					<a href="#" onclick="to_delete('${list.id}')"><span>删除</span></a>
					</td>
					<!-- <td>${fn:substring(list.carnum,0,30)}</td> -->
				</tr>
			</c:forEach>
			<!--内容结束-->
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
	function to_addorder() {
		layer.closeAll();
		layer
				.open({
					type : 2,
					title : '备注',
					shade : 0.1,
					//shadeClose : true,
					area : [ '60%', '50%' ],
					content : '${ctx}/order/ordermanualaddpage'
				});
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
	function to_edit(id) {
		layer.closeAll();
		layer
				.open({
					type : 2,
					title : '修改',
					shade : 0.1,
					//shadeClose : true,
					area : [ '60%', '50%' ],
					content : '${ctx}/order/editManualOrderPage?id='
							+ id// iframe的url
				});
	}
	//搜索内容
	function searchRecord() {
		document.forms[0].submit();
	}
	//新建
	function createRecord(){
		to_addorder();
	}
	//模拟数据
	function createSimulate(){
		to_simulate();
	}
	//删除
	function to_delete(id) {
		layer.open({
			content : '确定删除该数据？',
			btn : [ '确认', '取消' ],
			shadeClose : false,
			yes : function() {
				//layer.open({content: '你点了确认', time: 1});
				layer.closeAll();
				ajax_delete(id);
			},
			no : function() {
				layer.open({
					content : '你选择了取消',
					time : 1
				});
			}
		});		
		
	}
	function ajax_omitset(id){
		$.ajax({
			url : "${ctx}/order/omitSet",
			async : true,
			type : "post",
			dataType : "json",
			data : {id:id},
			success : function(data) {
				var barray = eval(data);
				if(barray.code == 200){
					layer.msg('遗漏设置成功!', {
						shade : 0.2,
						time : 1000
					}, function() {
						searchRecord();
					});				
				}else{
					layer.msg('遗漏设置失败!', {
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
	//模拟数据
	function to_simulate(){
		$.ajax({
			url : "${ctx}/order/simulate",
			async : true,
			type : "post",
			dataType : "json",
			success : function(data) {
				var barray = eval(data);
				if(barray.code == 200){
					layer.msg('模拟订单成功!', {
						shade : 0.2,
						time : 1000
					}, function() {
						searchRecord();
					});				
				}else{
					layer.msg('模拟订单失败!', {
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
	function ajax_delete(id){
		$.ajax({
			url : "${ctx}/order/deleteManualOrder",
			async : true,
			type : "post",
			dataType : "json",
			data : {id:id},
			success : function(data) {
				var barray = eval(data);
				if(barray.code == 200){
					layer.msg('删除成功!', {
						shade : 0.2,
						time : 1000
					}, function() {
						searchRecord();
					});				
				}else{
					layer.msg('删除失败!', {
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