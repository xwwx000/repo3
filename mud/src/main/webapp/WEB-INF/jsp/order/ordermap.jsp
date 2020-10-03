<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收货位置</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/default.css">
<link href="${ctx}/css/page.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/default.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/button/gh-buttons.css"
	type="text/css">
 <style type="text/css">  
        html{height:100%}  
        body{height:100%;margin:0px;padding:0px}  
        #container{height:100%}  
    </style> 
<script type="text/javascript" src="${ctx}/js/tool/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript"
	src="${ctx}/js/date/lhgcalendar/lhgcalendar.min.js"></script>
<script src="${ctx}/js/tool/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="${ctx}/js/tool/jquery-ui-1.12.1.custom/jquery-ui.min.css">
<script type="text/javascript" src="${ctx}/js/tool/layer/layer.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=3.0&ak=EyAvpyMK3MFZ15ozyI5DI36TaNaCc75t"></script>
<script type="text/javascript">
var map;
$(function (){
	initMap();
});
function initMap(){
	map = new BMap.Map("container");      // 创建地图实例  
	var lo = ${lo};
	var la = ${la};
	var rgoodsdept = '${rgoodsdept}';
	var point = new BMap.Point(lo,la);  			// 创建点坐标   123.360136,41.763299
	map.centerAndZoom(point, 15);                 	// 初始化地图，设置中心点坐标和地图级别  
	map.enableScrollWheelZoom(true);     		//开启鼠标滚轮缩放
	add_control();
	addMarker(point,rgoodsdept);
	/*
	var marker = new BMap.Marker(point);      // 创建标注    
	map.addOverlay(marker);                     		// 将标注添加到地图中 
	var label = new BMap.Label(rgoodsdept,{offset:new BMap.Size(20,-10)});
	marker.setLabel(label); 								//添加GPS labe
	*/
}
function addMarker(point,rgoodsdept){  
	setTimeout(function(){
        var convertor = new BMap.Convertor();
        var pointArr = [];
        pointArr.push(point);
        convertor.translate(pointArr, 1, 5, translateCallback)
    }, 1000);
	
	//坐标转换完之后的回调函数
    translateCallback = function (data){
      if(data.status === 0) {
        var marker = new BMap.Marker(data.points[0]);
        map.addOverlay(marker);
        var label = new BMap.Label(rgoodsdept,{offset:new BMap.Size(20,-10)});
        marker.setLabel(label); //添加百度label
        map.setCenter(data.points[0]);
      }
    }
}
//添加控件和比例尺
function add_control(){
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
	/*缩放控件type有四种类型:
	BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/	
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);     
	map.addControl(top_right_navigation);    
}
</script>
</head>
<body>
<div id="container"></div>
</body>
</html>