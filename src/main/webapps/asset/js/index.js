/**
 * 
 */

function clearWarnInfo(){
	$(".warn-span").each(function(){
		$(this).html('');
	});
}
function updatePwd(){
	var oldPwd = $("#oldPwdTxt").val();
	var newPwd = $("#newPwdTxt").val();
	var repPwd = $("#repPwdTxt").val();
	clearWarnInfo();
	if(oldPwd == ''){
		$("#oldPwdTxt").focus();
		$("#oldPwdTxt-span").html('原始密码不能为空');
		return;
	}else if(newPwd == ''){
		$("#newPwdTxt").focus();
		$("#newPwdTxt-span").html('新密码不能为空');
		return;
	}else if(repPwd==''){
		$("#repPwdTxt").focus();
		$("#repPwdTxt-span").html('重复密码不能为空');
		return;
	}else if(newPwd != repPwd ){
		$("#repPwdTxt").focus();
		$("#repPwdTxt-span").html('两次输入的密码不一致');
		return;
	}
	
	$.ajax({
		type : 'GET',
		url : common_path + 'user/update_pwd.json',
		data : {
			oldPwd:hex_md5(oldPwd),
			newPwd:hex_md5(newPwd)
		},
		success : function(result){
			if(result == 'WRONG'){
				alert('输入的原密码不正确！');
				return;
			}
			if(result != 0){
				alert('修改密码成功！');
				$("#dialogDiv").hide();
			}else{
				alert('修改密码失败，请稍后再试！');
			}
		},
		error:function(){
			alert('修改密码失败，请稍后再试！');
		}
	});
}








function updatePwdHtml(){
	var pwdHtml = "<table style='text-align:center;width:100%'><thead><tr><th class='menu-th-tbl' style='height:40px' colspan=2><span>修改密码</span><a class='a-close-div' href=javascript:void(0) onclick='closeDialogDiv()'>X</a></th></tr></thead>"
				+"<tbody>"
				+"<tr>"
				+"<td class='td-style border-left border-right'>旧密码："
				+"</td>"
				+"<td class='td-style border-left border-right'>"
				+"<input type='password' id='oldPwdTxt' class='mail-text' />"
				+"<span id='oldPwdTxt-span' class='warn-span'></span>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td class='td-style border-left border-right'>新密码："
				+"</td>"
				+"<td class='td-style border-left border-right'>"
				+"<input type='password' id='newPwdTxt' class='mail-text' />"
				+"<span id='newPwdTxt-span' class='warn-span'></span>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td class='td-style border-left border-right'>重复密码"
				+"</td>"
				+"<td class='td-style border-left border-right'>"
				+"<input type='password' id='repPwdTxt' class='mail-text' />"
				+"<span id='repPwdTxt-span' class='warn-span'></span>"
				+"</td>"
				+"</tr>"
				+"<tfoot>"
				+"<tr>"
				+"<td class='td-style border-left border-right' colspan=2>"
				+"<input type='button' value='修改密码' onclick='updatePwd()' class='add-comment-btn' />"
				+"</td>"
				+"</tr>"
				+"</tfoot></table>";
	$("#dialogDiv").show();
	$("#dialogDiv").html(pwdHtml);
}