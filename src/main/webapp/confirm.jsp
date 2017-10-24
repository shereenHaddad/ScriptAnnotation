<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.* , com.hello.world.beans.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello World Confirmation</title>


<!-- <script language="javascript" -->
<!-- 	src="https://maps.google.com/maps/api/js?sensor=false"></script> -->

<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.9.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/helloWorld.js"></script>

<script>
	function disableFields() {

		$("#fs1").attr("disabled", "disabled");
	}
</script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/style.css">
</head>
<body onload="getAgent();p1Init(''); disableFields();">
	<%
		User user = new User();
		if (request.getAttribute("user") != null) {
			user = (User) request.getAttribute("user");
		}
	%>
	<div class="centered" id="page1">
		<h1>
			Registration Confirmation <span>Hello World</span>
		</h1>

		<br>
		<ul class="tabs">



			<li class="rightFloat"><input type="radio" name="tabs" id="tab2"
				checked /> <label for="tab2">View Profile</label>
				<div id="tab-content2" class="tab-content">
					<img class="logoImg"
						src="<%=request.getContextPath()%>/resources/img/logo-nav.png">



					<div class="leftDiv">


						<div id="mainDiv" class="inputDiv1">

							<form name="regForm" id="regForm" method="post"
								action="RegisterUsers">
								<fieldset id="fs1">
									<legend>Profile</legend>
									<div class="sepDiv"></div>
									<div class="inputStyle">
										<div class="inputspan1">First Name :</div>
										<div class="inputspan2">
											<input type="text" name="firstName" id="firstName"
												value="<%=user.getFirstName()%>" class="inputDesign">
										</div>
									</div>


									<div class="sepDiv"></div>
									<div class="inputStyle">
										<div class="inputspan1">Last Name :</div>
										<div class="inputspan2">
											<input type="text" name="lastName" id="lastName"
												value="<%=user.getLastName()%>" class="inputDesign">
										</div>
									</div>

									<div class="sepDiv"></div>
									<div class="inputStyle">
										<div class="inputspan1">Address 1 :</div>
										<div class="inputspan2">
											<input type="text" name="address1" id="address1"
												value="<%=user.getAddress1()%>" class="inputDesign">
										</div>
									</div>
									<div class="sepDiv"></div>
									<div class="inputStyle">
										<div class="inputspan1">Address 2 :</div>
										<div class="inputspan2">
											<input type="text" name="address2" id="address2"
												value="<%=user.getAddress2() == null ? "" : user.getAddress2()%>"
												class="inputDesign">
										</div>
									</div>
									<div class="sepDiv"></div>
									<div class="inputStyle">
										<div class="inputspan1">Zip Code :</div>
										<div class="inputspan2">
											<input type="text" name="zipCode" id="zipCode"
												value="<%=user.getZipCode()%>" class="inputDesign"
												onchange="getLocation()">
										</div>
									</div>
									<div class="sepDiv"></div>
									<div class="inputStyle">
										<div class="inputspan1">City :</div>
										<div class="inputspan2">
											<input type="text" name="city" id="city"
												value="<%=user.getCity()%>" class="inputDesign">
										</div>
									</div>

									<div class="sepDiv"></div>
									<div class="inputStyle">
										<div class="inputspan1">State :</div>
										<div class="inputspan2">
											<input type="text" name="state" id="state"
												value="<%=user.getState()%>" class="inputDesign">
										</div>
									</div>


									<div class="sepDiv"></div>
									<div class="inputStyle">
										<div class="inputspan1">Country :</div>
										<div class="inputspan2">
											<input type="text" name="country" id="country"
												value="<%=user.getCountry()%>" class="inputDesign">
										</div>
									</div>
									<div class="sepDiv"></div>
								</fieldset>
							</form>
						</div>
					</div>

					<div class="banner">
						<div id="msgdiv1" class="leftDivBottom"><%=user.getMsg()%></div>
					</div>
				</div></li>
		</ul>
	</div>

</body>
</html>