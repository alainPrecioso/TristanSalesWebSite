let checkingPass= false;
let checkingName= false;

let validationFlags = {
	isNameValid: false,
	isEmailValid: true,
	isPassValid: false,
	doPassMatch: false
};

function handleInput(selector, checkFunction, key) {
	$(selector).on('input', function() {
		validationFlags[key] = checkFunction();
		validator();
	});
}

const inputEvents = [
	{ selector: '#name', checkFunction: nameCheck, key: 'isNameValid' },
	{ selector: '#email', checkFunction: emailCheck, key: 'isEmailValid' },
	{ selector: '#pass', checkFunction: passCheck, key: 'isPassValid' },
	{ selector: '#re-pass', checkFunction: passwordMatch, key: 'doPassMatch' }
];

inputEvents.forEach(event => {
	handleInput(event.selector, event.checkFunction, event.key);
});

function validator() {
	const allValid = Object.values(validationFlags).every(flag => flag);
	$('#signup').prop('disabled', !allValid);
}

function nameCheck() {
	if ($('#name').val().length >= 5 || checkingName) {
		let validations = [
			nameLength(),
			nameUnderscores(),
			nameDashes(),
			nameSpecialCharacters()
		];
		return validations.every(validation => validation);
	}
}

function emailCheck() {

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
		$('#name-underscores').css('color', 'black');
		$('#name-underscores').show();
		return false;
	} else {
		$('#name-underscores').hide();
		return true;
	}
	return false;
}

function nameDashes() {
	var dashes = new RegExp("^(?=.*^-|.*-$|.*--).*$");
	if (dashes.test($('#name').val())) {
		$('#name-dashes').css('color', 'black');
		$('#name-dashes').show();
		return false;
	} else {
		$('#name-dashes').hide();
		return true;
	}
	return false;
}

function nameSpecialCharacters() {
	var specialCharacters = new RegExp("[^a-zA-Z\u00C0-\u00FF0-9_-]");
	if (specialCharacters.test($('#name').val())) {
		$('#name-special').css('color', 'black');
		$('#name-special').show();
		return false;
	} else {
		$('#name-special').hide();
		return true;
	}
	return false;
}


function passCheck() {
	if ($('#pass').val().length >= 1 || checkingPass) {
	    checkingPass= true;
		let validations = [
			passDigit(),
			passLowercase(),
			passUppercase(),
			passLength()
		];
		return validations.every(validation => validation);
	}
}

function passwordMatch() {
	if ($('#pass').val() == $('#re-pass').val()) {
	    $('#passmatch-message').css('color', 'green');
    	$('#passmatch-message').text($('#passmatch-message').attr('match'));
		return true;
	} else {
	    $('#passmatch-message').css('color', 'red');
    	$('#passmatch-message').text($('#passmatch-message').attr('nomatch'));
		return false;
	}
}


function passDigit() {
	var digit = new RegExp("^.*[0-9].*$");

	if (digit.test($('#pass').val())) {
		$('#pass-digit').hide();
		return true;
	} else {
		$('#pass-digit').css('color', 'black');
		$('#pass-digit').show();
		return false;
	}
	return false;
}

function passLowercase() {
	var lowercase = new RegExp("^.*[a-z].*$");

	if (lowercase.test($('#pass').val())) {
	    //$('#pass-lowercase-display').text('');
		$('#pass-lowercase').hide();
		return true;
	} else {
	    $('#pass-lowercase').css('color', 'black');
		$('#pass-lowercase').show();
		return false;
	}
	return false;
}

function passUppercase() {
	var uppercase = new RegExp("^.*[A-Z].*$");

	if (uppercase.test($('#pass').val())) {
	    $('#pass-uppercase').hide();
	    return true;
	} else {
	    $('#pass-uppercase').css('color', 'black');
        $('#pass-uppercase').show();
        return false;
	}
	return false;
}

function passLength() {
	if ($('#pass').val().length >= 12 && $('#pass').val().length <= 30) {
	    $('#pass-length').hide();
	    return true;
	} else {
	    $('#pass-length').css('color', 'black');
        $('#pass-length').show();
        return false;
	}
	return false;
}