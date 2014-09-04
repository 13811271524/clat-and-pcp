<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/Web/common/page/jqueryMaster.jsp"%>
<%@include file="/Web/common/page/check.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- jquery easyui -->
<link rel="stylesheet" type="text/css"
	href="<%=root%>/Web/common/js/easyui/themes/dayun/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=root%>/Web/common/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="<%=root%>/Web/common/js/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=root%>/Web/common/js/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=root%>/Web/common/js/easyui/validate/easyui_validate.js"></script>

<script type="text/javascript" src="<%=root%>/Web/usermanage/js/user.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
</head>
<body class="easyui-layout" style="height: 100%">
	<div region="center" style="height: 100%">
		<table id="tt" class="easyui-datagrid">
		</table>
	</div>
	<!-- 添加 -->
	<div id="loginInfoAdd" icon="icon-save"
		style="padding: 5px; width: 400px; height: 270px;">
		<h5 id="loginInfoAdd_message" style="color: red;"></h5>
		<div class="ToolTip_Form" id="table_loginInfoAdd"
			onkeydown="if(event.keyCode==13){loginInfoAdd();}">
			<table>
				<tr>
					<td>ipv6前缀:</td>
					<td><input class="easyui-validatebox" type="text"
						id="loginInfoAdd_loginCode" required="true" maxlength="15"></input></td>
				</tr>
				<tr>
					<td>ipv4目的地址:</td>
					<td><input class="easyui-validatebox" type="text"
						id="loginInfoAdd_password" required="true" maxlength="15"></input></td>
				</tr>
				
			</table>
			<div style="text-align: center; padding: 25px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="loginInfoAdd()">提交</a> <a href="javascript:void(0)"
					class="easyui-linkbutton" onclick="loginInfoAddReset()">重置</a>
			</div>

		</div>
	</div>
	<!-- 编辑 -->
	<div id="loginInfoEdit" icon="icon-save"
		style="padding: 5px; width: 500px; height: 300px;">
		<h5 id="loginInfoEdit_message" style="color: red;"></h5>
		<div class="ToolTip_Form" id="table_loginInfoEdit"
			onkeydown="if(event.keyCode==13){loginInfoEdit();}">
			<input type="hidden" id="loginInfoEdit_loginId"></input>
			<table>
				<tr>
					<td>用户名:</td>
					<td><input type="text"
						id="loginInfoEdit_loginCode" disabled="disabled"></input></td>
				</tr>
				<tr>
					<td>角色:</td>
					<td><select class="easyui-combobox" id="edit_role" panelHeight="auto">
							<option value="user">普通用户</option>
							<option value="admin">管理员</option>
					</select></td>
				</tr>
				<tr>
					<td>文件操作:</td>
					<td><select class="easyui-combobox" id="edit_operation" panelHeight="auto">
							<option value="updown">上传下载</option>
							<option value="up">仅上传</option>
							<option value="down">仅下载</option>
					</select></td>
				</tr>
			</table>
			<div style="text-align: center; padding: 25px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="loginInfoEdit()">提交</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="closeDialog_edit()">关闭</a> 
			</div>
		</div>
	</div>
	
	<!-- 查看 -->
	<div id="loginInfoShow" icon="icon-save"
		style="padding: 5px; width: 400px; height: 250px;">
		<div class="ToolTip_Form" id="table_loginInfoShow">
			<table>
				<tr>
					<td>用户名:</td>
					<td><input  type="text"
						id="loginInfoShow_loginCode"  disabled="disabled"></input></td>
				</tr>
				<tr>
					<td>上次登录时间:</td>
					<td><input  type="text"
						id="loginInfoShow_time"  disabled="disabled"></input></td>
				</tr>
				<tr>
					<td>上次登录IP:</td>
					<td><input  type="text"
						id="loginInfoShow_ip"  disabled="disabled"></input></td>
				</tr>
				<tr>
					<td>角色:</td>
					<td><select class="easyui-combobox" id="show_role" panelHeight="auto" disabled="disabled">
							<option value="user">普通用户</option>
							<option value="admin">管理员</option>
					</select></td>
				</tr>
				<tr>
					<td>文件操作:</td>
					<td><select class="easyui-combobox" id="show_operation" panelHeight="auto" disabled="disabled">
							<option value="updown">上传下载</option>
							<option value="up">仅上传</option>
							<option value="down">仅下载</option>
					</select></td>
				</tr>
			</table>
			<div style="text-align: center; padding: 25px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="closeDialog_show()">关闭</a> 
			</div>
		</div>
	</div>
</body>

</html>

