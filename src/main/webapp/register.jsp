<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.* , com.hello.world.beans.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello World Registration Form</title>


<script language="javascript"
	src="<%=request.getContextPath()%>/resources/js/googleGeo.js"></script>

<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.9.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/helloWorld.js"></script>


<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/style.css">
</head>
<%
	User user = new User();
	if (request.getAttribute("user") != null) {
		user = (User) request.getAttribute("user");
	}
%>
<script>var state ="<%=user.getState()%>";
</script>
<body onload="getAgent();p1Init(state);">
<form id="agentForm" ></form>
	<div class="centered" id="page1">
		<h1>
			Registration Form <span>Hello World</span>
		</h1>

		<br>
		<ul class="tabs">



			<li class="rightFloat"><input type="radio" name="tabs" id="tab2"
				checked /> <label for="tab2">Registration Form</label>
				<div id="tab-content2" class="tab-content">
					<img class="logoImg"
						src="<%=request.getContextPath()%>/resources/img/logo-nav.png">

					<div  class="leftDiv">


						<div  id="mainDiv" class="inputDiv1">

							<form name="regForm" id="regForm" method="post"
								action="RegisterUsers">

								<div class="sepDiv"></div>
								<div class="inputStyle">
									<div class="inputspan1">First Name :</div>
									<div class="inputspan2">
										<input type="text" name="firstName" id="firstName" maxlength="35"
											value="<%=user.getFirstName() == null ? "" : user.getFirstName()%>"
											class="inputDesign">
									</div>
								</div>


								<div class="sepDiv"></div>
								<div class="inputStyle">
									<div class="inputspan1">Last Name :</div>
									<div class="inputspan2">
										<input type="text" name="lastName" id="lastName" maxlength="35"
											value="<%=user.getLastName() == null ? "" : user.getLastName()%>"
											class="inputDesign">
									</div>
								</div>

								<div class="sepDiv"></div>
								<div class="inputStyle">
									<div class="inputspan1">Address 1 :</div>
									<div class="inputspan2">
										<input type="text" name="address1" id="address1" maxlength="50"
											value="<%=user.getAddress1() == null ? "" : user.getAddress1()%>"
											class="inputDesign">
									</div>
								</div>
								<div class="sepDiv"></div>
								<div class="inputStyle">
									<div class="inputspan1">Address 2 :</div>
									<div class="inputspan2">
										<input type="text" name="address2" id="address2" maxlength="50"
											value="<%=user.getAddress2() == null ? "" : user.getAddress2()%>"
											class="inputDesign">
									</div>
								</div>

								<div class="sepDiv"></div>
								<div class="inputStyle">
									<div class="inputspan1">Zip Code :</div>
									<div class="inputspan2">
										<input type="text" name="zipCode" id="zipCode" maxlength="10"
											value="<%=user.getZipCode() == null ? "" : user.getZipCode()%>"
											class="inputDesign" onchange="getLocation()">
									</div>
								</div>
								<div class="sepDiv"></div>
								<div class="inputStyle">
									<div class="inputspan1">City :</div>
									<div class="inputspan2">
										<input type="text" name="city" id="city" maxlength="35"
											value="<%=user.getCity() == null ? "" : user.getCity()%>"
											class="inputDesign">
									</div>
								</div>

								<div class="sepDiv"></div>
								<div class="inputStyle">
									<div class="inputspan1">State :</div>
									<div class="inputspan2">

										<select name="state" id="state" class="inputDesign2">
											<option value="">Please Select</option>
											<option value="AL">Alabama</option>
											<option value="AK">Alaska</option>
											<option value="AZ">Arizona</option>
											<option value="AR">Arkansas</option>
											<option value="CA">California</option>
											<option value="CO">Colorado</option>
											<option value="CT">Connecticut</option>
											<option value="DE">Delaware</option>
											<option value="DC">District Of Columbia</option>
											<option value="FL">Florida</option>
											<option value="GA">Georgia</option>
											<option value="HI">Hawaii</option>
											<option value="ID">Idaho</option>
											<option value="IL">Illinois</option>
											<option value="IN">Indiana</option>
											<option value="IA">Iowa</option>
											<option value="KS">Kansas</option>
											<option value="KY">Kentucky</option>
											<option value="LA">Louisiana</option>
											<option value="ME">Maine</option>
											<option value="MD">Maryland</option>
											<option value="MA">Massachusetts</option>
											<option value="MI">Michigan</option>
											<option value="MN">Minnesota</option>
											<option value="MS">Mississippi</option>
											<option value="MO">Missouri</option>
											<option value="MT">Montana</option>
											<option value="NE">Nebraska</option>
											<option value="NV">Nevada</option>
											<option value="NH">New Hampshire</option>
											<option value="NJ">New Jersey</option>
											<option value="NM">New Mexico</option>
											<option value="NY">New York</option>
											<option value="NC">North Carolina</option>
											<option value="ND">North Dakota</option>
											<option value="OH">Ohio</option>
											<option value="OK">Oklahoma</option>
											<option value="OR">Oregon</option>
											<option value="PA">Pennsylvania</option>
											<option value="RI">Rhode Island</option>
											<option value="SC">South Carolina</option>
											<option value="SD">South Dakota</option>
											<option value="TN">Tennessee</option>
											<option value="TX">Texas</option>
											<option value="UT">Utah</option>
											<option value="VT">Vermont</option>
											<option value="VA">Virginia</option>
											<option value="WA">Washington</option>
											<option value="WV">West Virginia</option>
											<option value="WI">Wisconsin</option>
											<option value="WY">Wyoming</option>
										</select>


									</div>
								</div>


								<div class="sepDiv"></div>
								<div class="inputStyle">
									<div class="inputspan1">Country :</div>
									<div class="inputspan2">
										<input type="text" name="country" id="country"
											value="<%=user.getCountry() == null ? "" : user.getCountry()%>"
											class="inputDesign" ReadOnly>
									</div>
								</div>
								<div class="sepDiv"></div>
								<div class="inputStyle">
									<input type="submit" class="buttonspan1"
										onclick="return validateAndSubmit();" value="Register">

								</div>

							</form>
						</div>
					</div>

					<div class="banner">
						<div id="msgdiv1" class="leftDivBottom"><%=(user.getMsg() == null ? "" : user.getMsg())%></div>
					</div>
				</div></li>
		</ul>
	</div>

</body>
</html>