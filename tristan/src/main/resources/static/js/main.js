let validationFlags = {
	isNameValid: false,
	isEmailValid: false,
	isPassValid: false,
	doPassMatch: false
};

function handleInput(selector, on, checkFunction, key) {
	$(selector).on(on, function() {
		validationFlags[key] = checkFunction();
		validator();
	});
}

const inputEvents = [
	{ selector: '#name', on:'blur', checkFunction: nameCheck, key: 'isNameValid' },
	{ selector: '#email', on:'input', checkFunction: emailCheck, key: 'isEmailValid' },
	{ selector: '#pass', on:'input', checkFunction: passCheck, key: 'isPassValid' },
	{ selector: '#re-pass', on:'input', checkFunction: passwordMatch, key: 'doPassMatch' }
];

inputEvents.forEach(event => {
	handleInput(event.selector, event.on, event.checkFunction, event.key);
});

function validator() {
	const allValid = Object.values(validationFlags).every(value => value === true);
	$('#signup').prop('disabled', !allValid);
}

function nameCheck() {
	handleInput('#name', 'input', nameCheckInput, 'isNameValid');
}

function nameCheckInput() {
	let validations = [
		checkPattern('#name-underscore', new RegExp("^(?=.*^_|.*_$|.*__).*$")),
		checkPattern('#name-dash', new RegExp("^(?=.*^-|.*-$|.*--).*$")),
		checkPattern('#name-special', new RegExp("[^a-zA-Z\u00C0-\u00FF0-9_-]")),
		nameLength()
	];
	return validations.every(validation => validation);
}

function emailCheck() {
	return true;
}

function passCheck() {
		let validations = [
			checkPattern('#pass-digit', new RegExp("^.*[0-9].*$")),
			checkPattern('#pass-lowercase', new RegExp("^.*[a-z].*$")),
			checkPattern('#pass-uppercase', new RegExp("^.*[A-Z].*$")),
			passLength()
		];
		return validations.every(validation => validation);
}

function nameLength() {
	let selector = '#name-length';
	if ($('#name').val().length >= 5 && $('#name').val().length <= 25) {
	    hideText(selector);
	    return true;
	} else {
        showText(selector);
        return false;
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

function passLength() {
	let selector = '#pass-length';
	if ($('#pass').val().length >= 12 && $('#pass').val().length <= 30) {
	    hideText(selector);
	    return true;
	} else {
		showText(selector);
        return false;
	}
}

function checkPattern(selector, pattern) {
	let id = selector.split('-')[0];
	if (pattern.test($(id).val())) {
		console.log(true);
		if (id === '#pass') {
			hideText(selector);
		} else {
			showText(selector);
		}
		return true;
	} else {
		console.log(false);
		if (id === '#pass') {
			showText(selector);
		} else {
			hideText(selector);
		}
		return false;
	}
}

function hideText(element) {
	$(element).hide();
}

function showText(element) {
	$(element).css('color', 'black').show();
}