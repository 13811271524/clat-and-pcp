<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="/Web/common/page/jqueryMaster.jsp"%>
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

<script type="text/javascript" src="<%=root%>/Web/login/js/loginPage.js"></script>
<script type="text/javascript" src="<%=root%>/Web/login/js/code.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页</title>
<style>
<!--
body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td
	{
	padding: 0;
	margin: 0;
	overflow:hidden;
	zoom:100%;
}

body {
	background: url("<%=root%>/Web/login/images/loginbg.jpg") repeat-x;
	background-color: #fff;
}

.loginlogo {
	background: url("<%=root%>/Web/login/images/logo.png") no-repeat;
	width: 500px;
	height: 81px;
	margin-left: 10px;
	margin-top: 100px;
}

.bnlogin {
	background: url("<%=root%>/Web/login/images/loginbox.png") no-repeat;
	width: 392px;
	height: 470px;
	margin-left: 65%;
	margin-top: 15%;
}

.bnlogintd {
	position: relative;
	margin-top: 30px;
	margin-left: 50px;
	font-family: "微软雅黑";
	font-size: 13px;
	color: #333333;
}

.bnloginbtn {
	position: relative;
	margin-top: 20px;
	margin-left: 120px;
}

.bnloginbtn table td {
	font-size: 12px;
	color: #ff0000;
}

.btn01 {
	background: url("<%=root%>/Web/login/images/denglu.png") no-repeat;
	height: 30px;
	width: 60px;
	cursor: pointer;
}

.btn01:hover {
	background-position: left bottom;
}

.btn02 {
	background: url("<%=root%>/Web/login/images/quxiao.png") no-repeat;
	background-position: right top;
	height: 30px;
	width: 60px;
	cursor: pointer;
}

.btn02:hover {
	background-position: right bottom;
}

.bnlogin-right {
	width: 36px;
	height: 357px;
	background: url("<%=root%>/Web/login/images/login_bg02.png") no-repeat;
	position: absolute;
	margin-top: 100px;
	margin-left: 836px;
}

.zhdl {
	background: url("<%=root%>/Web/login/images/zhdl01.png") no-repeat;
	width: 36px;
	height: 114px;
	cursor: pointer;
	margin-top: 10px;
}

.zhdl:hover {
	background: url("<%=root%>/Web/login/images/zhdl02.png") no-repeat;
	width: 36px;
	height: 114px;
}

.zsdl {
	background: url("<%=root%>/Web/login/images/zsdl01.png") no-repeat;
	width: 36px;
	height: 114px;
	cursor: pointer;
}

.zsdl:hover {
	background: url("<%=root%>/Web/login/images/zsdl02.png") no-repeat;
	width: 36px;
	height: 114px;
}

.huanying {
	width: 100%;
	height: 32px;
	background: url("<%=root%>/Web/login/images/hengtiaobg.png") repeat;
	position:absolute; bottom:0; position:fixed;
	margin-left: 0;
	font-family: "微软雅黑";
	line-height: 32px;
	text-align: center;
	font-size: 15px;
	color: #3e6aad;
}
-->
</style>
</head>
<body>
	<div class="bnlogin" onkeydown="if(event.keyCode==13){doLogin();}">
		<div class="bnlogintd">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="20%" height="40px"><div align="right">账 户：</div></td>
					<td width="80%" colspan="2"><label> <input type="text"
							name="textfield" id="loginCode"
							style="font-size: 14px;width: 200px; height: 32px; line-height: 20px; text-align: left; background-color: #fff; border: solid #ccd4e7 1px;" />
					</label></td>
				</tr>
				<tr>
					<td height="40px"><div align="right">密 码：</div></td>
					<td><label> <input type="password" name="textfield"
							id="password"
							style="font-size: 14px;width: 200px; height: 32px; line-height: 20px; text-align: left; background-color: #fff; border: solid #ccd4e7 1px;" />
					</label></td>
				</tr>
				<tr>
					<td height="40px"><div align="right">身 份：</div></td>
					<td><label><select id="identity" style="font-size: 14px;width: 200px; height: 32px; line-height: 20px; text-align: center; background-color: #fff; border: solid #ccd4e7 1px;">
								<option value="admin" selected="selected">管理员</option>
					</select></label></td>
				</tr>
				<tr>
					<td height="40px"><div align="right">验证码：</div></td>
					<td><label> <input type="text" name="textfield2"
							id="checkcode"
							style="width: 100px; height: 32px; line-height: 20px; text-align: left; background-color: #fff; border: solid #ccd4e7 1px; float: left;" />
					</label>
						<div><input type="text" id="checkCode" class="code" style="font-size: 14px; width: 100px; height: 32px; line-height: 20px; text-align: center; background-color: #CFCFCF; border: solid #ccd4e7 1px; float: left;" onclick="createCode()" readonly/>
          				</div></td>
				</tr>
			</table>
		</div>
		<div class="bnloginbtn">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="120px"><div class="btn01" onclick="doLogin();"></div></td>
					<td><div class="btn02" onclick="doReset();"></div></td>
				</tr>
			</table>
			<table>
				<tr>
					<td id="login_msg" style="color: red;font: 12px"></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="huanying">欢迎使用文档管理系统</div>
</body>
</html>