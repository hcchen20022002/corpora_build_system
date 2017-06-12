function reset_history () {
	httpRequest = makeXMLRequest();
    httpRequest.onreadystatechange = function() { 
        if (httpRequest.readyState == 4 && httpRequest.status == 200) {
            location.reload();
        };
    };
    httpRequest.open("GET", "/cgi-bin/reset_history.cgi", true); 
    httpRequest.send(null); 

};
