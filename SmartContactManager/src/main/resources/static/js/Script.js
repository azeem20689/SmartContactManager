
function registerUser() {
	console.log("This is Script File")
	const isChecked = $("#agreement").prop("checked");
	if (isChecked) {
		const formData = new FormData(document.getElementById("registration-form"));
		console.log("FORM DATA ----------" + formData)
		$.ajax({
			url: "/registerUser",
			type: "post",
			contentType: false,
			processData: false,
			data: formData,
			success: function(result) {
				alert(result)
			}

		});

	} else {
		alert("Pleaes agree to Terms & Conditions ..!!")
	}

}

/*function registerUser() {

	const isChecked = $("#agreement").prop("checked");
	if (isChecked) {
		const formData = {
			name: $("#name").val(),
			email: $("#email").val(),
			password: $("#password").val(),
			role: "",
			enabel: "",
			imageUrl: "",
			about: $("#about").val()
		}
		console.log(formData)
		$.ajax({
			url: "/registerUser",
			type: "post",
			contentType: 'application/json; charset=UTF-8',
			data: JSON.stringify(formData),
			success: function(result) {
				alert(result)
			}

		});

	} else {
		alert("Pleaes agree to Terms & Conditions ..!!")
	}

}*/


function clearFeilds() {

	$("#name").val('');
	$("#email").val('');
	$("#password").val('');
	$("#about").val('');
	$("#agreement").prop("checked", false);
}


const toggelfunction = () => {
	if ($(".sidebar").is(":visible")) {
		$(".sidebar").css("display", "none")
		$(".content").css("margin-left", "0%")

	} else {
		$(".sidebar").css("display", "block")
		$(".content").css("margin-left", "20%")
	}
}


function stripHtmlTags(html) {
	const temp = document.createElement("div");
	temp.innerHTML = html;
	return temp.textContent || temp.innerText || "";
}

function saveContact() {
	// Get the TinyMCE editor instance and get the content
	const editor = tinymce.get('description');
	const descriptionContent = editor.getContent();

	// Strip HTML tags from the content
	const plainTextContent = stripHtmlTags(descriptionContent);

	// Update the textarea element's value with the stripped content
	const descriptionTextarea = document.getElementById('description');
	descriptionTextarea.value = plainTextContent;

	// Proceed with form submission
	const formData = new FormData(document.getElementById("contact-form"));
	console.log(formData);
	$.ajax({
		url: "/user/process-contact",
		type: "post",
		processData: false, // Don't process the data
		contentType: false, // Don't set content type (let the browser set it)
		data: formData,
		success: function(result) {
			alert(result);
		},
		error: function(error) {
			console.error(error);
		}
	});
}


function sendEmail() {
	// Get the TinyMCE editor instance and get the content
	const editor = tinymce.get('description');
	const descriptionContent = editor.getContent();

	// Strip HTML tags from the content
	const plainTextContent = stripHtmlTags(descriptionContent);
	
	formData = {
		to : $("#to").val(),
		from : $("#from").val(),
		subject : $("#subject").val(),
		message : plainTextContent
	}
	
	$.ajax({
		url: "/user/sendEmail",
		type:"post",
		contentType : "application/json",
		data : JSON.stringify(formData),
		success : function(result){
		swal("Congratulations !", "Email sent  Successfull!", "success");
		}, error : function (error){
			swal("Error !", "Something went wrong!"+error, "error");
		}
		
	})
}


function deleteContact(id) {
	window.location.href = "/user/deleteContact/" + id;
	swal("Cotact deleted !", "You clicked the button!", "success");
}

function updateContact(id) {
	window.location.href = "/user/updateContact/" + id;
}

function saveUpdatedContact() {
	const formData = new FormData(document.getElementById("contact-update-form"));
	console.log(formData);
	$.ajax({
		url: "/user/saveUpdatedContact",
		type: "post",
		processData: false, // Don't process the data
		contentType: false, // Don't set content type (let the browser set it)
		data: formData,
		success: function(result) {
			alert(result);
		},
		error: function(error) {
			console.error(error);
		}
	});
}

const search = () => {
	// let querry = document.getElementById("search-input")
	let querry = $("#search-input").val();
	if (querry == '') {
		$(".search-result").css("display", "none");
	} else {
		// console.log(querry);
		let url = `http://localhost:8080/user/search/${querry}`;
		fetch(url)
			.then((response) => {
				return response.json();
			}).then((e) => {
				let text = `<div class="list-group">`;
				e.forEach((el) => {
					console.log(el.cId, el.name, el);
					text += `<a href="/user/contactDetails/+${el.cId}" class="list-group-item list-group-item-action">${el.name}</a>`;
				});
				text += `</div>`;
				$(".search-result").html(text);
			})

		$(".search-result").css("display", "block");
	}
}



function paymentStart() {
	let amount = $("#amount").val();
	if (amount == '' || amount == null) {
		swal("Error !", "Amount can't be null!", "error");
	}
	$.ajax({
		url: "/user/createOrder",
		type: "post",
		data: JSON.stringify(parseInt(amount)),
		//data : JSON.stringify({amount:amount, info : "order_request"} ),
		contentType: "application/json",
		dataType: "json",
		success: function(response) {
			console.log(response)
			if (response.status == "created") {
				var options = {
					"key": "rzp_test_Z3A6v1cb8caRQp",
					"amount": response.amount,
					"currency": "INR",
					"name": "Smart Contact Manager ",
					"description": "Test Transaction",
					"image": "https://as2.ftcdn.net/v2/jpg/03/83/80/93/1000_F_383809316_N4vGOGjc5FLoZV8NAzVRWnMRbULG923M.jpg",
					"order_id": response.id,
					"handler": function(response) {
						console.log(response.razorpay_payment_id);
						console.log(response.razorpay_order_id);
						console.log(response.razorpay_signature)
						swal("Congratulations !", "Payment Successfull!", "success");
					},
					"prefill": {
						"name": "Smart Contact Manager",
						"email": "scm@email.com",
						"contact": "7011272985"
					},
					"notes": {
						"address": "SMART CONTACT MANAGER"

					},
					"theme": {
						"color": "#3399cc"
					}
				};
				var rzp1 = new Razorpay(options);
				rzp1.on('payment.failed', function(response) {
					console.log(response.error.description);
					console.log(response.error.code);
					console.log(response.error.source);
					console.log(response.error.step);
					console.log(response.error.reason);
					console.log(response.error.metadata.order_id);
					console.log(response.error.metadata.payment_id);
					swal("Something went wrong !", "Payment Failed!", "error");
				});
				rzp1.open();
			}


		},

		error: function(error) {
			console.log(error)
			swal("Error !", "Something went wrong!", "error");
		}
	})
}


