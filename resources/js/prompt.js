function addListener(element,e,fn) {    
	if(element.addEventListener) {    
		 element.addEventListener(e,fn,false);    
	}
	else {    
		element.attachEvent("on" + e,fn);    
	}    
}

var stringinput = document.getElementById("inputkey");
var resetbutton = document.getElementById("inputreset");
var stop_word_checkbox = document.getElementById("stopword");
var symbol_checkbox = document.getElementById("symbol");
var stemming_checkbox = document.getElementById("stemming");
var tagger_checkbox = document.getElementById("tagger");

promptstring = "Please use a comma or space to separate your multiple string key"

if (stringinput.value != promptstring) {
	stringinput.style = "color:#000000";
}


addListener(stringinput,"click",function() {
	if (stringinput.value == "" || stringinput.value == promptstring) {
	stringinput.style.color = "#000000";
	stringinput.value = "";
	}
});

addListener(stringinput,"focus",function() {
	if (stringinput.value == "" || stringinput.value == promptstring) {
	stringinput.style.color = "#000000";
	stringinput.value = "";
	}
});
addListener(stringinput,"blur",function() {
	if (stringinput.value == "" || stringinput.value == promptstring ) {
		stringinput.style.color = "#a9a9a9";
		stringinput.value = promptstring;
	}
});

addListener(resetbutton,"click",function() {
	stringinput.style.color = "#a9a9a9";
	stringinput.value = "";
    stop_word_checkbox.checked = false ;
    symbol_checkbox.checked = false ;
    stemming_checkbox.checked = false ;
    tagger_checkbox.checked = false ;

	document.getElementById("show").innerHTML = "";
});

/* addListener(selectedcategory, "click", function() {
    alert(selectedcategory.text);
    if (selectedcategory.value == 'upload'){
        var x = document.createElement("INPUT_FILE");
        x.setAttribute("type", "file");
        document.body.appendChild(x);
    }
});  */
