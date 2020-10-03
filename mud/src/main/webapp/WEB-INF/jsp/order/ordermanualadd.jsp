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
<style type="text/css">
.runcode {
	border: 1px solid #ddd;
	background: url(${ctx}/js/date/lhgcalendar/_doc/images/iconDate.gif)
		center right no-repeat #f7f7f7;
	cursor: pointer;
	font: 12px tahoma, arial;
	height: 21px;
	width: 160px;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#stime').calendar({
			//maxDate:'#etime',format:'yyyy-MM-dd HH:mm:ss'
			format:'yyyy-MM-dd HH:mm:ss'
		});
		$('#etime').calendar({
			//minDate:'#stime',format:'yyyy-MM-dd HH:mm:ss'
			format:'yyyy-MM-dd HH:mm:ss'
		});
	});
//-->
</script>
</head>
<body>
	<form id="form" action="" method="post">
	<input type="hidden" name="token" id="token" value="${token}">
		<br>
		<table width="98%" border="0" cellspacing="1" cellpadding="3"
			class="contentTable" align="center">
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">车牌号</td>
				<td width="30%"><span class="contentclass"><input
						type="text" id="carnum" name="carnum" size="20" maxlength="20" /></span></td>
				<td class="editLabel" width="20%" align="center">货物类型</td>
				<td width="30%">
				<select id="goodstype" name="goodstype">
					<option value="">--请选择--</option>
					<c:forEach items="${goodstypelist}" var="list" varStatus="status">
						<option value="${list.goodstypename}">${list.goodstypename}</option>
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
						<option value="${list.deptname}">${list.deptname}</option>
					</c:forEach>
				</select>
				</td>
				<td class="editLabel" width="20%" align="center">发货时间</td>
				<td width="30%"><input type="text" id="stime" name="stime" class="runcode"
								value="${stime}" readonly /></td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">发货人</td>
				<td width="30%">
					<input name="consignor" id="consignor" type="text" size="20" maxlength="30"/>
				</td>
				<td class="editLabel" width="20%" align="center"></td>
				<td width="30%"><span class="contentclass"></span></td>
			</tr>			
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">收货单位</td>
				<td width="30%">
				<select id="rgoodsdept" name="rgoodsdept">
					<option value="">--请选择--</option>
					<c:forEach items="${rgoodsdeptlist}" var="list" varStatus="status">
						<option value="${list.deptname}">${list.deptname}</option>
					</c:forEach>
				</select>				
				</td>
				<td class="editLabel" width="20%" align="center">收货时间</td>
				<td width="30%"><input type="text" id="etime" name="etime" class="runcode"
								value="${etime}" readonly /></td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">收货人</td>
				<td width="30%">
					<input name="consignee" id="consignee" type="text" size="20" maxlength="30"/>
				</td>
				<td class="editLabel" width="20%" align="center"></td>
				<td width="30%"><span class="contentclass"></span></td>
			</tr>	
			<tr class="defaultBGColor">
				<td class="editLabel" align="center" width="20%">货物重量:</td>
				<td class="editLabel">
				<input name="goodsweight" id="goodsweight" size="20" maxlength="30">&nbsp;&nbsp;公斤
				</td>
				<td class="editLabel" width="20%" align="center"></td>
				<td width="30%"><span class="contentclass"></span></td>				
			</tr>						
			<tr class="defaultBGColor">
				<td class="editLabel" align="center" width="20%">备注:</td>
				<td class="editLabel" colspan="5"><textarea name="remark"
						id="remark" rows="5" class="editText1"
						STYLE="width: 99%; resize: none;" onKeyUp="enter(this)">${order.remark}</textarea></td>
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
	function save() {
		var token = $("#token").val();
		var carnum = $("#carnum").val();
		var goodstype = $("#goodstype").val();
		var sgoodsdept = $("#sgoodsdept").val();
		var consignor = $("#consignor").val();
		var stime = $("#stime").val();
		var rgoodsdept = $("#rgoodsdept").val();
		var consignee = $("#consignee").val();
		var etime = $("#etime").val();
		var goodsweight = $("#goodsweight").val();
		var remark = $("#remark").val();
		if(carnum.length<1){
			layer.msg('车牌号不能为空!');
			return;
		}
		if(goodstype.length<1){
			layer.msg('请选择货物类型!');
			return;
		}
		if(sgoodsdept.length<1){
			layer.msg('请选择发货部门!');
			return;
		}
		if(stime.length<1){
			layer.msg('发货时间不能为空!');
			return;
		}
		if(etime.length<1){
			layer.msg('收货时间不能为空!');
			return;
		}
		if(!isNumeric(goodsweight)){
			layer.msg('重量格式错误!');
			return;
		}
		$.ajax({
			url : "${ctx}/order/ordermanualadd",
			async : true,
			type : "post",
			dataType : "json",
			data : {
				token : token,
				carnum : carnum,
				goodstype : goodstype,
				sgoodsdept : sgoodsdept,
				consignor : consignor,
				stime : stime,
				rgoodsdept : rgoodsdept,
				consignee : consignee,
				etime : etime,
				goodsweight : goodsweight,
				remark : remark
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
						parent.searchRecord();
						parent.layer.close(index);
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