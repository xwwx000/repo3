<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="${ctx}/css/frame/style.css" rel="stylesheet">
<link href="${ctx}/css/frame/reset.css" rel="stylesheet">
<link href="${ctx}/js/tool/ztree/zTreeStyle.css"
	rel="stylesheet">
<style type="text/css">
.ztree * {padding:0; margin:0; font-size:14px; font-family:黑体}
.ztree {margin:0; padding:5px; color:#333}
.ztree li{padding:1; margin:0; list-style:none; line-height:14px; text-align:left; white-space:nowrap; outline:0}
.ztree li ul{ margin:0; padding:0 0 0 18px}
</style>
<script type="text/javascript"
	src="${ctx}/js/tool/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/tool/ztree/jquery.ztree.all.min.js"></script>

</head>
<body>
	<input type="hidden" id="moduleid" name="moduleid"
		value="${requestScope.moduleid}" />
	<ul id="ztree" class="ztree"></ul>
</body>
<script type="text/javascript">
	var barray = [];
	var treeObj;
	var setting = {
		view: {
			dblClickExpand: false,
			showLine: false,
			showIcon: true
		},			
		data : {
			key : {
				name : "name"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid"
			}
		},callback:{
        	onClick: onClick
        }
	};
	$(document).ready(function() {
		$.ajax({
			url : "${ctx}/user/left",
			async : true,
			type : "post",
			dataType : "json",
			data : {
				moduleid : $("#moduleid").val()
			},
			success : function(data) {
				var barray = eval(data);
				//alert(JSON.stringify(barray));
				if (barray != null) {
					$.fn.zTree.init($("#ztree"), setting, barray);
					treeObj = $.fn.zTree.getZTreeObj("ztree");
					treeObj.expandAll(true);
					
					//变化图标
					changeico();
				}
			},
			error : function() {
			}
		});
	});
	function onClick(event, treeId, treeNode, clickFlag) {
		var zTree = $.fn.zTree.getZTreeObj("ztree");
		zTree.expandNode(treeNode);		
		if(treeNode.levelid == 3){
			
			//如果点击 坐席受理菜单 刷新整个页面
			if(treeNode.name == "坐席受理"){
				//top.location.href="<app:iniPath />/jsp/frame/mainorder.jsp";
				//top.location.href="<app:iniPath />/order/csrun.do";
				//window.open("<app:iniPath />/order/csrun.do","_blank");
				self.parent.document.getElementById("mainFrame").src="${ctx}/"+treeNode.target;
			}else{
				//self.parent.document.getElementById("mainFrame").src="<app:iniPath />/"+treeNode.target+"?r="+Math.random();
				self.parent.document.getElementById("mainFrame").src="${ctx}/"+treeNode.target;
			}
		}
	}
	function changeico(){
		var nodes = $.fn.zTree.getZTreeObj("ztree").getNodes();
		for(var i = 0;i<nodes.length;i++){
			for(var j = 0;j<(nodes[i].children).length; j++){
				var node = (nodes[i].children)[j];
				node.icon = "${ctx}/js/tool/ztree/img/diy/node.png";
				treeObj.updateNode(node);
			}
			nodes[i].iconOpen = "${ctx}/js/tool/ztree/img/diy/nodes_open.png";
			nodes[i].iconClose = "${ctx}/js/tool/ztree/img/diy/nodes_close.png";
			//调用updateNode(node)接口进行更新
			treeObj.updateNode(nodes[i]);
		}
	}
</script>
</html>