var checkingPass= false;
var checkingName= false;

var validator = function() {
	nameCheck();
	passCheck();
	if (nameCheck() && passCheck()) {
		$('#signup').prop('disabled', false);
	} else {
		$('#signup').prop('disabled', true);
	}
}

function nameCheck() {
	if ($('#name').val().length >= 5 || checkingName) {
		nameLength();
		nameUnderscores();
		nameDashes();
		nameSpecialCharacters();

		if (nameLength() && nameUnderscores() && nameDashes() && nameSpecialCharacters()) {
			return true;
		} else {
			return false;
		}
	}
	return false;
}

function nameLength() {
	if ($('#name').val().length >= 5 && $('#name').val().length <= 25) {
	    $('#name-length-display').text('');
	    return true;
	} else {
	    $('#name-length-display').css('color', 'black');
        $('#name-length-display').text($('#name-length').val());
        return false;
	}
	return false;
}

function nameUnderscores() {
	var underscores = new RegExp("^(?=.*^_|.*_$|.*__).*$");
	if (underscores.test($('#name').val())) {
		$('#name-underscores-display').css('color', 'black');
		$('#name-underscores-display').text($('#name-underscores').val());
		return false;
	} else {
		$('#name-underscores-display').text('');
		return true;
	}
	return false;
}

function nameDashes() {
	var dashes = new RegExp("^(?=.*^-|.*-$|.*--).*$");
	if (dashes.test($('#name').val())) {
		$('#name-dashes-display').css('color', 'black');
		$('#name-dashes-display').text($('#name-dashes').val());
		return false;
	} else {
		$('#name-dashes-display').text('');
		return true;
	}
	return false;
}

function nameSpecialCharacters() {
	var specialCharacters = new RegExp("[^a-zA-Z\u00C0-\u00FF0-9_-]");
	if (specialCharacters.test($('#name').val())) {
		$('#name-special-display').css('color', 'black');
		$('#name-special-display').text($('#name-special').val());
		return false;
	} else {
		$('#name-special-display').text('');
		return true;
	}
	return false;
}


function passCheck() {
	checkingName= true;
	if ($('#pass').val().length >= 6 || checkingPass) {
		checkingPass=true;
		passwordMatch();
		passDigit();
		passLowercase();
		passUppercase();
		passLength();
		if ( passwordMatch() && passDigit() && passLowercase() && passUppercase() && passLength() ) {
			return true;
	    } else {
			return false;
	    }
	}
	return false;
}

function passwordMatch() {
	if ($('#pass').val() == $('#re-pass').val()) {
	    $('#passmatch-message').css('color', 'green');
    	$('#passmatch-message').text($('#passmatch-match').val());
    	return true;
	} else {
	    $('#passmatch-message').css('color', 'red');
    	$('#passmatch-message').text($('#passmatch-no-match').val());
    	return false;
	}
	return false;
}

	
function passDigit() {
	var digit = new RegExp("^.*[0-9].*$");

	if (digit.test($('#pass').val())) {
		$('#pass-digit-display').text('');
		return true;
	} else {
		$('#pass-digit-display').css('color', 'black');
		$('#pass-digit-display').text($('#pass-digit').val());
		return false;
	}
	return false;
}

function passLowercase() {
	console.log("passLowercase"); 
	var lowercase = new RegExp("^.*[a-z].*$");

	if (lowercase.test($('#pass').val())) {
		$('#pass-lowercase-display').text('');
		return true;
	} else {
		$('#pass-lowercase-display').css('color', 'black');
		$('#pass-lowercase-display').text($('#pass-lowercase').val());
		return false;
	}
	return false;
}

function passUppercase() {
	var uppercase = new RegExp("^.*[A-Z].*$");

	if (uppercase.test($('#pass').val())) {
	    $('#pass-uppercase-display').text('');
	    return true;
	} else {
	    $('#pass-uppercase-display').css('color', 'black');
        $('#pass-uppercase-display').text($('#pass-uppercase').val());
        return false;
	}
	return false;
}

function passLength() {
	if ($('#pass').val().length >= 12 && $('#pass').val().length <= 30) {
	    $('#pass-length-display').text('');
	    return true;
	} else {
	    $('#pass-length-display').css('color', 'black');
        $('#pass-length-display').text($('#pass-length').val());
        return false;
	}
	return false;
}