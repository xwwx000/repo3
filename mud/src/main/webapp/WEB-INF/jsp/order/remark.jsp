<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${ctx}/css/default.css"
	type="text/css">
<link rel="stylesheet" href="${ctx}/css/button/gh-buttons.css"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/js/tool/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script
	src="${ctx}/js/tool/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="${ctx}/js/tool/jquery-ui-1.12.1.custom/jquery-ui.min.css">
<script type="text/javascript"
	src="${ctx}/js/tool/layer/layer.js"></script>
</head>
<body>
<form id="form" action="" method="post">
		<input type="hidden" id="id" name="id" value="${order.id}" /> 
		<br>
		<table width="98%" border="0" cellspacing="1" cellpadding="3"
			class="contentTable" align="center">
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">流水号</td>
				<td width="30%"><span class="contentclass">${order.snum}</span></td>
				<td class="editLabel" width="20%" align="center">车牌号</td>
				<td width="30%"><span class="contentclass">${order.carnum}</span></td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">发货单位</td>
				<td width="30%"><span class="contentclass">${order.sgoodsdept}</span></td>
				<td class="editLabel" width="20%" align="center">发货时间</td>
				<td width="30%"><span class="contentclass">${order.sgoodstime}</span></td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">收货单位</td>
				<td width="30%"><span class="contentclass">${order.rgoodsdept}</span></td>
				<td class="editLabel" width="20%" align="center">收货时间</td>
				<td width="30%"><span class="contentclass">${order.rgoodstime}</span></td>
			</tr>			
			<tr class="defaultBGColor">
				<td class="editLabel" align="center" width="20%">备注:</td>
				<td class="editLabel" colspan="5"><textarea name="remark"
						id="remark" rows="5" class="editText1" 
						STYLE="width: 99%; resize: none;" onKeyUp="enter(this)">${order.remark}</textarea></td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" colspan="6" align="center" ><a href="#" id="btnsave"
								class="button big" style="margin-right: 5px;">保存</a><a href="#" id="btnquit"
								class="button big" style="margin-right: 5px;">取消</a></td>
			</tr>		
		</table>
</form>
</body>
<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
$(function (){
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
	var id = $("#id").val();
	var remark = $("#remark").val();
	if (remark.length<1 || remark.length>200) {
		layer.tips('备注为空或超过200字!', '#remark', {
			tips : 2
		});
		return;
	}else{
		$.ajax({
			url : "${ctx}/order/saveremark.do",
			async : true,
			type : "post",
			dataType : "json",
			data : {id:id,remark:remark},
			success : function(data) {
				var barray = eval(data);
				if(barray.code == 200){
					layer.msg('保存成功!', {
						shade : 0.2,
						time : 1000
					}, function() {
						parent.layer.close(index);
					});				
				}else{
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
	
}
</script>
</html>