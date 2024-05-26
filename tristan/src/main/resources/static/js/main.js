var eventListenerNameInput = false;
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
	if (!eventListenerNameInput) {
		eventListenerNameInput = true;
		handleInput('#name', 'input', nameCheckInput, 'isNameValid');
	}
	return nameCheckInput();
}

function nameCheckInput() {
	let validations = [
		!checkPattern('#name-underscore', new RegExp("^(?=.*^_|.*_$|.*__).*$")),
		!checkPattern('#name-dash', new RegExp("^(?=.*^-|.*-$|.*--).*$")),
		!checkPattern('#name-special', new RegExp("[^a-zA-Z\u00C0-\u00FF0-9_-]")),
		checkLength('#name-length', 5 , 25)
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
			checkLength('#pass-length', 12, 30)
		];
		return validations.every(validation => validation);
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

function checkLength(selector, minLength, maxLength) {
	let id = selector.split('-')[0];
	let length = $(id).val().length;
	if (length >= minLength && length <= maxLength) {
		hideText(selector);
		return true;
	} else {
		showText(selector);
		return false;
	}
}

function checkPattern(selector, pattern) {
	let id = selector.split('-')[0];
	return toggleText(selector, pattern.test($(id).val()), id === '#pass');
}

function toggleText(selector, isValid, hideIfValid) {
	if (isValid) {
		hideIfValid ? hideText(selector) : showText(selector);
	} else {
		hideIfValid ? showText(selector) : hideText(selector);
	}
	return isValid
}

function hideText(element) {
	$(element).hide();
}

function showText(element) {
	$(element).css('color', 'black').show();
}