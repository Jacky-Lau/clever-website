$(document).ready(function() {
	$('#searchDiv').on('click', '#searchButton', function() {
		$.ajax({
			type: "post",
			url: "/clever-website/serviceReconfigure",
			dataType: "json",
			processData: false,  // tell jQuery not to process the data
			contentType: false,  // tell jQuery not to set contentType
		}).done(function(data) {
		    window.location.href = "/clever-website/serviceControl";
		}).fail(function(jqXHR, textStatus) {
		});
	});
});
