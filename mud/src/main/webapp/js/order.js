$(function() {
	// $("#dialhistory").css('display','none');

	// 绑定街路text变化 事件
	$('#cuaddr').bind('input propertychange', function() {
	});
	
	// 绑定小区text变化 事件
	$('#cuareas').bind('input propertychange', function() {
		showAlikeOrder();
	});
	
	$("#cuaddr").autocomplete({
		// source: "<app:iniPath />/sarea/sareajsonlist.do",
		dataType : 'json', // 必须填写，不然默认的是text
		minLength : 1,// 输入1个字母开始检索
		max : 10, // 最多显示10个
		source : function(request, response) {
			$.ajax({
				url : "../street/streetjsonlist.do",
				async : true,
				type : "post",
				dataType : "json",
				data : {
					// cuareas : encodeURI($("#cuareas").val())
					cuaddr : $("#cuaddr").val()
				},
				success : function(data) {
					response($.map(data, function(item) {
						return {
							value : item.streetname,
							lable : item.streetshort
						}
					}));
				},
				error : function() {
				}
			});
		},
		select : function(event, ui) {
			$("#cuaddr").val(ui.item.value);
			// alert(ui.item.value);
		}
	});
	
	$("#cuareas").autocomplete({
		// source: "<app:iniPath />/sarea/sareajsonlist.do",
		dataType : 'json', // 必须填写，不然默认的是text
		minLength : 1,// 输入1个字母开始检索
		max : 10, // 最多显示10个
		source : function(request, response) {
			$.ajax({
				url : "../sarea/sareajsonlist.do",
				async : true,
				type : "post",
				dataType : "json",
				data : {
					// cuareas : encodeURI($("#cuareas").val())
					cuareas : $("#cuareas").val()
				},
				success : function(data) {
					response($.map(data, function(item) {
						return {
							value : item.areaname,
							lable : item.areashort
						}
					}));
				},
				error : function() {
				}
			});
		},
		select : function(event, ui) {
			$("#cuareas").val(ui.item.value)
			showAlikeOrder();
			// alert(ui.item.value);
		}
	});
	
	// 来电历史记录
	$("#dialhistory").click(function() {
		layer.closeAll();
		if ($("#telno").val().length > 0 && $("#telnum").val() > 0) {
			layer.open({
				type : 2,
				title : '来电历史',
				shade : 0.1,
				shadeClose : true,
				area : [ '950px', '450px' ],
				content : '../order/dialhistory.do?telno=' + $("#telno").val() // iframe的url
			});
		}
	});

	// /////////////////////////////
	// //ajax弹出部门窗口
	// /////////////////////////////
	$("#btndept").click(function() {
		opendept();
	});

	// /////////////////////////////
	// //问题类型 级联菜单
	// /////////////////////////////
	$("#asktype1").change(
			function() {
				var p = $(this).children('option:selected').val();
				$("select[name='asktype2']").empty();
				if (p == "") {
					var option = $("<option>").val("").text("--请选择--");
					$("select[name='asktype2']").append(option);
				}
				// 级联
				for (var i = 0; i < ple_list.length; i++) {
					if (ple_list[i].pid == 'wtlx' && p == ple_list[i].itemno) {
						var option = $("<option>").val(ple_list[i].exitemno)
								.text(ple_list[i].itemstr);
						$("select[name='asktype2']").append(option);
						
						//选水费 默认扩展为 其他
						if(p == "55"){
							$("select[name='asktype2']").val("99");
						}
					}
				}
			});

	// /////////////////////////////
	// //产权责任 级联菜单
	// /////////////////////////////
	$("#propertyliab1").change(
			function() {
				var p = $(this).children('option:selected').val();
				$("select[name='propertyliab2']").empty();
				if (p == "") {
					var option = $("<option>").val("").text("--请选择--");
					$("select[name='propertyliab2']").append(option);
				}
				// 级联
				for (var i = 0; i < ple_list.length; i++) {
					if (ple_list[i].pid == 'cqzr' && p == ple_list[i].itemno) {
						var option = $("<option>").val(ple_list[i].exitemno)
								.text(ple_list[i].itemstr);
						$("select[name='propertyliab2']").append(option);
					}
				}
			});

});

///////////////////////////////
////弹出部门对话框
///////////////////////////////
function opendept() {
	layer.open({
		type : 2,
		area : [ '200px', '300px' ],
		fix : false, // 不固定
		maxmin : true,
		content : '../jsp/dept/orderdept.jsp'
	});
}

///////////////////////////////
////表单验证
///////////////////////////////
function save() {

	// 联系电话
	/*
	if ($("#cutelno").val().length < 1) {
		layer.tips('联系电话不能为空!', '#cutelno', {
			tips : 2
		});
		return;
	}
	*/
	// 姓名
	if ($("#cuname").val().length < 1) {
		layer.tips('姓名不能为空!', '#cuname', {
			tips : 2
		});
		return;
	}

	if ($("#asksource").val() == "") {
		layer.tips('请选择问题来源!', '#asksource', {
			tips : 2
		});
		return;
	} else {
		$("#asksourcetxt").val($('#asksource option:selected').text());
	}

	if ($("#askcategory").val() == "") {
		layer.tips('请选择问题类别!', '#askcategory', {
			tips : 2
		});
		return;
	} else {
		$("#askcategorytxt").val($('#askcategory option:selected').text());
	}

	if ($("#asktype1").val() == "") {
		layer.tips('请选择问题类型!', '#asktype1', {
			tips : 2
		});
		return;
	} else {
		$("#asktype1txt").val($('#asktype1 option:selected').text());
	}

	if ($("#asktype2").val() == "") {
		layer.tips('请选择问题类型!', '#asktype2', {
			tips : 2
		});
		return;
	} else {
		$("#asktype2txt").val($('#asktype2 option:selected').text());
	}

	// 环节类型1
	var s1ck = $("input[name='state1']:checked").val();
	if (s1ck == "11") {
		// 转办		
		if ($("#deptname").val() == "") {
			layer.tips('请选择责任单位!', '#deptname', {
				tips : 4
			});
			return;
		}
		//责任人
		if ($("#responsibleperson").val() == "") {
			layer.tips('请填写责任人!', '#responsibleperson', {
				tips : 4
			});
			return;
		}	
		//办件人
		if ($("#zbtouser").val() == "") {
			layer.tips('请填写办件人!', '#zbtouser', {
				tips : 4
			});
			return;
		}		
		//办件人电话
		if ($("#zbtousertel").val() == "") {
			layer.tips('请填写办件人电话!', '#zbtousertel', {
				tips : 2
			});
			return;
		}		
		//单据类型
		if ($("#zbreceiptsid").val() == "") {
			layer.tips('请选择单据类型!', '#zbreceiptsid', {
				tips : 2
			});
			return;
		} else {
			$("#zbreceiptstxt").val($('#zbreceiptsid option:selected').text());
		}
		//产权责任
		/*
		if ($("#propertyliab1").val() == "") {
			layer.tips('请选择产权责任!', '#propertyliab1', {
				tips : 2
			});
			return;
		} else {
			$("#propertyliabtxt1").val($('#propertyliab1 option:selected').text());
		}
		if ($("#propertyliab2").val() == "") {
			layer.tips('请选择产权责任!', '#propertyliab2', {
				tips : 2
			});
			return;
		} else {
			$("#propertyliabtxt2").val($('#propertyliab2 option:selected').text());
		}
		*/
	}
	//单据类型
	if ($("#zbreceiptsid").val() != "") {
		$("#zbreceiptstxt").val($('#zbreceiptsid option:selected').text());
	}	
	//产权责任
	if ($("#propertyliab1").val()!="") {
		$("#propertyliabtxt1").val($('#propertyliab1 option:selected').text());
	}
	if ($("#propertyliab2").val()!="") {
		$("#propertyliabtxt2").val($('#propertyliab2 option:selected').text());
	}	
	// 问题内容
	if ($("#askmsg").val().length < 1) {
		layer.tips('问题内容不能为空!', '#askmsg', {
			tips : 2
		});
		return;
	}
	$("#form").submit();
}

///////////////////////////////
////输入小区 显示相关问题列表
///////////////////////////////
function showAlikeOrder() {
	$("#xgwt").html("");
	var cuareas = $("#cuareas").val();
	if (cuareas.length < 1) {
		return;
	}
	$.ajax({
				url : "../order/getcsrunbyareas.do",
				async : true,
				type : "post",
				dataType : "json",
				data : {
					cuareas : cuareas
				},
				success : function(data) {
					barray = eval(data);
					if (barray != null) {
						var str;
						str = '<table width="100%" border="0" cellspacing="1" cellpadding="1" class="contentTable" align="center">'
						str += '<tr class="tableTitle" align="center" >';
						str += '<td width="30%">日期</td>';
						str += '<td width="70%">内容</td>';
						// str += '<td width="10%">结论</td>';
						// str += '<td width="10%">状态</td>';
						str += '</tr>';
						for (i = 0; i < barray.length; i++) {
							str += '<tr class="defaultBGColor" align="center" style="cursor:hand" ';
							str += 'onmouseout="this.style.backgroundColor=\'\';"onmouseover="this.style.backgroundColor=\'#FDF9E3\';">';
							str += '<td>';
							str += barray[i].calldate;
							str += '</td>';
							str += '<td>';
							str += '<a href="#" onclick="tocsrun(\''+ barray[i].acceptid + '\')">';
							str += barray[i].askmsg;
							str += '</a>';
							str += '</td>';
							// str += '<td>';
							// str += barray[i].bfResMsg;
							// str += '</td>';
							// str += '<td>';
							// str += barray[i].state2txt;
							// str += '</td>';
							str += '</tr>';
						}

						str += '</table>';
						$("#xgwt").html(str);
					}
				},
				error : function() {
				}
	});
	
	///显示对应的责任部门
	//1.根据cuareas 查找到责任部门名称,责任部门编码,泵站信息
	$.ajax({
		url : "../sarea/getDept.do",
		async : true,
		type : "post",
		dataType : "json",
		data : {
			cuareas : cuareas
		},
		success : function(data) {
			barray = eval(data);
			if (barray != null) {
				$("#other1").val(barray.other1);
				$("#other2").val(barray.other2);
				$("#deptcode").val(barray.deptcode);
				$("#deptname").val(barray.deptname);
				$("#responsibleperson").val(barray.manager);
				//2.根据责任部门名称取 责任人 办件人 办件人电话
				fillTransactor();
			}else{
				$("#other1").val("");
				$("#other2").val("");
				$("#deptcode").val("");
				$("#deptname").val("");
				$("#responsibleperson").val("");
				
				$("select[name='zbtouser']").empty();
				var option = $("<option>").val("").text("--请选择办件人--");
				$("select[name='zbtouser']").append(option);
				$("#zbtousertel").val("");
			}
		},
		error : function() {
			alert(-1);
		}
	});

}

///////////////////////////////
////点击历史记录或相关记录 进入run明细
///////////////////////////////
function tocsrun(acceptid) {
	layer.closeAll();
	layer.open({
		type : 2,
		title : '诉求信息',
		shade : 0.1,
		shadeClose : true,
		area : [ '950px', '400px' ],
		content : '../order/getcsrunbyidnoback.do?acceptid=' + acceptid // iframe的url
	});
}
///////////////////////////////
////显示历史来电记录
///////////////////////////////
function showHistoryCall() {

	var telno = $("#telno").val();
	if (telno.length < 1) {
		return;
	}
	$.ajax({
				url : "../order/gethistorycalllistbytelno.do",
				async : true,
				type : "post",
				dataType : "json",
				data : {
					telno : telno
				},
				success : function(data) {
					barray = eval(data);
					if (barray != null) {
						// 填充客户信息
						var str;
						str = '<table width="100%" border="0" cellspacing="1" cellpadding="1" class="contentTable" align="center">'
						str += '<tr class="tableTitle" align="center" >';
						str += '<td width="30%">日期</td>';
						str += '<td width="70%">内容</td>';
						// str += '<td width="10%">结论</td>';
						// str += '<td width="10%">状态</td>';
						str += '</tr>';
						for (i = 0; i < barray.length; i++) {
							str += '<tr class="defaultBGColor" align="center" style="cursor:hand" ';
							str += 'onmouseout="this.style.backgroundColor=\'\';"onmouseover="this.style.backgroundColor=\'#FDF9E3\';">';
							str += '<td>';
							str += barray[i].calldate;
							str += '</td>';
							str += '<td>';
							str += '<a href="#" onclick="tocsrun(\''+ barray[i].acceptid + '\')">';
							str += barray[i].askmsg;
							str += '</a>';
							str += '</td>';
							// str += '<td>';
							// str += barray[i].bfResMsg;
							// str += '</td>';
							// str += '<td>';
							// str += barray[i].state2txt;
							// str += '</td>';
							str += '</tr>';
						}

						str += '</table>';
						$("#historycall").html(str);
					}
				},
				error : function() {
				}
			});
}

// /////////////////////////////
// //来电填充客户信息
// /////////////////////////////
function fillCustomer(telno) {
	// 清空表单数据
	//clearForm();
	// 填充来电时间
	$("#calldate").val(currentDateTime());

	// 填充客户信息
	$.ajax({
		url : "../order/getcustomerbytelno.do",
		async : true,
		type : "post",
		dataType : "json",
		data : {
			telno : telno
		},
		success : function(data) {
			barray = eval(data);
			if (barray != null) {
				// 姓名
				$("#cuname").val(barray.cuname);
				// 性别
				if (barray.cusex == 1) {
					$("input[name='cusex']").get(0).checked = true;
				} else {
					$("input[name='cusex']").get(1).checked = true;
				}
				// 联系电话
				//$("#cutelno").val(barray.cuTelNo);
				// 小区
				$("#cuareas").val(barray.cuareas);
				// 路街
				$("#cuaddr").val(barray.cuaddr);
				// 门牌号
				$("#cuaddrother").val(barray.cuaddrother);
				// 显示小区相关问题
				showAlikeOrder();
				// 显示历史来电记录
				showHistoryCall();
			}
		},
		error : function() {
		}
	});
}

// /////////////////////////////
// //清除表单数据
// /////////////////////////////
function clearForm() {
	// 姓名
	$("#cuname").val("");
	// 填充来电时间
	$("#calldate").val("");
	// 联系电话
	$("#cutelno").val("");
	// 小区
	$("#cuareas").val("");
	// 路街
	$("#cuaddr").val("");
	// 门牌号
	$("#cuaddrother").val("");
	//历史来电
	$("#historycall").html("");
	//相关问题
	$("#xgwt").html("");
}

// /////////////////////////////
// //选择部门 填充责任人 办件人 部门联系电话
// /////////////////////////////
var arrTel = []; //办件人电话数组
function fillTransactor() {
	var deptcode = $("#deptcode").val();
	if (deptcode.length > 0) {
		// 取办件人
		$.ajax({
			url : "../user/getuserListbydeptcode.do",
			async : true,
			type : "post",
			dataType : "json",
			data : {
				deptcode : deptcode
			},
			success : function(data) {
				arrTel.length = 0;
				barray = eval(data);
				if (barray != null) {
					$("select[name='zbtouser']").empty();
					//var option = $("<option>").val("").text("--请选择办件人--");
					//$("select[name='zbtouser']").append(option);
					for (var i = 0; i < barray.length; i++) {
						var option = $("<option>").val(barray[i].user_name)
								.text(barray[i].user_name);
						$("select[name='zbtouser']").append(option);
						arrTel.push(barray[i].tel);
					}
					document.getElementById("zbtousertel").value = arrTel[0];
					/*
					$("select[name='zbtousertel']").empty();
					
					
					for (var i = 0; i < barray.length; i++) {
						var option = $("<option>").val(barray[i].tel).text(
								barray[i].user_name + " [" + barray[i].tel
										+ "]");
						$("select[name='zbtousertel']").append(option);
					}
					*/
				}
			}
		});
	}
}
//选择办件人
function zbtouserChang(){
	var obj = document.getElementById("zbtouser");
	var index = obj.selectedIndex;
	document.getElementById("zbtousertel").value = arrTel[index];
}
