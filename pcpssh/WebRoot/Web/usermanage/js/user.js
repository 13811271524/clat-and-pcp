$(function() {
	$('#tt').datagrid({
		title : '用户信息',
		iconCls : 'icon-ok',
		width : '100%',
		fit : true,
		fitcolumns : true,
		toolbar : [ {
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				openDialog_add();
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				// 删除
				delDoc();
			}
		} ],
		pageSize : 10,
		pageList : [ 5, 10, 15, 20 ],
		nowrap : false,
		striped : true,
		collapsible : true,
		url : 'queryAllDatalistAction.action',
		loadMsg : '数据装载中......',
		sortName : 'id',
		sortOrder : 'asc',
		remoteSort : false,

		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [ {
			title : 'ipv6前缀',
			field : 'ipv6fix',
			width : '200',
			rowspan : 2,
			align : 'center'
		}, {
			title : 'ipv4目的地址',
			field : 'ipv4addr',
			width : '200',
			rowspan : 2,
			align : 'center'
		} ] ],

		pagination : true,
		rownumbers : true
	});
	$('#tt').datagrid('getPager').pagination({
		displayMsg : '当前显示从{from}到{to}共{total}记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		},
	});

	// var txt
	// ='{"total":3,"rows":[{"id":4,"lastLogIp":"1.1.1.1","lastLogTime":"2014-03-26
	// 09:56:54","userPwd":"37343n3s3o13o1o3642s6o1i6o217ng4","menuId":5,"userName":"admin","userRole":"admin"},{"id":2,"lastLogIp":"","lastLogTime":"","userPwd":"313gns93og1s131ns96n13713y346n31","menuId":0,"userName":"song","userRole":""},{"id":3,"lastLogIp":"","lastLogTime":"","userPwd":"313gns93og1s131ns96n13713y346n31","menuId":0,"userName":"www","userRole":""}]}';

	// var txt
	// ='{"total":3,"rows":[{"menuId":0,"userPwd":"37343n3s3o13o1o3642s6o1i6o217ng4","id":1,
	// "userName":"user1", "userRole":"admin", "lastLogTime":"2014-03-07
	// 21:04:56", "lastLogIp":"120.111.11.30"}, {"id":2, "userName":"user2",
	// "userRole":"admin", "lastLogTime":"2014-03-08 22:05:57",
	// "lastLogIp":"121.112.12.31"},{"id":3, "userName":"user3",
	// "userRole":"admin", "lastLogTime":"2014-03-09 23:06:58",
	// "lastLogIp":"122.113.13.32"}]}';
	// var data = $.parseJSON(txt);
	// $('#tt').datagrid('loadData', data); //将数据绑定到datagrid

	// $('#tt').datagrid({url:'easyAction.action'});

	// 初始化弹出层
	setDialog_add();
	closeDialog_add();
	setDialog_edit();
	closeDialog_edit();
	setDialog_show();
	closeDialog_show();

});

// 刷新表格
function reloadTable() {
	$('#tt').datagrid('reload');
}

/** ----------------显示详细信息弹出框---------------------* */
function setDialog_show() {
	$('#loginInfoShow').dialog({
		title : '详细信息',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true, // 可拖动边框大小
		onClose : function() { // 继承自panel的关闭事件
			// loginInfoAddReset();
		}
	});
}
// 打开对话框
function openDialog_show(name, role, time, ip, menu) {
	$('#loginInfoShow').dialog('open');
	$("#loginInfoShow_loginCode").val(name);
	$('#show_role').combobox('setValue', role);
	$('#show_operation').combobox('setValue', menu);
	$("#loginInfoShow_time").val(time);
	$("#loginInfoShow_ip").val(ip);
}
// 关闭对话框
function closeDialog_show() {
	$('#loginInfoShow').dialog('close');
}

/** --------------添加操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_add() {
	$('#loginInfoAdd').dialog({
		title : '用户添加',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true, // 可拖动边框大小
		onClose : function() { // 继承自panel的关闭事件
			loginInfoAddReset();
		}
	});
}
// 打开对话框
function openDialog_add() {
	$('#loginInfoAdd').dialog('open');
}
// 关闭对话框
function closeDialog_add() {
	$('#loginInfoAdd').dialog('close');
}
// 执行用户添加操作
function loginInfoAdd() {

	var validateResult = true;
	// easyui 表单验证
	$('#table_loginInfoAdd input').each(function() {
		if ($(this).attr('required') || $(this).attr('validType')) {
			if (!$(this).validatebox('isValid')) {
				// 如果验证不通过，则返回false
				validateResult = false;
				return;
			}
		}
	});

	if (validateResult == false) {
		$("#loginInfoAdd_message").html("校验未通过!");
		return;
	}

	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"datalist.ipv6fix" : $("#loginInfoAdd_loginCode").val(),
			"datalist.ipv4addr" : $("#loginInfoAdd_password").val(),
		},
		url : root + '/addDatalist.action',// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) {

			var messgage = "添加成功!";
			if (data.result == 'success') {// 未返回任何消息表示添加成功
				loginInfoAddReset();
				// 刷新列表
				reloadTable();
			} else {// 返回异常信息
				messgage = data.result;
			}
			$("#loginInfoAdd_message").html(messgage);
			//
			closeDialog_add();
		}
	});
}
// 用户添加页面数据重置操作
function loginInfoAddReset() {
	$("#loginInfoAdd_message").html("");
	$("#loginInfoAdd_loginCode").val("");
	$("#loginInfoAdd_password").val("");
	$('#role').combobox('setValue', "user");
	$('#operation').combobox('setValue', "updown");
}

/** --------------编辑操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_edit() {
	$('#loginInfoEdit').dialog({
		title : '用户编辑',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true
	// 可拖动边框大小
	});
}
// 打开对话框
function openDialog_edit(id, name, role, menu) {
	$("#loginInfoEdit_loginId").val(id);
	$("#loginInfoEdit_loginCode").val(name);
	$('#edit_role').combobox('setValue', role);
	$('#edit_operation').combobox('setValue', menu);
	$('#loginInfoEdit').dialog('open');
}
// 关闭对话框
function closeDialog_edit() {
	$('#loginInfoEdit').dialog('close');
}
// 执行用户编辑操作
function loginInfoEdit() {

	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"user.id" : $("#loginInfoEdit_loginId").val(),
			"user.userRole" : $('#edit_role').combobox('getValue'),
			"user.menuId" : $('#edit_operation').combobox('getValue')
		},
		url : root + '/editUser.action',// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) {
			var messgage = "修改成功!";
			if (data.result == 'success') {// 未返回任何消息表示添加成功
				// 刷新列表
				reloadTable();
			} else {// 返回异常信息
				messgage = data.result;
			}
			$("#loginInfoEdit_message").html(messgage);
		}
	});
}

/** --------------执行停用操作------------------* */
function doDel(username) {
	$.messager.confirm('提示', '你确定停用该用户吗?', function(r) {
		if (r) {
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				dataType : "json",
				data : {
					"user.userName" : username,
				},
				url : root + '/moveUserToRecycle.action',// 请求的action路径
				error : function() {// 请求失败处理函数
					alert('请求失败');
				},
				success : function(data) {
					if (data.result == 'success') {// 未返回任何消息表示添加成功
						// 刷新列表
						reloadTable();
					} else {// 返回异常信息
						alert('停用失败');
					}
				}
			});
		}
	});
}
// 启用某用户
function doUp(username) {
	$.messager.confirm('提示', '你确定启用该用户吗?', function(r) {
		if (r) {
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				dataType : "json",
				data : {
					"user.userName" : username,
				},
				url : root + '/moveUserToUse.action',// 请求的action路径
				error : function() {// 请求失败处理函数
					alert('请求失败');
				},
				success : function(data) {
					if (data.result == 'success') {// 未返回任何消息表示添加成功
						// 刷新列表
						reloadTable();
					} else {// 返回异常信息
						alert('启用失败');
					}
				}
			});
		}
	});
}

// 批量停用
function delUser() {
	if ($('#tt').datagrid('getSelected')) {
		// 首先如果用户选择了数据，则获取选择的数据集合
		var names = [];
		var selectedRow = $('#tt').datagrid('getSelections');
		for ( var i = 0; i < selectedRow.length; i++) {
			names.push(selectedRow[i].userName);
		}
		var name = names.join(',');
		var sqlName = "";
		for ( var i = 0; i < names.length; i++) {
			sqlName += "'";
			sqlName += names[i];
			sqlName += "',";
		}
		sqlName = sqlName.substring(0, (sqlName.length - 1));
		// 停用操作
		$.messager.confirm('停用提示', '你确定停用下列用户吗?<br/>' + name, function(r) {
			if (r) {
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					dataType : "json",
					data : {
						"user.userName" : sqlName,
					},
					url : root + '/moveUserToRecycleBatch.action',// 请求的action路径
					error : function() {// 请求失败处理函数
						alert('请求失败');
					},
					success : function(data) {
						if (data.result == 'success') {// 未返回任何消息表示添加成功
							// 刷新列表
							reloadTable();
						} else {// 返回异常信息
							alert('停用失败');
						}
					}
				});
			}
		});
	} else {
		$.messager.confirm('提示', '请选择用户！', function(r) {
		});
	}
}
// 批量启用
function upUser() {
	if ($('#tt').datagrid('getSelected')) {
		// 首先如果用户选择了数据，则获取选择的数据集合
		var names = [];
		var selectedRow = $('#tt').datagrid('getSelections');
		for ( var i = 0; i < selectedRow.length; i++) {
			names.push(selectedRow[i].userName);
		}
		var name = names.join(',');
		var sqlName = "";
		for ( var i = 0; i < names.length; i++) {
			sqlName += "'";
			sqlName += names[i];
			sqlName += "',";
		}
		sqlName = sqlName.substring(0, (sqlName.length - 1));
		// 停用操作
		$.messager.confirm('启用提示', '你确定启用下列用户吗?<br/>' + name, function(r) {
			if (r) {
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					dataType : "json",
					data : {
						"user.userName" : sqlName,
					},
					url : root + '/moveUserToUseBatch.action',// 请求的action路径
					error : function() {// 请求失败处理函数
						alert('请求失败');
					},
					success : function(data) {
						if (data.result == 'success') {// 未返回任何消息表示添加成功
							// 刷新列表
							reloadTable();
						} else {// 返回异常信息
							alert('启用失败');
						}
					}
				});
			}
		});
	} else {
		$.messager.confirm('提示', '请选择用户！', function(r) {
		});
	}
}

// 重置密码
function resetPwd() {
	if ($('#tt').datagrid('getSelected')) {
		// 首先如果用户选择了数据，则获取选择的数据集合
		var names = [];
		var selectedRow = $('#tt').datagrid('getSelections');
		for ( var i = 0; i < selectedRow.length; i++) {
			names.push(selectedRow[i].userName);
		}
		var name = names.join(',');
		var sqlName = "";
		for ( var i = 0; i < names.length; i++) {
			sqlName += "'";
			sqlName += names[i];
			sqlName += "',";
		}
		sqlName = sqlName.substring(0, (sqlName.length - 1));
		$.messager.confirm('重置提示', '你确定重置下列用户的密码吗?<br/>' + name, function(r) {
			if (r) {
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					dataType : "json",
					data : {
						"user.userName" : sqlName,
					},
					url : root + '/resetPwd.action',// 请求的action路径
					error : function() {// 请求失败处理函数
						alert('请求失败');
					},
					success : function(data) {
						if (data.result == 'success') {// 未返回任何消息表示添加成功
							// 刷新列表
							// reloadTable();
						} else {// 返回异常信息
							alert('重置失败');
						}
					}
				});
			}
		});
	} else {
		$.messager.confirm('提示', '请选择用户！', function(r) {
		});
	}
}

// 删除
function delDoc() {
	if ($('#tt').datagrid('getSelected')) {
		// 首先如果用户选择了数据，则获取选择的数据集合
		var names = [];
		var selectedRow = $('#tt').datagrid('getSelections');
		for ( var i = 0; i < selectedRow.length; i++) {
			names.push(selectedRow[i].ipv6fix);
		}
		var name = names.join(',');
		var sqlName = "";
		for ( var i = 0; i < names.length; i++) {
			sqlName += "'";
			sqlName += names[i];
			sqlName += "',";
		}
		sqlName = sqlName.substring(0, (sqlName.length - 1));
		// 删除操作
		$.messager.confirm('删除提示', '你确定删除下列文档吗?<br/>' + name, function(r) {
			if (r) {
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					dataType : "json",
					data : {
						"datalist.ipv6fix" : sqlName,
					},
					url : root + '/delDatalist.action',// 请求的action路径
					error : function() {
						$.messager.confirm('删除提示', '请求失败', function(r) {
						});
					},
					success : function(data) {
						if (data.result == 'success') {
							// 刷新列表
							reloadTable();
						} else if (data.result == 'error') {
							$.messager.confirm('删除提示', '你没有权限删除文档',
									function(r) {
									});
						} else {// 返回异常信息
							alert('删除失败');
						}
					}
				});
			}
		});
	} else {
		$.messager.confirm('提示', '请选择数据！', function(r) {
		});
	}
}