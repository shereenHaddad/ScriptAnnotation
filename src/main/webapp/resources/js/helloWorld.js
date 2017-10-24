var screenWidth = screen.width;
var screenHeight = screen.height;

var displayWidth = (screenWidth * 9 / 10) + "px";
var displayHeight = (screenHeight * 9 / 10) + "px";
var displayMargin = (screenWidth / 20) + "px";
var dialogHeight = (screenHeight * 6 / 10) + "px";

var zip1_regex = /^\d{5}(?:[-\s]\d{4})?$/; // 
var username_regex = /^[a-zA-Z]+$/;
var address_regex = /^[a-zA-Z\s\d\/]*\d[a-zA-Z\s\d\/]*$/;
var city_regex = /^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$/;
// var margin = (screenWidth * 1 / 20) + "px";
function p1Init(s) {

	$(".tabs").width(displayWidth);
	$(".tabs").css('left', '10px');
	$(".tab-content").height(dialogHeight);
	$(".error1").hide();

	if (s != "") {
		$("#state").val(s);
	}

	

}

function getAgent() {

	$.ajax({
		url : 'GetUserAgent',
		type : "Get",
		data : $("#agentForm").serialize(),

		success : function(data) {
			if (data == "Mobile") {
				//$("#mainDiv").removeClass("leftDiv");
			//	$("#mainDiv").addClass("mobile");
				$('body > :not(#mainDiv)').hide();
				$('body').append("<div align='center' ><h2>Registration Form - Hello World</h2></div>");
				$("#msgdiv1").show();
				$("#msgdiv1").removeClass('leftDivBottom');
				$("#msgdiv1").removeClass('leftDivBottom2');
				$("#msgdiv1").addClass('mobile');
				$('#mainDiv').show();
				$('#mainDiv').appendTo('body');
				$("#msgdiv1").appendTo('body');
			}
		},
		error : function(jqXHR, exception) {
			// $("#msgdiv1").html('failed to initialize ' + jqXHR.status);
			// $('#showLoading').hide();
		}
	});
}

function validateAndSubmit() {
	var msg = "";
	$("#msgdiv1").text(msg);

	if ($.trim($("#firstName").val()) == "") {
		msg = "Please Fill in First Name";
		$("#msgdiv1").text(msg);
	} else if (!(username_regex.test($.trim($("#firstName").val())))) {
		msg = "First Name can only contain letters";
		$("#msgdiv1").text(msg);
	} else if ($.trim($("#lastName").val()) == "") {
		msg = "Please Fill in Last Name";
		$("#msgdiv1").text(msg);
	} else if (!(username_regex.test($.trim($("#lastName").val())))) {
		msg = "Last Name can only contain letters";
		$("#msgdiv1").text(msg);
	} else if ($.trim($("#address1").val()) == "") {
		msg = "Please Fill in Address 1";
		$("#msgdiv1").text(msg);
	} else if (!(address_regex.test($.trim($("#address1").val())))) {
		msg = "Address 1 Format is not Valid (must contain at least one digit and one letters)";
		$("#msgdiv1").text(msg);
	} else if (!$.trim($("#address2").val()) == ""
			&& !(address_regex.test($.trim($("#address2").val())))) {
		msg = "Address 2 Format is not Valid (must contain at least one digit and two letters)";
		$("#msgdiv1").text(msg);
	} else if ($.trim($("#zipCode").val()) == "") {
		msg = "Please Fill in Zip Code";
		$("#msgdiv1").text(msg);
	} else if ($.trim($("#city").val()) == "") {
		msg = "Please Fill in City";
		$("#msgdiv1").text(msg);
	} else if (!city_regex.test($.trim($("#city").val()))) {
		msg = "Invalid city Format (letters - and spaces )";
		$("#msgdiv1").text(msg);
	} else if ($.trim($("#state").val()) == "") {
		msg = "Please Select a State";
		$("#msgdiv1").text(msg);
	} else if ($.trim($("#country").val()) == "") {
		msg = "Please Select a Country";
		$("#msgdiv1").text(msg);
	} else if (!zip1_regex.test($.trim($("#zipCode").val()))) {
		msg = "Invalid Zip Code Format";
		$("#msgdiv1").text(msg);
	} else if ($.trim($("#country").val()) != "United States") {

		msg = "Registration is only available in US";
		$("#msgdiv1").text(msg);

	} else { // msg = "All Fields are filled" ;
		// $("#msgdiv1").text(msg);
		return true;
	}

	return false;
}

function getLocation() {
	getAddressInfoByZip($("#zipCode").val());
}

function response(obj) {
	console.log(obj);
}
function getAddressInfoByZip(zip) {
	if (zip.length >= 5 && typeof google != 'undefined') {
		var addr = {};
		var geocoder = new google.maps.Geocoder();
		geocoder
				.geocode(
						{
							'address' : zip
						},
						function(results, status) {
							if (status == google.maps.GeocoderStatus.OK) {
								if (results.length >= 1) {
									for (var ii = 0; ii < results[0].address_components.length; ii++) {
										var street_number = route = street = city = state = zipcode = country = formatted_address = '';
										var types = results[0].address_components[ii].types
												.join(",");
										if (types == "street_number") {
											addr.street_number = results[0].address_components[ii].long_name;
										}
										if (types == "route"
												|| types == "point_of_interest,establishment") {
											addr.route = results[0].address_components[ii].long_name;
										}
										if (types == "sublocality,political"
												|| types == "locality,political"
												|| types == "neighborhood,political"
												|| types == "administrative_area_level_3,political") {
											addr.city = (city == '' || types == "locality,political") ? results[0].address_components[ii].long_name
													: city;
										}
										if (types == "administrative_area_level_1,political") {
											addr.state = results[0].address_components[ii].short_name;
										}
										if (types == "postal_code"
												|| types == "postal_code_prefix,postal_code") {
											addr.zipcode = results[0].address_components[ii].long_name;
										}
										if (types == "country,political") {
											addr.country = results[0].address_components[ii].long_name;

										}
									}
									addr.success = true;
									$("#country").val(addr.country);
									$("#state").val(addr.state);
									$("#city").val(addr.city);

									// // for (name in addr){
									// console.log('### google maps api ### ' +
									// name + ': ' + addr[name] );
									// }
									response(addr);
								} else {
									response({
										success : false
									});
								}
							} else {
								response({
									success : false
								});
							}
						});
	} else {
		response({
			success : false
		});
	}
}