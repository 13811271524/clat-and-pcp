$(function() {
	// 进入页面，焦点在用户名文本框上
	$("#loginCode").focus();

	createCode();
	
	// $("#checkcode").val("");
});

/**
 * Ajax执行登录操作
 * 
 * @return
 */
function doLogin() {

	if (!check()) {
		return;
	}

	$("#login_msg").html("登录中，请稍后...");
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : "login.action",// 请求的action路径
		data : {
			"username" : $("#loginCode").val(),
			"userpwd" : $("#password").val(),
			"userrole": $("#identity").val()
		},
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后处理函数。
			if (data.result == "ADMIN") {
				$("#login_msg").html("登录成功");
				window.location = root + "/Web/common/page/main.jsp";
			}else if(data.result=="USER"){
				$("#login_msg").html("登录成功");
				window.location = root + "/Web/common/page/main_user.jsp";
			}else {// 后台异常处理
				$("#login_msg").html(data.result);
			}
		}
	});
}

/**
 * 执行reset操作
 */
function doReset() {
	$("#loginCode").val("");
	$("#password").val("");
	$("#login_msg").html("&nbsp;");
}

function check() {
	if ($("#loginCode").val() == "") {
		alert("用户名为空！");
		$("#loginCode").focus();
		return false;
	}
	if ($("#password").val() == "") {
		alert("密码为空！");
		$("#password").focus();
		return false;
	}
	if ($("#checkcode").val() == "") {
		alert("验证码为空！");
		$("#checkcode").focus();
		return false;
	}

	var inputCode = $("#checkcode").val().toUpperCase();
	var codeToUp = code.toUpperCase();

	if (inputCode != codeToUp) {
		alert("验证码输入错误！");
		$("#checkcode").val("");
		createCode();
		return false;
	}
	return true;
}
