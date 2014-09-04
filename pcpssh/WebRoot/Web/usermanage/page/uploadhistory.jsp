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

<script type="text/javascript" src="<%=root%>/Web/usermanage/js/uploadhistory.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传历史</title>
</head>
<body class="easyui-layout" style="height: 100%">
	<div region="center" style="height: 100%">
		<table id="tt" class="easyui-datagrid">
		</table>
	</div>
</body>

</html>

