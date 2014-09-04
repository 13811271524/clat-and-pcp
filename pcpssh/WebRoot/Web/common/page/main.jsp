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
<link rel="stylesheet" type="text/css"
	href="<%=root%>/Web/common/css/main.css">
<script type="text/javascript"
	src="<%=root%>/Web/common/js/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=root%>/Web/common/js/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=root%>/Web/common/js/easyui/validate/easyui_validate.js"></script>

<script type="text/javascript" src="<%=root%>/Web/common/js/main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务器数据管理</title>

</head>
<!-- 设置了class就可在进入页面加载layout -->
<body class="easyui-layout">
	<!-- 正上方panel -->
	<div region="north" style="height: 70px; padding: 0; overflow: hidden;">
		<div class="top">
			<div class="topbg01">
				<div class="xinxibox">	
					<div class="xinxilan">
						<s></s><b></b><img src="<%=root%>/Web/login/images/ico03.png"
							width="16" height="16" /><a href="#">首页</a>
					</div>
					<div class="welcometext"><%=user%>，您好</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 正左边panel -->
	<div region="west" title="菜单栏" split="true"
		style="width: 150px; padding1: 1px; overflow: hidden;">
		<div class="easyui-accordion" fit="true" border="false"
			style="text-align: center">
			<!-- selected -->
			<div title="数据管理" selected="false">
				<ul>
					<li style="text-align: left; padding-left: 15px;">
						<div class="leftico01"></div> <a
						href="javascript:addTab('tabId_loginInfo','数据管理','<%=root%>/toUserPage.action');">数据管理</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- 正中间panel -->
	<div region="center" title="功能区">
		<div class="easyui-tabs" id="centerTab" fit="true" border="false">
			<div title="欢迎页" style="padding: 20px; overflow: hidden;">
				<div style="margin-top: 20px;">
					<h3>你好，欢迎来到pcp服务器数据管理系统</h3>
				</div>
			</div>
		</div>
	</div>
</body>
</html>