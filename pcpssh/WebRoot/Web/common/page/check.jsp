<!-- 基础页面 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String user = null;
	if(session.getAttribute("user")==null){
	//用户没有登陆
	response.sendRedirect(request.getContextPath()+"/toLoginPage.action");
	}else{
		user = session.getAttribute("user").toString();
	}
%>
<script type="text/javascript">
	var user = <%=user%>;
</script>
