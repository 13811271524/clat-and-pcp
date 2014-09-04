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
<title>文档管理系统</title>

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
							width="16" height="16" /><a href="#">首页</a><img
							src="<%=root%>/Web/login/images/ico01.png" /><a href="#">注销</a><img
							src="<%=root%>/Web/login/images/ico02.png" /><a href="#">修改密码</a>
					</div>
					<div class="welcometext"><%=user%>您好，欢迎登录文档管理系统!</div>
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
			<div title="个人信息" selected="false">
				<ul>
					<li style="text-align: left; padding-left: 15px;">
						<div class="leftico01"></div> <a
						href="javascript:addTab('tabId_loginInfo1','基本信息','<%=root%>/toDocPage.action');">基本信息</a>
					</li>
					<li style="text-align: left; padding-left: 15px;">
						<div class="leftico01"></div> <a
						href="javascript:addTab('tabId_loginInfo1','上传历史','<%=root%>/Web/usermanage/page/uploadhistory.jsp');">上传历史</a>
					</li>
				</ul>
			</div>
			<div title="文档资源" selected="false">
				<ul>
					<li style="text-align: left; padding-left: 15px;">
						<div class="leftico01"></div> <a
						href="javascript:addTab('tabId_loginInfo1','文档信息','<%=root%>/toDocPage.action');">文档信息</a>
					</li>
				</ul>
			</div>
			<div title="其他功能" selected="false">
				<ul>
					<li style="text-align: left; padding-left: 15px;">
						<div class="leftico01"></div>
						 <a href="javascript:addTab('tabId_loginInfo2','上传排行','<%=root%>/Web/docmanage/page/uploadcount.jsp');">上传排行</a>
					</li>
				</ul>
				<ul>
					<li style="text-align: left; padding-left: 15px;">
						<div class="leftico01"></div>
						 <a href="javascript:addTab('tabId_loginInfo3','下载排行','<%=root%>/Web/docmanage/page/downloadcount.jsp');">下载排行</a>
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
					<h3>你好，欢迎来到文档管理系统</h3>
				</div>
			</div>
		</div>
	</div>
</body>
</html>