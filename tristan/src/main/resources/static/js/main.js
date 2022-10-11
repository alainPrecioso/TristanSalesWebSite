var bool= false;




var passCheck = function() {
	passwordMatch();
	if ($('#pass').val().length >= 6 || bool) {
		bool=true;
		regexDigit();
		regexLowercase();
		regexUppercase();
		regexLength();
	}
}

function passwordMatch() {
	if ($('#pass').val() == $('#re-pass').val()) {
	    $('#check-message').css('color', 'green');
    	$('#check-message').text($('#passcheck-match').val());
	} else {
	    $('#check-message').css('color', 'red');
    	$('#check-message').text($('#passcheck-no-match').val());
	}
}

	
function regexDigit() {
	var digit = new RegExp("^.*[0-9].*$");

	if (digit.test($('#pass').val())) {
		$('#regex-digit-display').text('');
	} else {
		$('#regex-digit-display').css('color', 'black');
		$('#regex-digit-display').text($('#regex-digit').val());
	}
}

function regexLowercase() {
	var digit = new RegExp("^.*[a-z].*$");

	if (digit.test($('#pass').val())) {
		$('#regex-lowercase-display').text('');
	} else {
		$('#regex-lowercase-display').css('color', 'black');
		$('#regex-lowercase-display').text($('#regex-lowercase').val());
	}
}

function regexUppercase() {
	var digit = new RegExp("^.*[A-Z].*$");

	if (digit.test($('#pass').val())) {
	$('#regex-uppercase-display').text('');
	} else {
	    $('#regex-uppercase-display').css('color', 'black');
        $('#regex-uppercase-display').text($('#regex-uppercase').val());
	}
}

function regexLength() {
	if ($('#pass').val().length >= 12 && $('#pass').val().length <= 30) {
	    $('#regex-length-display').text('');
	} else {
	    $('#regex-length-display').css('color', 'black');
        $('#regex-length-display').text($('#regex-length').val());
	}
}