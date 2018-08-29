/**
 * 
 */
function getMenuList() {
	closeDialogDiv();
	$.ajax({
		type : 'GET',
		url : common_path + 'menu/menulist.json',
		data : {
			pageNow : 1
		},
		success : function(result) {
			drawMenuTable(result);
		},
		error : function() {

		}
	});
}
function drawMenuTable(result) {
	var tableHtmlTemp = '<table style="  width: 87%; margin-left: 1px;margin-top: 3px;text-align: center;font-family:微软雅黑">'
			+ '<thead>'
			+ '<tr class="menu-th-tbl border-right">'
			+ '<th style="height:30px">出发地</th>'
			+ '<th>目的地</th>'
			+ '<th>菜单级别</th>'
			+ '<th>添加评论</th>'
			+ '<th>是否抓取</th>'
			+ '<th>删除</th>'
			+ '</tr>'
			+ '</thead>' + '<tbody>';
	for ( var key in result) {
		var del = '删除';
		var srcTemp = result[key].srcPlace;
		var destTemp = result[key].destPlace;
		var idTemp = result[key].id;
		tableHtmlTemp += '<tr>'
				+ '<td style="height:36px" class="td-style border-left border-right">'
				+ result[key].srcPlace
				+ '&nbsp;&nbsp;('
				+ result[key].srcCode
				+ ')</td>'
				+ '<td class="td-style border-left border-right">'
				+ result[key].destPlace
				+ '&nbsp;&nbsp;('
				+ result[key].destCode
				+ ')</td>'
				+ "<td class='td-style border-left border-right'>"
				+ result[key].level
				+ "级</td>"
				+ "<td class='td-style border-left border-right'><input class='add-comment-btn' type=button value=修改评论 onclick=addComment('"
				+ srcTemp
				+ "&"
				+ destTemp
				+ "&"
				+ idTemp
				+ "&"
				+ result[key].srcCode
				+ "&"
				+ result[key].destCode
				+ "') /></td>"
				+"<td>"
				+ result[key].unDone
				+"</td>"
				+ '<td class="td-style border-left border-right"><a style="text-decoration: underline;" href=javascript:void(0) onclick=deleteMenu("'
				+ result[key].id + '") >' + del + '</a></td>'

	}
	;
	tableHtmlTemp = tableHtmlTemp + "<tfoot></tfoot></table>";
	$("#pageDiv").html(tableHtmlTemp);
}
function addMenuHtml() {
	closeDialogDiv();
	var menuHtmlTemp = '<table class="menu-tbl">'
			+ '<thead>'
			+ '<tr>'
			+ '<th class="menu-th-tbl" style="height:42px;" colspan=2>添加菜单</th>'
			+ '</tr>'
			+ '</thead>'
			+ '<tr>'
			+ '<td style="height:40px" class="td-style border-left border-right">出发国家'
			+ '</td>'
			+ '<td class="td-style border-left border-right">'
			+ '<select class="menu-select" onchange="selectChange()" id="country">'
			+ '<option value=USA>美国&nbsp;&nbsp;USA</option>'
			+ '<option value=CN>中国&nbsp;&nbsp;China</option>'
			+ '<option value=CA>加拿大&nbsp;&nbsp;Canada</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td class="td-style border-left border-right" style="height:40px">出发城市</td>'
			+ '<td class="td-style border-left border-right"><div id="srcCitySelectDiv"></div></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td class="td-style border-left border-right" style="height:40px">目地城市</td>'
			+ '<td class="td-style border-left border-right"><div id="destCitySelectDiv"></div></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td class="td-style border-left border-right" style="height:40px">菜单级别</td>'
			+ '<td class="td-style border-left border-right"> '
			+ '<input type=text id="level_select" class="menu-select" />'

			+ '<span style="color:#969696" class="warn-span">级别越高，菜单显示越靠前</span>'
			+ '</td>'
			+ '</tr>'

			+ '<tr>'
			+ '<td class="td-style border-left border-right" style="height:40px">是否在首页展示</td>'
			+ '<td class="td-style border-left border-right">'
			+ '<select class="menu-select" id="image_show">'
			+ '<option value="0">否</option>'
			+ '<option value="1">是</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'

			+ '<tr>'
			+ '<td class="td-style border-left border-right" style="height:40px">菜单关联图片</td>'
			+ '<td style="text-align:left;" class="td-style border-left border-right"> '
			+ '<form id="file_form" action="'
			+ common_path
			+ 'upload/upload_image.json" enctype="multipart/form-data" method="post">'
			+ '<input type="file" multiple="multiple" name="aaa" id="image_url" class="" />'
			+ '</form>'
			+ '</td>'
			+ '</tr>'

			+ '<tr>'
			+ '<td class="td-style border-left border-right">评论</td>'
			+ '<td style="width:552px" class="td-style border-left border-right">'
			+ '<textarea style="word-wrap:normal;" class="menu-text-area" id="description" ></textarea>'
			+ '<span style="color:#969696;width:108px" class="warn-span" id="description-span">（如有多条评论，请按回车键隔开）</span></td>'
			+ '</tr>'
			+ "<tfoot>"

			+ "<td style='height:50px' class='td-style border-left border-right' colspan=2>"
			+ "<input class='submit-btn' onclick='addMenu()' type=button value='提&nbsp;交' /></td>"
			+ "</tr>"
	"</tfoot></table>";
	$("#pageDiv").html(menuHtmlTemp);
	$.ajax({
		type : 'GET',
		url : common_path + 'city/from_to_city.json',
		data : {
			country : 'USA'
		},
		success : function(result) {
			drawsrcDestSelect(result);
		},
		error : function() {

		}

	});
}

function drawsrcDestSelect(result) {
	var srcSelect = "<select class='menu-select' id='srcSelect'>";
	var destSelect = "<select class='menu-select' id='destSelect'>";
	var fromArry = result[0];
	var toArry = result[1];
	for ( var key in fromArry) {
		srcSelect += "<option value='" + fromArry[key].cityCode + "&"
				+ fromArry[key].chineseName + "&" + fromArry[key].englishName
				+ "'>" + fromArry[key].cityCode + fromArry[key].chineseName
				+ "</option>";
	}

	for ( var key in toArry) {
		destSelect += '<option value=' + toArry[key].cityCode + "&"
				+ toArry[key].chineseName + "&" + toArry[key].englishName + '>'
				+ toArry[key].cityCode + toArry[key].chineseName + '</option>';
	}

	// for(var key in result ){
	// srcSelect += '<option
	// value='+result[key].cityCode+"&"+result[key].chineseName+'>'
	// +result[key].cityCode+result[key].chineseName+'</option>';
	// destSelect += '<option
	// value='+result[key].cityCode+"&"+result[key].chineseName+'>'
	// +result[key].cityCode+result[key].chineseName+'</option>';
	// }
	srcSelect = srcSelect + "</select>";
	destSelect = destSelect + "</select>"
	$("#srcCitySelectDiv").html(srcSelect);
	$("#destCitySelectDiv").html(destSelect);
}
function addComment(obj) {

	var objArry = obj.split("&");
	var srcPlace = objArry[0];
	var destPlace = objArry[1];
	var srcCode = objArry[3];
	var destCode = objArry[4];
	var id = objArry[2];
	var addCommentHtml = "<table style='width:900px;margin-left:-102px;text-align:center'><thead>"
			+ "<tr><th class='menu-th-tbl' style='height:40px;' colspan=2><span>修改评论</span><a style='margin-left: 393px' class='a-close-div' href=javascript:void(0) onclick='closeDialogDiv()'>X</a></th></tr></thead>"
			+ "<tbody>"
			+

			"<tr>"
			+ "<td class='td-style border-left border-right' style='height:40px'>出发城市</td>"
			+ "<td colspan=1 class='td-style border-left border-right' style='height:40px'><input style='margin-left: -547px;' class='mail-text' type=text id=srcPlace value='"
			+ srcPlace
			+ "' readonly='readonly'  /></td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td class='td-style border-left border-right' style='height:40px'>目的城市</td>"
			+ "<td colspan=1 class='td-style border-left border-right' style='height:40px'><input style='margin-left: -547px;' class='mail-text' type=text id=destPlace value='"
			+ destPlace
			+ "' readonly='readonly'  /></td>"
			+ "</tr>"

			+ '<tr>'
			+ '<td class="td-style border-left border-right" style="height:40px">是否在首页展示</td>'
			+ '<td colspan=1 class="td-style border-left border-right">'
			+ '<select style="width: 128px;" class="menu-select" id="dia_image_show">'
			+ '<option value="0">否</option>'
			+ '<option value="1">是</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'

			+ '<tr>'
			+ '<td class="td-style border-left border-right" style="height:40px">菜单关联图片</td>'
			+ '<td style="text-align:left;width:100px;height:110px;" class="td-style border-left border-right"> '
			+ '<div style="width:100px;height:100px;float:left;margin-left:13px;" id="img_url_div"></div>'
			+ '<form style="margin-left:120px;margin-top:80px;position:absolute" id="dia_file_form" action="'
			+ common_path
			+ 'upload/upload_image.json" enctype="multipart/form-data" method="post">'
			+ '<input type="file" multiple="multiple" name="aaa" id="dia_image_url" class="" />'
			+ '</form>'
			+ '</td>'
			+ '</tr>'

			+ "<tr>"
			+ "<td  class='td-style border-left border-right' style='height:40px'>菜单级别</td>"
			+ "<td colspan=1 class='td-style border-left border-right' style='height:40px'>"
			+ "<input type='text' style='margin-left: -547px;'  class='mail-text' id='dia_select_level'/>"
			+ "<span style='color:#969696' class='warn-span'>（级别越高，菜单显示越靠前）</span>"
			+ "</td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td class='td-style border-left border-right' style='height:40px'>评论</td>"
			+ "<td colspan=1  class='td-style border-left border-right' style='height:40px;width:369px'>"
			+ "<textarea class='dia-textarea' style='word-wrap:normal;width:695px' id=comment_textarea></textarea><span id='comment_textarea-span' style='color:#969696;width:108px' class='warn-span'>（如有多条评论，请按回车键隔开）<span></td>"
			+ "</tr>"
			+ "<input type=hidden id=id_hidden_commnet value='"
			+ id
			+ "' />"
			+ "</tbody>"
			+ "<tfoot>"
			+ "<tr>"
			+ "<td class='td-style border-left border-right' style='height:40px' colspan=2>"
			+ "<input type='button' style='  font-size: 15px;height: 35px;width: 86px;' value='修改评论' class='submit-btn' onclick='addCommentBtn()' />"
			+ "</td></tr></tfoot></table>"
			+ "<input type='hidden' id='srcCodeHid' value='"
			+ srcCode
			+ "' />"
			+ "<input  type='hidden' id='destCodeHid' value='"
			+ destCode
			+ "' />";
	$("#dialogDiv").show();
	$("#dialogDiv").html(addCommentHtml);
	$
			.ajax({
				type : 'GET',
				url : common_path + 'comment/get_comment.json',
				data : {
					id : id
				},
				success : function(result) {
					// 评论
					$("#comment_textarea").html(result.description);
					// 菜单级别
					document.getElementById("dia_select_level").value = result.level;
					// 是否在首页显示
					if (result.show == '1') {
						var select = document.getElementById("dia_image_show");
						for (var i = 0; i < select.options.length; i++) {
							if (select.options[i].value == result.show) {
								select.options[i].selected = true;
								break;
							}
						}
					}
					// image_url
					var imgHtml = "<a href='javascript:void(0)' onclick=showLargerImg('"
							+ result.image_url
							+ "')><img src='./asset/img/"
							+ result.image_url
							+ "' width='100px' height='100px' /></a>";
					$("#img_url_div").html(imgHtml);

				},
				error : function() {
				}

			});

}
function updateUploadImg() {
	if ($("#dia_image_url").val() != '') {

		$("#dia_file_form").ajaxSubmit({
			type : "post",
			iframe : true,
			dataType : "json",
			success : function(result) {
				// alert(result);
			},
			error : function() {

			}
		});

	}
}
function showLargerImg(srvImg) {
	var imgHtml = "<p class='show-img-p'><span onclick='closeImgDiv()' class='show-img-span'>X</span>"
		+"</p>"
		+"<img src='./asset/img/"
		+ srvImg
		+ "' width='500px' height='500px' />";
	$("#show-img-div").slideDown();
	$("#show-img-div").html(imgHtml);
}
function closeImgDiv(){
	$("#show-img-div").slideUp();
}
function addCommentBtn() {

	if ($("#comment_textarea").val() == '') {
		alert('评论不能为空');
		return;
	}
	var levelNum = $("#dia_select_level").val();
	if (!isPort(levelNum)) {
		alert('菜单级别不符合规则');
		return;
	}
	//alert($("#dia_image_url").val());
	if ($("#dia_image_url").val()) {
		updateUploadImg();
	}
	$.ajax({
		type : 'POST',
		url : common_path + 'comment/update_comment.json',
		data : {
			id : $("#id_hidden_commnet").val(),
			description : $("#comment_textarea").val(),
			fromCode : $("#srcCodeHid").val(),
			toCode : $("#destCodeHid").val(),
			level : $("#dia_select_level").val(),
			show : $("#dia_image_show").val(),
			image_url : $("#dia_image_url").val()
		},
		success : function(result) {
			if (result == 'EXIST') {
				alert('要添加的菜单已存在！');
				return;
			}
			if (result != 0) {
				alert('修改评论成功！');
				$("#dialogDiv").hide();
				document.getElementById("menu_list_href").click();
			}
		},
		error : function() {
		}
	});
}
function addMenu() {
	var srcPlace = $("#srcSelect").val();
	var destPlace = $("#destSelect").val();
	var description = $("#description").val();
	var href = '';
	var country = $("#country").val();
	var image_url = $("#image_url").val();
	var isHomePage = $("#image_show").val();
	var level = $("#level_select").val();

	if (image_url != '') {
		uploadImg();
	}
	if (!isPort(level)) {
		alert('菜单级别不符合规则');
		return;
	}

	$.ajax({
		type : 'POST',
		url : common_path + 'menu/addMenu.json',
		data : {
			srcPlace : srcPlace,
			destPlace : destPlace,
			description : description,
			href : href,
			country : country,
			level : $("#level_select").val(),
			image_url : image_url,
			isHomePage : isHomePage
		},
		success : function(result) {
			if (result == 'EXIST') {
				// alert('要添加的菜单已存在！');
				return;
			}
			if (result != 0) {
				alert('添加成功');
				document.getElementById("menu_list_href").click();
			}
			;
		},
		error : function() {
		}
	});
}
function uploadImg() {
	$("#file_form").ajaxSubmit({
		type : "post",
		iframe : true,
		dataType : "json",
		success : function(result) {
			alert(result);
		},
		error : function() {

		}
	});
}
function deleteMenu(id) {
	if (confirm("确定要删除菜单及评论吗？")) {
		$.ajax({
			type : 'GET',
			data : {
				id : id
			},
			url : "/v1/menu/delete_menu.json",
			success : function(result) {

				// alert('删除成功！');
				document.getElementById("menu_list_href").click();
			},
			error : function() {

			}
		});
	}
}

function getCommentsById(id) {
	$.ajax({
		type : 'GET',
		url : common_path + 'menu/get_comments.json',
		data : {
			id : id
		},
		success : function(result) {
			drawCommentsTable(result);
		},
		error : function() {

		}
	});
}

function drawCommentsTable(result) {
	var commentsHtml = "<table style='width:499px;text-align:center'><thead><tr class='menu-th-tbl' style='height:40px;'><th >序号</th>"
			+ "<th>评论</th>" + "<th>删除</th></tr></thead>";
	for ( var key in result) {
		commentsHtml += "<tr>"
				+ "<td class='td-style border-left border-right' style='height:40px'>"
				+ (parseInt(key) + 1)
				+ "</td>"
				+ "<td class='td-style border-left border-right' style='height:40px'>"
				+ result[key].description
				+ "</td>"
				+ "<td class='td-style border-left border-right' style='height:40px'><a style='text-decoration: underline;' href=javascript:void(0) onclick=deleteByCid('"
				+ result[key].cid + "') >删除" + "</a></td></tr>";
	}
	commentsHtml = commentsHtml
			+ "<tfoot>"
			+ "<tr>"
			+ "<td colspan=3 class='td-style border-left border-right' style='height:41px'><input style='  font-size: 15px;height: 35px;width: 86px;' type=button value=关&nbsp;&nbsp;闭   class='submit-btn'  onclick='closeDialogDiv()'/>";
	$("#dialogDiv").show();
	$("#dialogDiv").html(commentsHtml);
}

function deleteByCid(cid) {
	if (confirm("确定要删除评论吗？")) {

		$.ajax({
			type : 'GET',
			url : common_path + 'menu/delete_comments_by_cid.json',
			data : {
				cid : cid
			},
			success : function(result) {
				if (result != '0') {
					alert('删除成功！');
					$("#dialogDiv").hide();
					document.getElementById("menu_list_href").click();
				}
			},
			error : function() {

			}

		});

	}
}

function closeDialogDiv() {
	$("#dialogDiv").hide();
	$("#show-img-div").hide();
}

function selectChange() {
	var country = $("#country").val();
	$.ajax({
		type : 'GET',
		url : common_path + 'city/from_to_city.json',
		data : {
			country : country
		},
		success : function(result) {
			drawSelectChange(result);
		},
		error : function() {

		}

	});
}

function drawSelectChange(result) {
	var fromArry = result[0];
	var srcSelect = "<select  class='menu-select' id='srcSelect'>";
	for ( var key in fromArry) {
		srcSelect += '<option value=' + fromArry[key].cityCode + "&"
				+ fromArry[key].chineseName + "&" + fromArry[key].englishName
				+ '>' + fromArry[key].cityCode + fromArry[key].chineseName
				+ '</option>';
	}
	srcSelect = srcSelect + "</select>";
	$("#srcCitySelectDiv").html(srcSelect);

}

function isPort(str) {
	return (isNumber(str) && str < 2000);
}

function isNumber(s) {
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (s.search(re) != -1) {
		return true;
	} else {
		return false;
	}
}
