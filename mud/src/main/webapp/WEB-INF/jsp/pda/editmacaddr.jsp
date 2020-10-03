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
<script type="text/javascript" src="${ctx}/js/tool/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/tool/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="${ctx}/js/tool/jquery-ui-1.12.1.custom/jquery-ui.min.css">
<script type="text/javascript" src="${ctx}/js/tool/layer/layer.js"></script>
</head>
<body>
	<form id="form" action="" method="post">
	<input type="hidden" id="id" name="id" value="${id}">
		<table width="98%" border="0" cellspacing="1" cellpadding="3"
			class="contentTable" align="center">
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">MAC地址信息</td>
				<td width="30%"><span class="contentclass"><input
						type="text" name="macaddr" id="macaddr" size="30" maxlength="50"
						value="${macaddr}" /></span></td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center">状态</td>
				<td width="30%">
				<select id="status" name="status">
				<c:choose>
					<c:when test="${status == 0}">
						<option value="0" selected>有效</option>
						<option value="1">无效</option>
					</c:when>
					<c:otherwise>
						<option value="0">有效</option>
						<option value="1" selected>无效</option>
					</c:otherwise>
				</c:choose>
				</select>
				</td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center" height="20"></td>
				<td width="30%"></td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" width="20%" align="center" height="20"></td>
				<td width="30%"></td>
			</tr>
			<tr class="defaultBGColor">
				<td class="editLabel" colspan="2" align="center"><a href="#"
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
		var id = $("#id").val();
		var macaddr = $("#macaddr").val();
		var status = $("#status").val();
		if (macaddr.length<1 || macaddr.length>50) {
			layer.tips('mac不能为空或超过50个字符!', '#macaddr', {
				tips : 2
			});
			return;
		} else {
			$.ajax({
				url : "${ctx}/pda/editmacaddr",
				async : true,
				type : "post",
				dataType : "json",
				data : {
					id : id,
					macaddr : macaddr,
					status : status
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

	}
</script>
</html>