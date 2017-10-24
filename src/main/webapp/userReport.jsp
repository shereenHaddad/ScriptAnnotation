<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.* , com.hello.world.beans.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello World Admin Report</title>


<!-- <script language="javascript" -->
<!-- 	src="https://maps.google.com/maps/api/js?sensor=false"></script> -->

<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.9.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/helloWorld.js"></script>



<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resources/css/style.css">
</head>
<body onload="getAgent();p1Init(''); ">
	<%
		List users= new ArrayList();;
	if(request.getAttribute("users")!=null){
		users =(ArrayList)request.getAttribute("users");
	};
	%>
	<div class="centered" id="page1">
		<h1>
			Registered Users Report <span>Hello World</span>
		</h1>

		<br>
		<ul class="tabs">



			<li class="rightFloat"><input type="radio" name="tabs" id="tab2"
				checked /> <label for="tab2">Registration Form</label>
				<div id="tab-content2" class="tab-content">
					<img class="logoImg"
						src="<%=request.getContextPath()%>/resources/img/logo-nav.png">


					
					<div class="leftDiv2">


						<div id="mainDiv" class="inputDiv1">

							<table class="reportTable"><tr><th width="10%">First Name</th><th width="10%">Last Name</th><th width="15%">Address1</th><th width="15%">Address2</th><th width="10%">city</th><th width="10%">state</th><th width="10%">zip</th><th width="10%">Country</th><th width="10%">Date</th></tr>
						
							  <tbody>
							<%
							Iterator it = users.iterator();
							while(it.hasNext()){
								
								User user = (User)it.next();
								%>
								<tr><td ><%= user.getFirstName() %></td><td ><%= user.getLastName() %></td><td><%= user.getAddress1() %></td><td><%= user.getAddress2()==null?"":user.getAddress2()  %></td><td><%= user.getCity() %></td><td><%= user.getState() %></td><td><%= user.getZipCode() %></td><td><%= user.getCountry()%></td><td><%= user.getRegDate() %></td></tr>
								<%
								
							}
							
							%>
							  </tbody>
							</table>
						</div>
					</div>

					<div class="banner">
						<div id="msgdiv1" class="leftDivBottom2"></div>
					</div>
				</div></li>
		</ul>
	</div>

</body>
</html>