function showDiv(ele) {

	var srcElement = document.getElementById(ele);

	if (srcElement != null) {
		if (srcElement.style.display == "block") {
			srcElement.style.display = 'none';
		} else {
			srcElement.style.display = 'block';
		}
		return false;
	}
}

function mostrarDiv(ele) {
	var srcElement = document.getElementById(ele);
	srcElement.style.display = 'block';
}

function ocultarDiv(ele) {
	var srcElement = document.getElementById(ele);
	srcElement.style.display = 'none';
}

function nextFocus(elementName) {

	// reg = /\d/;
	// num = reg.exec(elementName);
	// if (num) {
	// elementName = elementName.replace(num, parseInt(num) + 1);
	// }

	element = document.getElementById(elementName);

	if (element != null) {
		element.focus();
	}
}