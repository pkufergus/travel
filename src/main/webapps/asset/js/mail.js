/**
 * 
 */
function getMailConfig(){
	closeDialogDiv();
	$.ajax({
		type : 'GET',
		url : common_path+'mail/maillist.json',
		success : function(result){
			drawMainConfig(result);
		},
		error : function(){
			drawMainConfig(null);
		}
	});
}

function editMailConfig(){
	if(!checkEmail()){
		return;
	}
	
	$.ajax({
		type : 'GET',
		url : common_path+'mail/mailedit.json',
		data : {
			id : $("#eid").val(),
			server : $("#serverTxt").val(),
			port : $("#portTxt").val(),
			username: $("#eUserNameTxt").val(),
			password:$("#ePwdTxt").val(),
			from :$("#eFromTxt").val(),
		},
		success : function(result){
			alert('修改成功！');
			drawMainConfig(result);
		},
		error : function(){
			drawMainConfig(null);
		}
	});
}

function checkEmail(){
	var id = $("#eid").val();
	var server = $("#serverTxt").val();
	var port = $("#portTxt").val();
	var username= $("#eUserNameTxt").val();
	var password=$("#ePwdTxt").val();
	var from =$("#eFromTxt").val();
	 if(server == ''){
		 $("#serverTxt").focus();
		 $("#serverTxt-span").html('邮件服务器地址不能为空');
		 return false;
	 }else if( port== ''){
		 $("#portTxt").focus();
		 $("#portTxt-span").html('端口不能为空');
		 return false;
	 }else if(username == ''){
		 $("#eUserNameTxt").focus();
		 $("#eUserNameTxt-span").html('用户名不能为空');
		 return false;
	 }else if(password == ''){
		 $("#ePwdTxt").focus();
		 $("#ePwdTxt-span").html('密码不能为空');
		 return false;
	 }else if(from == ''){
		 $("#eFromTxt").focus();
		 $("#eFromTxt-span").html('发件人不能为空');
		 return false;
	 }
	
	return true;
}

function drawMainConfig(result){
	var htmlTemp =  '<table class="menu-tbl">'
		+'<thead>'
		+'<tr>'
		+'<th class="td-style border-left border-right" style="height:42px;background: #2DA5DE;color: white; border-top: solid 1px #2DA5DE;  font-size: 15px;" colspan=2>邮件服务器配置</th>'
		+'</tr>'
		+'</thead>'
		+'<tr>'
			+'<td class="td-style border-left border-right" style="height:30px">邮件服务器地址</td>'
			+'<td class="td-style border-left border-right"><input type="text" class="mail-text" style="height:35px" id="serverTxt" /><span id="serverTxt-span" class="warn-span"></span></td>'
			+'</tr>'
			+'<tr>'
			+'<td class="td-style border-left border-right" style="height:30px">端口</td>'
			+'<td class="td-style border-left border-right"> <input style="height:35px" class="mail-text" type="text" id="portTxt" /><span id="portTxt-span" class="warn-span"></span></td>'
			+'</tr>'
			+'<tr>'
			+'<td class="td-style border-left border-right" style="height:30px">邮箱用户名</td>'
			+'<td class="td-style border-left border-right"><input style="height:35px" class="mail-text" type="text" id="eUserNameTxt" /><span id="eUserNameTxt-span" class="warn-span"></span></td>'
			+'</tr>'
			+'<tr>'
			+'<td class="td-style border-left border-right" style="height:30px">邮箱密码</td>'
			+'<td class="td-style border-left border-right"><input style="height:35px" class="mail-text" type="password" id="ePwdTxt" /><span id="ePwdTxt-span" class="warn-span"></span></td>'
			+'</tr>'
			+'<tr>'
			+'<td class="td-style border-left  border-right" style="height:30px">发件人</td>'
			+'<td class="td-style border-left border-right"><input style="height:35px" class="mail-text" type="text" id="eFromTxt" /><span id="eFromTxt-span" class="warn-span"></span>'
			+'<input type="hidden" id="eid" /></td>'
			+'</tr>'
			+"<tfoot>" 
			+"<tr>" 
			+"<td style='height:50px' class='td-style border-left  border-right' colspan=2><input class='submit-btn' onclick='editMailConfig()' type=button value='提&nbsp;交' /></td>"
			+"</tr>"
			"</tfoot></table>";
	$("#pageDiv").html(htmlTemp);
	if(result != null){
		document.getElementById("serverTxt").value=result.server;
		document.getElementById("portTxt").value=result.port;
		document.getElementById("eUserNameTxt").value=result.username;
		document.getElementById("ePwdTxt").value=result.password;
		document.getElementById("eFromTxt").value=result.from;
		document.getElementById("eid").value=result.id;
	}
}

function loginout(){
	$.ajax({
		url : common_path+'user/loginout.json',
		type : 'GET',
		success:function(){
			window.location.href="http://www.e-traveltochina.com/";
		},
		error : function(){
			window.location.href="http://www.e-traveltochina.com/";
		}
	});
}

function changeMenuBg(obj){
	var liArry = document.getElementsByName("menu-li");
	for(var key=0;key<liArry.length;key++){
		liArry[key].className='';
		var id = $(liArry[key]).attr("id");
		document.getElementById(id).style.background='';
	}
	document.getElementById(obj).style.background='#59CBFB';
	document.getElementById(obj).style.color='white';
	//document.getElementById(obj).className='li-bg';
	//$("#"+obj).addClass('li-bg');
}