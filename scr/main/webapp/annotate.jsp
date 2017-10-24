<%@ page pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Annotation Home</title>

<script src="<%=request.getContextPath()%>/jquery-1.9.1.min.js"></script>
<script src="jscolor.js"></script>

<style>
#fullBodyLogin {
	overflow: hidden;
	position: fixed;
	top: 0px;
	left: 0px;
}

#fullBody {
	overflow: hidden;
	position: fixed;
	top: 0px;
	left: 0px;
}

#titleDiv {
	border: 1px solid #0E094A;
	height: 70px;
}

h2 {
	color: #0E094A;
}

h3 {
	color: #0E094A;
}

#framesDiv {
	width: 100%;
}

#fileFrameDiv {
	border: 1px solid #0E094A;
	width: 20%;
	height: 100%;
}

#annTextDiv {
	width: 60%;
	float: left;
	height: 100%;
}

#CategoryFrameDiv {
	border: 1px solid #0E094A;
	width: 19%;
	float: right;
	height: 100%;
}

#newArticleLink {
	height: 20px;
}

.smallLink {
	float: right;
	font-size: 12px;
}

.dButton {
	background-color: #CFC7D6;
	border: 1px solid #0E094A;
	float: right;
}

.rButton {
	background-color: #CFC7D6;
	border: 1px solid #0E094A;
}
</style>

<script>
var reqCat = 0;
var rId=0;
var userName = '<%=session.getAttribute("userName") == null ? "" : session.getAttribute("userName").toString()%>';
	$(document)
			.ready(
					function() {

						if (userName == '') {
							$("#fullBodyLogin").show();
							$("#fullBody").hide();
						} else {
							$("#fullBodyLogin").hide();
							$("#fullBody").width(screen.width + "px");
							$("#fullBody").height(screen.height + "px");
							$("#framesDiv").height(screen.height - 65 + "px");
							$("#spanCF").hide();
							$("#newArticleTxt").hide();
							//$("#fullSize").width(screen.width + "px");
							//$("#aDiv").width((screen.width/2) + "px");
							$
									.ajax({
										url : 'GetCategories',
										type : 'POST',
										data : $('#addCat').serialize(),

										success : function(data) {
											var initData = data.split('^');

											if (initData[1].trim().length == 0) {
												$("#Articlelist")
														.html(
																$(
																		"#Articlelist")
																		.html()
																		+ "<span id='emSp'> No Articles are saved</span>");

											} else {

												var files = initData[1]
														.split(',');

												for (var i = 0; i < files.length; i++) {

													var vv = files[i]
															.split(':');

													if (vv[1] != ",") {
														$("#Articlelist")
																.html(
																		$(
																				"#Articlelist")
																				.html()
																				+ "<div onClick='getFile("
																				+ vv[0]
																				+ ");' style='width:99%;border:1px solid black' id='arf"
																				+ vv[0]
																				+ "' ><span >"
																				+ vv[1]
																				+ "</span><span class='dButton' onclick='removeFile("
																				+ vv[0]
																				+ ");'>Remove</span></div>");
													}
												}
											}
											var cats = initData[0].split(',');
											for (var i = 0; i < cats.length; i++) {

												var vv = cats[i].split(':');
												if (vv[0].trim().length == 0) {
													continue;
												}

												var subs = initData[2]
														.split(",");
												var rows = "";

												for (var t = 0; t < subs.length; t++) {

													var x = subs[t].split(":");

													if (x[1] == vv[0].trim()) {
														rows = rows
																+ "<tr  id='tr"+x[0]+"'><td colspan=2  onclick='getCat(&#39;"
																+ vv[0]
																+ "&#39;);' align=center>"
																+ x[0]
																+ "</td><td onclick=removeSubCat('"
																+ x[0]
																+ "') >Del</td></tr>";
													}
												}

												$("#catSpan")
														.html(
																$("#catSpan")
																		.html()
																		+ "<table style='color:#"+vv[1]+";' id='t"+vv[0]+"'><tr><td width=70%><span    width=100% onclick='getCat(&#39;"
																		+ vv[0]
																		+ "&#39;);'>"
																		+ vv[0]
																		+ "</span></td><td onclick=addSub('"
																		+ vv[0]
																		+ "');>Sub</td><td onclick=removeCat('"
																		+ vv[0]
																		+ "');> Del</td></tr>"
																		+ rows
																		+ "</table>");
											}
										},
										error : function(jqXHR, exception) {

											alert('failed to initialize'
													+ jqXHR.status);

										}
									});
						}
					});

	function saveSubCategory(catName) {

		$.ajax({
			url : 'HelloWorld',
			type : 'POST',
			data : $('#f1' + catName).serialize(),
			//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

			success : function(data) {
				id = $("#t1" + catName).closest("tr").attr('id',
						'tr' + $("#t1" + catName).val());
				$("#t1" + catName).closest("tr").html(
						"<td onclick='getCat(&#39;" + catName
								+ "&#39;);' colspan=2 align=center>"
								+ $("#t1" + catName).val()
								+ "</td><td  onclick=removeSubCat('"
								+ $("#t1" + catName).val() + "')>Del</td>");
				$("#t1" + catName).remove();
			},
			error : function(jqXHR, exception) {

				alert('failed to login' + jqXHR.status);

			}
		});

	}
	function addSub(catName) {

		$("#t" + catName + " tr:last")
				.after(
						'<tr  id=tr'+rId+'><td><form id=f1'+catName+'><input type=hidden name=catName value='+catName+' ><input type=text name=t1'
							+ catName
							+ ' id=t1'
							+ catName
							+ ' ></form></td><td onClick=saveSubCategory(&#39;'
								+ catName
								+ '&#39;);>save</td><td onClick=this.closest(\"tr\").remove();>cancel</td></tr>');

		rId++;
	}

	function login() {

		if ($("#userName").val() == ""
				|| $("#userName").val().trim().length == 0) {
			$("#errorDiv").text("Please enter your userName");
			return;
		}

		if ($("#password").val() == ""
				|| $("#password").val().trim().length == 0) {
			$("#errorDiv").text("Please enter your password");
			return;
		}

		$.ajax({
			url : 'Login',
			type : 'POST',
			data : $('#loginForm').serialize(),
			//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

			success : function(data) {

				if (data == "false") {
					$("#errorDiv").text("Invalid user or password");
				}//else if (data == "f"){$("#errorDiv").text("User Already Exist");}
				else {
					$("#fullBodyLogin").hide();
					location.reload();
				}
				//alert("ARTICLE WAS REMOVED.")
			},
			error : function(jqXHR, exception) {

				alert('failed to login' + jqXHR.status);

			}
		});

	}

	function addUser() {

		if ($("#userName").val() == ""
				|| $("#userName").val().trim().length == 0) {
			$("#errorDiv").text("Please enter your userName");
			return;
		}

		if ($("#password").val() == ""
				|| $("#password").val().trim().length == 0) {
			$("#errorDiv").text("Please enter your password");
			return;
		}

		$.ajax({
			url : 'AddUser',
			type : 'POST',
			data : $('#loginForm').serialize(),
			//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

			success : function(data) {
				if (data == "false") {
					$("#errorDiv").text("User Already Exist");
				}//else if (data == "f"){$("#errorDiv").text("User Already Exist");}
				else {
					$("#fullBodyLogin").hide();
					location.reload();
				}
				//alert("ARTICLE WAS REMOVED.")
			},
			error : function(jqXHR, exception) {

				alert('failed to create user' + jqXHR.status);

			}
		});

	}

	function removeCat(catName) {

		$("#cid").val(catName);
		$("#deleteType").val("c");

		$.ajax({
			url : 'RemveFile',
			type : 'POST',
			data : $('#deleteFileForm').serialize(),
			//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

			success : function(data) {

				//alert("ARTICLE WAS REMOVED.")
			},
			error : function(jqXHR, exception) {

				alert('failed to annotate script' + jqXHR.status);

			}
		});

		$("#t" + catName).remove();

	}

	function removeSubCat(catName) {

		$("#cid").val(catName);
		$("#deleteType").val("s");

		$.ajax({
			url : 'RemveFile',
			type : 'POST',
			data : $('#deleteFileForm').serialize(),
			//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

			success : function(data) {

				//alert("ARTICLE WAS REMOVED.")
			},
			error : function(jqXHR, exception) {

				alert('failed to annotate script' + jqXHR.status);

			}
		});

		$("#tr" + catName).remove();

	}

	function removeFile(fid) {
		$("#fid").val(fid);
		$("#deleteType").val("f");

		$.ajax({
			url : 'RemveFile',
			type : 'POST',
			data : $('#deleteFileForm').serialize(),
			//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

			success : function(data) {

				//alert("ARTICLE WAS REMOVED.")
			},
			error : function(jqXHR, exception) {

				alert('failed to annotate script' + jqXHR.status);

			}
		});

		$("#arf" + fid).remove();
	}

	function AnnotateSript(fid) {
		$("#fid").val(fid);
		$.ajax({
			url : 'ColorMe',
			type : 'POST',
			data : $('#deleteFileForm').serialize(),
			//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

			success : function(data) {
				var d = data.split(';');
				for (k = 0; k < d.length; k++) {
					var dd = d[k].split("=");

					$("#w" + dd[0]).attr(
							'style',
							'background: linear-gradient(to right' + dd[1]
									+ ')');
					//	$("#w"+dd[0])
				}
			},
			error : function(jqXHR, exception) {

				alert('failed to annotate script' + jqXHR.status);

			}
		});

	}

	function getFile(fid) {
		$("#fid").val(fid);
		$("#fileid").val(fid);
		$.ajax({
			url : 'GetArticle',
			type : 'POST',
			data : $('#deleteFileForm').serialize(),
			//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

			success : function(data) {

				$("#annTextDiv").html(data);
				AnnotateSript(fid);
			},
			error : function(jqXHR, exception) {

				alert('failed to annotate script' + jqXHR.status);

			}
		});

	}

	function getCat(cat) {
		if (reqCat == 0) {

			return;
		}

		$("#selectedCat").val(cat);
		var r = confirm('Adding \'' + $("#w" + $('#selectedWord').val()).text()
				+ '\' to category \'' + cat + '\'?');
		if (r == true) {

			$.ajax({
				url : 'AddWordToCategory',
				type : 'POST',
				data : $('#catWordForm').serialize(),
				//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

				success : function(data) {

					if (data.indexOf(",") != 0) {
						alert(data);
					} else {
						//alert(data);
						$("#w" + $("#selectedWord").val()).attr(
								'style',
								'background: linear-gradient(to right' + data
										+ ')');

					}
				},
				error : function(jqXHR, exception) {

					alert('failed to add word to category' + jqXHR.status);

				}
			});
		}

	}
	function colorSpan(i) {

		var r = confirm('Adding \''
				+ $("#w" + i).text()
				+ '\' to a new category.. please select a category from left panel');
		if (r == true) {
			$("#selectedWord").val(i);
			reqCat = 1;

		} else {
			reqCat = 0;
		}
	}
	function addMe() {

		$.ajax({
			url : 'AddCategories',
			type : 'POST',
			data : $('#addCat').serialize(),

			success : function(data) {

				if (data.indexOf("Failed") == 0) {
					alert(data);
				} else {

					$("#catSpan").html($("#catSpan").html() + data);

				}
			},
			error : function(jqXHR, exception) {

				alert('failed to add category' + jqXHR.status);

			}
		});

	}

	function showNewArticle() {
		$("#spanCF").show();
		$("#spanNF").hide();
		$("#newArticleTxt").show();
		$("#Articlelist").hide();
	}

	function showArticles() {
		$("#spanCF").hide();
		$("#spanNF").show();
		$("#newArticleTxt").hide();
		$("#Articlelist").show();
	}

	function logout() {
		$.ajax({
			url : 'Logout',
			type : 'POST',
			data : $('#loginForm').serialize(),
			//style=\"background: linear-gradient(to right" + abc.get(word)+ ");\"

			success : function(data) {

				location.reload();

			},
			error : function(jqXHR, exception) {

				alert('failed to login' + jqXHR.status);

			}
		});

	}
</script>

</head>
<body>

	<form id="catWordForm">
		<input type="hidden" name="selectedWord" id="selectedWord"> <input
			type="hidden" name="selectedCat" id="selectedCat"> <input
			type="hidden" id="fileid" name="fileid">

	</form>



	<form id="deleteFileForm">
		<input type="hidden" name="fid" id="fid"> <input type="hidden"
			name="cid" id="cid"> <input type="hidden" name="deleteType"
			id="deleteType">
	</form>
	<div id="fullBodyLogin">
		<form id="loginForm">
			<table width="300px">
				<tr>
					<td>User Name :</td>
					<td><input type="text" name="userName" id="userName"
						value="shereen"></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><input type="password" name="password" id="password"
						value="password"></td>
				</tr>
				<tr>
					<td><input type="button" value="Login" onClick="login();"></td>
					<td><input type="button" value="Create New User"
						onClick="addUser();"></td>
				</tr>
			</table>
		</form>

		<div id="errorDiv" style="color: red;"></div>
	</div>
	<div id="fullBody">
		<div id="titleDiv">
			<table width="100%">
				<tr>
					<td width="70%"><p style="font-size: 20px;">
							<b><u>Article Annotation Project</b></u><br>
							<i>by Amal Haddad</i>
						</p></td>
					<td width="30%"><p align="right">
							<i>Welcome <b><%=session.getAttribute("userName")%></i></b>&#32;&#32;&#32;<span
								onclick="logout();">sign out</span>
						</p></td>
				</tr>
			</table>

		</div>


		<div id="framesDiv">
			<div id="fileFrameDiv" style="float: left">

				<div id="newArticleLink">
					<span class="smallLink" id="spanCF" onClick="showArticles();">-
						cancel</span> <span id="spanNF" class="smallLink"
						onClick="showNewArticle();">+ Add Article</span>
				</div>
				<div id="newArticleTxt" style="width: 100%">

					<form id="AddForm" method="post" action="SaveArticle" >
						<table>
							<tr>
								<td>Article Title:</td>
								<td><input type="text" name="artTitle" id="artTitle"></td>
							</tr>
							<tr>
								<td>Article Text:</td>
								<td></td>
							</tr>
							<tr>
								<td colspan="2"><textarea cols="35" rows="30" id="data"
										name="data"></textarea>
							<tr>
								<td></td>
								<td><input type="submit" value="Save Article"
									onClick="SaveSript();"></td>
							</tr>

						</table>
					</form>
				</div>
				<div style="width: 100%" id="Articlelist">
					Annotated Files:<br>
				</div>
			</div>
			<div style="float: left" id="annTextDiv"></div>

			<div id="CategoryFrameDiv">
				Add Category:<br>
				<form id="addCat">
					Category &nbsp;&nbsp;&nbsp;:<input type="text" name="newCat"><br>
					Pick Color : <input class="jscolor" value="ab2567" name="colorMe">
					<br> <input type="button" value="Add Category"
						onclick="addMe()">
				</form>
				<hr width="100%">

				Available categories:<br> <span id="catSpan"></span>





			</div>
		</div>
	</div>
</body>
</html>