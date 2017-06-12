function getinput () {
	var key = document.getElementById("inputkey");
	var text = document.getElementById("originaltext").files;

	var stop_word_checkbox = document.getElementById("stopword").checked;
	var symbol_checkbox = document.getElementById("symbol").checked;
	var stemming_checkbox = document.getElementById("stemming").checked;
	var tagger_checkbox = document.getElementById("tagger").checked;

    // check key and file
	if ( key.value == '' || key.value == promptstring ) {
		alert("The key field is must");
		return;
	}
    if ( ! text.length ) {
        var text_name = "";
	    //alert("The file field is must if you choice to upload a text!");
        //return;
    }else{
        var text_name = text[0].name;
    }
    // start loading
	var loading_overlay = document.createElement('div');
	var loading_message = document.createElement('div');

	loading_overlay.id="loading-overlay";
	loading_message.id="loading-message";

	var message = document.createTextNode('Building corpora...');

	loading_message.appendChild(message);

	document.body.appendChild(loading_overlay);
	document.body.appendChild(loading_message);

    //start processing
	httpRequest = makeXMLRequest();
	var stringkey = key.value.replace(/\,/g, "%2C").replace(/\ /g, "");
    var submiturl= "/cgi-bin/getstringvalue.cgi?key=" + stringkey + "&text_name=" + text_name + "&stop_word=" + stop_word_checkbox + "&stemming=" + stemming_checkbox + "&symbol=" + symbol_checkbox + "&tagger=" + tagger_checkbox;
    
    httpRequest.onreadystatechange = function() { 
        if (httpRequest.readyState == 4 && httpRequest.status == 200) {
            document.getElementById("show").innerHTML = httpRequest.responseText;

            var js = document.createElement("script");

            js.type = "text/javascript";
            js.src = "resources/js/table.js";

            document.getElementById("show").appendChild(js);

            unlockscreen();
        };
    };

    httpRequest.open("GET", submiturl, true);
    httpRequest.send(null);
};

function uploadFile(){
	var text = document.getElementById("originaltext").files;
    var reader = new FileReader();

    var loading_overlay = document.createElement('div');
	var loading_message = document.createElement('div');

	loading_overlay.id="loading-overlay";
	loading_message.id="loading-message";

	var message = document.createTextNode('Upload the text file...');

	loading_message.appendChild(message);

	document.body.appendChild(loading_overlay);
	document.body.appendChild(loading_message);

	httpRequest = makeXMLRequest();
    reader.onloadend = function() {
        httpRequest.onreadystatechange = function() { 
            if (httpRequest.readyState == 4 && httpRequest.status == 200) {
                unlockscreen();
            };
        };
        httpRequest.open("POST", "/cgi-bin/getfile.cgi?text_name=" + text[0].name, true); 
        httpRequest.send(reader.result); 
    };
    reader.readAsText(text[0], 'UTF-8');
}

