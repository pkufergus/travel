/**
 * 
 */
$(function(){
	getCityList();
	closeDialogDiv();
});
function getCityList(){
	closeDialogDiv();
	$.ajax({
		type : 'GET',
		url : common_path+'city/citylist.json',
		data : {
			pageNow : 1
		},
		success : function(result){
			drawCityTable(result);
		},
		error : function(){
			
		}
	
	});
}
function drawCityTable(result){
	var tableHtmlTemp = '<table style="  width: 87%; margin-left: 1px;margin-top: 3px;text-align: center;font-family:微软雅黑">'
  			+'<thead >'
  			+'<tr style="height:35px" class="menu-th-tbl border-right">'
  			+'<th>CityCode</th><th>英文名</th>'
  			+'<th>中文名</th><th>繁体名</th><th>城市级别</th><th>删除</th>'
  			+'</tr>'
  			+'</thead>'
  			+'<tbody>';
	for(var key in result){
		var del = '删除';
		tableHtmlTemp += '<tr>'
						+'<td  style="height:30px" class="td-style border-left border-right">'+result[key].cityCode+'</td>'
						+'<td class="td-style border-left border-right">'+result[key].englishName+'</td>'
						+'<td class="td-style border-left border-right">'+result[key].chineseName+'</td>'
						+'<td class="td-style border-left border-right">'+result[key].traditionalChineseName+'</td>'
						+'<td class="td-style border-left border-right">'+result[key].city_level+'</td>'
						+'<td class="td-style border-left border-right"><a style="text-decoration: underline;" href=javascript:void(0) onclick=deleteCity("'+result[key].cityCode+'") >'+del+'</a></td>'
	}
	tableHtmlTemp = tableHtmlTemp+"<tfoot></tfoot></table>";
	$("#pageDiv").html(tableHtmlTemp);
}

function addCityHtml(){
	closeDialogDiv();
	var cityHtmlTemp = '<table class="menu-tbl">'
		+'<thead>'
		+'<tr style="height:42px" class="menu-th-tbl">'
		+'<th colspan=2>添加城市</th>'
		+'</tr>'
		+'</thead>'
		+'<tr>'
		+'<td style="height:40px" class="td-style border-left border-right">国家'
		+'</td>'
		+'<td  class="td-style border-left border-right">'
		+'<select class="city-select" id="country">'
		+'<option value=USA>美国&nbsp;&nbsp;USA</option>'
		+'<option value=CN>中国&nbsp;&nbsp;China</option>'
		+'<option value=CA>加拿大&nbsp;&nbsp;Canada</option>'
		+'</select>'
		+'</td>'
		+'</tr>'
		+'<tr>'
		+'<td style="height:40px" class="td-style border-left border-right">城市级别'
		+'</td>'
		+'<td  class="td-style border-left border-right">'
		 +'<input type=text id=city_level class="mail-text" style="height:35px" />'
		 +'<span id="city_level-span"   class="warn-span"></span>'
		+'</td>'
		+'</tr>'
		+'<tr>'
			+'<td class="td-style border-left border-right" style="height:40px">CityCode</td>'
			+'<td class="td-style border-left border-right"><div class="city-add-div"><input class="mail-text" type="text" style="height:35px" id="cityCodeTxt" /><span id="cityCodeTxt-span"   class="warn-span"></span></td>'
			+'</tr>'
			+'<tr>'
			+'<td class="td-style border-left border-right" style="height:40px">英文名</td>'
			+'<td class="td-style border-left border-right"> <div class="city-add-div"><input class="mail-text" style="height:35px" type="text" id="engNameTxt" /><span id="engNameTxt-span"   class="warn-span"></td>'
			+'</tr>'
			+'<tr>'
			+'<td class="td-style border-left border-right" style="height:40px">中文名</td>'
			+'<td class="td-style border-left border-right"><div class="city-add-div"><input class="mail-text" style="height:35px" type="text" id="chNameTxt" /><span id="chNameTxt-span"   class="warn-span"></td>'
			+'</tr>'
			+'<tr>'
			+'<td class="td-style border-left border-right" style="height:40px">繁体名</td>'
			+'<td class="td-style border-left border-right"><div class="city-add-div" ><input class="mail-text" style="height:35px" type="text" id="triNameTxt" /><span id="triNameTxt-span"  class="warn-span"></td>'
			+'</tr>'
			+'<tr>'
			+'<td class="td-style border-left border-right" style="height:40px">图片</td>'
			+'<td class="td-style border-left border-right">'
			+'<form id="file_form" action="../../upload/upload_image.json" enctype="multipart/form-data" method="post">'
			+'<div class="city-add-div" ><input type="file" name="aaa" multiple="multiple"  /><span id="pic-span"  class="warn-span">'
			+'</form>'
			+'</td>'
			+'</tr>'
			+"<tfoot>" 
			+"<tr>" 
			+"<td style='height:50px' class='td-style border-left border-right' colspan=2><input  class='submit-btn' onclick='addCity()' type=button value='提&nbsp;交' /></td>"
			+"</tr>"
			"</tfoot></table>";
	$("#pageDiv").html(cityHtmlTemp);
}
function addCity(){
	uploadImg();
	if(!checkAddCity()){
		return;
	}
	var cityCode = $("#cityCodeTxt").val();
	var engName = $("#engNameTxt").val();
	var chName = $("#chNameTxt").val();
	var triName = $("#triNameTxt").val();
	var country = $("#country").val();
	var city_level = $("#city_level").val();
	
	$.ajax({
		type : 'GET',
		url : common_path+'city/addcity.json',
		data : {
			cityCode : cityCode,
			engName : engName,
			chName : chName ,
			triName : triName,
			country : country,
			city_level:city_level
		},
		success : function(result){
			if(result == 'EXIST'){
				alert('要添加的城市已存在！');
				return;
			}
			if(result != 0){
				//alert('添加成功');
				document.getElementById("city_list_href").click();
			}
		},
		error:function(){}
	});
}

function uploadImg(){
	$("#file_form").ajaxSubmit({
  		type: "post",
  		iframe:true,
  		dataType:"json",
		success: function (result) {
			alert(result);
		},
		complete:function(){
		  
		}
});
}
function checkAddCity(){
	cleanMsg();
	var cityCode = $("#cityCodeTxt").val();
	var engName = $("#engNameTxt").val();
	var chName = $("#chNameTxt").val();
	var triName = $("#triNameTxt").val();
	var cityLevel = $("#city_level").val();
	if(cityCode == ''){
		$("#cityCodeTxt").focus();
		$("#cityCodeTxt-span").html('city code 不能为空');
		return false;
	}else if(engName==''){
		$("#engNameTxt").focus();
		$("#engNameTxt-span").html('英文名不能为空');
		return false;
	}else if(chName==''){
		$("#chNameTxt").focus();
		$("#chNameTxt-span").html('中文名不能为空');
		return false;
	}else if(triName==''){
		$("#triNameTxt").focus();
		$("#triNameTxt-span").html('繁体名不能为空');
		return false;
	}else if(cityLevel == ''){
		$("#city_level").focus();
		$("#city_level-span").html('城市级别不能为空');
		return false;
	}
	if(!isPort(cityLevel)){
		$("#city_level").focus();
		$("#city_level-span").html('城市级别有误');
		return false;
	}
	return true;
}
function deleteCity(city_code){
	if(confirm("确定要删除城市信息吗？")){
		$.ajax({
			type : 'GET',
			url : common_path+'city/delete_city.json',
			data: {
				cityCode:city_code
			},
			success : function(){
				alert('删除成功');
				document.getElementById("city_list_href").click();
			},
			error : function(){
				
			}
		});
	}
}

function wait(){
	$("#").show();
	$("#").html("/v1/asset/img/loading.gif");
}
function waitClose(){
	$("#").hide()
}
function cleanMsg(){
	var warn_span_arry = $(".warn-span");
	for(var key in warn_span_arry){
		warn_span_arry[key].innerHTML='';
	}
}