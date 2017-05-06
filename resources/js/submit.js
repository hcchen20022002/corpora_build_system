function getinput () {
	var key = document.getElementById("inputkey");
	var text = document.getElementById("originaltext").files;
    // check key and file
	if ( key.value == '' || key.value == promptstring ) {
		alert("The key field is must");
		return;
	}
	if ( ! text.length ) {
	    alert("The file field is must if you choice to upload a text!");
        return;
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

	httpRequest = makeXMLRequest();
    //start processing
	var stringkey = key.value.replace(/\,/g, "%2C").replace(/\ /g, "%2C");

    //TODO
    uploadFile(text[0]);

    var submiturl= "/cgi-bin/getstringvalue.cgi?key=" + stringkey + "&text_name=" + text[0].name;

	httpRequest.onreadystatechange = function() { 
		if (httpRequest.readyState == 4 && httpRequest.status == 200) {
			document.getElementById("show").innerHTML = httpRequest.responseText;

			var js = document.createElement("script");

			js.type = "text/javascript";
			js.src = "resources/js/table.js";

			document.getElementById("show").appendChild(js);

			unlockscreen();
		}
	};

	httpRequest.open("GET", submiturl, true);
	httpRequest.send(null);
}

function uploadFile(file){
    var fd = new FormData(); 
    var reader = new FileReader();
    reader.onloadend = function() {
        var xhr = new XMLHttpRequest(); 
        xhr.open("POST", "testJsUpload.php"); 
        xhr.overrideMimeType("application/octet-stream"); 
        xhr.sendAsBinary(reader.result); 
    }
    reader.readAsBinaryString(file); 
}
