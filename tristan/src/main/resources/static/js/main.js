var bool= false;




var passCheck = function() {
	if ($('#pass').val().length >= 6 || bool) {
		bool=true;
		if ( passwordMatch() && regexDigit() && regexLowercase() && regexUppercase() && regexLength() ) {
		    $('#signup').prop('disabled', false);
	    } else {
	        $('#signup').prop('disabled', true);
	}

	}
}

function passwordMatch() {
	if ($('#pass').val() == $('#re-pass').val()) {
	    $('#check-message').css('color', 'green');
    	$('#check-message').text($('#passcheck-match').val());
    	return true;
	} else {
	    $('#check-message').css('color', 'red');
    	$('#check-message').text($('#passcheck-no-match').val());
    	return false;
	}
}

	
function regexDigit() {
	var digit = new RegExp("^.*[0-9].*$");

	if (digit.test($('#pass').val())) {
		$('#regex-digit-display').text('');
		return true;
	} else {
		$('#regex-digit-display').css('color', 'black');
		$('#regex-digit-display').text($('#regex-digit').val());
		return false;
	}
}

function regexLowercase() {
	var digit = new RegExp("^.*[a-z].*$");

	if (digit.test($('#pass').val())) {
		$('#regex-lowercase-display').text('');
		return true;
	} else {
		$('#regex-lowercase-display').css('color', 'black');
		$('#regex-lowercase-display').text($('#regex-lowercase').val());
		return false;
	}
}

function regexUppercase() {
	var digit = new RegExp("^.*[A-Z].*$");

	if (digit.test($('#pass').val())) {
	    $('#regex-uppercase-display').text('');
	    return true;
	} else {
	    $('#regex-uppercase-display').css('color', 'black');
        $('#regex-uppercase-display').text($('#regex-uppercase').val());
        return false;
	}
}

function regexLength() {
	if ($('#pass').val().length >= 12 && $('#pass').val().length <= 30) {
	    $('#regex-length-display').text('');
	    return true;
	} else {
	    $('#regex-length-display').css('color', 'black');
        $('#regex-length-display').text($('#regex-length').val());
        return false;
	}
}