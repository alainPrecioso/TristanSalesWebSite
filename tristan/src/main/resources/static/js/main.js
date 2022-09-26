var bool= false;




var passCheck = function() {
	passwordMatch();
	if (document.getElementById('pass').value.length >= 6 || bool) {
		bool=true;
		regexDigit();
		regexLowercase();
		regexUppercase();
		regexLength();
	}
}

function passwordMatch() {
	if (document.getElementById('pass').value ==
		document.getElementById('re_pass').value) {
		document.getElementById('checkmessage').style.color = 'green';
		document.getElementById('checkmessage').innerHTML = document.getElementById('passcheckmatch').value;
	} else {
		document.getElementById('checkmessage').style.color = 'red';
		document.getElementById('checkmessage').innerHTML = document.getElementById('passchecknomatch').value;
	}
}

	
function regexDigit() {
	var digit = new RegExp("^.*[0-9].*$");

	if (digit.test(document.getElementById('pass').value)) {
		document.getElementById('regexdigitdisplay').innerHTML = '';
	} else {
		document.getElementById('regexdigitdisplay').style.color = 'black';
		document.getElementById('regexdigitdisplay').innerHTML = document.getElementById('regexdigit').value;
	}
}

function regexLowercase() {
	var digit = new RegExp("^.*[a-z].*$");

	if (digit.test(document.getElementById('pass').value)) {
		document.getElementById('regexlowercasedisplay').innerHTML = '';
	} else {
		document.getElementById('regexlowercasedisplay').style.color = 'black';
		document.getElementById('regexlowercasedisplay').innerHTML = document.getElementById('regexlowercase').value;
	}
}

function regexUppercase() {
	var digit = new RegExp("^.*[A-Z].*$");

	if (digit.test(document.getElementById('pass').value)) {
		document.getElementById('regexuppercasedisplay').innerHTML = '';
	} else {
		document.getElementById('regexuppercasedisplay').style.color = 'black';
		document.getElementById('regexuppercasedisplay').innerHTML = document.getElementById('regexuppercase').value;
	}
}

function regexLength() {
	if (document.getElementById('pass').value.length >= 12 && document.getElementById('pass').value.length <= 30) {
		document.getElementById('regexlengthdisplay').innerHTML = '';
	} else {
		document.getElementById('regexlengthdisplay').style.color = 'black';
		document.getElementById('regexlengthdisplay').innerHTML = document.getElementById('regexlength').value;
	}
}