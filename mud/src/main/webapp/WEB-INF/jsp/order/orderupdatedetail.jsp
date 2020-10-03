<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${ctx}/css/default.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/button/gh-buttons.css"
	type="text/css">
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
<script type="text/javascript" src="${ctx}/js/form_validate.js"></script>
</head>
<body>
	<form id="form" action="" method="post">
	<input type="hidden" id="id" name="id" value="${order.id}" />
		<br>
		<table width="98%" border="0" cellspacing="1" cellpadding="3"
			class="contentTable" align="center">
			<tr class="defaultBGColor">
				<td class="editLabel" align="center" width="20%">订单编码:</td>
				<td class="editLabel">
				<input name="snum" id="snum" size="20" maxlength="30" value=${order.snum} readonly/>
				</td>
				<td class="editLabel" width="20%" align="center"></td>
				<td width="30%"><span class="contentclass"></span></td>				
			</tr>				
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">车牌号</td>
				<td width="30%"><span class="contentclass"><input
						type="text" id="carnum" name="carnum" size="20" maxlength="20" value="${order.carnum}" /></span></td>
				<td class="editLabel" width="20%" align="center">货物类型</td>
				<td width="30%">
				<select id="goodstype" name="goodstype">
					<option value="">--请选择--</option>
					<c:forEach items="${goodstypelist}" var="list" varStatus="status">
						<c:choose>
						<c:when test="${list.goodstypename == order.goodstype}">
						<option value="${list.goodstypename}" selected>${list.goodstypename}</option>
						</c:when>
						<c:otherwise>
						<option value="${list.goodstypename}">${list.goodstypename}</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">发货单位</td>
				<td width="30%">
				<select id="sgoodsdept" name="sgoodsdept">
					<option value="">--请选择--</option>
					<c:forEach items="${sgoodsdeptlist}" var="list" varStatus="status">
						<c:choose>
						<c:when test="${list.deptname == order.sgoodsdept}">
						<option value="${list.deptname}" selected>${list.deptname}</option>
						</c:when>
						<c:otherwise>
						<option value="${list.deptname}">${list.deptname}</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				</td>
				<td class="editLabel" width="20%" align="center">发货时间</td>
				<td width="30%"><input type="text" id="stime" name="stime"
								readonly value="${order.sgoodstime}" /></td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">收货单位</td>
				<td width="30%"><input name="rgoodsdept" id="rgoodsdept" type="text" size="20" maxlength="50" value="${order.rgoodsdept}"/></td>
				<td class="editLabel" align="center" width="20%">货物重量:</td>
				<td class="editLabel">
				<input name="goodsweight" id="goodsweight" size="20" maxlength="30" value=${order.goodsweight} />&nbsp;&nbsp;公斤
				</td>
			</tr>									
			<tr class="defaultBGColor">
				<td class="editLabel" colspan="6" align="center"><a href="#"
					id="btnsave" class="button big" style="margin-right: 5px;">保存</a><a
					href="#" id="btnquit" class="button big" style="margin-right: 5px;">取消</a></td>
			</tr>
		</table>
	</form>
</body>
<script type="text/javascript">
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	$(function() {
		$("#btnsave").click(function() {
			//ajax保存
			save();
		});
		$("#btnquit").click(function() {
			//退出
			parent.layer.close(index);
		});
	});
	function Number(val) {
		　　if (parseFloat(val).toString() == "NaN") {
		　　　　
		　　　　return false;
		　　} else {
		　　　　return true;
		　　}
	}
	function save() {
		var id = $("#id").val();
		var carnum = $("#carnum").val();
		var goodstype = $("#goodstype").val();
		var sgoodsdept = $("#sgoodsdept").val();
		var rgoodsdept = $("#rgoodsdept").val();
		var goodsweight = $("#goodsweight").val();

		if(carnum.length<1){
			layer.msg('车牌号不能为空!');
			return;
		}
		if(goodstype.length<1){
			layer.msg('请选择货物类型!');
			return;
		}
		if(sgoodsdept.length<1){
			layer.msg('请选择发货单位!');
			return;
		}
		if(!isNumeric(goodsweight)){
			layer.msg('重量格式错误!');
			return;
		}
		$.ajax({
			url : "${ctx}/order/orderUpdateDetail",
			async : true,
			type : "post",
			dataType : "json",
			data : {
				id : id,
				carnum : carnum,
				goodstype : goodstype,
				sgoodsdept : sgoodsdept,
				rgoodsdept : rgoodsdept,
				goodsweight : goodsweight
			},
			success : function(data) {
				var barray = eval(data);
				if (barray.code == 200) {
					layer.msg('保存成功!', {
						shade : 0.2,
						time : 1000
					}, function() {
						parent.searchRecord();
						parent.layer.close(index);
					});
				} else {
					layer.msg('保存失败!', {
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