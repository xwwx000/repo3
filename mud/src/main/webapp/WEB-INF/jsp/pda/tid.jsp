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
<link rel="stylesheet" type="text/css" href="${ctx}/js/tool/layuiold/css/layui.css">
<script src="${ctx}/js/tool/layuiold/layui.js"></script>
<script type="text/javascript" src="${ctx}/js/tool/layer/layer.js"></script>
<script src="${ctx}/js/page.js" type="text/javascript"></script>
<style type="text/css">
/*a  upload */
.a-upload {
    padding: 4px 25px;
    height: 50px;
    line-height: 50px;
    position: relative;
    cursor: pointer;
    color: #888;
    background: #fafafa;
    border: 1px solid #ddd;
    border-radius: 4px;
    overflow: hidden;
    display: inline-block;
    *display: inline;
    *zoom: 1
}

.a-upload  input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
    filter: alpha(opacity=0);
    cursor: pointer
}

.a-upload:hover {
    color: #444;
    background: #eee;
    border-color: #ccc;
    text-decoration: none
}
</style>
</head>
<body>
	<FORM action="${ctx}/pda/tid" id="tidform"
		name="tidform" method="post">
		<table width="98%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td width="150" height="35" align="left" id="Title">系统维护--tid维护</td>
				<td bgcolor="#f7f7f7">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						height="24">
						<tr>
							<td align="left"></td>
							<td align="right"
								background="<app:iniPath />/images/htimages/titlebg_center.gif">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="98%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr align="center">
				<td align="center" height="100"></td>
			</tr>
			<tr align="center">
				<td align="center" height="100"><span style="font-size: 28px;">当前标签数量:${tidcount}</span></td>
			</tr>	
			<tr align="center">
				<td>
	    			<a href="javascript:;" class="a-upload"><span style="font-size: 28px;"><input type="file" id="filename1" name="filename1" lay-title="导入TID码"></span></a>
				</td>
			</tr>		
		</table>
		<!--页下开始-->
	</FORM>
	<script type="text/javascript">
	function imptid(){
		window.location.href = "${ctx}/pda/pda_fahuo_d";
	}	
	</script>
	<script>
		layui.use([ 'form', 'upload' ], function() { //如果只加载一个模块，可以不填数组。如：layui.use('form')
			var form = layui.form() //获取form模块
			, upload = layui.upload; //获取upload模块

			//监听提交按钮
			form.on('submit(test)', function(data) {
				console.log(data);
			});

			//实例化一个上传控件
			upload({
				elem: '#filename1',
				url : '${ctx}/pda/upload.do',
				ext: 'xls|xlsx|txt',
				dataType : "json",
				method: 'post',//上传接口的http类型
				before: function(input){
				    //返回的参数item，即为当前的input DOM对象
				    console.log('文件上传中');
				},
				success : function(data) {
					alert("上传成功!");
					$("#tidform").submit();
				},
				error : function(){
					alert("上传失败!");
				}
			});					
		});
	</script>	
</BODY>
</html>